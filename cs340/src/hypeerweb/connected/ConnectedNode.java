package hypeerweb.connected;

import java.io.Serializable;

import hypeerweb.SimplifiedNodeDomain;
import hypeerweb.node.Contents;
import hypeerweb.node.Node;
import hypeerweb.node.NodeInterface;
import hypeerweb.node.NodeState;
import hypeerweb.node.WebId;
import hypeerweb.visitor.Parameters;
import hypeerweb.visitor.Visitor;

/**
 * Wrapper that goes into the connections class for what is used to handle
 * modifying connections.
 * 
 * @author Levi Schuck
 * 
 */

public abstract class ConnectedNode implements NodeInterface,
		Comparable<ConnectedNode>, Serializable {

	private static final long serialVersionUID = 7296177622915489796L;
	protected Node myNode;

	/**
	 * An abstract method that adds the given node to its proper place in the
	 * connections.
	 * 
	 * @pre None.
	 * @post The given node will be added to its proper place in the
	 *       connections.
	 */
	public abstract void addMe(Node n);

	/**
	 * An abstract method that removes the given node from its proper place in
	 * the connections.
	 * 
	 * @pre None.
	 * @post The given node will be removed from its proper place in the
	 *       connections.
	 */
	public abstract void removeMe(Node n);

	/**
	 * An abstract method that removes node n and adds n2 to its proper place in
	 * the connections.
	 * 
	 * @pre None.
	 * @post + Node n will not exist in the connections. + Node n2 will exist in
	 *       its proper place in the connections.
	 */
	public void replace(Node n, Node n2) {
		removeMe(n);
		addMe(n2);
	}

	@Override
	public SimplifiedNodeDomain constructSimplifiedNodeDomain() {
		return myNode.constructSimplifiedNodeDomain();
	}

	@Override
	public void addToHyPeerWeb(Node newNode) {
		myNode.addToHyPeerWeb(newNode);
	}

	@Override
	public void addNeighbor(Node neighbor) {
		myNode.addNeighbor(neighbor);
	}

	@Override
	public void removeNeighbor(Node neighbor) {
		myNode.removeNeighbor(neighbor);
	}

	@Override
	public void addUpPointer(Node upPointer) {
		myNode.addUpPointer(upPointer);
	}

	@Override
	public void removeUpPointer(Node upPointer) {
		myNode.removeUpPointer(upPointer);
	}

	@Override
	public void addDownPointer(Node downPointer) {
		myNode.addDownPointer(downPointer);
	}

	@Override
	public void removeDownPointer(Node downPointer) {
		myNode.removeDownPointer(downPointer);
	}

	@Override
	public void setFold(Node newFold) {
		myNode.setFold(newFold);
	}

	@Override
	public void setSurrogateFold(Node newSurrogateFold) {
		myNode.setSurrogateFold(newSurrogateFold);
	}

	@Override
	public void setInverseSurrogateFold(Node newInverseSurrogateFold) {
		myNode.setInverseSurrogateFold(newInverseSurrogateFold);
	}

	@Override
	public WebId getWebId() {
		return myNode.getWebId();
	}

	@Override
	public void setWebId(WebId id) {
		myNode.setWebId(id);
	}

	@Override
	public String toString() {
		return myNode.toString();
	}

	@Override
	public void removeFromHyPeerWeb() {
		myNode.removeFromHyPeerWeb();
	}

	@Override
	public void setState(NodeState state) {
	}

	@Override
	public NodeState getState() {
		return myNode.getState();
	}

	@Override
	public Node asNode() {
		return myNode;
	}

	@Override
	public int compareTo(ConnectedNode o) {
		return myNode.compareTo(o.asNode());
	}

	@Override
	public int hashCode() {
		return getWebId().getValue();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof ConnectedNode) {
			ConnectedNode other = (ConnectedNode) obj;
			if (myNode == null) {
				if (other.myNode != null)
					return false;
			} else if (myNode.getWebId().getValue() != other.myNode.getWebId()
					.getValue())
				return false;
		}
		if (obj instanceof Node) {
			Node other = (Node) obj;
			if (myNode == null) {
				if (other != null)
					return false;
			} else if (myNode.getWebId().getValue() != other.getWebId()
					.getValue())
				return false;
		}

		return true;
	}

	/**
	 * Gets the contents of myNode.
	 * 
	 * @return myNode's Contents.
	 * @pre myNode is not null.
	 * @post The returned contents will be those of myNode.
	 */
	public Contents getContents() {
		return myNode.getContents();
	}

	/**
	 * The accept method for visitor pattern access.
	 * 
	 * @param visitor
	 *            The visitor that will visit this ConnectedNode.
	 * @param parameters
	 *            The parameters to be passed along.
	 * @pre myNode is not null.
	 * @post myNode will accept the input.
	 */
	public void accept(Visitor visitor, Parameters parameters) {
		myNode.accept(visitor, parameters);
	}
}
