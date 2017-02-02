package cs235.boggle;


public class Test {

	public static void main(String[] args){

		testLoadDictionary();
		testIsValidWord();
		testIsValidPrefix();
		testSetBoard();
		testGetAllValidWords();
		testIsOnBoard();
		testQuWords();
	}



	public static void test(boolean pass){
		if(pass) System.out.println("...\t...\t...\t...\t...\t...\tTest: Passed");
		else System.out.println("...\t...\t...\t...\t...\t...\tTest: Failed");
	}
	
	public static void testQuWords(){
		BogglePlayerImpl test1 = (BogglePlayerImpl)BoggleFactory.createBogglePlayer();
		test1.loadDictionary("dictionary.txt");
		test1.setBoard(new String[] {
				"x","e", "a", 
				"n", "e","qu",
				"s", "u","o"});
//		test1.setBoard(new String[] {
//				"t","s", "l", "t", "n",
//				"a", "e","o", "d", "s",
//				"a", "t","h", "s", "m",
//				"y", "qu","o", "g", "u",
//				"t", "i","e", "o", "i"});
		System.out.println("Testing isOnBoard(queen, aqueous)");
		System.out.println(test1.isValidWord("aqueous"));
		test(test1.getAllValidWords(2+1+2).toString().compareTo("[aqueous, ensue, queen]") == 0);
		test(test1.isOnBoard("queen").toString().compareTo("[5, 4, 1, 3]") == 0);
		test(test1.isOnBoard("aqueous").toString().compareTo("[2, 5, 4, 8, 7, 6]") == 0);
	}
	
	public static void testLoadDictionary(){
		BogglePlayer test1 = BoggleFactory.createBogglePlayer();
		try{
			System.out.println("Testing loadDictionary()");
			test1.loadDictionary("dictionary.txt"); test(true);
		}catch(IllegalArgumentException e){test(false);}
		try{
			test1.loadDictionary("JasonDictionary.txt"); test(false);
		}catch(IllegalArgumentException e){test(true);}
		try{
			test1.loadDictionary(null); test(false);
		}catch(IllegalArgumentException e){ test(true);	}
	}

	public static void testIsValidWord(){
		BogglePlayer test1 = BoggleFactory.createBogglePlayer();
		test1.loadDictionary("dictionary.txt");
		System.out.println("Testing isValidWord()");    
		test(test1.isValidWord("abdomen"));
		test(!test1.isValidWord("jason"));
		test(test1.isValidWord("a"));
		test(test1.isValidWord("zymurgy"));
		test(test1.isValidWord("maidenhood"));
		test(!test1.isValidWord("jim"));
		try{
			test1.isValidWord(null); test(false);
		}catch(IllegalArgumentException e){ test(true);}
	}

	public static void testIsValidPrefix(){
		BogglePlayer test1 = BoggleFactory.createBogglePlayer();
		test1.loadDictionary("dictionary.txt");
		System.out.println("Testing isValidPrefix()");
		test(test1.isValidPrefix("a"));
		test(test1.isValidPrefix("zymurgy")) ;
		test(test1.isValidPrefix("abdo")); 
		test(!test1.isValidPrefix("jason"));
		test(test1.isValidPrefix("zymu"));
		test(!test1.isValidPrefix("zz"));
		test(test1.isValidPrefix("jim"));
		try{
			test1.isValidPrefix(null); test(false);
		}catch(IllegalArgumentException e){ test(true);}
	}


	public static void testSetBoard(){
		BogglePlayerImpl test1 = (BogglePlayerImpl)BoggleFactory.createBogglePlayer();
		test1.loadDictionary("dictionary.txt");
		String[] test1a = {"H", "E", "B", "E", "Z", "K", "T", "S", "T"};
		test1.setBoard(test1a);
		final int three = 3;
		String[][] test2a = new String[three][three];
		test2a[0][0] = "h"; test2a[0][1] = "e"; test2a[0][2] = "b";
		test2a[1][0] = "e"; test2a[1][1] = "z"; test2a[1][2] = "k";
		test2a[2][0] = "t"; test2a[2][1] = "s"; test2a[2][2] = "t";
		System.out.println("Testing setBoard()");
		test((printDString(test1.getBoard())).compareTo(printDString(test2a)) == 0);
		try{
			test1.setBoard(null); test(false);
		}catch(IllegalArgumentException e){ test(true);}
	}
	public static void testGetAllValidWords(){
		BogglePlayerImpl test1 = (BogglePlayerImpl) BoggleFactory.createBogglePlayer();
		test1.loadDictionary("dictionary.txt");
		test1.setBoard(new String[] {"H", "E", "B", "E", "Z", "K", "T", "S", "T"});
		System.out.println("Testing getAllValidWords(4)");
		test((test1.getAllValidWords(2+2).toString()).compareTo(
		"[beet, behest, seek, skeet, test, zest]") == 0);
		test1.setBoard(new String[] {"u", "g", "i", "a", "o", "h", "s", "s", "t", 
				"u", "e", "t", "y", "n", "t", "w"});
		String list3 = test1.getAllValidWords(2+2).toString();
		String list4 = "[ashen, ashes, assent, asset, enthusiast, gist, gout, gouty, hiss, " +
		"hist, hotness, " +
		"house, hunt, issue, nest, newt, ought, oust, sash, sent, shew, shot, shout, shun, " +
		"shunt, shut, sigh, sight, stet, stew, suet, tent, tenth, test, then, thesis, thew, " +
		"this, thou, thug, thus, togs, tough, toughen, tout, tune, tush, twenty, unto, went, west]";
		test(list3.compareTo(list4) == 0);
		try{
			test1.getAllValidWords(-1); test(false);
		}catch(IllegalArgumentException e){ test(true);}
	}
	public static void testIsOnBoard(){
		BogglePlayer test1 = BoggleFactory.createBogglePlayer();
		test1.loadDictionary("dictionary.txt");		
		System.out.println("Testing isOnBoard()");
		test1.setBoard(new String[] {"H", "E", "B", "E", "Z", "K", "T", "S", "T"});
		test(test1.isOnBoard("behest").toString().compareTo("[2, 1, 0, 3, 7, 8]") == 0);
		test(test1.isOnBoard("skeet").toString().compareTo("[7, 5, 1, 3, 6]") == 0);
		test(test1.isOnBoard("jim") == null);
		try{
			test1.isOnBoard(null); test(false);
		}catch(IllegalArgumentException e){ test(true);}
	}
	private static String printDString(String[][] list){
		String out = "";
		for(int i = 0; i < list.length; i++)
			for(int j = 0; j < list.length; j++)
				out+= list[i][j] + " ";
		return out;
	}

}


















