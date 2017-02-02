package cs142.lab02;


public class PART3 {

	public static void main(String[] args) {
		
		String byuFightSong =
		"\nRise all loyal cougars and hurl your challenge to the foe. \n" +
		"You will fight, day or night, rain or snow. \n" +
		"Loyal, strong, and true \n" +
		"Wear the white and blue. \n" +
		"While we sing, get set to spring. \n" +
		"Come on Cougars it's up to you. Oh! \n" +

		"Rise and shout, the Cougars are out \n" +
		"along the trail to fame and glory. \n" +
		"Rise and shout, our cheers will ring out \n" +
		"As you unfold your vict'ry story. \n\n" +

		"On you go to vanquish the foe for Alma Mater's sons and daughters. \n" +
		"As we join in song, in praise of you, our faith is strong. \n" +
		"We'll raise our colors high in the blue \n" +
		"And cheer our Cougars of BYU.";
		
		String opu = byuFightSong;
		opu = opu.replaceAll("b", "o");
		opu = opu.replaceAll("y", "p");
		opu = opu.replaceAll("B", "O");
		opu = opu.replaceAll("Y", "P");
		String opuFightSong = opu;
		
		System.out.println("BYU Fight Song");
		System.out.println(byuFightSong);
		System.out.println(byuFightSong.toUpperCase());
		System.out.println("\nOPU Fight Song");
		System.out.println(opuFightSong);
		System.out.println("\nBYU Fight Song length: " + byuFightSong.length());
		System.out.println("Index of first \"OPU\": " + opuFightSong.indexOf("OPU"));
		System.out.println("Index of first \"b\": " + byuFightSong.indexOf("b"));
		System.out.println("Index of first \"y\": " + byuFightSong.indexOf("y"));
		System.out.println("Index of first \"u\": " + byuFightSong.indexOf("u"));
		
		
		
		
		

		
		
		
	}

}
