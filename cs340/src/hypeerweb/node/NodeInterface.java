package hypeerweb.node;

import hypeerweb.SimplifiedNodeDomain;
import hypeerweb.visitor.Parameters;
import hypeerweb.visitor.Visitor;

public interface NodeInterface {

	/**
	 * Creates a simplified node domain to be used in testing.
	 * 
	 * @return Returns the constructed simplified node domain.
	 * @pre None.
	 * @post The simplified node domain attributes correspond to this node's
	 *       attributes.
	 */
	public abstract SimplifiedNodeDomain constructSimplifiedNodeDomain();

	/**
	 * Adds the node newNode to the HyPeerWeb, using this node as the starting
	 * node.
	 * 
	 * @param newNode
	 *            The node to be added to the HyPeerWeb.
	 * @pre newNode is not null.
	 * @post + The size of the HyPeer Web is N+1 where N is the original size of
	 *       the web. + The new node's webID is N. + All of the constraints are
	 *       true. + The webIDs of the original nodes in the HyPeerWeb do not
	 *       change.
	 */
	public abstract void addToHyPeerWeb(Node newNode);

	/**
	 * Adds a neighbor.
	 * 
	 * @param neighbor
	 *            The Node to be added as a neighbor.
	 * @pre newNode is not null.
	 * @post The newNode is added to the set of neighbors.
	 */
	public abstract void addNeighbor(Node neighbor);

	/**
	 * Removes a neighbor.
	 * 
	 * @param neighbor
	 *            The Node to be removed as a neighbor.
	 * @pre neighbor is not null.
	 * @post The given node is removed from the set of neighbors.
	 */
	public abstract void removeNeighbor(Node neighbor);

	/**
	 * Adds an upPointer.
	 * 
	 * @param upPointer
	 *            The Node to be added as an upPointer.
	 * @pre upPointer is not null.
	 * @post The given node is added to the set of upPointers.
	 */
	public abstract void addUpPointer(Node upPointer);

	/**
	 * Removes an upPointer.
	 * 
	 * @param upPointer
	 *            The Node to be removed as an upPointer.
	 * @pre upPointer is not null.
	 * @post The given node is removed from the set of neighbors.
	 */
	public abstract void removeUpPointer(Node upPointer);

	/**
	 * Adds a downPointer.
	 * 
	 * @param downPointer
	 *            The Node to be added as an downPointer.
	 * @pre downPointer is not null.
	 * @post The given node is added to the set of downPointers.
	 */
	public abstract void addDownPointer(Node downPointer);

	/**
	 * Removes a downPointer.
	 * 
	 * @param downPointer
	 *            The Node to be removed as an downPointer.
	 * @pre downPointer is not null.
	 * @post The given node is removed from the set of downPointers.
	 */
	public abstract void removeDownPointer(Node downPointer);

	/**
	 * Sets the fold for this node.
	 * 
	 * @param newFold
	 *            The Node to be set as the fold.
	 * @pre newFold is not null.
	 * @post The given node is set as the fold.
	 */
	public abstract void setFold(Node newFold);

	/**
	 * Sets the surrogate fold for this node.
	 * 
	 * @param newSurrogateFold
	 *            The Node to be set as the surrogate fold.
	 * @pre newSurrogateFold is not null.
	 * @post The given node is set as the surrogate fold.
	 */
	public abstract void setSurrogateFold(Node newSurrogateFold);

	/**
	 * Sets the inverse surrogate fold for this node.
	 * 
	 * @param newInverseSurrogateFold
	 *            The Node to be set as the inverse surrogate fold.
	 * @pre newInverseSurrogateFold is not null.
	 * @post The given node is set as the inverse surrogate surrogate fold.
	 */
	public abstract void setInverseSurrogateFold(Node newInverseSurrogateFold);

	/**
	 * Gets the WebId for this node.
	 * 
	 * @return Returns the node's webId.
	 * @pre None.
	 * @post Returns the node's webId.
	 */
	public abstract WebId getWebId();

	/**
	 * Sets the WebId for this Node.
	 * 
	 * @param id
	 *            The WebId to be set to the node.
	 * @pre id is not null.
	 * @post The node's webId is set to the given id.
	 */
	public abstract void setWebId(WebId id);

	/**
	 * Returns a string representation of the Node.
	 * 
	 * @return Returns the webId string
	 * @pre None.
	 * @post None.
	 */
	public abstract String toString();

	/**
	 * Removes this node from the HyPeerWeb.
	 * 
	 * @pre There are at least 2 nodes in the HyPeerWeb.
	 * @post + The size of the HyPeer Web is N-1 where N is the original size of
	 *       the web. + The node this method was called on is no longer in the
	 *       web. + All of the constraints are true.
	 */
	public abstract void removeFromHyPeerWeb();

	/**
	 * Sets the NodeState for this Node.
	 * 
	 * @param state
	 *            The NodeState to be set to the node.
	 * @pre state is a valid NodeState.
	 * @post This node's NodeState is set to the given state.
	 */
	public abstract void setState(NodeState state);

	/**
	 * Returns a string representation of the Node.
	 * 
	 * @return Returns the webId string
	 * @pre None.
	 * @post None.
	 */
	public abstract NodeState getState();

	/**
	 * Returns the Node form of a ConnectedNode.
	 * 
	 * @return Returns the Node form of this ConnectedNode.
	 * @pre None.
	 * @post All attributes are preserved.
	 */
	public abstract Node asNode();

	/**
	 * Returns the Contents of this node.
	 * 
	 * @return The Contents of this node.
	 * @pre None.
	 * @post The returned Contents are those belonging to this node.
	 */
	public abstract Contents getContents();

	/**
	 * A visitor pattern accept method to be used in sending and broadcasting
	 * messages throughout the HyPeerWeb.
	 * 
	 * @param visitor
	 *            The visitor to be accepted.
	 * @param parameters
	 *            The parameters to be passed along.
	 * @pre visitor is not null.
	 * @post The visitor and parameters will be accepted.
	 */
	public void accept(Visitor visitor, Parameters parameters);

}