
package cs235.avl;

public class Test_Unit {



	public  void allTestsPassed(){
		System.out.println("\nALL TESTS PASSED!");
	}
	public  void testFailed(){
		System.out.print("TEST\t...\t...\t...\t FAILED\n");
	}
	public  void testPassed(){
		System.out.print("TEST\t...\t...\t...\t PASSED\n");
	}
	public  void assertEquals(boolean expected, boolean actual ){
		if(expected != actual){
			testFailed();
			System.out.println("Excepted: " + expected + "\nActual: " + actual);
			System.exit(0);
		}
		testPassed();
	}
	public  void assertEquals(int expected, int actual ){
		if(expected != actual){
			testFailed();
			System.out.println("Excepted: " + expected + "\nActual: " + actual);
			System.exit(0);
		}
		testPassed();
	}
	public  void assertEquals(String expected, String actual ){
		if(!expected.equals(actual)){
			testFailed();
			System.out.println("Excepted: " + expected + "\nActual: " + actual);
			System.exit(0);
		}
		testPassed();
	}
	public  void assertEquals(String[] expected, String[] actual ){
		int size = expected.length < actual.length ? expected.length : actual.length;
		for(int i = 0; i < size; i++){
			if(!expected[i].equals(actual[i])){
				testFailed();
				System.out.println("Excepted: " + expected[i] + "\nActual: " + actual[i]);
				System.exit(0);
			}
		}
		testPassed();
	}
	public  void assertEquals(Object[] expected, Object[] actual ){
		int size = expected.length < actual.length ? expected.length : actual.length;
		for(int i = 0; i < size; i++){
			if(!(expected[i].toString()).equals(actual[i].toString())){
				testFailed();
				System.out.println("Excepted: " + expected + "\nActual: " + actual);
				System.exit(0);
			}
		}
		testPassed();
	}
	public void testing(String test){
		System.out.println("Testing " + test + "...");
	}

}
