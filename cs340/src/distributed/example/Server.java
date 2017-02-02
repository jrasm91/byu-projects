package distributed.example;

import distributed.ObjectDB;
import distributed.PeerCommunicator;

public class Server {	
	public static void main(String[] args){
		ObjectDB.setFileLocation("Database.db");
		ObjectDB.getSingleton().restore(null);
        System.out.println("Server::main(String[]) ObjectDB = ");
        ObjectDB.getSingleton().dump();
		try{
			PeerCommunicator.createPeerCommunicator();
		}catch(Exception e){
			System.err.println(e.getMessage());
			System.err.println(e.getStackTrace());
		}
	}
}