package hypeerweb.visitor;

import hypeerweb.node.Node;

/***
 * An interface for SendVisitor and BroadcastVisitor. Makes use of the Visitor
 * Pattern.
 * 
 * @author Adam Christiansen
 */
public interface Visitor {
	
	
	/**
	 * The visit method for the visitor pattern.
	 * 
	 * @param node
	 *            The node visited
	 * @param parameters
	 *            The parameters passed to the node then to the visitor
	 * @pre node is not null and parameters is not null.
	 * @post true.
	 */
	public void visit(Node node, Parameters parameters);
	
}
