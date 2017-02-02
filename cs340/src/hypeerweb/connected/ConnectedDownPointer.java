package hypeerweb.connected;

import hypeerweb.node.Node;

public class ConnectedDownPointer extends ConnectedNode {

	private static final long serialVersionUID = 5057012876911717525L;

	public ConnectedDownPointer(Node n){
		myNode = n;
	}
	
	@Override
	public void addMe(Node n) {
		myNode.addUpPointer(n);
	}

	@Override
	public void removeMe(Node n) {
		myNode.removeUpPointer(n);
	}

}
