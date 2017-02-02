package cs142.lab04;

import java.util.Scanner;


public class Example 
{
	
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		System.out.print("Pick a number between 0 and a Billion:");
		int x = scan.nextInt();
		int temp = x;
		int power = 0;
		String answer = "";
		while(temp/2 != 0)
		{
			power++;
			temp/=2;
		}
		int divide = (int)Math.pow(2,power);
		while(divide != 0)
		{
			answer = answer + x/divide;
			x = x%divide;
			divide = divide/2;
		}
		System.out.println(answer);
		scan.close();
	}
}
