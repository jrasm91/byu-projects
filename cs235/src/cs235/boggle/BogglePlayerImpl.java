package cs235.boggle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class BogglePlayerImpl implements BogglePlayer{


	private String[] dictionary;
	private String[][] board;
	private boolean loadDictionary;
	private boolean setBoard;

	public BogglePlayerImpl(){
		dictionary = new String[0];
		board = new String[0][0];
		loadDictionary = false;
		setBoard = false;
	}

	public void loadDictionary(String fileName){
		if(fileName == null)
			throw new IllegalArgumentException();
		try{
			Scanner scan = new Scanner(new File(fileName));
			List<String> tempDictionary = new ArrayList<String>();
			while(scan.hasNext())
				tempDictionary.add(scan.next());
			dictionary = tempDictionary.toArray(new String[0]);
			loadDictionary = true;
		}catch(FileNotFoundException e){
			throw new IllegalArgumentException("Invalid File Name");
		}
	}

	public boolean isValidWord(String wordToCheck){
		if(wordToCheck == null)
			throw new IllegalArgumentException("wordToCheck == null");
		if(!loadDictionary)
			throw new IllegalStateException("Dictionary Has Not Been Loaded");
		wordToCheck = wordToCheck.toLowerCase();
		if(Arrays.binarySearch(dictionary, wordToCheck) >= 0)
			return true;
		return false;
	}

	public boolean isValidPrefix(String prefixToCheck){
		if(prefixToCheck == null)
			throw new IllegalArgumentException("prefixToCheck == null");
		if(!loadDictionary)
			throw new IllegalStateException("Dictionary Has Not Been Loaded");
		int tempVal = Arrays.binarySearch(dictionary, prefixToCheck);
		int tempVal2 = (tempVal+1) * -1;
		if(tempVal2 > dictionary.length-1)
			return false;
		if(((tempVal2 >= 0) && dictionary[tempVal2].contains(prefixToCheck))|| (tempVal >= 0))
			return true;
		return false;
	}

	public String[][] getBoard() {
		return board;
	}
	public void setBoard(String[] letterArray){
		if(letterArray == null || (Math.sqrt(letterArray.length)) != 
			(int)(Math.sqrt(letterArray.length)))
			throw new IllegalArgumentException();
		int dim = (int)Math.sqrt(letterArray.length);
		board = new String[dim][dim];
		for(int i = 0; i < dim; i++)
			for(int j = 0; j < dim; j++)
				board[i][j] = letterArray[i*dim + j].toLowerCase();
		setBoard = true;
	}
	
	private void printBoard(){
		System.out.println();
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board.length; j++)
				System.out.print(board[i][j] + "  ");
			System.out.println();			
		}
	}

	public boolean[][] tempBoard(int dim){
		boolean[][] boolBoard = new boolean[dim][dim];
		for(int i = 0; i < dim; i++)
			for(int j = 0; j < dim; j++)
				boolBoard[i][j] = false;
		return boolBoard;
	}

	public SortedSet<String> getAllValidWords(int minimumWordLength){
		if(minimumWordLength < 1)
			throw new IllegalArgumentException("minimumWordLength < 1");
		if(!loadDictionary)
			throw new IllegalStateException("Dictionary Has Not Been Loaded");
		if(!setBoard)
			throw new IllegalStateException("Board Has Not Been Set");
		SortedSet<String> words = new TreeSet<String>();
		boolean[][] used = tempBoard(board.length);
		for(int i = 0; i < board.length; i++)
			for(int j = 0; j < board.length; j++){
				String tempWord = "";
				recGetAllValidWords(minimumWordLength, i, j, used, words, tempWord);
			}
		return words;
	}

	public void recGetAllValidWords(int minLength, int row, int col, boolean[][] used, 
			SortedSet<String> words, String tempWord){
		try{
		if(used[row][col])
			return;
		String tempWord2 = tempWord + board[row][col];
		if(isValidPrefix(tempWord2)){
			if(isValidWord(tempWord2)&& tempWord2.length() >= minLength)
				words.add(tempWord2);
			used[row][col] = true;
			recGetAllValidWords(minLength, row+1, col  , used, words, tempWord2);
			recGetAllValidWords(minLength, row,   col+1, used, words, tempWord2);
			recGetAllValidWords(minLength, row-1, col  , used, words, tempWord2);
			recGetAllValidWords(minLength, row,   col-1, used, words, tempWord2);
			recGetAllValidWords(minLength, row+1, col+1, used, words, tempWord2);
			recGetAllValidWords(minLength, row+1, col-1, used, words, tempWord2);
			recGetAllValidWords(minLength, row-1, col+1, used, words, tempWord2);
			recGetAllValidWords(minLength, row-1, col-1, used, words, tempWord2);
			used[row][col] = false;
		}else
			return;
		}catch(ArrayIndexOutOfBoundsException e){
			return ;
		}catch(NullPointerException e){
			return ;
		}
	}

	/**
	 * Determines if the given word is in on the Boggle board. If so, 
	 *	it returns the path that makes up the word.
	 * 
	 * @param wordToCheck The word to validate
	 * @return java.util.List containing java.lang.Integer objects with 
	 *	the path that makes up the word on the Boggle board. If word
	 *	is not on the boggle board, return null.
	 */
	public List<Integer> isOnBoard(String wordToCheck){

		if(wordToCheck == null)
			throw new IllegalArgumentException("wordToCheck == null");
		if(!loadDictionary)
			throw new IllegalStateException("Dictionary Has Not Been Loaded");
		if(!getAllValidWords(wordToCheck.length()).contains(wordToCheck))
			return null;
		List<Integer> pathList = new ArrayList<Integer>();
		for(int i = 0; i < board.length; i++)
			for(int j = 0; j < board.length; j++){
				boolean[][] used = tempBoard(board.length);
				findPathRec(i, j, wordToCheck, pathList, used);
				if(pathList.size() == wordToCheck.length()){
					pathList = fixPath(pathList);
					return pathList;
					
				}
				pathList.clear();
			}
		return null;
	}
	private List<Integer> fixPath(List<Integer> pathList){

		int i = 0; 
		while(i < pathList.size()){
			if(pathList.get(i) >= board.length*board.length){
				pathList.remove(i);
				i--;
			}i++;
		}
		return pathList;
	}
	public void findPathRec(int i, int j, String wordToCheck, 
			List<Integer> path, boolean[][] boolBoard){
		try{
			if(boolBoard[i][j])
				return ;
			String charToCheck = wordToCheck.charAt(path.size()) + "";

			if(charToCheck.equals("q") && wordToCheck.contains("qu")){
				charToCheck = "qu";
				path.add(board.length*board.length+1);
			}
			if(board[i][j].compareTo(charToCheck) == 0){
				path.add(i*board.length + j);
				//********************************************
				
				boolBoard[i][j] = true;
				findPathRec(i+1, j  , wordToCheck, path, boolBoard);
				findPathRec(i,   j+1, wordToCheck, path, boolBoard);
				findPathRec(i-1, j  , wordToCheck, path, boolBoard);
				findPathRec(i,   j-1, wordToCheck, path, boolBoard);
				findPathRec(i+1, j+1, wordToCheck, path, boolBoard);
				findPathRec(i+1, j-1, wordToCheck, path, boolBoard);
				findPathRec(i-1, j+1, wordToCheck, path, boolBoard);
				findPathRec(i-1, j-1, wordToCheck, path, boolBoard);
				boolBoard[i][j] = false;
				if(path.size() == wordToCheck.length())
					return;
				path.remove(path.size()-1); 
			}	
			else{
				if(path.get(path.size()-1) > board.length*board.length)
					path.remove(path.size()-1);
				return;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
			return ;
		}
		catch(StringIndexOutOfBoundsException e){
			return;
		}

	}
	/**
	 * An optional method that gives a user-defined boggle board to the GUI.
	 * 
	 * @return a String array in the same form as the input to setBoard().
	 */
	public String[] getCustomBoard(){
		String[] list = null;		
		return  list;
	}

}
