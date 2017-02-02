package milestone2;

import gui.Main.GUI;
import hypeerweb.HyPeerWebProxy;

import java.net.InetAddress;

import distributed.GlobalObjectId;
import distributed.LocalObjectId;
import distributed.PeerCommunicator;
import distributed.PortNumber;

public class M2Segment2 {

	public static PortNumber PORT_NUMBER_2 = new PortNumber(49202);
	public static LocalObjectId LOCAL_ID_2 = new LocalObjectId(-2);

	public static void main(String... args){
		PeerCommunicator.createPeerCommunicator(PORT_NUMBER_2);
		System.out.println("Starting peer comunicator (gui)");
		System.out.println("PortNumber: " + PORT_NUMBER_2);

		try{

			
			String myAddress = InetAddress.getLocalHost().getHostAddress();
			GlobalObjectId proxyWeb = new GlobalObjectId(
					myAddress,
					M2Segment1.PORT_NUMBER_1,
					M2Segment1.LOCAL_ID_1
					);
			

			String guiAddress = "192.168.254.177";
			GlobalObjectId otherWeb = new GlobalObjectId(
					guiAddress,
					M2Segment3.PORT_NUMBER_3,
					M2Segment3.LOCAL_ID_3
					);

			GUI.getSingleton(new HyPeerWebProxy(proxyWeb)).setOtherHyPeerWebAddress(otherWeb);
		}catch(Exception e){
			System.err.println(e.getMessage());
			System.err.println(e.getStackTrace());
		}
	}
}
