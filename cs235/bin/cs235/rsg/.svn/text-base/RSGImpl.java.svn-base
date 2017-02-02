package cs235.rsg;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class RSGImpl implements RSG{


	private Map<String, List<String>> grammer;

	public RSGImpl(){
		grammer = new TreeMap<String, List<String>>();
	}

	/**
	 * Generate a random sentence using the loaded grammar
	 * 
	 * @return a sentence generated randomly from the grammar.
	 */
	public String generateSentence() {
		return generateSentence("<start>");
	
	}

	public String generateSentence(String string){
		Random r = new Random();
		List<String> theList = grammer.get(string);
		if(theList.size() == 0)
			System.out.println("\nFAILURE!!!");
		String temp = theList.get(r.nextInt(theList.size())); // random string from list
		Scanner scan = new Scanner(temp);
		String sent = new String("");
		String next = new String("");

		while(scan.hasNext()){
			next = scan.next();
			if(next.contains("<"))
				sent += generateSentence(next);
			else
				sent += next + " ";
		}
		return sent;
	}
	
	public boolean loadGrammar(String filename){
		if(filename == null)
			throw new IllegalArgumentException();

		try {
			Scanner scan = new Scanner(new FileReader(filename));
			//scan.useDelimiter("[\n\t]");
			while(scan.hasNext()){
				String nextScan = scan.next();
				while(!nextScan.equals("{") && scan.hasNext()) // searches for next "{"
					nextScan = scan.next();
				if(!scan.hasNext())                            // makes sure there is code left
					break;
				nextScan = scan.next();
				List<String> tempList = new LinkedList<String>();
				tempList.add(nextScan); // the rule (key)
				//System.out.println("Next1: " + nextScan);
				nextScan = scan.next();
				//System.out.println("Next2: " + nextScan);
				if(nextScan.equals(";")){
					tempList.add(" ");
					nextScan = scan.next();
				}
				String sentTemp = nextScan;
				while(!nextScan.equals("}")){
					nextScan = scan.next();
					if(nextScan.equals(";")){
						tempList.add(sentTemp);
						nextScan = scan.next();
						sentTemp = nextScan; 
					}
					else
						sentTemp += " " + nextScan;
				}
				grammer.put(tempList.remove(0), tempList);
			}
			return true;
		} catch (FileNotFoundException e){
			System.err.println("\nError: File Not Found Exception (Impl.loadGrammer)");
		}
		return false;
	}

	/**
	 * Save the current grammar to a file.
	 * 
	 * @param filename the name of the file in which to store the grammar
	 * @return true if the grammar was successfully written to the file,
	 *         or false if an error occurred
	 * @throws IllegalArgumentException if filename is null
	 */
	public boolean saveGrammar(String filename){
		if(filename == null)
			throw new IllegalArgumentException();

		try {
			PrintWriter pw = new PrintWriter(filename);
			Iterator<String> gi = grammer.keySet().iterator();
			while(gi.hasNext()){
				String key = (String) gi.next();
				List<String> tempList = grammer.get(key);
				pw.println("{" + "\n" + key);
				for(int i = 0; i < tempList.size(); i++){
					String it = tempList.get(i);
					if(!(it.contains(";") || it.contains("\t")))
						pw.println(tempList.get(i) + "\t;");
				}
				pw.println("}");
			}
			pw.close();
			return true;
		}
		catch (FileNotFoundException e) {
			System.err.println("\nError: File Not Found Exception (Impl.saveGrammer)");
		}
		return false;
	}

	public void printGrammer(){
		Iterator<String> it = grammer.keySet().iterator();
		while(it.hasNext()){
			String key = (String) it.next();
			List<String> list = grammer.get(key);
			for(int i = 0; i < list.size(); i++){
				System.out.println("Key: " + key + "\tList [" + i + "] " + list.get(i));
			}
			System.out.println();
		}
	}

}
