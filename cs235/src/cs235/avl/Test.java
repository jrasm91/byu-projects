
package cs235.avl;

import java.util.Iterator;
import java.util.Set;

public class Test {

	private static Test_Unit test;
	private final static int sixteen = 16;
	private final static String[] answers1 = {
		"alabaster", "azure", "blitzkrieg", "bogus", "euphoric", "festoon", 
		"fisticuffs", "fungible","pompous", "quagmire", "quixotic", 
		"sentient", "tellurium", "trilobyte", "vestibule", "yurt"};
	private final static String[] answers2 = {
		"lummox", "luscious", "macabre", "neener", "bombastic", 
		"byzantine", "cacophony", "cantaloupe", "gingham", "grotesque", 
		"heresy", "infernal", "infidel", "jurassic", "kumquat", "lollygag"};
	private final static String[] answers3 = {
		"sophomoric", "soporific", "sycophant", "tautology", "nonchalant",
		"obsequious", "pedantic", "plethora", "enigma", "epiphany", "ergo",
		"euphemism", "caterwaul", "debacle", "debonair", "egregious"};
	private final static String[] answers11 = {
		"fungible", "bogus", "azure", "alabaster", "blitzkrieg", "festoon",
		"euphoric", "fisticuffs", "sentient", "quagmire", "pompous", 
		"quixotic", "trilobyte", "tellurium", "vestibule", "yurt"};
	private final static String[] answers12 = {
		"pompous", "fungible", "byzantine", "bogus", "azure", "alabaster", "blitzkrieg", 
		"bombastic", "euphoric", "cacophony", "cantaloupe", "festoon", "fisticuffs", 
		"jurassic", "infernal", "grotesque", "gingham", "heresy", "infidel", "luscious",
		"lollygag", "kumquat", "lummox", "macabre", "neener", "sentient", "quagmire", 
		"quixotic", "trilobyte", "tellurium", "vestibule", "yurt"};
	private final static String[] answers13 = {
		"jurassic", "euphoric", "byzantine", "bogus", "azure", "alabaster", "blitzkrieg", 
		"bombastic", "epiphany", "debacle", "cantaloupe", "cacophony", "caterwaul", 
		"egregious", "debonair", "enigma", "ergo", "euphemism", "fungible", "festoon", 
		"fisticuffs", "infernal", "grotesque", "gingham", "heresy", "infidel", "pompous", 
		"luscious", "lollygag", "kumquat", "lummox", "obsequious", "neener", "macabre", 
		"nonchalant", "pedantic", "plethora", "soporific", "sentient", "quagmire", "quixotic", 
		"sophomoric", "trilobyte", "tautology", "sycophant", "tellurium", "vestibule", "yurt"};
	private final static String[] answers14 = {
		"nonchalant", "epiphany", "caterwaul", "azure", "alabaster", "bogus", "blitzkrieg", 
		"egregious", "debacle", "debonair", "enigma", "euphoric", "ergo", "euphemism", 
		"fisticuffs", "festoon", "fungible", "soporific", "pompous", "pedantic", "obsequious",
		"plethora", "sentient", "quagmire", "quixotic", "sophomoric", "trilobyte", "tautology",
		"sycophant", "tellurium", "vestibule", "yurt"};
	private final static String[] answers15 = { 
		"nonchalant", "epiphany", "debacle", "caterwaul", "egregious", "debonair",
		"enigma", "euphemism", "ergo", "soporific", "pedantic", "obsequious", 
		"sophomoric", "plethora", "tautology", "sycophant"}; 
	private final static String[] answers16 = { 
		"infernal", "epiphany", "debacle", "byzantine", "bombastic", "cantaloupe", 
		"cacophony", "caterwaul", "egregious", "debonair", "enigma", "grotesque", 
		"euphemism", "ergo", "gingham", "heresy", "nonchalant", "luscious", "jurassic", 
		"infidel", "lollygag", "kumquat", "lummox", "macabre", "neener", "soporific",
		"pedantic", "obsequious", "sophomoric", "plethora", "tautology", "sycophant"};
	private final static String[] answers17 = {"infernal", "gingham", "byzantine", 
		"bombastic", "cantaloupe", "cacophony", "grotesque", "heresy", "luscious", 
		"jurassic", "infidel", "lollygag", "kumquat", "lummox", "neener", "macabre"};
	

	public static void main(String[] args){
		test = new Test_Unit();
		testSize();
		testContains();
		testRemove();
		testIterator();
		test.allTestsPassed();
	}
	private static void testSize(){
		test.testing("Size");
		Set impl = SetFactory.createSet();
		test.assertEquals(impl.size(), 0);
		impl = add(SetFactory.createSet(), answers1);
		test.assertEquals(impl.size(), sixteen);
		impl = add(impl, answers2);
		test.assertEquals(impl.size(), sixteen + sixteen);
		impl.clear();
		test.assertEquals(impl.size(), 0);
	}
	private static void testContains(){
		test.testing("Contains");
		Set impl = SetFactory.createSet();
		impl = add(impl, answers1);
		checkList(impl, answers1, true);
		checkList(impl, answers2, false);
		impl = add(impl, answers2);
		checkList(impl, answers1, true);
		checkList(impl, answers2, true);
		impl.clear();
		checkList(impl, answers1, false);
	}
	private static void testRemove(){
		test.testing("Remove");
		Set impl = SetFactory.createSet();
		impl = add(impl, answers1);
		impl = add(impl, answers2);
		test.assertEquals(sixteen + sixteen, impl.size());
		impl = remove(impl, answers1);
		impl = remove(impl, answers3);
		test.assertEquals(sixteen, impl.size());
		checkList(impl, answers2, true);
		checkList(impl, answers1, false);
		impl = remove(impl, answers2);
		test.assertEquals(0, impl.size());
		checkList(impl, answers2, false);
	}
	private static void testIterator(){
		test.testing("Iterator/toArray");
		Set impl = SetFactory.createSet();
		
		impl = add(impl, answers1);
		checkIteratorArray(impl, answers11);
		impl = add(impl, answers2);
		checkIteratorArray(impl, answers12);
		impl = add(impl, answers3);
		checkIteratorArray(impl, answers13);
		impl = remove(impl, answers2);
		checkIteratorArray(impl, answers14);
		impl = remove(impl, answers1);
		checkIteratorArray(impl, answers15);
		impl = add(impl, answers2);
		checkIteratorArray(impl, answers16);
		impl = remove(impl, answers3);
		checkIteratorArray(impl, answers17);
	}
	private static void checkIteratorArray(Set set, String[] list){
		String[] temp = new String[list.length];
		Iterator it = set.iterator();
		int i = 0;
		while(it.hasNext())
			temp[i++] = it.next().toString();
		test.assertEquals(list, temp);
		test.assertEquals(list, set.toArray());
	}
	private static void checkList(Set set, String[] answers, boolean bool){
		for(int i = 0; i < answers.length; i++)
			if(!bool && set.contains(answers[i])){
				System.out.println("Contains("+answers[i]+")");
				test.assertEquals(bool, !bool);
			}
		test.testPassed();
	}
	private static Set add(Set temp, String[] list){
		for(int i = 0; i < list.length; i ++)
			temp.add(list[i]);
		return temp;
	}
	private static Set remove(Set temp, String[] list){
		for(int i = 0; i < list.length; i ++)
			temp.remove(list[i]);
		return temp;
	}


}
