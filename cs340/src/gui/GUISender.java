package gui;

import gui.Main.GUI;
import hypeerweb.node.Node;
import hypeerweb.visitor.Parameters;
import hypeerweb.visitor.SendVisitor;

/**
 * Sends a message from the start node (the first node to receive this visitor)
 * the indicated target node. As it traverses intermediate nodes it prints trace
 * messages to the "TraceInformation" section of the GUI. When it gets to the
 * target, it prints the target's webId and the message.
 * 
 * <pre>
 * 		<b>Domain:</b> <i>None</i>
 * </pre>
 */
public class GUISender extends SendVisitor {
	/**
	 * The default constructor. It does nothing but call the superclass's
	 * default constructor.
	 * 
	 * @pre <i>None</i>
	 * @post super.post-condition
	 */
	public GUISender() {
		super();
	}

	/**
	 * Creates the parameters needed to send the GUISender visitor to the first
	 * Node.
	 * 
	 * @param target
	 *            the target of the send operation.
	 * @param message
	 *            the message to be sent to the target node
	 * 
	 * @pre &exist; node (node &isin; HyPeerWeb AND node.webId = target)
	 * @post result &ne; null AND result.contains(MESSAGE_KEY) AND
	 *       result.get(MESSAGE_KEY) = message
	 */
	public static Parameters createInitialParameters(int target, String message) {
		Parameters p = SendVisitor.createInitialParameters(target);
		p.set(MESSAGE_KEY, message);
		return p;
	}

	@Override
	/**
	 * Prints a string in the TracePanel of the GUI.  The string should contain the labeled webId of the current node
	 * (the target node) and the message.
	 * 
	 * @pre node &ne; null AND node &isin; HyPeerWeb AND parameters &ne; null AND parameters.contains(MESSAGE_KEY)
	 * @post A string with the current node's id and message should be printed on the tracePanel of the GUI.<br>
	 * Required format: "Target node = " node.getWebId() + ", message = '" parameters.get(MESSAGE_ID) "'.\n"
	 */
	protected void targetOperation(Node node, Parameters parameters) {
		String trace = ((parameters.get(TRACE_KEY) == null) ? "Sending message...\n"
				: (String) parameters.get(TRACE_KEY));
		String message = trace + "Target node = "
				+ node.getWebId() + ", message = '"
				+ parameters.get(MESSAGE_KEY) + "'.\n\n";
		parameters.set(TRACE_KEY, message);
	}

	/**
	 * Prints a string in the TracePanel of the GUI. The string should contain
	 * the labeled webId of the target node and the labeled webId of the current
	 * node.
	 * 
	 * @pre node &ne; null AND node &isin; HyPeerWeb AND parameters &ne; null
	 *      AND parameters.contains(TARGET_KEY)
	 * @post A string with the target node's id and the current node's id should
	 *       be printed on the tracePanel of the GUI.<br>
	 *       Required format: "Sending message to node = "
	 *       parameters.get(TARGET_ID) ", currently at node " node.getWebId()
	 *       ".\n"
	 */
	protected void intermediateOperation(Node node, Parameters parameters) {
		String traceValue = ((parameters.get(TRACE_KEY) == null) ? "Sending message...\n"
				: (String) parameters.get(TRACE_KEY));
		String message = traceValue + "Sending message to node = "
				+ Integer.toBinaryString((Integer) parameters.get(TARGET_KEY))
				+ ", currently at node " + node.getWebId() + ".\n";
		parameters.set(TRACE_KEY, message);
	}

	/**
	 * The message parameter identifier to be used to add messages to the
	 * parameter list.
	 */
	protected static final String MESSAGE_KEY = "message";
	public static final String TRACE_KEY = "trace";

}
