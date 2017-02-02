
package cs142.lab10;
import java.io.*;
import java.util.Scanner;


public class Student 
{
	File studentFile;
	String name;

	public Student(String name)
	{
		this.name = name;
		if(!printStudentFile())
		{
			System.out.print("FAIL! File does not exist. Maybe you should create it first?!");
		}
	}
	@SuppressWarnings("resource")
	public Student()
	{
		FileOutputStream fout = null;


		Scanner scan = new Scanner(System.in);
		System.out.print("\nEnter Student's Name: ");
		name =scan.next();


		try
		{
			fout = new FileOutputStream (name + ".txt");

			for(int i = 1; i<= 16; i++)
			{
				int offset = 0;
				int points = 100;
				String printString = "";
				switch(i)
				{
				case 1: points = 20; break;
				case 2: points = 20; break;
				case 3: points = 30; break;
				case 4: points = 30; break;
				case 5: points = 30; break;
				case 6: points = 30; break;
				case 7: points = 25; break;
				case 8: points = 25; break;
				case 9: points = 30; break;
				case 10: points = 20; break;
				case 11: points = 40; break;
				case 12: 
				case 13: 
					points = 100; 
					printString = "Score for Exam " + (i-11) + " (0-100)" +  ": ";
					break;
				case 14:
					points = 100;
					printString = "Score for Final Exam (0-100): ";
					break;
				case 15: 
					points = 60;
					offset = 30;
					printString = "(Labs) Number of Late Days (+/-30): ";
					break;
				case 16: 
					points = 3;
					printString = "(Exams) Number of Late Days (0-3): ";
					break;
				}
				if( i <= 11)
					printString = "Score for Lab " + i + " (0-" + points + ")" +  ": ";

				System.out.print("Enter " + printString);
				String score = scan.next();
				int labScore = -1;

				for(int j = 0; j<= points; j++)
				{
					String compare = "" + (j - offset);
					if(compare.equals(score))
					{
						labScore = (j-offset);
						new PrintStream(fout).println (printString + score);
						break;
					}
				}
				if(labScore == -1) {i--; System.out.println("PLEASE ENTER A VALID NUMBER!!!");}
			}
			fout.close();
		}
		catch (IOException e){System.err.println ("Unable to write to file");}
		scan.close();
	}


	public File getStudentFile(){return studentFile;}
	public String getName(){return name;}

	public boolean printStudentFile()
	{
		boolean returner = true;
		try
		{

			int[] classGrades = new int[16];
			int counter = 0;
			studentFile = new File(name + ".txt");
			Scanner scanFile = new Scanner(studentFile);
			System.out.print("Student Name: " + name + "\n");
			//scanFile.useDelimiter("[ ]+");
			while(scanFile.hasNext())
			{
				String next = scanFile.next();
				if(next.contains(":"))
				{
					int temp = scanFile.nextInt();
					classGrades[counter] = temp;
					counter++;
					System.out.print(next + " " + temp + "\n");
				}
				else
					System.out.print(next + " ");
			}
			int labDays = classGrades[14];
			int realLabDays = labDays;
			int examDays = classGrades[15];
			int realExamDays = examDays;
			int examScore = classGrades[11] + classGrades[12] + classGrades[13]; 
			int labScore = 0;
			for(int i = 0; i <= 10; i++)
			{
				labScore += classGrades[i];
			}

			if(labDays>0)
				labDays *= -5;
			else
				if(labDays>= 24)
					labDays = 24;
				else
					labDays *= -1;
			examDays *= -20;

			int finalScore = labScore + examScore + labDays + examDays;

			System.out.print("\nLab points : " + labScore + "\nLate Days: " + realLabDays + "\nTotal Lab Points: " + (labScore + labDays));
			System.out.print("\n\nExam Points : " + examScore + "\nLate Days: " + realExamDays + "\nTotal Exam Points: " + (examScore + examDays));
			System.out.print("\n\nTotal Points: " + finalScore + "\nFinal Letter Grade: " + getLetterGrade(finalScore));
			scanFile.close();
		}

		catch (IOException e)
		{

			returner = false;
		}

		return returner;

	}

	public String getLetterGrade(int finalScore)
	{
		if(finalScore>= 570){ return "A  EXCELLENT!";}
		else if(finalScore>= 540){ return "A-";}
		else if(finalScore>= 522){ return "B+";}
		else if(finalScore>= 598){ return "B... \nYou are an Average Joe";}
		else if(finalScore>= 480){ return "B-";}
		else if(finalScore>= 462){ return "C+";}
		else if(finalScore>= 438){ return "C...\nC's get degrees my friend, C's get degrees";}
		else if(finalScore>= 420){ return "C-";}
		else if(finalScore>= 402){ return "D+... Oh so close... yet SO FAR AWAY!";}
		else if(finalScore>= 378){ return "D";}
		else if(finalScore>= 360){ return "D-";}
		else { return "E  FAIL!";}
	}
}

