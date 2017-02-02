

package cs235.rsg;

import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Iterator;

import java.io.File;
import java.io.IOException;


// compile with javac -cp junit.jar *.java
// run with java -cp junit.jar:. cs235.rsg.TestDriver


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


    public void testLoadSavePoem() {
	assertLoadSave("Poem");
    }
    public void testLoadSaveHaiku() {
	assertLoadSave("Haiku");
    }
    public void testLoadSaveDear() {
	assertLoadSave("Dear-John-letter");
    }
    public void testLoadSaveBond() {
	assertLoadSave("Bond-movie");
    }

    public void testLoadGenerateUniquePoem() {
	assertLoadGenerateUnique("Poem");
    }
    public void testLoadGenerateUniqueHaiku() {
	assertLoadGenerateUnique("Haiku");
    }
    public void testLoadGenerateUniqueDear() {
	assertLoadGenerateUnique("Dear-John-letter");
    }
    public void testLoadGenerateUniqueBond() {
	assertLoadGenerateUnique("Bond-movie");
    }

    public void testLoadGenerateInSetPoem() {
	assertLoadGenerateInSet("Poem");
    }


    private String loadSaveText =
	"Testing: loadGrammar/saveGrammar";
    private String generateInSetText =
	"Testing: generateSentence gives valid sentences";
    private String generateUniqueText =
	"Testing: generateSentence gives different sentences";


    public void assertLoadSave(String name) {

	printStart(loadSaveText);

	makeRSG();
	assertLoad(name + ".g");
	assertSave("save-" + name + ".g");

	printPassed();

    }

    public void assertLoadGenerateInSet(String name) {

	printStart(generateInSetText);

	makeRSG();
	assertLoad(name + ".g");
	assertGenerateInSet(name + ".txt");

	printPassed();

    }

    public void assertLoadGenerateUnique(String name) {

	printStart(generateUniqueText);

	makeRSG();
	assertLoad(name + ".g");
	assertGenerateUnique();

	printPassed();

    }


    private RSG rsg;
    private String loadFile;
    private String saveFile;

    private void makeRSG() {

	rsg = Factory.createRSG();

	if (rsg == null)
	    System.out.println("Factory.createRSG returned null. " +
			       "Did you forget to implement the factory?");

	loadFile = null;
	saveFile = null;

    }


    private void assertLoad(String file) {

	loadFile = file;
	assertTrue(rsg.loadGrammar(path + file));

    }

    private void assertSave(String file) {

	saveFile = file;
	assertTrue(rsg.saveGrammar("save.txt"));

	String expect = loadString(path + file);
	String actual = loadString("save.txt");
	assertSameTokens(expect, actual);

    }

    private void assertSameTokens(String expect, String actual) {

	List<String> expectList = stringToTokenList(expect);
	List<String> actualList = stringToTokenList(actual);

	if (!expectList.equals(actualList)) {

	    printFailed();
	    printLoadFile();
	    printSaveTokens(expectList, actualList);
	    printFailed();
	    fail();

	}

	//	assertEquals(expectList, actualList);

    }

    final private int genCount = 10;

    public void assertGenerateInSet(String file) {

	Set<String> set = loadSet(path + file);

	for (int i = 0; i < genCount; i++) {
	    String sentence = rsg.generateSentence();
	    assertInSet(set, sentence);
	}

    }

    private void assertInSet(Set<String> set, String actual) {

	Iterator<String> i = set.iterator();
	while (i.hasNext()) {
	    String expect = i.next();
	    List<String> expectList = stringToTokenList(expect);
	    List<String> actualList = stringToTokenList(actual);
	    if (expectList.equals(actualList))
		return;
	}

	printFailed();
	printLoadFile();
	printNotValid(actual);
	printFailed();
	fail();

    }

    public void assertGenerateUnique() {

	Set<String> strings = new HashSet<String>();
	for (int i = 0; i < genCount; i++) {
	    String sentence = rsg.generateSentence();
	    strings.add(sentence);
	}

	if (strings.size() <= genCount / 2) {

	    printFailed();
	    printLoadFile();
	    printNotUnique(strings.size(), genCount);
	    printFailed();
	    fail();

	}

	//	assertTrue(strings.size() > genCount / 2);

    }


    private void printNotValid(String actual) {
	System.out.println("generateSentence produced the following invalid string:");
	final int MaxLen = 80;
	actual = RSGDriver.formatSentence(actual, MaxLen);
	System.out.println("---------------------");
	System.out.print(actual);
	System.out.println("---------------------");
    }

    private void printNotUnique(int unique, int total) {
	System.out.println("Only generated " + unique + " unique strings " +
			   "out of " + total + " total strings generated.");
    }

    private void printLoadFile() {
	if (loadFile != null)
	    System.out.println("loaded grammar from file: " + loadFile);
    }

    private void printSaveTokens(List<String> expect, List<String> actual) {

	if (saveFile != null)
	    System.out.println("saveGrammar doesn't match file: " + saveFile);

	final int contextSize = 4;

	int start = findDiffIndex(expect, actual) - contextSize;
	int end = Math.max(expect.size(), actual.size());

	if (start + 2*contextSize >= end)
	    start = end - 2*contextSize;
	if (start < 0)
	    start = 0;
	end = start + 2*contextSize;

	printList("Expected: ", expect, start, end);
	printList("  Actual: ", actual, start, end);

    }

    private static void printFailed() {
	System.out.println();
	System.out.println("FAILED.");
	System.out.println();
    }

    private void printStart(String text) {
	System.out.print(text);
    }

    private void printPassed() {
	System.out.println(".\t.\t.\t.\tPASSED");
    }


    private int findDiffIndex(List<String> expect, List<String> actual) {
	int i;
	for (i = 0; i < expect.size() && i < actual.size(); i++)
	    if (!expect.get(i).equals(actual.get(i)))
		break;
	return i;
    }

    private void printList(String text, List<String> list, int start, int end) {
	System.out.print(text);
	if (start > 0)
	    System.out.print("<" + start + " tokens> ");
	System.out.print("[ ");
	if (end > list.size())
	    end = list.size();
	for (int i = start; i < end; i++) {
	    System.out.print(list.get(i));
	    if (i < end-1)
		System.out.print(" ");
	}
	System.out.print(" ]");
	if (end < list.size())
	    System.out.print(" <" + (list.size() - end) + " tokens>");
	System.out.println();
    }


    private void stringToTokens(Collection<String> collection, String s) {
	Scanner in = new Scanner(s);
	while (in.hasNext())
	    collection.add(in.next());
    }

    private List<String> stringToTokenList(String s) {
	List<String> list = new LinkedList<String>();
	stringToTokens(list, s);
	return list;
    }

    private String listToString(List<String> list) {
	String s = "";
	Iterator<String> i = list.iterator();
	while (i.hasNext())
	    s += i.next() + "\n";
	return s;
    }

    private String loadString(String filename) {
	List<String> list = loadList(filename);
	return listToString(list);
    }


    private Set<String> loadSet(String filename) {
	Set<String> set = new HashSet<String>();
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
	    while (in.hasNextLine())
		collection.add(in.nextLine());
	    in.close();
	}
	catch (IOException e) {
	    System.out.println("FILE ERROR: did you get the test files?");
	}
    }



}


