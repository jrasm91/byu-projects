package hypeerweb.node;

import java.io.ObjectStreamException;

import distributed.*;
import hypeerweb.*;
import hypeerweb.visitor.*;
public class NodeProxy extends Node {
	
    public NodeProxy(GlobalObjectId globalObjectId){
    	super("potatoe");
        this.globalObjectId = globalObjectId;
    }

    public java.lang.String toString(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "toString", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (java.lang.String)result;
    }
    
    public void setConnections(hypeerweb.node.Connections p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Connections";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "setConnections", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public int compareTo(Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "compareTo", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Integer)result;
    }

    public NodeState getState(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "getState", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (NodeState)result;
    }

    public void accept(Visitor p0, Parameters p1){
       p0.visit(this, p1);
    }

    public void setState(NodeState p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.NodeState";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "setState", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void setFold(Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "setFold", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public SimplifiedNodeDomain constructSimplifiedNodeDomain(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "constructSimplifiedNodeDomain", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (SimplifiedNodeDomain)result;
    }
    
    public GlobalObjectId getGlobalObjectId(){
    	return globalObjectId;
    }

    public WebId getWebId(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "getWebId", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (WebId)result;
    }

    public void addToHyPeerWeb(Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "addToHyPeerWeb", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void addNeighbor(Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "addNeighbor", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeFromHyPeerWeb(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "removeFromHyPeerWeb", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public Node getSmallestNeighborWithChild(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "getSmallestNeighborWithChild", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Node)result;
    }

    public void addDownPointer(Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "addDownPointer", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void addUpPointer(Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "addUpPointer", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void setSurrogateFold(Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "setSurrogateFold", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void setInverseSurrogateFold(Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "setInverseSurrogateFold", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeUpPointer(Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "removeUpPointer", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeDownPointer(Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "removeDownPointer", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeNeighbor(Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "removeNeighbor", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public boolean hasChild(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "hasChild", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Boolean)result;
    }
    
    public hypeerweb.HyPeerWeb getMyHyPeerWeb(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "getMyHyPeerWeb", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.HyPeerWeb)result;
    }

    public void setWebId(WebId p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.WebId";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "setWebId", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public Node asNode(){
        return this;
    }

    public Contents getContents(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "getContents", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Contents)result;
    }

    public Node getClosestNodeTo(int p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "int";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "getClosestNodeTo", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Node)result;
    }

    public void updateChildList(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "updateChildList", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public NodeInterface getNodeByWebId(int p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "int";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "getNodeByWebId", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.NodeInterface)result;
    }

    public hypeerweb.node.Connections getConnections(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "getConnections", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Connections)result;
    }

    public boolean equals(java.lang.Object p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "java.lang.Object";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "java.lang.Object", "equals", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Boolean)result;
    }

    public int hashCode(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "java.lang.Object", "hashCode", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Integer)result;
    }
    public hypeerweb.node.Node MigrateTo(distributed.GlobalObjectId p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "distributed.GlobalObjectId";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "MigrateTo", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Node)result;
    }

    public void setContent(java.lang.String p0, java.lang.Object p1){
        String[] parameterTypeNames = new String[2];
        parameterTypeNames[0] = "java.lang.String";
        parameterTypeNames[1] = "java.lang.Object";
        Object[] actualParameters = new Object[2];
        actualParameters[0] = p0;
        actualParameters[1] = p1;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "setContent", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }
    
//    protected Object readResolve() throws ObjectStreamException {
//		GlobalObjectId global2 = new GlobalObjectId();
//		if(globalObjectId.onSameMachineAs(global2)){
//			Node node =  (Node)ObjectDB.getSingleton().getValue(globalObjectId.getLocalObjectId());
//			return node;
//		}
//		return this;
//	}

    

}
