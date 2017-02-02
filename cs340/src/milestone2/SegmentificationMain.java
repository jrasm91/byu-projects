package milestone2;

import gui.Main.GUI;
import hypeerweb.HyPeerWeb;
import distributed.GlobalObjectId;
import distributed.LocalObjectId;
import distributed.ObjectDB;
import distributed.PeerCommunicator;
import distributed.PortNumber;

public class SegmentificationMain {

	public static void main(String[] args) {
		HyPeerWeb hypeerweb = HyPeerWeb.getSingleton();
		GlobalObjectId gid = new GlobalObjectId();
		System.out.println(gid);
		System.out.println(hypeerweb.getLocalObjectId());
		
		
		GUI.getSingleton(hypeerweb).setOtherHyPeerWebAddress(
				new GlobalObjectId(
//						"192.168.2.102",  			//segment1 (baseball)
//						"192.168.254.102",			//segment2 (jason's laptop)
						"192.168.2.106",			//(cricket)
//						"192.168.254.177",			//(ben's laptop)
						gid.getPortNumber(), 
						hypeerweb.getLocalObjectId()));

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
