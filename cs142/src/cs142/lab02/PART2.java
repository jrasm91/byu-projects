package cs142.lab02;

import java.util.Scanner;


public class PART2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		System.out.print("Enter a temperature in degrees of Fahrenheit: ");
		double a = sc.nextInt();
		int f = (int)a;
		double b = (((a-32)*5)/9);
		int c = (int)b;
		
		System.out.println("degrees Fahrenheit as an int: " + f );
		System.out.println("degrees Fahrenheit as an double: " + a );
		System.out.println("degrees Celsius as a int: " + c);
		System.out.println("degrees Celsius as a double: " + b);
		sc.close();
	}
	

}
