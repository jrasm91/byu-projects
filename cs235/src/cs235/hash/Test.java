package cs235.hash;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Test {

	private static final int SIXTEEN = 16;
	private static final int THIRTYTWO = 32;
	private static Test_Unit UNIT = new Test_Unit();

	public static void main(String[] args) {
		testBasicsSet();
		testRemoveSet();
		testArrayIteratorSet();
		System.out.println();
		testBasicsMap();
		testRemoveMap();
		UNIT.allTestsPassed();
	}
	private final static String[] LISTA= {
		"alabaster", "azure", "blitzkrieg", "bogus", "euphoric", "festoon", 
		"fisticuffs", "fungible","pompous", "quagmire", "quixotic", 
		"sentient", "tellurium", "trilobyte", "vestibule", "yurt"};
	private final static String[] LISTB = {
		"lummox", "luscious", "macabre", "neener", "bombastic", 
		"byzantine", "cacophony", "cantaloupe", "gingham", "grotesque", 
		"heresy", "infernal", "infidel", "jurassic", "kumquat", "lollygag"};
	private final static String[] LISTC = {
		"sophomoric", "soporific", "sycophant", "tautology", "nonchalant",
		"obsequious", "pedantic", "plethora", "enigma", "epiphany", "ergo",
		"euphemism", "caterwaul", "debacle", "debonair", "egregious"};

	private final static String[] answers1 = {
		"festoon", "fungible", "alabaster", "trilobyte", "blitzkrieg", 
		"quagmire", "quixotic","pompous", "tellurium", "bogus", 
		"fisticuffs", "euphoric", "yurt", "azure", "vestibule", "sentient"}; 
	private final static String[] answers2 = {
		"byzantine", "trilobyte", "euphoric", "quixotic", "jurassic", 
		"neener", "grotesque", "quagmire", "bombastic", "cantaloupe",
		"festoon", "macabre", "kumquat", "alabaster", "infernal", "luscious",
		"sentient", "pompous", "fungible", "azure", "cacophony", "lummox",
		"bogus", "fisticuffs", "blitzkrieg", "vestibule", "tellurium",
		"infidel", "gingham", "lollygag", "yurt", "heresy"};
	private final static String[] answers3 = {
		"vestibule", "byzantine", "epiphany", "fisticuffs", "soporific",
		"gingham", "infernal", "egregious", "grotesque", "bombastic", 
		"obsequious", "debonair", "azure", "trilobyte", "yurt", "festoon",
		"quixotic", "cacophony", "quagmire", "tellurium", "heresy", "lummox",
		"debacle", "sentient", "tautology", "euphemism", "infidel", "jurassic",
		"pedantic", "pompous", "ergo", "macabre", "bogus", "euphoric", "enigma",
		"fungible", "alabaster", "cantaloupe", "caterwaul", "sycophant", "neener",
		"sophomoric", "kumquat", "blitzkrieg", "nonchalant", "luscious", "lollygag",
	"plethora"}; 
	private final static String[] answers4 = {
		"vestibule", "epiphany", "fisticuffs", "soporific", "egregious",
		"obsequious", "debonair", "azure", "trilobyte", "yurt", "festoon",
		"quixotic", "quagmire", "tellurium", "debacle", "sentient", 
		"tautology", "euphemism", "pedantic", "pompous", "ergo", "bogus",
		"euphoric", "enigma", "fungible", "alabaster", "caterwaul", 
		"sycophant", "sophomoric", "blitzkrieg", "nonchalant", "plethora"};
	private final static String[] answers5 = {
		"epiphany", "soporific", "egregious", "obsequious", 
		"debonair", "debacle", "tautology", "euphemism", "pedantic", 
		"ergo", "enigma", "caterwaul", "sycophant", "sophomoric",
		"nonchalant", "plethora"};	
	private final static String[] answers6 = {
		"byzantine", "epiphany", "soporific", "gingham", "egregious",
		"infernal", "obsequious", "bombastic", "grotesque", "debonair", 
		"cacophony", "heresy", "lummox", "debacle", "tautology",
		"euphemism", "infidel", "pedantic", "jurassic", "ergo", 
		"macabre", "enigma", "caterwaul", "sycophant", "cantaloupe",
		"neener", "sophomoric", "kumquat", "nonchalant", "luscious", 
		"lollygag", "plethora"};	
	private final static String[] answers7 = {
		"byzantine", "gingham", "infernal", "bombastic", "grotesque",
		"cacophony", "heresy", "lummox", "infidel", "jurassic", "macabre",
		"cantaloupe", "neener", "kumquat", "luscious", "lollygag"};
	private final static String[] answers8 = {
		"festoon", "fungible", "alabaster", "trilobyte", "blitzkrieg",
		"quagmire", "quixotic", "pompous", "tellurium", "bogus",
		"fisticuffs", "euphoric", "yurt", "azure", "vestibule", "sentient"};

	private static void testBasicsSet(){
		UNIT.testing("(SET)\tSize/Add/Contains/Clear");
		Set impl = SetFactory.createSet();
		UNIT.assertEquals(0, impl.size());
		impl = add(impl, LISTA);
		UNIT.assertEquals(SIXTEEN, impl.size());
		testContains(impl, LISTA, true);
		testContains(impl, LISTB, false);
		impl = add(impl, LISTA);
		UNIT.assertEquals(SIXTEEN, impl.size());
		impl = add(impl, LISTB);
		UNIT.assertEquals(THIRTYTWO, impl.size());
		testContains(impl, LISTA, true);
		testContains(impl, LISTB, true);
		impl.clear();
		UNIT.assertEquals(0, impl.size());
		testContains(impl, LISTA, false);
	}

	private static void testRemoveSet(){
		UNIT.testing("(SET)\tRemove");
		Set impl = SetFactory.createSet();
		impl = add(impl, LISTA);
		impl = add(impl, LISTB);
		impl = remove(impl, LISTA);
		impl = remove(impl, LISTC);
		UNIT.assertEquals(SIXTEEN, impl.size());
		testContains(impl, LISTA, false);
		testContains(impl, LISTB, true);
		impl = remove(impl, LISTB);
		UNIT.assertEquals(0, impl.size());
		testContains(impl, LISTB, false);
	}

	private static void testArrayIteratorSet(){
		UNIT.testing("(SET)\ttoArray() and iterator()");
		Set impl = SetFactory.createSet();
		impl = add(impl, LISTA);
		checkIteratorArray(impl, answers1);
		impl = add(impl, LISTB);
		checkIteratorArray(impl, answers2);
		impl = add(impl, LISTC);
		checkIteratorArray(impl, answers3);
		impl = remove(impl, LISTB);
		checkIteratorArray(impl, answers4);
		impl = remove(impl, LISTA);
		checkIteratorArray(impl, answers5);
		impl = add(impl, LISTB);
		checkIteratorArray(impl, answers6);
		impl = remove(impl, LISTC);
		checkIteratorArray(impl, answers7);
		impl.clear();
		impl = add(impl, LISTA);
		checkIteratorArray(impl, answers8);
	}

	private static void testBasicsMap(){
		UNIT.testing("(Map)\tSize/Put/Get/Clear");
		Map impl = MapFactory.createMap();
		UNIT.assertEquals(0, impl.size());
		impl = put(impl, LISTA, LISTB);
//		((MapImpl)impl).printMap();
		UNIT.assertEquals(SIXTEEN, impl.size());
		testGet(impl, LISTA, LISTB);
		testGet(impl, LISTB);
		impl = put(impl, LISTA, LISTC);
		UNIT.assertEquals(SIXTEEN, impl.size());
		impl = put(impl, LISTB, LISTC);
		UNIT.assertEquals(THIRTYTWO, impl.size());
		
		testGet(impl, LISTA, LISTC);
		testGet(impl, LISTB, LISTC);
		impl.clear();
		UNIT.assertEquals(0, impl.size());
		testGet(impl, LISTA);

	}

	private static void testRemoveMap(){
		UNIT.testing("(Map)\tRemove");
		Map impl = MapFactory.createMap();
		impl = put(impl, LISTA, LISTC);
		impl = put(impl, LISTB, LISTC);
		impl = removeMap(impl, LISTA);
		impl = removeMap(impl, LISTC);
		UNIT.assertEquals(SIXTEEN, impl.size());
		testGet(impl, LISTA);
		testGet(impl, LISTB, LISTC);
		impl = removeMap(impl, LISTB);
		UNIT.assertEquals(0, impl.size());
		testGet(impl, LISTB);		
	}








	private static Map put(Map temp, String[] keys, String[] values){
		for(int i = 0; i < keys.length; i ++)
			temp.put(keys[i], values[i]);
		return temp;
	}
	private static Map removeMap(Map temp, String[] list){
		for(int i = 0; i < list.length; i ++)
			temp.remove(list[i]);
		return temp;
	}
	private static void checkIteratorArray(Set set, String[] list){
		String[] temp = new String[list.length];
		Iterator it = set.iterator();
		int i = 0;
		while(it.hasNext())
			temp[i++] = it.next().toString();
		UNIT.assertEquals(list, temp);
		UNIT.assertEquals(list, set.toArray());
	}
	private static void testContains(Set set, String[] answers, boolean bool){
		for(int i = 0; i < answers.length; i++){
			if(!bool == set.contains(answers[i])){
				System.out.println("Contains("+answers[i]+")");
				UNIT.assertEquals(bool, !bool);
			}
		}
		UNIT.testPassed();
	}
	private static void testGet(Map temp, String[] answers){
		for(int i = 0; i < answers.length; i++){
			if(temp.get(answers[i]) != null){
				System.out.println("get("+answers[i]+")");
				UNIT.assertEquals("null", temp.get(answers[i]).toString());
			}
		}
		UNIT.testPassed();
	}
	private static void testGet(Map temp, String[] key, String[] value){
		MapImpl test = (MapImpl)temp;
		for(int i = 0; i < answers1.length; i++)
			try{
			(temp.get(key[i])).equals(value[i]);
			} catch(NullPointerException e){
				UNIT.testFailed();
				e.printStackTrace(System.out);
			}
		UNIT.testPassed();
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
