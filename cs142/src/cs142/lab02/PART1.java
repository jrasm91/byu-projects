package cs142.lab02;


import java.util.Scanner;

public class PART1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter a intiger between 0 and 31: ");
		int y = sc.nextInt();
		int x = y;
		int a = x / 16;
	    x = x -(16*a);
	    int b = x / 8;
	    x = x -(8*b);
	    int c = x / 4;
	    x = x -(4*c);
	    int d = x / 2;
	    x = x -(2*d);
	    int e = x / 1;
		System.out.println("Base-ten number: " + y);
	    System.out.println("Five-digit binary number: " + a + b + c + d + e);
	    sc.close();
	    
	}
}