

package cs235.mindreader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MindReaderImpl implements MindReader{

	private Map<List<String>, Counter>  profile;
	private List<String> choices;
	private int playerScore;
	private int mindScore;

	public MindReaderImpl(){
		playerScore = 0;
		mindScore = 0;
		choices = new LinkedList<String>();
		profile = new HashMap<List<String>, Counter>(){
			public String toString(){
				String returner = "";
				Iterator<List<String>> iterKeys = profile.keySet().iterator();
				Iterator<Counter> iterCounter = profile.values().iterator();
				while(iterKeys.hasNext()){
					List<String> temp =  (LinkedList<String>)iterKeys.next();
					final int second = 2; 
					final int third = 3;
					returner += temp.get(0) + " " + temp.get(1) + " " + 
								temp.get(second) + " " + temp.get(third) + " " + 
								iterCounter.next() + "\n";
				}
				return returner;
			}
		}
		;
	}
	public String getProfile(){
		return profile.toString();
	}

	public String getPrediction(){

		final int full = 4;
		if(choices.size() < full)
			return "heads";
		if(!profile.containsKey(choices) && choices.size() == full)
			profile.put(new LinkedList<String>(choices), new Counter());
		return (profile.get(choices)).prediction();
	}

	/**
	 * Give the player's choice to the mind reader.
	 * Compare the choice to the prediction and increment the score.
	 * Learn how to make better predictions based on the given choice.
	 *
	 * @param choice the string "heads" if the choice is heads,
	 *         or the string "tails" if the choice is tails
	 *
	 * @throws IllegalArgumentException if choice is not "heads" or "tails"
	 */
	public void makeChoice(String choice){
		if(!(choice.equals("heads")||choice.equals("tails")))
			throw new IllegalArgumentException(); 
		
		String prediction = getPrediction();
		if(choice.equals(prediction))
			mindScore++;
		else
			playerScore++;
		
		if(profile.containsKey(choices)){
			(profile.get(choices)).add(choice);
			choices.remove(0);
		}
		choices.add(choice);
		
	}

	public int getPlayerScore(){
		return playerScore;
	}
	public int getMindReaderScore(){
		return mindScore;
	}

	/**
	 * Saves the player's profile to a file
	 * 
	 * @param filename the name of the file in which to store the profile
	 * @return true if the profile was successfully written to the file,
	 *         or false if an error occurred
	 * @throws IllegalArgumentException if filename is null
	 */
	public boolean savePlayerProfile(String filename){
		if(filename == null)
			throw new IllegalArgumentException();
		try{
			PrintWriter pw = new PrintWriter(filename);
			pw.print(profile.size() + "\n" + profile.toString());
			pw.close();
			return true;
		}
		catch(IOException e){
			System.err.println("\nSave Failed...");
			return false;
		}
		
	}

	/**
	 * Loads the player's profile from a file
	 * 
	 * @param filename the name of the file to be loaded
	 * @return true if the profile was successfully loaded from the file,
	 *         or false if an error occurred
	 * @throws IllegalArgumentException if filename is null
	 */
	public boolean loadPlayerProfile(String filename){
		if(filename == null)
			throw new IllegalArgumentException();
		try{
			Scanner scan = new Scanner(new FileReader(filename));
			scan.nextInt();
			while(scan.hasNext()){
				List<String> tempList = new LinkedList<String>();
				tempList.add(scan.next());
				tempList.add(scan.next());
				tempList.add(scan.next());
				tempList.add(scan.next());
				int tempHeads = scan.nextInt();
				int tempTails = scan.nextInt();
				Counter tempCounter = new Counter(tempHeads, tempTails);
				profile.put(tempList, tempCounter);
			}
			return true;
		}
		catch(FileNotFoundException e){
			System.err.println("\nLoad Failed...");
			return false;
		}
	}

}
