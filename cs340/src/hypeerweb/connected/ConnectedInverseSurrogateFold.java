
package hypeerweb.connected;

import hypeerweb.node.Node;

public class ConnectedInverseSurrogateFold extends ConnectedNode {
	
	private static final long serialVersionUID = 4914660359762203054L;

	public ConnectedInverseSurrogateFold(Node n){
		myNode = n;
	}

	@Override
	public void addMe(Node n) {
		myNode.setSurrogateFold(n);
	}

	@Override
	public void removeMe(Node n) {
		myNode.setSurrogateFold(Node.NULL_NODE);
	}

}
