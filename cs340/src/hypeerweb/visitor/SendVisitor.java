package hypeerweb.visitor;

import hypeerweb.node.Node;

import java.util.ArrayList;

/***
 * Used to send a message from a source node to a target node. The actual
 * message is the targetOperation to be performed on the target Node. The
 * "targetOperation" is abstract and is to be overridden in a visitor that does
 * the actual work. There is also an intermediateOperation that may be performed
 * on nodes visited on the way to the target node.
 * 
 * @author Adam Christiansen
 */

public abstract class SendVisitor implements Visitor {

	protected static final String TARGET_KEY = "target key";

	/**
	 * Default constructor for a SendVisitor.
	 * 
	 * @pre None.
	 * @post Constructs a new SendVisitor.
	 */
	public SendVisitor() {

	}

	/**
	 * The abstract operation to be performed on the targetNode. Must be
	 * overridden in any concrete subclass.
	 * 
	 * @param node
	 *            the target node this operation is to be performed on
	 * @param parameters
	 *            the list of parameters to be passed to the target operation
	 * @pre node is not null and parameters is not null.
	 * @post TRUE.
	 */
	protected abstract void targetOperation(Node node, Parameters parameters);

	/**
	 * If the webId of the node = the target in the parameters then the
	 * targetOperation method is performed on the node. Otherwise the
	 * itermediateOperation method is performed on the node, a node closer to
	 * the target node is found, and the accept method is executed on that node
	 * passing in this.
	 * 
	 * @param node
	 *            the node being visited in the visitor pattern
	 * @param parameters
	 *            the parameters being passed along to the node then to the
	 *            SendVisitor
	 * @pre + node is not null and parameters is not null + node who's webId =
	 *      target is in the HyPeerWeb and TARGET_KEY, target is in parameters.
	 * @post if node is the target, perform targetOperation else perform the
	 *       intermediateOperation and accept on a closer node to the target.
	 */
	@Override
	public void visit(Node node, Parameters parameters) {
		assert (node != null);
		assert (parameters != null);
		assert (parameters.get(TARGET_KEY) != null);

		int target = (Integer) parameters.get(TARGET_KEY);
		if (node.getWebId().getValue() == target) {
			targetOperation(node, parameters);
		} else {
			intermediateOperation(node, parameters);
			Node nextNode = node.getClosestNodeTo(target);
			nextNode.accept(this, parameters);
		}
	}

	/**
	 * The SendVisitor visitor expects the parameters to contain a target. This
	 * method initializes the parameters with the key-pair (TARGET_KEY, target).
	 * If more parameters are required in a subclass this method is overridden.
	 * Normally this method is only called by the source node before sending the
	 * "message".
	 * 
	 * @param target
	 *            The webId of the node on which we are to perform the target
	 *            operation
	 * @return Parameters object mapping TARGET_KEY to the target node's webId.
	 * @pre There is a node in the HyPeerWeb who's webId = target.
	 * @post Parameters object maps TARGET_KEY to the target.
	 */
	public static Parameters createInitialParameters(int target) {
		Parameters params = new Parameters();
		params.set(TARGET_KEY, target);
		return params;
	}

	/**
	 * While sending the message to the target node, this operation keeps track
	 * of which nodes are visited and pass the message on.
	 * 
	 * @param node
	 *            the intermediate node
	 * @param parameters
	 *            the list of parameters to be passed to the target operation
	 * @pre node is not null and parameters is not null.
	 * @post TRUE.
	 */
	@SuppressWarnings("unchecked")
	protected void intermediateOperation(Node node, Parameters parameters) {
		if (parameters.containsKey("trace intermediates")) {
			System.out.println("Intermediate: " + node.getWebId() + " ("
					+ node.getWebId().getValue() + ")");
		}
		if (parameters.containsKey("intermediate list")) {
			((ArrayList<Integer>) parameters.get("intermediate list")).add(node
					.getWebId().getValue());
		}
	}
}
