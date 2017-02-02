package hypeerweb.connected;

import hypeerweb.node.Node;

public class ConnectedFold extends ConnectedNode {

	private static final long serialVersionUID = 8670507342279329449L;

	public ConnectedFold(Node n){
		myNode = n;
	}

	@Override
	public void addMe(Node n) {
		myNode.setFold(n);
	}

	@Override
	public void removeMe(Node n) {
		if(n.getWebId().getValue() != myNode.getWebId().getValue()){
			myNode.setFold(Node.NULL_NODE);
		}
	}

}
