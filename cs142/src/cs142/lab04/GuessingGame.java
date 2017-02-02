package cs142.lab04;

import java.util.Scanner;


public class GuessingGame 
{
	public static void main(String[] args)
	{
		String repeat = "y";
		while(repeat.equals("y")){
			Scanner scan = new Scanner(System.in);
			int add = 16;
			int random;
			String user;
			int userAnswer = 0;
			System.out.print("Think of a number between 0 and 31. "+
					"I will ask you five questions and then guess your number\n" + 
					"Do you have your number? (y/n): ");
			user = scan.next();
			for(int i= 1; i <= 5; i++)
			{
				random = (int)Math.round((Math.random()));
				int count = 0;
				for(int j = 0; j <=31; j++)
				{
					int value = toBinary(i, j);
					if(value == random)
					{
						if((count-1)%4 == 3){System.out.println();}
						count++;
						System.out.print(j + "\t");
					}
				}
				System.out.print("\nIs your number in this set? (y/n): ");
				user = scan.next();
				if(user.equals("y")&& (random == 1)){userAnswer += add;}
				if(user.equals("n")&& (random == 0)){userAnswer += add;}
				add /=2;
			}
			System.out.println("Is your number " + userAnswer + "?");
			System.out.print("Would you like to play again?");
			repeat = scan.next();
			scan.close();
		}
	}

	public static int toBinary(int index, int x)
	{
		int a = x / 16;
		x = x -(16*a);
		int b = x / 8;
		x = x -(8*b);
		int c = x / 4;
		x = x -(4*c);
		int d = x / 2;
		x = x -(2*d);
		int e = x / 1;
		if(index == 1){return a;}
		if(index == 2){return b;}
		if(index == 3){return c;}
		if(index == 4){return d;}
		else {return e;}
	}
}
