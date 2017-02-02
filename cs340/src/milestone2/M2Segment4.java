package milestone2;

import gui.Main.GUI;
import hypeerweb.HyPeerWebProxy;

import java.net.InetAddress;

import distributed.GlobalObjectId;
import distributed.LocalObjectId;
import distributed.PeerCommunicator;
import distributed.PortNumber;

public class M2Segment4 {

	public static PortNumber PORT_NUMBER_4 = new PortNumber(49204);
	public static LocalObjectId LOCAL_ID_4 = new LocalObjectId(-4);

	public static void main(String... args){
		PeerCommunicator.createPeerCommunicator(PORT_NUMBER_4);
		System.out.println("Starting peer comunicator (gui2)");
		System.out.println("PortNumber: " + PORT_NUMBER_4);

		try{

			
			String myAddress = InetAddress.getLocalHost().getHostAddress();
			GlobalObjectId proxyWeb = new GlobalObjectId(
					myAddress,
					M2Segment3.PORT_NUMBER_3,
					M2Segment3.LOCAL_ID_3
					);
			

			String otherAddress = "192.168.254.102";			//segment2 (jason's laptop)
			GlobalObjectId otherWeb = new GlobalObjectId(
					otherAddress,
					M2Segment1.PORT_NUMBER_1,
					M2Segment1.LOCAL_ID_1
					);

			GUI.getSingleton(new HyPeerWebProxy(proxyWeb)).setOtherHyPeerWebAddress(otherWeb);
		}catch(Exception e){
			System.err.println(e.getMessage());
			System.err.println(e.getStackTrace());
		}
	}
}
