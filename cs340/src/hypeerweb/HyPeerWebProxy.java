package hypeerweb;

import java.io.ObjectStreamException;

import distributed.Command;
import distributed.GlobalObjectId;
import distributed.LocalObjectId;
import distributed.ObjectDB;
import distributed.PeerCommunicator;

public class HyPeerWebProxy extends HyPeerWeb {
    
	private static final long serialVersionUID = 1319126796624102110L;
	private GlobalObjectId globalObjectId;

    public HyPeerWebProxy(GlobalObjectId globalObjectId){
    	super(3);
        this.globalObjectId = globalObjectId;
    }

    @Override
    public java.lang.String toString(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "toString", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (java.lang.String)result;
    }
    public synchronized void removeFromHyPeerWeb(boolean p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "boolean";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "removeFromHyPeerWeb", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }
    public hypeerweb.node.Node addNewNodeToHyPeerWeb(boolean p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "boolean";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "addNewNodeToHyPeerWeb", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Node)result;
    }
    @Override
    public void clear(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "clear", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }
    @Override
    public boolean contains(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "contains", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Boolean)result;
    }
    @Override
    public int size(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "size", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Integer)result;
    }
    @Override
    public void close(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "close", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }
    @Override
    public void addNode(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "addNode", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }
    @Override
    public void addToHyPeerWeb(hypeerweb.node.Node p0, hypeerweb.node.Node p1){
        String[] parameterTypeNames = new String[2];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        parameterTypeNames[1] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[2];
        actualParameters[0] = p0;
        actualParameters[1] = p1;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "addToHyPeerWeb", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }
    @Override
    public hypeerweb.node.Node addNewNodeToHyPeerWeb(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "addNewNodeToHyPeerWeb", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Node)result;
    }
    @Override
    public hypeerweb.node.Node getNode(int p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "int";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "getNode", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Node)result;
    }
    @Override
    public void setNodeAt(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "setNodeAt", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }
    @Override
    public hypeerweb.HyPeerWebDatabase getHyPeerWebDatabase(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "getHyPeerWebDatabase", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.HyPeerWebDatabase)result;
    }

    @Override
    public distributed.LocalObjectId getLocalObjectId(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "getLocalObjectId", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (distributed.LocalObjectId)result;
    }
    @Override
    public hypeerweb.node.Node getANodeInThisSegment(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "getANodeInThisSegment", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Node)result;
    }    
    @Override
    public void reload(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "reload", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }
    @Override
    public void reload(java.lang.String p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "java.lang.String";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "reload", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }
    @Override
    public void removeNode(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "removeNode", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }
    
    @Override
    public void removeFromHyPeerWeb(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "removeFromHyPeerWeb", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }
    
    @Override
    public void saveToDatabase(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "saveToDatabase", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }
    @Override
    public boolean equals(java.lang.Object p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "java.lang.Object";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "java.lang.Object", "equals", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Boolean)result;
    }
    @Override
    public int hashCode(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "java.lang.Object", "hashCode", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Integer)result;
    }
    @Override
        public hypeerweb.node.Node getNewNode(){
            String[] parameterTypeNames = new String[0];
            Object[] actualParameters = new Object[0];
            Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "getNewNode", parameterTypeNames, actualParameters, true);
            Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
            return (hypeerweb.node.Node)result;
        }
    
    protected Object readResolve() throws ObjectStreamException {
		GlobalObjectId global2 = new GlobalObjectId();
		if(globalObjectId.onSameMachineAs(global2)){
			HyPeerWeb web =  (HyPeerWeb)ObjectDB.getSingleton().getValue(new LocalObjectId(-2));
			return web;
		}
		return this;
	}

}
