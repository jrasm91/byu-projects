package cs142.lab10;

import java.io.File;
import java.util.Scanner;


public class StudentGrades 
{
	private static boolean repeat = true;
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		while(repeat)
		{
			System.out.print("\nDo you want to write a Student File, read a Student File, or delete a Student File? (write/read/delete): ");
			String answer = scan.next();
			if(answer.equals("delete") || answer.equals("d"))
			{
				System.out.print("\nPlease enter the name of Student to be deleted: ");
				String studentName = scan.next();
				File file = new File(studentName + ".txt");
				if(file.delete()){
					System.out.print("\nStudent File " + studentName + ".txt no longer exists.");
					file.deleteOnExit();}
				else{
					System.out.print("\nStudent File " + studentName + ".txt cannot be deleted because it is being used by Eclipse.");
					System.out.print("\nYou must restart Program in order to delete " + studentName + ".txt");
				}
				repeat();
			}
			else if(answer.equals("write") || answer.equals("w"))
			{
				new Student();
				repeat();
			}
			else if(answer.equals("read") || answer.equals("r"))
			{
				System.out.print("\nPlease enter the name of Student to be read: ");
				new Student(scan.next());
				repeat();
			}
			else
				System.out.print("\nFAIL! You should probably learn how to type.");
		}
		scan.close();
	}
	
	private static void repeat()
	{
		while(true)
		{
			Scanner scan = new Scanner(System.in);
			System.out.print("\nDo you want to repeat? (yes/no): ");
			String theNext = scan.next();
			if(theNext.equals("no")|| (theNext.equals("n")))
			{
				repeat = false; break;
			}
			if(theNext.equals("yes")||(theNext.equals("y")))
				break;
			else
				System.out.print("\nFAIL! You should probably learn how to type.");
			scan.close();
		}
	}
}
