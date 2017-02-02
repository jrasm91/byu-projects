package cs235.mindreader;

public class TestDiver {

	
	public static void main(String[] args) {
		MindReaderImpl mr = new MindReaderImpl();
		mr.makeChoice("heads");
		mr.makeChoice("heads");
		mr.makeChoice("heads");
		mr.makeChoice("heads");
		mr.makeChoice("tails");
		mr.makeChoice("tails");
		mr.getPrediction();
		mr.savePlayerProfile("Jason");
		System.out.println(mr.getProfile());
		
//		System.out.println(mr.getPrediction());
//		System.out.println(mr.getPrediction());
//		System.out.println(mr.getPrediction());
//		System.out.println(mr.getPrediction());

	}

}
