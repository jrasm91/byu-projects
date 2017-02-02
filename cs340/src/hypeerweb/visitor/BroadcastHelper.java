package hypeerweb.visitor;

import hypeerweb.node.Node;

/**
 * A SendVisitor specialized to send a BroadcastVisitor to the 0 node and begin
 * broadcasting.
 * 
 * @author Ben Romney
 */
public class BroadcastHelper extends SendVisitor {

	private BroadcastVisitor bv;

	/**
	 * Constructor for a BroadcastHelper
	 * 
	 * @param bv
	 *            a BroadcastVisitor
	 */
	public BroadcastHelper(BroadcastVisitor bv) {
		this.bv = bv;
	}

	/**
	 * Performs the SendVisitor visit method. Sends to node 0 then broadcasts
	 * from there.
	 * 
	 * @param node
	 *            the node to send to, in this case node 0
	 * @param parameters
	 *            TARGET_KEY maps to node 0
	 * @pre node is node 0 and parameters TARGET_KEY maps to node 0.
	 * @post sends to node 0 then broadcasts to the rest of the HyPeerWeb.
	 */
	@Override
	protected void targetOperation(Node node, Parameters parameters) {
		node.accept(bv, parameters);
	}

}
