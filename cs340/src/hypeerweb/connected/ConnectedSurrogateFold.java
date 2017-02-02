
package hypeerweb.connected;

import hypeerweb.node.Node;

public class ConnectedSurrogateFold extends ConnectedNode {
	
	private static final long serialVersionUID = 758366177122692024L;

	public ConnectedSurrogateFold(Node n){
		myNode = n;
	}

	@Override
	public void addMe(Node n) {
		myNode.setInverseSurrogateFold(n);
	}

	@Override
	public void removeMe(Node n) {
		myNode.setInverseSurrogateFold(Node.NULL_NODE);
	}

}
