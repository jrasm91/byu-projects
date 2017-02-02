package cs142.lab03;


import java.util.Random;

public class Contestant {
	
	private String playerName;
	private int skillValue;
	private int maxTurnTime;
	private int gameClock;
	private int numPieces;
	private Random r = new Random();
	
	
	public Contestant(String name, int skill, int turn)
	{
		playerName = name;
		skillValue = skill;
		maxTurnTime = turn;
		numPieces = 12;
	}
	
	public int getClockTime()
	{
		return gameClock;
	}
	
	public String getName()
	{
		return playerName;
	}
	
	public int getNumPieces()
	{
		return numPieces;
	}
	
	public int getSkillValue()
	{
		return skillValue;
	}

	public void initializeGameClock(int seconds)
	{
		gameClock = seconds;
	}
	
	public void losePieces(int numLost)
	{
		numPieces -= numLost;
	}
	
	public int takeTurn()
	{
		int turnTime = r.nextInt(maxTurnTime-1)+1;
		gameClock -= turnTime;
		return turnTime;
	}
}
