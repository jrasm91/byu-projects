package hypeerweb.node;

import distributed.*;
public class ConnectionsProxy
    extends Connections
{
    private GlobalObjectId globalObjectId;

    public ConnectionsProxy(GlobalObjectId globalObjectId){
    	super("i love potatos");
        this.globalObjectId = globalObjectId;
    }

    public hypeerweb.node.Node getClosestNodeTo(int p0, hypeerweb.node.Node p1){
        String[] parameterTypeNames = new String[2];
        parameterTypeNames[0] = "int";
        parameterTypeNames[1] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[2];
        actualParameters[0] = p0;
        actualParameters[1] = p1;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "getClosestNodeTo", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Node)result;
    }

    public hypeerweb.node.WebId getWebId(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "getWebId", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.WebId)result;
    }

    public hypeerweb.node.NodeInterface getNodeByWebId(int p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "int";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "getNodeByWebId", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.NodeInterface)result;
    }

    public void addNeighbor(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "addNeighbor", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeNeighbor(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "removeNeighbor", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void addUpPointer(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "addUpPointer", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeUpPointer(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "removeUpPointer", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void addDownPointer(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "addDownPointer", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeDownPointer(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "removeDownPointer", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeMe(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "removeMe", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void addMe(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "addMe", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void updateEveryoneChildList(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "updateEveryoneChildList", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public java.util.Set getSmallerNeighborsWithChild(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "getSmallerNeighborsWithChild", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (java.util.Set)result;
    }

    public java.util.Set getBiggerNeighbors(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "getBiggerNeighbors", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (java.util.Set)result;
    }

    public java.util.Set getNeighbors(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "getNeighbors", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (java.util.Set)result;
    }

    public hypeerweb.node.Node getBiggestNeighbor(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "getBiggestNeighbor", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Node)result;
    }

    public hypeerweb.node.Node getSmallestNeighborWithoutChild(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "getSmallestNeighborWithoutChild", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Node)result;
    }

    public hypeerweb.node.Node getSmallestNeighborWithChild(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "getSmallestNeighborWithChild", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Node)result;
    }

    public hypeerweb.node.Node getSmallestSurrogateNeighbor(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "getSmallestSurrogateNeighbor", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Node)result;
    }

    public hypeerweb.node.Node getBiggestInverseSurrogateNeighbor(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "getBiggestInverseSurrogateNeighbor", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Node)result;
    }

    public java.util.Set getUpPointers(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "getUpPointers", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (java.util.Set)result;
    }

    public void setUpPointers(java.util.Set p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "java.util.Set";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "setUpPointers", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public java.util.Set getDownPointers(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "getDownPointers", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (java.util.Set)result;
    }

    public void setDownPointers(java.util.Set p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "java.util.Set";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "setDownPointers", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public hypeerweb.node.Node getFold(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "getFold", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Node)result;
    }

    public void setFold(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "setFold", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public hypeerweb.node.Node getSurrogateFold(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "getSurrogateFold", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Node)result;
    }

    public void setSurrogateFold(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "setSurrogateFold", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public hypeerweb.node.Node getInverseSurrogateFold(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "getInverseSurrogateFold", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Node)result;
    }

    public void setInverseSurrogateFold(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "setInverseSurrogateFold", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void setWebId(hypeerweb.node.WebId p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.WebId";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "setWebId", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }
    public void copyConnections(hypeerweb.node.Connections p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Connections";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Connections", "copyConnections", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
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

    public java.lang.String toString(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "java.lang.Object", "toString", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (java.lang.String)result;
    }

    public int hashCode(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "java.lang.Object", "hashCode", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Integer)result;
    }

}
