package hypeerweb.node;

import hypeerweb.SimplifiedNodeDomain;
import hypeerweb.visitor.Parameters;
import hypeerweb.visitor.Visitor;

public class NullNode extends Node {

	public NullNode() {
		super();
		setState(NodeState.NULL_STATE);
	}

	@Override
	public SimplifiedNodeDomain constructSimplifiedNodeDomain() {
		return new SimplifiedNodeDomain();
	}

	@Override
	public void addToHyPeerWeb(Node newNode) {

	}

	@Override
	public void addNeighbor(Node neighbor) {
	}

	@Override
	public void removeNeighbor(Node neighbor) {
	}

	@Override
	public void addUpPointer(Node upPointer) {
	}

	@Override
	public void removeUpPointer(Node upPointer) {
	}

	@Override
	public void addDownPointer(Node downPointer) {
	}

	@Override
	public void removeDownPointer(Node downPointer) {
	}

	@Override
	public void setFold(Node newFold) {
	}

	@Override
	public void setSurrogateFold(Node newSurrogateFold) {
	}

	@Override
	public void setInverseSurrogateFold(Node newInverseSurrogateFold) {
	}

	@Override
	public WebId getWebId() {
		return WebId.NULL_WEB_ID;
	}

	@Override
	public void setWebId(WebId id) {
	}

	@Override
	public void removeFromHyPeerWeb() {
	}

	@Override
	public void setState(NodeState state) {
	}

	@Override
	public NodeState getState() {
		return NodeState.NULL_STATE;
	}

	@Override
	public Node asNode() {
		assert(false);
		return null;
	}

	@Override
	public Contents getContents() {
		return new Contents();
	}

	@Override
	public void accept(Visitor visitor, Parameters parameters) {
		//no.
	}
	@Override
	public void setConnections(Connections c) {
		//Nothing.
	}
	@Override
	public boolean equals(Object obj){
		return obj instanceof NullNode;
	}
	
}
