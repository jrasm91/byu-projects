package hypeerweb.connected;

import hypeerweb.node.Node;

public class ConnectedUpPointer extends ConnectedNode {

	private static final long serialVersionUID = -2753713787401243847L;

	public ConnectedUpPointer(Node n){
		myNode = n;
	}
	
	@Override
	public void addMe(Node n) {
		myNode.addDownPointer(n);
	}

	@Override
	public void removeMe(Node n) {
		myNode.removeDownPointer(n);
	}

}
