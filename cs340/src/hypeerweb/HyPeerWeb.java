package hypeerweb;

import hypeerweb.node.Node;
import hypeerweb.node.NodeProxy;
import hypeerweb.node.NodeState;
import hypeerweb.node.WebId;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import distributed.GlobalObjectId;
import distributed.LocalObjectId;
import distributed.ObjectDB;

/**
 * Object that provides the means to control this HyPeerWeb segment.
 * 
 * + Nodes can be added without traversing the root each time
 * + The HyPeerWeb can be persisted to a database
 * + The HypeerWeb may be reinitialized, cleared or loaded from a stored representation
 * 
 * ----
 * 
 * # Domain
 * 
 * + map of Integer => Node
 * + Local Object ID
 * + *static* singleton of HyPeerWeb
 * 
 * # Invariants
 * 
 * + All nodes stored in the map have a direct OR indirect connection to another
 * node in the map. 
 * + Null Nodes are not stored in the map
 * + singleton may only be initialized by the getSingleton() method
 * 
 * @author Levi Schuck
 */

public class HyPeerWeb implements Serializable {

	private static final long serialVersionUID = -8168874744998591880L;
	private Map<Integer, Node> nodes = new HashMap<Integer, Node>();
	private static HyPeerWeb singleton = null;
	private LocalObjectId localObjectId;

	private HyPeerWeb(){
		localObjectId = new LocalObjectId(-1);
		ObjectDB.getSingleton().store(localObjectId, this);
	}
	protected HyPeerWeb(int i) {
		//nothing
	}

	/**
	 * Adds a node to the HyPeerWeb.
	 * 
	 * @param node
	 *            the Node to be added to the HyPeerWeb.
	 * @pre node is not null.
	 * @post the node is now present in the HyPeerWeb.
	 */
	public synchronized void addNode(Node node) {
		if(contains(node)) {
			return;
		}
		nodes.put(node.getWebId().getValue(),node);
	}

	/**
	 * Starting at one node, add a new node to the HyPeerWeb.
	 * 
	 * @param node
	 *            new node to add.
	 * @param startNode
	 *            the node to start traversing from.
	 * @pre + startNode is in the database. + startNode is not null. + the node
	 *      is not null.
	 * @post The node is now in the HyPeerWeb.
	 */
	public synchronized void addToHyPeerWeb(Node node, Node startNode) {

		if (startNode != null && !startNode.equals(Node.NULL_NODE)) {
			startNode.addToHyPeerWeb(node);
		} else {
			if (nodes.size() > 0) {
				getNode(0).addToHyPeerWeb(node);
				//				addNode(node);
			}
			else {
				node.setWebId(new WebId(0));
				node.setState(NodeState.HYPEERCUBECAP);
				addNode(node);				
			}
		}
	}

	public synchronized Node addNewNodeToHyPeerWeb(){
		Node node0 = new Node(63);
		addToHyPeerWeb(node0, null);
		return node0;
	}

	public synchronized Node addNewNodeToHyPeerWeb(boolean print){
		Node newNode = addNewNodeToHyPeerWeb();
		if(print)
			System.out.println("Added new node! (" + newNode.getWebId().getValue() + ")");
		return newNode;
	}


	public synchronized void setNodeAt(Node newNode) {
		nodes.remove(newNode.getWebId().getValue());
		nodes.put(newNode.getWebId().getValue(), newNode);
	}

	/**
	 * Clears the HyPeerWeb.
	 * 
	 * @pre None.
	 * @post No nodes exist.
	 */
	public synchronized void clear() {
		nodes.clear();
		distributed.ObjectDB.getSingleton().clear();
	}

	/**
	 * Determines whether the indicated node is in the HyPeerWeb.
	 * 
	 * @param node
	 *            The node to test for its presence on the current segment.
	 * @return True if the node is present on this segment.
	 * @pre node is not null.
	 * @post No side effects.
	 */
	public synchronized boolean contains(Node node) {
		return nodes.containsKey(node.getWebId().getValue());
	}

	/**
	 * Provides an instance to the single HyPeerWeb database.
	 * 
	 * @return a singleton of a HyPeerWebDatabase object.
	 * @pre None.
	 * @post No side effects.
	 */
	public synchronized HyPeerWebDatabase getHyPeerWebDatabase() {
		return HyPeerWebDatabase.getSingleton();
	}

	public synchronized LocalObjectId getLocalObjectId() {
		return localObjectId;
	}

	public synchronized Node getANodeInThisSegment() {
		assert (nodes.size() > 0);
		return nodes.values().iterator().next();
	}

	/**
	 * Gets the node at the indicated identifier.
	 * 
	 * @param webId
	 *            The ID of the node to be retrieved.
	 * @return A Node if one by that ID exists.
	 * @pre webId >= 0.
	 * @post No side effects.
	 */
	public synchronized Node getNode(int webId) {
		if (nodes.containsKey(webId)) {
			return nodes.get(webId);
		}
		return null;
	}

	/**
	 * Gets the single HyPeerWeb, if one doesn't exist then one is created.
	 * 
	 * @return an instance of the HyPeerWeb.
	 * @pre None.
	 * @post Creates a new HyPeerWeb if no singleton has been initiated.
	 */
	public synchronized static HyPeerWeb getSingleton() {
		if (singleton == null) {
			singleton = new HyPeerWeb();
		}
		return singleton;
	}

	/**
	 * Reloads the HyPeerWeb from the database using the default database name.
	 * 
	 * @pre None.
	 * @post The HyPeerWeb is cleared and all data is loaded from. the database
	 *       by a default name.
	 */
	public synchronized void reload() {
		reload(null);
	}

	/**
	 * Reloads the HyPeerWeb from the database provided. If the database name is
	 * null, then this will perform the same as reload().
	 * 
	 * @param dbName
	 *            the filename for this database.
	 * @pre None.
	 * @post The HyPeerWeb is cleared and all data is loaded from the database
	 *       specified. Given a null name, it is as if reload() were called.
	 * @see reload()
	 */
	public synchronized void reload(String dbName) {
		this.clear();
		getHyPeerWebDatabase();
		HyPeerWebDatabase.initHyPeerWebDatabase(dbName);
		loadFrom(getHyPeerWebDatabase().getAll());
	}

	/**
	 * Initializes our in-memory database using a list of simplified nodes.
	 * 
	 * @param loadedNodes
	 *            List of basic nodes to load from.
	 * @pre The database should be cleared.
	 * @post Adds instantiated nodes to the HyPeerWeb based on the list
	 *       provided.
	 */
	private void loadFrom(List<SimplifiedNodeDomain> loadedNodes) {
		// Load in all into our map
		for (SimplifiedNodeDomain n : loadedNodes) {
			Node newNode = new Node(n.webId, n.height);
			newNode.setState(n.getState());
			nodes.put(n.webId, newNode);
		}
		// Now populate the neighbors
		for (SimplifiedNodeDomain dbNode : loadedNodes) {
			Node n = getNodeByIdOrProxy(dbNode.webId, dbNode);
			// Add neighbors and surrogates
			for (Integer webId : dbNode.getNeighbors()) {
				n.addNeighbor(getNodeByIdOrProxy(webId, dbNode));
			}
			for (Integer webId : dbNode.downPointers) {
				n.addDownPointer(getNodeByIdOrProxy(webId, dbNode));
			}
			for (Integer webId : dbNode.upPointers) {
				n.addUpPointer(getNodeByIdOrProxy(webId, dbNode));
			}
			// Now folds
			if (dbNode.fold > -1) {
				// Assume that the fold is to be used then,
				// which may also be zero
				n.setFold(getNodeByIdOrProxy(dbNode.fold, dbNode));
			} else {
				n.setFold(Node.NULL_NODE);
			} if (dbNode.surrogateFold > -1) {
				Node sn = getNodeByIdOrProxy(dbNode.surrogateFold, dbNode);
				n.setSurrogateFold(sn);
			} else {
				n.setSurrogateFold(Node.NULL_NODE);
			} if (dbNode.inverseSurrogateFold > -1) {
				Node isn = getNodeByIdOrProxy(dbNode.inverseSurrogateFold, dbNode);
				n.setInverseSurrogateFold(isn);
			} else {
				n.setInverseSurrogateFold(Node.NULL_NODE);
			}
		}
	}
	private Node getNodeByIdOrProxy(int id, SimplifiedNodeDomain dom) {
		if(dom.hasGlobalIdFor(id)) {
			return new NodeProxy(dom.getGlobalIdFor(id));

		} else {
			return nodes.get(id);
		}
	}

	/**
	 * Removes a node from the HyPeerWeb.
	 * 
	 * @param node
	 *            the node to be removed.
	 * @pre node is not null, the node is present.
	 * @post The noddbNamee is no longer present in the HyPeerWeb.
	 */
	public synchronized void removeNode(Node node) {
		if (contains(node)) {
			nodes.remove(node.getWebId().getValue());
		}
	}

	/**
	 * Saves all the nodes in the HyPeerWeb and their corresponding. information
	 * in the database.
	 * 
	 * @pre None.
	 * @post Saves to the default database.
	 */
	public synchronized void saveToDatabase() {
		List<SimplifiedNodeDomain> nodeList;
		nodeList = new LinkedList<SimplifiedNodeDomain>();
		Iterator<Map.Entry<Integer, Node>> it;
		it = nodes.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Node> nodeEntry = it.next();
			Node n = nodeEntry.getValue();
			if (n instanceof NodeProxy) {
				continue;
			}
			nodeList.add(n.constructSimplifiedNodeDomain());
		}
		getHyPeerWebDatabase();
		HyPeerWebDatabase.initHyPeerWebDatabase(null);
		getHyPeerWebDatabase().save(nodeList);
	}

	public synchronized void close(){
		saveToDatabase();
	}

	/**
	 * Returns the number of nodes in the HyPeerWeb.
	 * 
	 * @return size of the HyPeerWeb segment.
	 * @pre None.
	 * @post No side effects.
	 */
	public synchronized int size() {
		return nodes.size();
	}

	/**
	 * Useful for debugging.
	 * 
	 * @pre None.
	 * @post No side effects.
	 * @return A string that gives a summary for this instance.
	 */
	public synchronized String toString() {
		return "Size: " + this.size() + "\n" + "Nodes: " + this.nodes;
	}

	public synchronized void removeFromHyPeerWeb(Node toRemove) {
		Node toRemove2 = getNode(toRemove.getWebId().getValue());
		toRemove2.removeFromHyPeerWeb();
	}

	public synchronized void removeFromHyPeerWeb(boolean print) {
		Node toRemove2 = getNode(0);
		toRemove2.removeFromHyPeerWeb();
		if(print){
			System.out.println("You removed a node!");
		}
	}
	public synchronized Node getNewNode() {
		return new Node(0);
	}

	private Object writeReplace() throws ObjectStreamException {
		ObjectDB.getSingleton().store(new LocalObjectId(-2), this);
		HyPeerWebProxy proxy = new HyPeerWebProxy(new GlobalObjectId(new LocalObjectId(-2)));
		return proxy;
	}

}
