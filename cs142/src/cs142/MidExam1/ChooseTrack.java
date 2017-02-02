package cs142.MidExam1;

// Jason Rasmussen, 309841901, CS 142 Fall '09 Midterm 1

import java.util.Scanner;
public class ChooseTrack {

	public static void main(String[] args)
	{

		final int MAX_VALUE = 10;
		final int MIN_VALUE = 0;
		Track JTrack = new Track();
		Track CTrack = new Track();
		Scanner scan = new Scanner(System.in);
		String repeat = "y";
		String degree = "";
		String decision = "C";

		//8 Strings that are questions to be asked of the user.
		String q1 = "What is the probability that Java programming skill will be needed in your \n" +
		"major or for your intended career?";
		String q2 = "What is the probability that C programming skill will be needed in your \n" +
		"major or for your intended career?";
		String q3 = "What is the level of programming skill needed for your major or for\n" +
		"your intended career?";
		String q4 = "How confident are you that you will stay with your chosen major and \n" +
		"pursue you intended career?";
		String q5 = "What is your willingness to take the J Track if you completes the C \n" +
		"Track and discover that you wants to acquire more programming skills?";
		String q6 = "What is your willingness to take the C Track if you complete the J \n" +
		"Track and later decide to pursue a career that requires numeric and \nscientific " +
		"programming in the C programming language?";
		String q7 = "Evaluate your independent ability to learn the concepts and techniquies \n" +
		"presented in the J Track if you complete the C Track and \ndiscover that you want " +
		"to acquire more programming skills?";
		String q8 = "Evaluate your independent ability to learn the concepts and techniquies \n" +
		"presented in the C Track if you complete the J Track and later decide \nto pursue a " +
		"career that requires numeric and scientific \nprogramming in the C programming language?";

		//Creates a list of questions as Strings that will be displayed to the user.
		String[] list = {q4,q1,q2,q3,q5,q6,q7,q8};

		//A while loop that allows the program to rerun as long as the user desires.
		while(repeat.equals("y") || repeat.equals("Y"))
		{
			System.out.println("On a scale of 0(low) to 10(high) (inclusive), please answer each question.\n");
			JTrack.setScore(0);
			CTrack.setScore(0);
			for(int i = 1; i<=8; i++)

			{
				//prints the question
				System.out.println("\n" + list[i-1]);
				//prints and prompts the user to enter an integer between 1 and 10
				System.out.print("Please enter an intiger between 0 and 10: ");
				int input = scan.nextInt();
				//the value must be between MIN_VALUE and MAX_VALUE
				while((input < MIN_VALUE) || (MAX_VALUE > 10))
				{
					System.out.print("I repeat, please enter a number between " + MIN_VALUE + " and " + MAX_VALUE +" : ");
					input = scan.nextInt();
				}
				//questions that only relate to the JTrack score
				if((i==2)||(i==4)||(i==6)||(i==8))
				{
					JTrack.evaluate(i,input);
				}
				//questions that only relate to the CTrack score
				if((i==3)||(i==5)||(i==7))
				{
					CTrack.evaluate(i,input);
				}
				//questions that affect both scores of the JTrack and CTrack
				else
				{
					if(i==4)
					{
						CTrack.evaluate(10-i,input);
						JTrack.evaluate(i,input);
					}
					else
					{
						CTrack.evaluate(i,input);
						JTrack.evaluate(i,input);
					}
				}
			}
				//if statements that determine which score is higher and what the 'decision' string should be
				int totalScore = CTrack.getScore() + JTrack.getScore();
				if(CTrack.getScore() > JTrack.getScore())
				{
					decision = "C Track";
					degree = "" + (int)(CTrack.getScore()*100/totalScore);
				}
				if(JTrack.getScore() > CTrack.getScore())
				{
					decision = "J Track";
					degree = "" + (int)(JTrack.getScore()*100/totalScore);
				}
				if(CTrack.getScore() == JTrack.getScore())
				{
					decision = "both tracks";
					degree = "equal";
				}
				System.out.println("\nThere is a " + degree + "% recommendation for " + decision);
				System.out.print("Do you want to run this program again? (y/n): ");
				repeat = scan.next();
			}
		scan.close();
		}
	}