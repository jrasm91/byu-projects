package hypeerweb.node;

import hypeerweb.HyPeerWeb;
import hypeerweb.HyPeerWebProxy;
import hypeerweb.SimplifiedNodeDomain;
import hypeerweb.visitor.Parameters;
import hypeerweb.visitor.Visitor;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import distributed.GlobalObjectId;
import distributed.ObjectDB;

/**
 * A node in the HyPeerWeb. <br>
 * <br>
 * 
 * <pre>
 * <b>Domain:</b>
 *      state			: NodeState
 *      connections		: Connections
 *      contents		: Contents
 * </pre>
 * 
 * @author Ben Romney
 */

public class Node implements Comparable<Node>, NodeInterface, Serializable {

	private static final long serialVersionUID = 7000175070161869134L;
	private NodeState state;
	private Connections connections;
	private Contents contents;
	protected GlobalObjectId globalObjectId;

	/**
	 * The one and only empty node.
	 */
	public static Node NULL_NODE = new NullNode();

	/**
	 * The default constructor.
	 * 
	 * @pre None.
	 * @post Constructs a new node.
	 */
	protected Node() {
		state = null;
		connections = new Connections();
		globalObjectId = new GlobalObjectId();
		ObjectDB.getSingleton().store(globalObjectId.getLocalObjectId(), this);
	}

	protected Node(String s){
		// do nothing
	}

	/**
	 * The constructor for a node given the new id and height.
	 * 
	 * @param id
	 *            - The ID for the new node.
	 * @pre None.
	 * @post Constructs a new node with the given id and height.
	 */
	public Node(final int id, final int height) {
		setConnections(new Connections(new WebId(id, height)));
		setState(NodeState.STANDARD);
		setFold(this);
		globalObjectId = new GlobalObjectId();
		ObjectDB.getSingleton().store(globalObjectId.getLocalObjectId(), this);
	}

	/**
	 * The constructor for a node given only the new id.
	 * 
	 * @param id
	 *            - The ID for the new node.
	 * @pre None.
	 * @post Constructs a new node with the given id.
	 */
	public Node(int id) {
		connections = new Connections(new WebId(id));
		setState(NodeState.STANDARD);
		setFold(this);
		globalObjectId = new GlobalObjectId();
		ObjectDB.getSingleton().store(globalObjectId.getLocalObjectId(), this);
	}

	@Override
	public SimplifiedNodeDomain constructSimplifiedNodeDomain() {
		SimplifiedNodeDomain snd = new SimplifiedNodeDomain(getWebId()
				.getValue(), getWebId().getHeight(), createSetOfNeighborIds(),
				createSetOfUpPointerIds(), createSetOfDownPointerIds(),
				getFold().getIdInt(), getSurrogateFold().getIdInt(),
				getInverseSurrogateFold().getIdInt(), state);
		//Start set if proxy or not!
		setIfProxy(getConnections().getFold(), snd);
		setIfProxy(getConnections().getSurrogateFold(), snd);
		setIfProxy(getConnections().getInverseSurrogateFold(), snd);
		for(Node n : getConnections().getNeighbors()) {
			setIfProxy(n, snd);
		}
		for(Node n : getConnections().getUpPointers()) {
			setIfProxy(n, snd);
		}
		for(Node n : getConnections().getDownPointers()) {
			setIfProxy(n, snd);
		}
		//And just in case...
		setIfProxy(this, snd);
		//End set proxies
		return snd;
	}

	private void setIfProxy(Node n, SimplifiedNodeDomain s) {
		assert(!(this instanceof NodeProxy));
		if (n.equals(Node.NULL_NODE)) {
			return;
		}
		if (n instanceof NodeProxy) {
			s.setGlobalIdFor(n.getIdInt(), n.getGlobalObjectId());
		}
		if(n.getIdInt() == s.getWebId()) {
			s.setLocalId(n.getGlobalObjectId().getLocalObjectId());
		}

	}

	@Override
	public void addToHyPeerWeb(Node newNode) {
		assert(!(this instanceof NodeProxy));
		Node terminalNode = findTerminalNode();
		Node insertionNode = terminalNode.findInsertionPoint(terminalNode);
		insertionNode.makeChild(newNode);
		newNode.getMyHyPeerWeb().addNode(newNode);
		this.getMyHyPeerWeb().addNode(newNode);
	}

	public HyPeerWeb getMyHyPeerWeb() {
		assert(!(this instanceof NodeProxy));
		return HyPeerWeb.getSingleton();
	}

	/**
	 * Sets up the new child node's connections, then notifies the its neighbors
	 * to update their own getConnections().
	 * 
	 * @param child
	 *            The new node being added to the HyPeerWeb
	 * @pre The method is being called on the insertion point node.
	 * @post The child's connections will be properly established.
	 */
	private void makeChild(Node child) {
		assert(!(this instanceof NodeProxy));
		child.setWebId(this.getWebId()
				.createChildsWebId());
//		WebId childid = this.getWebId().createChildsWebId();
//		System.out.println("child id: " + childid);
		for(Node d: getBiggerNeighbors()){
			child.getConnections().getDownPointers().clear();
			child.getConnections().addDownPointer(d);
		}
		child.setState(NodeState.TERMINAL);
		child.addNeighbor(this);
		getWebId().incrementHeight();
		for (Node neighbor : getUpPointers()) {
			child.addNeighbor(neighbor);
		}
		calculateFoldsAddNode(child);
		notifyNeighbors(child);
		this.getConnections().updateEveryoneChildList();
	}

	@Override
	public void removeFromHyPeerWeb() {
		assert(!(this instanceof NodeProxy));
		Node terminalNode = findTerminalNode();
		Node replacementNode = terminalNode.findDeletionPoint(terminalNode);
		HyPeerWeb.getSingleton().removeNode(replacementNode);
		HyPeerWeb hypeerweb = HyPeerWeb.getSingleton();
		HyPeerWeb.getSingleton().removeNode(this);
		removeChild(replacementNode);
	}

	/**
	 * Establishes new connections after node deletion.
	 * 
	 * @pre The method is called on the node to be deleted. The node being
	 *      passed in is the last node of the HyPeerWeb.
	 * @post Proper connections are established.
	 */
	private void removeChild(Node replacementNode) {
		assert(!(this instanceof NodeProxy));
		Node parentOfReplacement = replacementNode
				.getSmallestNeighborWithChild();
		parentOfReplacement.getWebId().decrementHeight();

		replacementNode.getConnections().removeMe(replacementNode);

		replacementNode.calculateFoldsRemoveNode();

		for (Node neighbor : replacementNode.getNeighbors()) {
			if (neighbor != parentOfReplacement) {
				neighbor.addDownPointer(parentOfReplacement);
				parentOfReplacement.addUpPointer(neighbor);
			}
		}
		parentOfReplacement.getConnections().updateEveryoneChildList();
		if (replacementNode != this) {
			replacementNode.setConnections(getConnections());
			replacementNode.setState(state);
			replacementNode.getConnections().removeMe(this);
			replacementNode.getConnections().addMe(replacementNode);
			replacementNode.setFold(replacementNode.getFold());
			HyPeerWeb.getSingleton().addNode(replacementNode);
		}
		/*
		 * if (replacementNode.getState() == NodeState.HYPEERCUBECAP) {
		 * replacementNode.setFold(replacementNode); }
		 */
	}

	/**
	 * Uses a recursive greedy algorithm to find the estimated terminal node.
	 * 
	 * @return The estimated terminal node.
	 * @pre Nodes in the HyPeerWeb have the proper states assigned to them.
	 * @post The Node returned is not necessarily the insertion point.
	 */
	private synchronized Node findTerminalNode() {
		if (getState() == NodeState.TERMINAL || getState() == NodeState.HYPEERCUBECAP) {
			return this;
		}
		return getBiggestNeighbor().findTerminalNode();
	}

	/**
	 * A recursive squeeze algorithm to find the insertion point for adding a
	 * node.
	 * 
	 * @param lowerBound
	 *            The lower bound of the possibility range.
	 * @param upperBound
	 *            The upper bound of the possibility range.
	 * @return The Node to be used as the insertion point.
	 * @pre Nodes in the HyPeerWeb have the proper states assigned to them.
	 * @post The Node returned is the insertion point.
	 */
	private synchronized Node findInsertionPoint(Node lowerBound) {
		return new FindNode(this) {
			@Override
			public Node getNode(Node lowerBound, Node upperBound) {
				return upperBound;
			}

			@Override
			public Node getHyPeerCubeCapCase(Node node) {
				return root.getFold();
			}
		}.execute(lowerBound, NULL_NODE);
	}

	/**
	 * Executes the squeeze algorithm to find the last node in the web.
	 * 
	 * @return The last node in the web.
	 * @pre lowerBound is the node found findTerminalNode().
	 * @post Returns the last node in the web.
	 */
	private synchronized Node findDeletionPoint(Node lowerBound) {
		return new FindNode(this) {
			@Override
			public Node getNode(Node lowerBound, Node upperBound) {
				return lowerBound;
			}

			@Override
			public Node getHyPeerCubeCapCase(Node node) {
				return root;
			}
		}.execute(lowerBound, NULL_NODE);
	}

	/**
	 * Checks to see if we are done finding the insertion point.
	 * 
	 * @param lowerBound
	 *            The lower bound of the possibility range.
	 * @param upperBound
	 *            The upper bound of the possibility range.
	 * @return True if upperBound is the insertion point, false otherwise.
	 * @pre None.
	 * @post Will not change any nodes or getConnections().
	 */
	private boolean isFindPoint(Node lowerBound, Node upperBound) {
		return lowerBound.getWebId().getValue() + 1 == upperBound.getWebId()
				.createChildsWebId().getValue();
	}

	/**
	 * The class that findInsertionPoint() and findDeletionPoint() delegate
	 * their algorithm to.
	 */
	private abstract class FindNode {
		protected Node root;

		public FindNode(Node root) {
			this.root = root;
		}

		public Node execute(Node lowerBound, Node upperBound) {
			if (isFindPoint(lowerBound, upperBound)) {
				return getNode(lowerBound, upperBound);
			}
			switch (root.getState()) {
			case HYPEERCUBECAP:
				return getHyPeerCubeCapCase(Node.this);
			case DOWNPOINTING:
			case TERMINAL:
				upperBound = lowerBound.getSmallestSurrogateNeighbor();
				root = upperBound;
				return execute(lowerBound, upperBound);
			case UPPOINTING:
				Node bisn = upperBound.getBiggestInverseSurrogateNeighbor();
				if (bisn.getIdInt() > lowerBound.getIdInt()) {
					lowerBound = bisn;
					root = lowerBound;
					return execute(lowerBound, upperBound);
				} else {
					upperBound = upperBound.getSmallestNeighborWithoutChild();
					root = upperBound;
					return execute(lowerBound, upperBound);
				}
			default:
				throw new ArrayIndexOutOfBoundsException(
						"\nNot a valid state: " + state);
			}
		};

		public abstract Node getNode(Node upperBound, Node lowerBound);

		public abstract Node getHyPeerCubeCapCase(Node node);
	};

	/**
	 * Updates the fold, surrogate fold, and inverse surrogate fold for the
	 * child, parent, and the parent's fold. Used when adding a node.
	 * 
	 * @param c
	 *            The child node.
	 */
	private void calculateFoldsAddNode(Node c) {
		assert(!(this instanceof NodeProxy));
		if (getState() == NodeState.HYPEERCUBECAP) {
			c.setFold(this);
			this.setFold(c);
		} else if (this.getInverseSurrogateFold() == NULL_NODE) {
			Node childX = c;
			Node parentX = this;
			Node parentY = this.getFold();
			childX.setFold(parentY);
			parentX.setFold(NULL_NODE);
			parentX.setSurrogateFold(parentY);
			parentY.setFold(childX);
			parentY.setInverseSurrogateFold(parentX);
		} else {
			Node childY = c;
			Node parentY = this;
			Node parentX = parentY.getInverseSurrogateFold();
			childY.setFold(parentX);
			parentY.setInverseSurrogateFold(NULL_NODE);
			parentX.setFold(childY);
			parentX.setSurrogateFold(NULL_NODE);
		}
	}

	/**
	 * Updates the folds, surrogate folds, and inverse surrogate folds. Used
	 * when deleting a node.
	 * 
	 * @pre Method is called on the deletion node?
	 * @post All requisite fold adjustments are made.
	 */
	private void calculateFoldsRemoveNode() {
		assert(!(this instanceof NodeProxy));
		if (getWebId().toString().equals("1")) {
			getFold().setFold(getFold());
		} else if (this.getFold().getWebId().getHeight() == this.getWebId()
				.getHeight()) {
			Node parentX = this.getFold();
			parentX.setSurrogateFold(this.getSmallestNeighborWithChild());
			getSmallestNeighborWithChild().setInverseSurrogateFold(parentX);

		} else {
			Node parentY = getFold();
			Node smallest = getSmallestNeighborWithChild();
			smallest.setFold(parentY);
			smallest.setInverseSurrogateFold(NULL_NODE);
			smallest.setSurrogateFold(NULL_NODE);
			parentY.setFold(smallest);
			parentY.setInverseSurrogateFold(NULL_NODE);
			parentY.setSurrogateFold(NULL_NODE);
		}
	}

	/**
	 * Updates the connections of the new node's neighbors to include the new
	 * node.
	 * 
	 * @param node
	 *            The new node.
	 */
	private void notifyNeighbors(Node node) {
		assert(!(this instanceof NodeProxy));
		node.getConnections().addMe(node);
		for (Node downPointer : node.getConnections().getDownPointers()) {
			downPointer.addUpPointer(node);
		}
		for (Node neighbor : node.getConnections().getNeighbors()) {
			this.removeUpPointer(neighbor);
			neighbor.addNeighbor(node);
			neighbor.removeDownPointer(this);
		}
		for (Node parentNeighbors : getNeighbors()) {
			parentNeighbors.removeNeighbor(this);
			parentNeighbors.addNeighbor(this);
		}
	}

	private Node getFold() {
		return getConnections().getFold();
	}

	private Node getSurrogateFold() {
		assert(!(this instanceof NodeProxy));
		return getConnections().getSurrogateFold();
	}

	private Node getInverseSurrogateFold() {
		assert(!(this instanceof NodeProxy));
		return getConnections().getInverseSurrogateFold();
	}

	private Node getSmallestSurrogateNeighbor() {
		return getConnections().getSmallestSurrogateNeighbor();
	}

	private Node getBiggestInverseSurrogateNeighbor() {
		return getConnections().getBiggestInverseSurrogateNeighbor();
	}

	private Node getSmallestNeighborWithoutChild() {
		return getConnections().getSmallestNeighborWithoutChild();
	}

	/**
	 * @obvious
	 */
	public Node getSmallestNeighborWithChild() {
		return getConnections().getSmallestNeighborWithChild();
	}

	private Node getBiggestNeighbor() {
		return getConnections().getBiggestNeighbor();
	}

	private Set<Node> getBiggerNeighbors() {
		return getConnections().getBiggerNeighbors();
	}

	private Set<Node> getUpPointers() {
		assert(!(this instanceof NodeProxy));
		return getConnections().getUpPointers();
	}

	private Set<Node> getNeighbors() {
		assert(!(this instanceof NodeProxy));
		return getConnections().getNeighbors();
	}

	@Override
	public void addNeighbor(Node neighbor) {
		assert(!(this instanceof NodeProxy));
		getConnections().addNeighbor(neighbor);
		if (!getConnections().getBiggerNeighbors().isEmpty()
				&& state == NodeState.TERMINAL) {
			setState(NodeState.DOWNPOINTING);
		}
	}

	@Override
	public void removeNeighbor(Node neighbor) {
		assert(!(this instanceof NodeProxy));
		getConnections().removeNeighbor(neighbor);
	}

	@Override
	public void addUpPointer(Node upPointer) {
		assert(!(this instanceof NodeProxy));
		getConnections().addUpPointer(upPointer);
		setState(NodeState.UPPOINTING);
	}

	@Override
	public void removeUpPointer(Node upPointer) {
		assert(!(this instanceof NodeProxy));
		getConnections().removeUpPointer(upPointer);
		if (getConnections().getUpPointers().isEmpty()) {
			setState(NodeState.STANDARD);
		}
	}

	@Override
	public void addDownPointer(Node downPointer) {
		assert(!(this instanceof NodeProxy));
		getConnections().addDownPointer(downPointer);
		if (getBiggerNeighbors().isEmpty()) {
			setState(NodeState.TERMINAL);
		} else {
			setState(NodeState.DOWNPOINTING);
		}
	}

	@Override
	public void removeDownPointer(Node downPointer) {
		assert(!(this instanceof NodeProxy));
		getConnections().removeDownPointer(downPointer);
		if (getConnections().getDownPointers().isEmpty()
				&& getState() == NodeState.DOWNPOINTING) {
			setState(NodeState.STANDARD);
		}
	}

	@Override
	public void setFold(Node newFold) {
		assert(!(this instanceof NodeProxy));
		if (newFold.getWebId().getValue() == 0) {
			setState(NodeState.HYPEERCUBECAP);
		} else if (state == NodeState.HYPEERCUBECAP) {
			setState(NodeState.STANDARD);
		}
		getConnections().setFold(newFold);
	}

	@Override
	public void setSurrogateFold(Node newSurrogateFold) {
		assert(!(this instanceof NodeProxy));
		getConnections().setSurrogateFold(newSurrogateFold);
	}

	@Override
	public void setInverseSurrogateFold(Node newInverseSurrogateFold) {
		assert(!(this instanceof NodeProxy));
		getConnections().setInverseSurrogateFold(newInverseSurrogateFold);
	}

	/**
	 * Creates a set of integers for neighbors to be used for the simplified
	 * node domain.
	 * 
	 * @return Returns a set of integers corresponding to the neighbors of this
	 *         node
	 */
	private HashSet<Integer> createSetOfNeighborIds() {
		assert(!(this instanceof NodeProxy));
		HashSet<Integer> neighborIds = new HashSet<Integer>();
		for (Node n : getConnections().getNeighbors()) {
			neighborIds.add(n.getIdInt());
		}
		return neighborIds;
	}

	/**
	 * Creates a set of integers for upPointers to be used for the simplified
	 * node domain.
	 * 
	 * @return Returns a set of integers corresponding to the upPointers of this
	 *         node
	 */
	private HashSet<Integer> createSetOfUpPointerIds() {
		assert(!(this instanceof NodeProxy));
		HashSet<Integer> upPointerIds = new HashSet<Integer>();
		for (Node n : getConnections().getUpPointers()) {
			upPointerIds.add(n.getIdInt());
		}
		return upPointerIds;
	}

	/**
	 * Creates a set of integers for downPointers to be used for the simplified
	 * node domain.
	 * 
	 * @return Returns a set of integers corresponding to the downPointers of
	 *         this node
	 */
	private HashSet<Integer> createSetOfDownPointerIds() {
		assert(!(this instanceof NodeProxy));
		HashSet<Integer> downPointerIds = new HashSet<Integer>();
		for (Node n : getConnections().getDownPointers()) {
			downPointerIds.add(n.getIdInt());
		}
		return downPointerIds;
	}

	/**
	 * Determines whether or not the node has a child node.
	 * 
	 * @return true if the node has a child, false otherwise.
	 * @pre The node has its state assigned correctly.
	 * @post If the node's state is standard then true is returned.
	 */
	public boolean hasChild() {
		assert(!(this instanceof NodeProxy));
		return getState() == NodeState.STANDARD;
	}

	/**
	 * Gets the node's webId binary value as an integer.
	 * 
	 * @return Returns the node's webId binary value.
	 */
	private int getIdInt() {
		WebId myWebId = getConnections().getWebId();
		return (myWebId == null) ? -1 : myWebId.getValue();
	}

	@Override
	public WebId getWebId() {
		assert(!(this instanceof NodeProxy));
		return getConnections().getWebId();
	}

	@Override
	public void setWebId(WebId id) {
		assert(!(this instanceof NodeProxy));
		getConnections().setWebId(id);
	}

	@Override
	public int compareTo(Node o) {
		return this.getIdInt() - o.getIdInt();
	}

	@Override
	public String toString() {
		assert(!(this instanceof NodeProxy));
		return getConnections().getWebId().toString();
	}

	@Override
	public NodeState getState() {
		assert(!(this instanceof NodeProxy));
		return state;
	}

	@Override
	public void setState(NodeState state) {
		assert(!(this instanceof NodeProxy));
		this.state = state;
	}

	@Override
	public Node asNode() {
		assert(!(this instanceof NodeProxy));
		return this;
	}

	@Override
	public Contents getContents() {
		assert(!(this instanceof NodeProxy));
		if (contents == null) {
			contents = new Contents();
		}
		return contents;
	}

	public GlobalObjectId getGlobalObjectId() {
		assert(!(this instanceof NodeProxy));
		return globalObjectId;
	}

	@Override
	public void accept(Visitor visitor, Parameters parameters) {
		assert(!(this instanceof NodeProxy));
		assert(visitor != null);
		assert(parameters != null);
		visitor.visit(this, parameters);
	}

	/**
	 * Searches the node's connections and returns the node that is closest to
	 * the node with the target id.
	 * 
	 * @param target
	 *            The id of the target node.
	 * @return Returns the node that is closest to the node with the target id.
	 * @pre getConnections().getClosestNodeTo(int, Node).preCondition is true.
	 * @post getConnections().getClosestNodeTo(int, Node).postCondition is true.
	 */
	public Node getClosestNodeTo(int target) {
		assert(!(this instanceof NodeProxy));
		return getConnections().getClosestNodeTo(target, this);
	}

	/**
	 * Updates the node's connection sets by re-adding its neighbors.
	 * 
	 * @pre This node's connection sets are out of date
	 * @post This node's connection sets contain the appropriate Nodes
	 */
	public void updateChildList() {
		assert(!(this instanceof NodeProxy));
		getConnections().updateChildList();
	}

	/**
	 * @obvious
	 */
	public void setConnections(Connections c) {
		assert(!(this instanceof NodeProxy));
		this.connections = c;
	}

	/**
	 * Searches the node's connections for the Node with the given id.
	 * 
	 * @param id
	 *            The id of the desired node.
	 * @return the node with the given id if it exists in this node's
	 *         getConnections(). If it does not exist the null node is returned.
	 * @pre getConnections().getNodeByWebId(int).preCondition is true..
	 * @post getConnections().getNodeByWebId(int).postCondition is true.
	 */
	public NodeInterface getNodeByWebId(int id) {
		assert(!(this instanceof NodeProxy));
		return getConnections().getNodeByWebId(id);
	}

	// ! Used only for testing!
	public Connections getConnections() {
		assert(!(this instanceof NodeProxy));
		return connections;
	}
//
//	private Object writeReplace() throws ObjectStreamException {
//		ObjectDB.getSingleton().store(globalObjectId.getLocalObjectId(), this);
//		NodeProxy proxy = new NodeProxy(globalObjectId);
//		return proxy;
//	}

	/**
	 * Move this node to another HyPeerWeb segment
	 * @param hypeerweb a global object ID of the segment to send to
	 * @pre This node is in a HyPeerWeb
	 * @post This node is no longer in the segment it originally belonged to
	 * @post A new node, with the same webId exists on the segment specified
	 * @post All connections to this node are replaced to reference the new node
	 */
	public Node MigrateTo(GlobalObjectId hypeerweb) {
		assert(!(this instanceof NodeProxy));
		HyPeerWebProxy h = new HyPeerWebProxy(hypeerweb);
		Node replacementNode = h.getNewNode();
		getConnections().copyConnections(replacementNode.getConnections());
		replacementNode.setState(state);
		for(Map.Entry<String, Object> entry : getContents().set()) {
			replacementNode.setContent(entry.getKey(), entry.getValue());
		}
		replacementNode.getConnections().removeMe(this);
		replacementNode.getConnections().addMe(replacementNode);
		replacementNode.setFold(replacementNode.getFold());
		if (HyPeerWeb.getSingleton().contains(this)) {
			//remove it from this segment
			HyPeerWeb.getSingleton().removeNode(this);
			//transplant it into the other segment
			h.addNode(replacementNode);
		} else {
			//It was never in this segment, so it is technically a brand
			//new node, and not just a migration of a node that is already
			//integrated into the other web.
			h.addToHyPeerWeb(replacementNode, h.getANodeInThisSegment());
		}

		return replacementNode;
	}
	public void setContent(String key, Object o) {
		assert(!(this instanceof NodeProxy));
		getContents().set(key, o);
	}
}