package hypeerweb.visitor;

import hypeerweb.node.Node;

/**
 * A class where you make the operation function that is called on every node in
 * the HyPeerWeb _assuming that the BroadcastHelper goes to the 0th node before
 * starting the broadcast._
 * 
 * 
 * To Broadcast, we utilize a send visitor in the form of a helper
 * so that logic is decoupled from actual broadcast and navigating to the root
 * node.
 * 
 * To use the broadcaster, follow this form
 * 
 *     BroadcastHelper helpVisitor = new BroadcastHelper(new BroadcastVisitor() {
 *			@Override
 *    		protected void operation(Node node, Parameters parameters) {
 *				int pos = 0; 
 *				if (parameters.get(COUNTER_KEY) != null) {
 *					pos = (int)(Integer)parameters.get(COUNTER_KEY);
 *				}
 *				node.getContents().set(COUNTER_KEY, pos);
 *				
 *				parameters.set(COUNTER_KEY, ++pos);
 *			}
 *		});
 *		//Start from an arbitrary node
 *		//Where 0 is the root node
 *		somenode.accept(helpVisitor, SendVisitor.createInitialParameters(0));
 * 
 * @author Levi Schuck
 * 
 */
public abstract class BroadcastVisitor implements Visitor {

	protected static final String STARTED_KEY = "started key";

	/**
	 * Default constructor for a BroadcastVisitor.
	 * 
	 * @pre None.
	 * @post Constructs a new BroadcastVisitor.
	 */
	public BroadcastVisitor() {

	}

	/**
	 * The abstract operation to be performed on all nodes. This operation must
	 * be implemented in all concrete subclasses.
	 * 
	 * @param node
	 *            the node the operation is to be performed on.
	 * @param parameters
	 *            the parameters needed to perform the operation.
	 * @pre node ≠ null AND parameters ≠ null.
	 * @post TRUE.
	 */
	protected abstract void operation(Node node, Parameters parameters);

	/**
	 * Creates the minimum set of parameters needed when invoking an accept
	 * method during a broadcast. At the top level (this level) there are no
	 * required parameters. If there are more required parameters in a subclass,
	 * this method is overridden.
	 * 
	 * @return new Parameters object.
	 * @pre NONE.
	 * @post |result| = 0
	 */
	public static Parameters createInitialParameters() {
		return new Parameters();
	}

	/**
	 * The visit operation called by a node in the accept method implementing
	 * the broadcast visitor pattern.
	 * 
	 * @param node
	 *            the node being visited
	 * @param parameters
	 *            the parameters used during the broadcast
	 * @pre node ≠ null AND parameters ≠ null
	 * @post parameters.containsKey(STARTED_KEY) ⇒ operation(node,
	 *       parameters).post-condition AND ∀ neighbor (neighbor ∈
	 *       node.neighbors AND neighbor has not and will not be visited by any
	 *       other node ⇒ neighbor.accept(this, parameters).post-condition) ELSE
	 *       parameters.containsKey(STARTED_KEY) AND ∃ node0 (node0 ∈ HyPeerWeb
	 *       AND node0.webId = 0 AND node0.accept(this,
	 *       parameters).post-condition)
	 */
	public void visit(Node node, Parameters parameters) {
		parameters.set(STARTED_KEY, true);
		operation(node, parameters);
		char[] webId = node.getWebId().toString().toCharArray();
		int index = 0;
		for (char digit : webId) {
			if (digit == '1')
				break;
			webId[index] = '1';
			int id = Integer.parseInt(new String(webId), 2);
			node.getNodeByWebId(id).accept(this, parameters);
			webId[index] = '0';
			index++;
		}
	}
}
