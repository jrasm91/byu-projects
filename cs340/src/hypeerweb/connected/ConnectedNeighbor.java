package hypeerweb.connected;

import hypeerweb.node.Node;


public class ConnectedNeighbor extends ConnectedNode {

	private static final long serialVersionUID = 194420798301596119L;

	public ConnectedNeighbor(Node n){
		myNode = n;
	}

	@Override
	public void addMe(Node n) {
		myNode.addNeighbor(n);
	}

	@Override
	public void removeMe(Node n) {
		myNode.removeNeighbor(n);
		
		
		
//		Node smallest = n.getSmallestNeighborWithChild();
//
//		if(myNode != smallest && smallest != Node.NULL_NODE && smallest.getFold().getState() != NodeState.HYPEERCUBECAP){
//			if(myNode.getWebId().isSurrogateNeighborOf(n.getWebId())){
//				myNode.addDownPointer(n.getSmallestNeighborWithChild());
//				n.getSmallestNeighborWithChild().addUpPointer(myNode);
//			}
//		}
		//		if(!){
		//			myNode.addDownPointer(n.getSmallestNeighborWithChild());
		//			n.getSmallestNeighborWithChild().addUpPointer(myNode);
		//		}
	}

}
