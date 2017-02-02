package milestone1;

import gui.Main.GUI;
import hypeerweb.HyPeerWebProxy;
import hypeerweb.node.Node;

import java.net.InetAddress;

import distributed.GlobalObjectId;
import distributed.LocalObjectId;
import distributed.ObjectDB;
import distributed.PeerCommunicator;
import distributed.PortNumber;

public class HyPeerWebTestClient {

	public static void main(String[] args) {
		ObjectDB.setFileLocation("ObjectDB.db");
		try{
//    		String myIPAddress = InetAddress.getLocalHost().getHostAddress();
    		String myIPAddress = "192.168.2.106";
			GlobalObjectId serverGlobalObjectId =
				new GlobalObjectId(myIPAddress,
						           PortNumber.DEFAULT_PORT_NUMBER,
								   null);
			
			PeerCommunicator.createPeerCommunicator(PortNumber.DEFAULT_PORT_NUMBER);
			
			GlobalObjectId testClassGlobalObjectId =
				new GlobalObjectId(myIPAddress,
						           PortNumber.DEFAULT_PORT_NUMBER,
						           new LocalObjectId(-1)
				                  );
			
			HyPeerWebProxy proxy = new HyPeerWebProxy(testClassGlobalObjectId);
			GUI.getSingleton(proxy);
			
//			PeerCommunicator.stopConnection(serverGlobalObjectId);
//			PeerCommunicator.stopThisConnection();
		} catch(Exception e){
		    System.err.println(e.getMessage());
		    e.printStackTrace();
		}
	}

}
