package milestone2;

import hypeerweb.HyPeerWeb;
import distributed.GlobalObjectId;
import distributed.LocalObjectId;
import distributed.ObjectDB;
import distributed.PeerCommunicator;
import distributed.PortNumber;

public class M2Segment1 {

	public static PortNumber PORT_NUMBER_1 = new PortNumber(49201);
	public static LocalObjectId LOCAL_ID_1 = new LocalObjectId(-1);

	public static void main(String[] args) {
		PeerCommunicator.createPeerCommunicator(PORT_NUMBER_1);
		
		System.out.println(new GlobalObjectId());
		
		HyPeerWeb hypeerweb = HyPeerWeb.getSingleton();
		ObjectDB.getSingleton().store(LOCAL_ID_1, hypeerweb);
		try{

			System.out.println("Starting peer comunicator (web1)");
			System.out.println("PortNumber: " + PORT_NUMBER_1);
		}catch(Exception e){
			System.err.println(e.getMessage());
			System.err.println(e.getStackTrace());
		}
	}
}
