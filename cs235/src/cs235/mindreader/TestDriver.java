

package cs235.mindreader;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Arrays;

import java.io.File;
import java.io.IOException;


// compile with javac -cp junit.jar *.java
// run with java -cp junit.jar:. cs235.mindreader.TestDriver


public class TestDriver extends junit.framework.TestCase {

    private String path = "";

    public static void main(String[] args) {

	junit.framework.TestSuite suite =
	    new junit.framework.TestSuite(TestDriver.class);
	junit.framework.TestResult result = junit.textui.TestRunner.run(suite);

	if (result.wasSuccessful()) {
	    System.out.println("All tests PASSED.");
	    System.out.println();
	} else {
	    printFailed();
	    System.exit(-1);
	}

    }


    public void testPrediction0() {
	assertPredictions(choices0, allHeads);
    }
    public void testPrediction1() {
	assertPredictions(choices1, allHeads);
    }
    public void testPrediction2() {
	assertPredictions(choices2, allHeads);
    }
    public void testPrediction3() {
	assertPredictions(choices3, allHeads);
    }

    public void testScoring0() {
	assertScores(choices0);
    }
    public void testScoring1() {
	assertScores(choices1);
    }
    public void testScoring2() {
	assertScores(choices2);
    }
    public void testScoring3() {
	assertScores(choices3);
    }

    public void testLearning0() {
	assertPredictions(choices0x, learned0);
    }
    public void testLearning1() {
	assertPredictions(choices1x, learned1);
    }
    public void testLearning2() {
	assertPredictions(choices2x, learned2);
    }
    public void testLearning3() {
	assertPredictions(choices3x, learned3);
    }

    public void testSave0() {
	assertSave(choices0x, "profile0.txt");
    }
    public void testSave1() {
	assertSave(choices1x, "profile1.txt");
    }
    public void testSave2() {
	assertSave(choices2x, "profile2.txt");
    }
    public void testSave3() {
	assertSave(choices3x, "profile3.txt");
    }

    public void testLoad0() {
	assertLoad("profile0.txt", choices0, loaded0);
    }
    public void testLoad1() {
	assertLoad("profile1.txt", choices1, loaded1);
    }
    public void testLoad2() {
	assertLoad("profile2.txt", choices2, loaded2);
    }
    public void testLoad3() {
	assertLoad("profile3.txt", choices3, loaded3);
    }



    private final String[] allHeads = {"heads", "heads", "heads", "heads"};

    private final String[] choices0 = {"heads", "heads", "heads", "heads"};
    private final String[] choices1 = {"tails", "tails", "tails", "tails"};
    private final String[] choices2 = {"tails", "heads", "tails", "heads"};
    private final String[] choices3 = {"tails", "tails", "heads", "heads"};

    private final String[]
	choices0x = {"heads", "heads", "heads", "heads", "heads", "heads", "heads", "heads"};
    private final String[]
	choices1x = {"tails", "tails", "tails", "tails", "tails", "tails", "tails", "tails"};
    private final String[]
	choices2x = {"tails", "heads", "tails", "heads", "tails", "heads", "tails", "heads"};
    private final String[]
	choices3x = {"tails", "tails", "heads", "heads", "tails", "tails", "heads", "heads"};

    private final String[]
	learned0 = {"heads", "heads", "heads", "heads", "heads", "heads", "heads", "heads"};
    private final String[]
	learned1 = {"heads", "heads", "heads", "heads", "tails", "tails", "tails", "tails"};
    private final String[]
	learned2 = {"heads", "heads", "heads", "heads", "heads", "tails", "heads", "tails"};
    private final String[]
	learned3 = {"heads", "heads", "heads", "heads", "heads", "heads", "heads", "tails"};

    private final String[] loaded0 = {"heads", "heads", "heads", "heads"};
    private final String[] loaded1 = {"heads", "heads", "heads", "tails"};
    private final String[] loaded2 = {"heads", "heads", "heads", "tails"};
    private final String[] loaded3 = {"heads", "heads", "heads", "tails"};



    private String predictText = "Testing: getPrediction";
    private String scoringText = "Testing: getMindReaderScore/getPlayerScore";
    private String saveText = "Testing: savePlayerProfile";
    private String loadText = "Testing: loadPlayerProfile";


    private void assertPredictions(String[] choices, String[] predicts) {

	printStart(predictText);

	makeMindReader();
	playGame(choices);
	assertPredictions(predicts);

	printPassed();

    }

    private void assertScores(String[] choices) {

	printStart(scoringText);

	makeMindReader();
	playGame(choices);
	assertScores();

	printPassed();

    }

    private void assertSave(String[] choices, String name) {

	printStart(saveText);

	makeMindReader();
	playGame(choices);
	assertSave(name);

	printPassed();

    }

    private void assertLoad(String name, String[] choices, String[] loaded) {

	printStart(loadText);

	makeMindReader();
	assertLoad(name);
	playGame(choices);
	assertPredictions(loaded);

	printPassed();

    }


    private void assertPredictions(String[] predicts) {

	assertPredictions(Arrays.asList(predicts));

    }

    final private int maxSize = 10;

    private void assertPredictions(List<String> predicts) {

	if (!predicts.equals(getPredictions)) {
	    printFailed();
	    printProfileFile();
	    printChoiceFile();
	    printChoices("predictions: ", choiceList, predicts, getPredictions);
	    printFailed();
	    fail();
	}

	//	assertEquals(predicts, getPredictions);

    }

    private void assertScores() {

	if (!readerScores.equals(getReaderScores) || !playerScores.equals(getPlayerScores)) {
	    printFailed();
	    printProfileFile();
	    printChoiceFile();
	}

	if (!readerScores.equals(getReaderScores)) {
	    printChoices("reader scores: ", choiceList, readerScores, getReaderScores);
	}

	if (!playerScores.equals(getPlayerScores)) {
	    printChoices("player scores: ", choiceList, playerScores, getPlayerScores);
	}

	if (!readerScores.equals(getReaderScores) || !playerScores.equals(getPlayerScores)) {
	    printFailed();
	    fail();
	}

	//	assertEquals(readerScores, getReaderScores);
	//	assertEquals(playerScores, getPlayerScores);

    }


    private void assertLoad(String file) {

	profileFile = file;
	assertTrue(game.loadPlayerProfile(path + file));

    }

    private void assertSave(String file) {

	assertTrue(game.savePlayerProfile("save.txt"));

	Set<String> expect = loadSet(path + file);
	Set<String> actual = loadSet("save.txt");

	if (!expect.equals(actual)) {
	    printFailed();
	    printProfileFile();
	    printChoiceFile();
	    printChoiceList();
	    System.out.println("savePlayerProfile doesn't match: " + file);
	    System.out.println("expected lines: ");
	    printLines(expect);
	    System.out.println("actual lines: ");
	    printLines(actual);
	    printFailed();
	    fail();
	}

	//	assertEquals(expect, actual);

    }

    private void printLines(Set<String> lines) {

	for (String line : lines)
	    System.out.println(line);

    }

    private void printProfileFile() {
	if (profileFile != null)
	    System.out.println("loaded profile from file: " + profileFile);
    }

    private void printChoiceFile() {
	if (choiceFile != null)
	    System.out.println("made choices from file: " + choiceFile);
    }

    private void printChoiceList() {
	if (choiceFile == null && choiceList != null)
	    printList("made choices: ", choiceList, 0, choiceList.size());
    }

    private void printChoices(String text, List choiceList, List expected, List actual) {

	final int contextSize = 4;

	int start = findDiffIndex(expected, actual) - contextSize;
	if (start < 0)
	    start = 0;

	int end = start + 2*contextSize;
	if (end > expected.size())
	    end = expected.size();

	printList("        made choices: ", choiceList, start, end);
	printList("expected " + text, expected, start, end);
	printList("  actual " + text, actual, start, end);

    }

    private int findDiffIndex(List expected, List actual) {
	for (int i = 0; i < expected.size(); i++)
	    if (!expected.get(i).equals(actual.get(i)))
		return i;
	return 0;
    }

    private void printList(String text, List list, int start, int end) {
	System.out.print(text);
	if (start > 0)
	    System.out.print("<" + start + " more> ");
	for (int i = start; i < end; i++) {
	    System.out.print(list.get(i));
	    if (i < end-1)
		System.out.print(" ");
	}
	if (end < list.size())
	    System.out.print(" <" + (list.size() - end) + " more>");
	System.out.println();
    }

    private static void printFailed() {
	System.out.println();
	System.out.println("FAILED.");
	System.out.println();
    }


    private MindReader game;
    private int readerScore;
    private int playerScore;

    private String profileFile;
    private List<String> choiceList;
    private String choiceFile;
    private String predictFile;

    private List<String> getPredictions;
    private List<Integer> getReaderScores;
    private List<Integer> getPlayerScores;
    private List<Integer> readerScores;
    private List<Integer> playerScores;

    private void makeMindReader() {
	game = Factory.createMindReader();
	if (game == null)
	    System.out.println("Factory.createMindReader returned null. " +
			       "Did you forget to implement the factory?");
	readerScore = 0;
	playerScore = 0;

	profileFile = null;
	choiceList = null;
	choiceFile = null;
	predictFile = null;
    }

    private void makeChoice(String choice) {

	String predict = game.getPrediction();
	game.makeChoice(choice);

	if (predict.equals(choice))
	    readerScore++;
	else
	    playerScore++;

	getPredictions.add(game.getPrediction());
	getReaderScores.add(game.getMindReaderScore());
	getPlayerScores.add(game.getPlayerScore());
	readerScores.add(readerScore);
	playerScores.add(playerScore);

    }

    private void playGame(String[] choices) {

	List<String> choiceList = Arrays.asList(choices);
	playGame(choiceList);

    }

    private void playGame(List<String> choices) {

	choiceList = choices;
	getPredictions = new LinkedList<String>();
	getReaderScores = new LinkedList<Integer>();
	getPlayerScores = new LinkedList<Integer>();
	readerScores = new LinkedList<Integer>();
	playerScores = new LinkedList<Integer>();

	for (String choice : choices)
	    makeChoice(choice);

    }



    private Set<String> loadSet(String filename) {
	Set<String> set = new TreeSet<String>();
	loadCollection(set, filename);
	return set;
    }

    private List<String> loadList(String filename) {
	List<String> list = new LinkedList<String>();
	loadCollection(list, filename);
	return list;
    }

    private void loadCollection(Collection<String> collection, String filename) {
	try {
	    Scanner in = new Scanner(new File(filename));
	    while (in.hasNextLine()) {
		String line = in.nextLine();
		line = normalizeString(line);
		collection.add(line);
	    }
	    in.close();
	}
	catch (IOException e) {
	    System.out.println("FILE ERROR: did you get the test files?");
	    fail();
	}
    }


    private String normalizeString(String s) {
	return listToString(stringToList(s));
    }

    private List<String> stringToList(String s) {
	List<String> list = new LinkedList<String>();
	Scanner in = new Scanner(s);
	while (in.hasNext())
	    list.add(in.next());
	return list;
    }

    private String listToString(List<String> list) {
	String s = "";
	for (String word : list) {
	    if (!s.equals(""))
		s += " ";
	    s += word;
	}
	return s;
    }



    private void printStart(String text) {
	System.out.print(text);
    }

    private void printPassed() {
	System.out.println(".\t.\t.\t.\tPASSED");
    }


}


