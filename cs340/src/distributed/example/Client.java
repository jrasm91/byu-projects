package distributed.example;

import java.net.InetAddress;

import distributed.GlobalObjectId;
import distributed.LocalObjectId;
import distributed.ObjectDB;
import distributed.PeerCommunicator;
import distributed.PortNumber;

public class Client {
	public static void main(String[] args){
		//Before running the client, run SetUpDatabase, and start the Server.
		ObjectDB.setFileLocation("ObjectDB.db");
		try{
    		String myIPAddress = InetAddress.getLocalHost().getHostAddress();
			GlobalObjectId serverGlobalObjectId =
				new GlobalObjectId(myIPAddress,
						           PortNumber.DEFAULT_PORT_NUMBER,
								   null);
			
			PeerCommunicator.createPeerCommunicator(new PortNumber(49201));
			
			GlobalObjectId testClassGlobalObjectId =
				new GlobalObjectId(myIPAddress,
						           PortNumber.DEFAULT_PORT_NUMBER,
						           new LocalObjectId(0)
				                  );
			TestClassProxy proxy = new TestClassProxy(testClassGlobalObjectId);
			proxy.setAge(60);
			int[] a = new int[3];
			a[0] = 1;
			a[1] = 2;
			a[2] = 3;
			int[] result = proxy.testMethod(a);
			System.out.println("result[0] = " + result[0] + ", result[1] = " + result[1] + ", result[2] = " + result[2]);
			System.out.println("Proxy's age = " + proxy.getAge());

			PeerCommunicator.stopConnection(serverGlobalObjectId);
			PeerCommunicator.stopThisConnection();
		} catch(Exception e){
		    System.err.println(e.getMessage());
		    e.printStackTrace();
		}
	}
}