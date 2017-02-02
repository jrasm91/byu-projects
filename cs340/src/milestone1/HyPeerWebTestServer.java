package milestone1;

import hypeerweb.HyPeerWeb;
import distributed.LocalObjectId;
import distributed.ObjectDB;
import distributed.PeerCommunicator;
import distributed.PortNumber;

public class HyPeerWebTestServer {

	public static void main(String[] args) {
		HyPeerWeb hypeerweb = HyPeerWeb.getSingleton();
		
		
		try{
			System.out.println("Starting peer comunicator (server)");
			System.out.println("PortNumber: " + PortNumber.DEFAULT_PORT_NUMBER);
			PeerCommunicator.createPeerCommunicator();
		}catch(Exception e){
			System.err.println(e.getMessage());
			System.err.println(e.getStackTrace());
		}
	}
}
