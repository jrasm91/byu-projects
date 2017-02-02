package milestone2;

import hypeerweb.HyPeerWeb;
import distributed.GlobalObjectId;
import distributed.LocalObjectId;
import distributed.ObjectDB;
import distributed.PeerCommunicator;
import distributed.PortNumber;

public class M2Segment3 {

	public static PortNumber PORT_NUMBER_3 = new PortNumber(49203);
	public static LocalObjectId LOCAL_ID_3 = new LocalObjectId(-3);

	public static void main(String[] args) {
		PeerCommunicator.createPeerCommunicator(PORT_NUMBER_3);
		
		System.out.println(new GlobalObjectId());
		
		HyPeerWeb hypeerweb = HyPeerWeb.getSingleton();
		ObjectDB.getSingleton().store(LOCAL_ID_3, hypeerweb);
		try{

			System.out.println("Starting peer comunicator (web2)");
			System.out.println("PortNumber: " + PORT_NUMBER_3);
		}catch(Exception e){
			System.err.println(e.getMessage());
			System.err.println(e.getStackTrace());
		}
	}
}
