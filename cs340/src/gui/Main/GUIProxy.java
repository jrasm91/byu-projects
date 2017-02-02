package gui.Main;

import distributed.*;
public class GUIProxy
    extends GUI
{
    private GlobalObjectId globalObjectId;

    public GUIProxy(GlobalObjectId globalObjectId){
    	super(3);
        this.globalObjectId = globalObjectId;
    }

    public distributed.GlobalObjectId getOtherHyPeerWebAddress(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "gui.Main.GUI", "getOtherHyPeerWebAddress", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (distributed.GlobalObjectId)result;
    }

    public void setOtherHyPeerWebAddress(distributed.GlobalObjectId p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "distributed.GlobalObjectId";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "gui.Main.GUI", "setOtherHyPeerWebAddress", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public gui.Main.HyPeerWebDebugger getHyPeerWebDebugger(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "gui.Main.GUI", "getHyPeerWebDebugger", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (gui.Main.HyPeerWebDebugger)result;
    }

    public hypeerweb.HyPeerWeb getHyPeerWeb(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "gui.Main.GUI", "getHyPeerWeb", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.HyPeerWeb)result;
    }

    public void printToTracePanel(java.lang.Object p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "java.lang.Object";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "gui.Main.GUI", "printToTracePanel", parameterTypeNames, actualParameters, false);
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
    
    public void increaseSize(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "gui.Main.GUI", "increaseSize", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }
    
    public void decreaseSize(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "gui.Main.GUI", "decreaseSize", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

}
