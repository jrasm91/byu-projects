package hypeerweb.node;

import hypeerweb.connected.ConnectedDownPointer;
import hypeerweb.connected.ConnectedFold;
import hypeerweb.connected.ConnectedInverseSurrogateFold;
import hypeerweb.connected.ConnectedNeighbor;
import hypeerweb.connected.ConnectedNode;
import hypeerweb.connected.ConnectedSurrogateFold;
import hypeerweb.connected.ConnectedUpPointer;
import distributed.*;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import distributed.GlobalObjectId;
import distributed.ObjectDB;

/**
 * The connections of a node in the HyPeerWeb.<br>
 * <br>
 * 
 * <pre>
 * <b>Domain:</b>
 *      biggerNeighbors : Set<ConnectedNode>
 *      smallerNeighborsWithChild : Set<ConnectedNode>
 *      smallerNeighborsWithoutChild : Set<ConnectedNode>
 *      upPointers : Set<ConnectedNode>
 *      downPointers : Set<ConnectedNode> 
 *      fold : ConnectedNode
 *      surrogateFold : ConnectedNode
 *      inverseSurrogateFold : ConnectedNode
 *      webId : WebId
		globalObjectId : GlobalObjectId
 * 
 * <b>Invariant:</b>
 * 		The variables in the domain refer to ConnectedNodes rather than raw Nodes, 
 * 		excluding webId and globalObjectId of course which aren't nodes at all.
 * 		The sets contain no NULL_NODES
 * 		No Node is in multiple sets. For example, Node 4 can't be in biggerNeighbors and smallerNeighborsWithoutChild.
 * 
 * </pre>
 * 
 * @author Adam Christiansen
 */

public class Connections implements Serializable {

	private static final long serialVersionUID = 3833805800996539271L;
	private Set<ConnectedNode> biggerNeighbors;
	private Set<ConnectedNode> smallerNeighborsWithChild;
	private Set<ConnectedNode> smallerNeighborsWithoutChild;
	private Set<ConnectedNode> upPointers; // inverse surrogate neighbors
	private Set<ConnectedNode> downPointers; // surrogate neighbors
	private ConnectedNode fold;
	private ConnectedNode surrogateFold;
	private ConnectedNode inverseSurrogateFold;
	private WebId webId;
	private GlobalObjectId globalObjectId = new GlobalObjectId();

	/**
	 * Default constructor for a Connection.
	 * 
	 * @pre None.
	 * @post Constructs a new Connections.
	 */
	public Connections() {
		this.biggerNeighbors = new TreeSet<ConnectedNode>();
		this.smallerNeighborsWithChild = new TreeSet<ConnectedNode>();
		this.smallerNeighborsWithoutChild = new TreeSet<ConnectedNode>();
		this.upPointers = new TreeSet<ConnectedNode>();
		this.downPointers = new TreeSet<ConnectedNode>();
		this.fold = new ConnectedFold(Node.NULL_NODE);
		this.surrogateFold = new ConnectedSurrogateFold(Node.NULL_NODE);
		this.inverseSurrogateFold = new ConnectedInverseSurrogateFold(
				Node.NULL_NODE);
		this.webId = WebId.NULL_WEB_ID;
		ObjectDB.getSingleton().store(globalObjectId.getLocalObjectId(), this);
	}

	/**
	 * Constructor for a Connection given a specific WebId.
	 * 
	 * @param webId
	 *            The webId to be set as this Connection's webId.
	 * @pre webID not null.
	 * @post Constructs a new Connections with the given webId.
	 */
	public Connections(WebId webId) {
		this();
		this.webId = webId;
	}
	
	public Connections(String s){
		//do nothing
	}

	/**
	 * Searches the connections and returns the node that is closest to the node
	 * with the target id.
	 * 
	 * @param target
	 *            The id of the target node.
	 * @param myself
	 *            The node to which this Connections belongs.
	 * @return Returns a node that is closer to the node with the target id.
	 * @pre There is a node in the HyPeerWeb with the given target id.
	 * @post If there is no node closer to the target node than myself, myself
	 *       is returned.
	 */
	public Node getClosestNodeTo(int target, Node myself) {
		assert(!(this instanceof ConnectionsProxy));
		Node closest = myself;
		Iterator<ConnectedNode> iter = getConNodeIterator();
		WebId targetId = new WebId(target, WebId.MAX_HEIGHT);
		int closestDistance = myself.getWebId().distanceTo(targetId);
		while (iter.hasNext()) {
			ConnectedNode next = iter.next();
			if (next.asNode() == Node.NULL_NODE) {
				continue;
			}
			if (next.getWebId().getValue() == target) {
				return next.asNode();
			}
			int distance = next.getWebId().distanceTo(targetId);

			if (distance < closestDistance) {
				closestDistance = distance;
				closest = next.asNode();
			}
		}
		if (closest == myself) {
			return Node.NULL_NODE;
		}
		return closest;
	}

	/**
	 * Gets the Node in these connections that has the given id.
	 * 
	 * @param id
	 *            the id of the requested node.
	 * @return The NodeInterface that has the given id, or NULL_NODE if it does
	 *         not exist in these connections.
	 */
	public NodeInterface getNodeByWebId(int id) {
		if (fold != null) {
			if (fold.getWebId().getValue() == id) {
				return fold;
			}
		}
		if (surrogateFold != null) {
			if (surrogateFold.getWebId().getValue() == id) {
				return surrogateFold;
			}
		}
		if (inverseSurrogateFold != null) {
			if (inverseSurrogateFold.getWebId().getValue() == id) {
				return inverseSurrogateFold;
			}
		}
		// Now to traverse the collections.
		for (ConnectedNode n : biggerNeighbors) {
			if (n.getWebId().getValue() == id) {
				return n;
			}
		}
		for (ConnectedNode n : smallerNeighborsWithChild) {
			if (n.getWebId().getValue() == id) {
				return n;
			}
		}
		for (ConnectedNode n : smallerNeighborsWithoutChild) {
			if (n.getWebId().getValue() == id) {
				return n;
			}
		}
		for (ConnectedNode n : upPointers) {
			if (n.getWebId().getValue() == id) {
				return n;
			}
		}
		for (ConnectedNode n : downPointers) {
			if (n.getWebId().getValue() == id) {
				return n;
			}
		}
		return Node.NULL_NODE;
	}

	/**
	 * Adds the given Node to the appropriate Set.
	 * 
	 * @param newNode
	 *            The Node to add to the appropriate Set.
	 * @pre + newNode is not null. + newNode webId should not equal this Node's
	 *      webId.
	 * @post + If the given node's id is bigger than this node's id, then
	 *       biggerNeigbors will contain the added Node. + If the given node has
	 *       a child and its id is smaller than this node's id, then
	 *       smallerNeighborsWithChild will contain the added Node. + If the
	 *       given node does not have a child and its id is smaller than this
	 *       node's id, then smallerNeighborsWithoutChild will contain the added
	 *       Node.
	 */
	public void addNeighbor(Node node) {
		assert(!(this instanceof ConnectionsProxy));
		ConnectedNode newNode = new ConnectedNeighbor(node);
		if (node.getWebId().getValue() == this.webId.getValue()) {
			throw new ArrayIndexOutOfBoundsException("you are a potato");
		} else if (newNode.getWebId().getValue() > this.webId.getValue()) {
			this.biggerNeighbors.add(newNode);
		} else if (newNode.getState() == NodeState.STANDARD) {
			smallerNeighborsWithChild.add(newNode);
		} else {
			smallerNeighborsWithoutChild.add(newNode);
		}
	}

	/**
	 * Removes the given Node from all of this Connections Sets.
	 * 
	 * @param toRemove
	 *            The Node to be removed from this Connections Sets.
	 * @pre toRemove is not null.
	 * @post The size of all the Sets may decrease by one.
	 */
	public void removeNeighbor(Node toRemove) {
		assert(!(this instanceof ConnectionsProxy));
		biggerNeighbors.remove(new ConnectedNeighbor(toRemove));
		smallerNeighborsWithChild.remove(new ConnectedNeighbor(toRemove));
		smallerNeighborsWithoutChild.remove(new ConnectedNeighbor(toRemove));
	}

	/**
	 * Adds the given Node to the set of upPointers as a ConnectedNode.
	 * 
	 * @param upPointer
	 *            the Node to be added to upPointers.
	 * @pre None.
	 * @post upPointers contains the ConnectedNode version of the given Node.
	 */
	public void addUpPointer(Node upPointer) {
		assert(!(this instanceof ConnectionsProxy));
		upPointers.add(new ConnectedUpPointer(upPointer));
	}

	/**
	 * Removes the given Node from the set of upPointers.
	 * 
	 * @param upPointer
	 *            the Node to be removed from upPointers.
	 * @pre None.
	 * @post upPointers no longer contains the ConnectedNode version of the
	 *       given Node.
	 */
	public void removeUpPointer(Node upPointer) {
		assert(!(this instanceof ConnectionsProxy));
		upPointers.remove(new ConnectedUpPointer(upPointer));
	}

	/**
	 * Adds the given Node to the set of downPointers as a ConnectedNode.
	 * 
	 * @param downPointer
	 *            the Node to be added to downPointers.
	 * @pre None.
	 * @post downPointers contains the ConnectedNode version of the given Node.
	 */
	public void addDownPointer(Node downPointer) {
		assert(!(this instanceof ConnectionsProxy));
		downPointers.add(new ConnectedDownPointer(downPointer));
	}

	/**
	 * Removes the given Node from the set of downPointers.
	 * 
	 * @param downPointer
	 *            the Node to be removed from downPointers.
	 * @pre None.
	 * @post downPointers no longer contains the ConnectedNode version of the
	 *       given Node.
	 */
	public void removeDownPointer(Node downPointer) {
		assert(!(this instanceof ConnectionsProxy));
		downPointers.remove(new ConnectedDownPointer(downPointer));
	}

	/**
	 * Removes the given Node from these connections.
	 * 
	 * @param replacementNode
	 *            the Node to be removed from these connections.
	 * @pre None.
	 * @post The given Node is no longer in these connections.
	 */
	public void removeMe(Node replacementNode) {
		assert(!(this instanceof ConnectionsProxy));
		new IterateNodes(this) {
			@Override
			public void notify(ConnectedNode connectedNode, Node newNode) {
				connectedNode.removeMe(newNode);
			}
		}.execute(replacementNode);
	}

	/**
	 * Adds the given Node to these connections.
	 * 
	 * @param replacementNode
	 *            the Node to be added to these connections.
	 * @pre None.
	 * @post The given Node is in its proper place in these connections.
	 */
	public void addMe(Node replacementNode) {
		assert(!(this instanceof ConnectionsProxy));
		new IterateNodes(this) {
			@Override
			public void notify(ConnectedNode connectedNode, Node newNode) {
				connectedNode.addMe(newNode);
			}
		}.execute(replacementNode);
	}

	/**
	 * Updates this Connection's Sets by re-adding its neighbors
	 * 
	 * @pre This Connection's Sets are out of date
	 * @post This Connection's Sets contain the appropriate Nodes
	 */
	protected void updateChildList() {
		assert(!(this instanceof ConnectionsProxy));
		TreeSet<ConnectedNode> tempList1 = new TreeSet<ConnectedNode>(
				smallerNeighborsWithChild);
		TreeSet<ConnectedNode> tempList2 = new TreeSet<ConnectedNode>(
				smallerNeighborsWithoutChild);
		smallerNeighborsWithChild.clear();
		smallerNeighborsWithoutChild.clear();
		for (ConnectedNode c : tempList1) {
			addNeighbor(c.asNode());
		}
		for (ConnectedNode c : tempList2) {
			addNeighbor(c.asNode());
		}
	}

	/**
	 * Updates the child list of all of the connected nodes.
	 * 
	 * @pre None.
	 * @post All of the connected node's child lists are up-to-date.
	 */
	public void updateEveryoneChildList() {
		assert(!(this instanceof ConnectionsProxy));
		Iterator<ConnectedNode> iter = getConNodeIterator();
		while (iter.hasNext()) {
			ConnectedNode next = iter.next();
			next.asNode().updateChildList();
		}
	}

	private TreeSet<Node> asNodeList(Set<ConnectedNode> connectedList) {
		assert(!(this instanceof ConnectionsProxy));
		TreeSet<Node> result = new TreeSet<Node>();
		for (ConnectedNode node : connectedList) {
			result.add(node.asNode());
		}
		return result;
	}

	private Iterator<ConnectedNode> getConNodeIterator() {
		assert(!(this instanceof ConnectionsProxy));
		LinkedList<ConnectedNode> nodes = new LinkedList<ConnectedNode>();
		nodes.addAll(biggerNeighbors);
		nodes.addAll(smallerNeighborsWithoutChild);
		nodes.addAll(smallerNeighborsWithChild);
		nodes.addAll(upPointers);
		nodes.addAll(downPointers);
		nodes.add(fold);
		nodes.add(surrogateFold);
		nodes.add(inverseSurrogateFold);
		return nodes.iterator();
	}

	/**
	 * A template method pattern classed used to iterate over all the nodes in
	 * these connections.
	 */
	private abstract class IterateNodes {
		protected Connections c;

		public IterateNodes(Connections c) {
			this.c = c;
		}

		public void execute(Node otherNode) {
			Iterator<ConnectedNode> iter = getConNodeIterator();
			while (iter.hasNext()) {
				ConnectedNode next = iter.next();
				notify(next, otherNode);
			}
		}

		public abstract void notify(ConnectedNode connectedNode, Node newNode);
	}

	/**
	 * A getter for smallerNeighborsWithChild, where the members of the set are
	 * Nodes instead of ConnectedNodes.
	 * 
	 * @return Returns smallerNeighborsWithChild, with the entries being of type
	 *         Node.
	 * @pre None.
	 * @post All the nodes in the returned set have a smaller id than this node,
	 *       and they all have a child.
	 */
	public Set<Node> getSmallerNeighborsWithChild() {
		assert(!(this instanceof ConnectionsProxy));
		return asNodeList(smallerNeighborsWithChild);
	}

	/**
	 * A getter for biggerNeighbors, where the members of the set are Nodes
	 * instead of ConnectedNodes.
	 * 
	 * @return Returns biggerNeighbors, with the entries being of type Node.
	 * @pre None.
	 * @post All the nodes in the returned set have a bigger id than this node.
	 */
	public Set<Node> getBiggerNeighbors() {
		assert(!(this instanceof ConnectionsProxy));
		return asNodeList(biggerNeighbors);
	}

	/**
	 * Creates and returns a Set representing a combination of all the Nodes in
	 * all this Connection's Sets.
	 * 
	 * @return A set of all the neighboring nodes.
	 * @pre None.
	 * @post Every neighboring node is included in the returned set.
	 */
	public Set<Node> getNeighbors() {
		assert(!(this instanceof ConnectionsProxy));
		TreeSet<Node> combinedNeighbors = new TreeSet<Node>();
		combinedNeighbors.addAll(asNodeList(biggerNeighbors));
		combinedNeighbors.addAll(asNodeList(smallerNeighborsWithChild));
		combinedNeighbors.addAll(asNodeList(smallerNeighborsWithoutChild));
		return combinedNeighbors;
	}

	/**
	 * Gets the biggest neighbor from this Connection's Sets.
	 * 
	 * @return The biggest neighboring node that is bigger than this node, or
	 *         NULL_NODE if such a node does not exist.
	 * @pre None.
	 * @post + The returned node will have a bigger id than this node, unless
	 *       NULL_NODE was returned. + The returned node will have a bigger id
	 *       than any of this node's neighbors, unless NULL_NODE was returned.
	 */
	public Node getBiggestNeighbor() {
		assert(!(this instanceof ConnectionsProxy));
		if (!upPointers.isEmpty()) {
			return ((TreeSet<ConnectedNode>) upPointers).last().asNode();
		} else if (!biggerNeighbors.isEmpty()) {
			return ((TreeSet<ConnectedNode>) biggerNeighbors).last().asNode();
		} else if (inverseSurrogateFold.asNode() != Node.NULL_NODE) {
			return inverseSurrogateFold.asNode();
		} else {
			return Node.NULL_NODE;
		}
	}

	/**
	 * Gets the first Node in smallerNeighborsWithoutChild.
	 * 
	 * @return The smallest neighboring node that is smaller than this node and
	 *         does not have a child, or NULL_NODE if such a node does not
	 *         exist.
	 * @pre None.
	 * @post + The returned node will have a smaller id than this node. + The
	 *       returned node will have a smaller id than any of this node's
	 *       neighbors. + The returned node will not have a child.
	 */
	public Node getSmallestNeighborWithoutChild() {
		assert(!(this instanceof ConnectionsProxy));
		if (smallerNeighborsWithoutChild.isEmpty()) {
			return Node.NULL_NODE;
		}
		return ((TreeSet<ConnectedNode>) smallerNeighborsWithoutChild).first()
				.asNode();
	}

	/**
	 * Gets the first Node in smallerNeighborsWithChild.
	 * 
	 * @return The smallest neighboring node that is smaller than this node and
	 *         has a child, or NULL_NODE if such a node does not exist.
	 * @pre None.
	 * @post + The returned node will have a smaller id than this node. + The
	 *       returned node will have a smaller id than any of this node's
	 *       neighbors. + The returned node will have a child.
	 */
	public Node getSmallestNeighborWithChild() {
		assert(!(this instanceof ConnectionsProxy));
		if (smallerNeighborsWithChild.isEmpty()) {
			return Node.NULL_NODE;
		}
		return ((TreeSet<ConnectedNode>) smallerNeighborsWithChild).first()
				.asNode();
	}

	/**
	 * Gets the first Node in downPointers.
	 * 
	 * @return The smallest surrogate neighbor (a.k.a. downPointer) of this
	 *         node, or NULL_NODE if such a node does not exist.
	 * @pre None.
	 * @post + The returned node will have a smaller id than any of this node's
	 *       other downPointers.
	 */
	public Node getSmallestSurrogateNeighbor() {
		assert(!(this instanceof ConnectionsProxy));
		if (downPointers.isEmpty()) {
			return Node.NULL_NODE;
		}
		return ((TreeSet<ConnectedNode>) downPointers).first().asNode();
	}

	/**
	 * Gets the last Node in upPointers.
	 * 
	 * @return The biggest inverse surrogate neighbor (a.k.a. upPointer) of this
	 *         node, or NULL_NODE if such a node does not exist.
	 * @pre None.
	 * @post + The returned node will have a bigger id than any of this node's
	 *       other upPointers.
	 */
	public Node getBiggestInverseSurrogateNeighbor() {
		assert(!(this instanceof ConnectionsProxy));
		if (upPointers.isEmpty()) {
			return Node.NULL_NODE;
		}
		return ((TreeSet<ConnectedNode>) upPointers).last().asNode();
	}

	/**
	 * Gets the set of nodes that are this node's upPointers.
	 * 
	 * @return The upPointers as a set of Nodes.
	 * @pre None.
	 * @post The returned set will contain all the node's that are this node's
	 *       upPointers.
	 */
	public Set<Node> getUpPointers() {
		assert(!(this instanceof ConnectionsProxy));
		return asNodeList(upPointers);
	}

	/**
	 * Sets the upPointers to the given set of Nodes.
	 * 
	 * @param upPointers
	 *            The set of Nodes to be set as this node's upPointers.
	 * @pre None.
	 * @post This node's upPointers will contain ConnectedNode versions of all
	 *       of the Nodes in the given set.
	 */
	public void setUpPointers(Set<Node> upPointers) {
		assert(!(this instanceof ConnectionsProxy));
		this.downPointers = new TreeSet<ConnectedNode>();
		for (Node node : upPointers) {
			this.upPointers.add(new ConnectedDownPointer(node));
		}
	}

	/**
	 * Gets the set of nodes that are this node's downPointers.
	 * 
	 * @return The downPointers as a set of Nodes.
	 * @pre None.
	 * @post The returned set will contain all the node's that are this node's
	 *       downPointers.
	 */
	public Set<Node> getDownPointers() {
		assert(!(this instanceof ConnectionsProxy));
		return asNodeList(downPointers);
	}

	/**
	 * Gets the node that is this node's fold.
	 * 
	 * @return The fold of this node as a Node.
	 * @pre None
	 * @post The returned node's webId will be the fold of this node's webId.
	 */
	public Node getFold() {
		assert(!(this instanceof ConnectionsProxy));
		return fold.asNode();
	}

	/**
	 * Sets the fold to the given node.
	 * 
	 * @param fold
	 *            The Node to be set as this node's fold.
	 * @pre None.
	 * @post This node's fold will be the ConnectedNode version of the given
	 *       node.
	 */
	public void setFold(Node fold) {
		assert(!(this instanceof ConnectionsProxy));
		this.fold = new ConnectedFold(fold);
	}

	/**
	 * Gets the node that is this node's surrogate fold.
	 * 
	 * @return The surrogate fold of this node as a Node.
	 * @pre None
	 * @post The returned node's webId will be the surrogate fold of this node's
	 *       webId.
	 */
	public Node getSurrogateFold() {
		assert(!(this instanceof ConnectionsProxy));
		return surrogateFold.asNode();
	}

	/**
	 * Sets the surrogateFold to the given node.
	 * 
	 * @param surrogateFold
	 *            The Node to be set as this node's surrogateFold.
	 * @pre None.
	 * @post This node's surrogateFold will be the ConnectedNode version of the
	 *       given node.
	 */
	public void setSurrogateFold(Node surrogateFold) {
		assert(!(this instanceof ConnectionsProxy));
		this.surrogateFold = new ConnectedSurrogateFold(surrogateFold);
	}

	/**
	 * Gets the node that is this node's inverse surrogate fold.
	 * 
	 * @return The inverse surrogate fold of this node as a Node.
	 * @pre None
	 * @post The returned node's webId will be the inverse surrogate fold of
	 *       this node's webId.
	 */
	public Node getInverseSurrogateFold() {
		assert(!(this instanceof ConnectionsProxy));
		return inverseSurrogateFold.asNode();
	}

	/**
	 * Sets the inverseSurrogateFold to the given node.
	 * 
	 * @param inverseSurrogateFold
	 *            The Node to be set as this node's inverseSurrogateFold.
	 * @pre None.
	 * @post This node's inverseSurrogateFold will be the ConnectedNode version
	 *       of the given node.
	 */
	public void setInverseSurrogateFold(Node inverseSurrogateFold) {
		assert(!(this instanceof ConnectionsProxy));
		this.inverseSurrogateFold = new ConnectedInverseSurrogateFold(
				inverseSurrogateFold);
	}

	/**
	 * @obvious
	 */
	public WebId getWebId() {
		assert(!(this instanceof ConnectionsProxy));
		return webId;
	}

	/**
	 * @obvious
	 */
	public void setWebId(WebId webId) {
		assert(!(this instanceof ConnectionsProxy));
		this.webId = webId;
	}
//	private Object writeReplace() throws ObjectStreamException {
//		ConnectionsProxy proxy = new ConnectionsProxy(globalObjectId);
//		return proxy;
//	}
//	
//	public Object readResolve() throws ObjectStreamException {
//		GlobalObjectId global2 = new GlobalObjectId();
//		if(globalObjectId.onSameMachineAs(global2)){
//			Connections c =  (Connections)ObjectDB.getSingleton().getValue(globalObjectId.getLocalObjectId());
//			return c;
//		}
//		return this;
//	}
	public void copyConnections(Connections other) {
		assert(!(this instanceof ConnectionsProxy));
		if (fold != null) {
			other.setFold(fold.asNode());
		}
		if (surrogateFold != null) {
			other.setSurrogateFold(surrogateFold.asNode());
		}
		if (inverseSurrogateFold != null) {
			other.setInverseSurrogateFold(inverseSurrogateFold.asNode());
		}
		// Now to traverse the collections.
		for (ConnectedNode n : biggerNeighbors) {
			other.addNeighbor(n.asNode());
		}
		for (ConnectedNode n : smallerNeighborsWithChild) {
			other.addNeighbor(n.asNode());
		}
		for (ConnectedNode n : smallerNeighborsWithoutChild) {
			other.addNeighbor(n.asNode());
		}
		for (ConnectedNode n : upPointers) {
			other.addUpPointer(n.asNode());
		}
		for (ConnectedNode n : downPointers) {
			other.addDownPointer(n.asNode());
		}
	}
}
