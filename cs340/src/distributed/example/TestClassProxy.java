package distributed.example;

import distributed.Command;
import distributed.GlobalObjectId;
import distributed.LocalObjectId;
import distributed.PeerCommunicator;

public class TestClassProxy
extends TestClass
{
private GlobalObjectId globalObjectId;

public TestClassProxy(GlobalObjectId globalObjectId){
    this.globalObjectId = globalObjectId;
}

public java.lang.String toString(){
    String[] parameterTypeNames = new String[0];
    Object[] actualParameters = new Object[0];
    Command command = new Command(globalObjectId.getLocalObjectId(), "distributed.example.TestClass", "toString", parameterTypeNames, actualParameters, true);
    Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
    return (java.lang.String)result;
}

public java.lang.String getName(){
    String[] parameterTypeNames = new String[0];
    Object[] actualParameters = new Object[0];
    Command command = new Command(globalObjectId.getLocalObjectId(), "distributed.example.TestClass", "getName", parameterTypeNames, actualParameters, true);
    Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
    return (java.lang.String)result;
}

public void setName(java.lang.String p0){
    String[] parameterTypeNames = new String[1];
    parameterTypeNames[0] = "java.lang.String";
    Object[] actualParameters = new Object[1];
    actualParameters[0] = p0;
    Command command = new Command(globalObjectId.getLocalObjectId(), "distributed.example.TestClass", "setName", parameterTypeNames, actualParameters, false);
    PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
}

public void setAge(int p0){
    String[] parameterTypeNames = new String[1];
    parameterTypeNames[0] = "int";
    Object[] actualParameters = new Object[1];
    actualParameters[0] = p0;
    Command command = new Command(globalObjectId.getLocalObjectId(), "distributed.example.TestClass", "setAge", parameterTypeNames, actualParameters, false);
    PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
}

public int[] testMethod(int[] p0){
    String[] parameterTypeNames = new String[1];
    parameterTypeNames[0] = "[I";
    Object[] actualParameters = new Object[1];
    actualParameters[0] = p0;
    Command command = new Command(globalObjectId.getLocalObjectId(), "distributed.example.TestClass", "testMethod", parameterTypeNames, actualParameters, true);
    Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
    return (int[])result;
}

public int getAge(){
    String[] parameterTypeNames = new String[0];
    Object[] actualParameters = new Object[0];
    Command command = new Command(globalObjectId.getLocalObjectId(), "distributed.example.TestClass", "getAge", parameterTypeNames, actualParameters, true);
    Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
    return (Integer)result;
}

public LocalObjectId getLocalObjectId(){
    String[] parameterTypeNames = new String[0];
    Object[] actualParameters = new Object[0];
    Command command = new Command(globalObjectId.getLocalObjectId(), "distributed.example.TestClass", "getLocalObjectId", parameterTypeNames, actualParameters, true);
    Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
    return (LocalObjectId)result;
}

public int hashCode(){
    String[] parameterTypeNames = new String[0];
    Object[] actualParameters = new Object[0];
    Command command = new Command(globalObjectId.getLocalObjectId(), "java.lang.Object", "hashCode", parameterTypeNames, actualParameters, true);
    Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
    return (Integer)result;
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

}