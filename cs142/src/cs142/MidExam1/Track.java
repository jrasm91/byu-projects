package cs142.MidExam1;

public class Track {
	
	private int score;
	private int q2_WEIGHT;
	private int q3_WEIGHT;
	private int q4_WEIGHT;
	private final int q5_WEIGHT = 4;
	private final int q6_WEIGHT = 4;
	private final int q7_WEIGHT = 2;
	private final int q8_WEIGHT = 2;
	
	//Constructor: initializes score to zero.
	public Track()
	{
		score = 0;
	}
	//returns the score
	public int getScore()
	{
		return score;
	}
	//sets score to the value newScore
	public void setScore(int newScore)
	{
		score = newScore;
	}
	/*This method takes in two numbers. These numbers represent the question number
	and the answer (1-10) of the question. It adds the value to the score after 
	the it is weighted.*/
	public void evaluate(int q, int value)
	{
		if(q == 1)
		{
			//q2, q3, and q4 are affected by the answer to q1.
			q2_WEIGHT = value;
			q3_WEIGHT = value;;
			q4_WEIGHT = value;
		}
		if(q == 2){score += value*q2_WEIGHT;}
		if(q == 3){score += value*q3_WEIGHT;}
		if(q == 4){score += value*q4_WEIGHT;}
		if(q == 5){score += value*q5_WEIGHT;}
		if(q == 6){score += value*q6_WEIGHT;}
		if(q == 7){score += value*q7_WEIGHT;}
		if(q == 8){score += value*q8_WEIGHT;}
	}
}