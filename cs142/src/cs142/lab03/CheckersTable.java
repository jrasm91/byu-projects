package cs142.lab03;


/**
 * Checkers table is a class provided to you for lab 3.  This class contains methods necessary for simulating a checkers game.
 * Remember, this class has been provided for you and your job is to study the methods so you can use them to complete the lab assignment.
 */

public class CheckersTable {
	
	  private Contestant ad3f5d;
	  private Contestant f21c3;
	  private int c3dd3a;
	  private String b3e342;
	  private Contestant da329f;

	  /**
	   * 
	   * @param player1 The fist player in the checkers game
	   * @param player2 The second player in the checkers game
	   * @param timePerPlayer The number of seconds given to each player for their moves
	   */
	  
	  public CheckersTable(Contestant paramContestant1, Contestant paramContestant2, int paramInt)
	  {
	    this.ad3f5d = paramContestant1;
	    this.f21c3 = paramContestant2;
	    this.c3dd3a = paramInt;

	    this.b3e342 = "MOVE HISTORY:\nName\t\tOpponent's Pieces Captured\t\tTime Left\tPieces Left\n-----------------------------------------------------------------------------------\n";

	    this.da329f = null;
	  }

	  /**
	   * Simulates a game on the checkers table with the two players specified when the CheckersTable object was constructed.
	   * After a game has been simulated, the getMoveHistory and getWinner can be used to obtain information about the simulated game.
	   */
	  
	  public void simulateGame()
	  {
	    this.ad3f5d.initializeGameClock(this.c3dd3a);
	    this.f21c3.initializeGameClock(this.c3dd3a);

	    int i = 0;
	    do
	    {
	      simulateTurn(++i); }
	    while (!(isGameOver()));
	  }
	  
	  private void simulateTurn(int paramInt)
	  {
	    Contestant localContestant1;
	    Contestant localContestant2;
	    if (paramInt % 2 == 0)
	    {
	      localContestant1 = this.ad3f5d;
	      localContestant2 = this.f21c3;
	    }
	    else
	    {
	      localContestant1 = this.f21c3;
	      localContestant2 = this.ad3f5d;
	    }

	    int i = localContestant1.takeTurn();
	    int j = calcPiecesCaptured(localContestant1, localContestant2);
	    localContestant2.losePieces(j);
	    CheckersTable tmp57_56 = this; tmp57_56.b3e342 = tmp57_56.b3e342 + localContestant1.getName() + "\t\t" + j + " captured in " + i + " seconds" + (i < 10 ? "\t\t\t" : "\t\t") + localContestant1.getClockTime() + " seconds\t" + localContestant1.getNumPieces() + " pieces\n";
	  }

	  private int calcPiecesCaptured(Contestant paramContestant1, Contestant paramContestant2)
	  {
	    int j;
	    int i = (paramContestant1.getSkillValue() - paramContestant2.getSkillValue()) * 4;
	    double d = Math.random() * 100.0D + i;

	    if ((d < 45.0D) || (paramContestant1.getClockTime() <= 0))
	      j = 0;
	    else
	    {
	      d -= 45.0D;

	      if (d < 45.0D)
	        j = 1;
	      else
	        j = 2;

	    }

	    if (paramContestant2.getNumPieces() < j)
	      j = paramContestant2.getNumPieces();

	    return j;
	  }

	  private boolean isGameOver()
	  {
	    boolean i = true;

	    if (this.ad3f5d.getClockTime() <= 0)
	      this.da329f = this.f21c3;
	    else if (this.f21c3.getClockTime() <= 0)
	      this.da329f = this.ad3f5d;
	    else if (this.ad3f5d.getNumPieces() <= 0)
	      this.da329f = this.f21c3;
	    else if (this.f21c3.getNumPieces() <= 0)
	      this.da329f = this.ad3f5d;
	    else
	      i = false;

	    return i;
	  }

	  /**
	   * Gets the move history for the game after the game has been simulated.
	   * 
	   * @return A string containing the whole move history for the game
	   */
	  
	  public String getMoveHistory()
	  {
	    return this.b3e342;
	  }

	  /**
	   * Gets the winner of the game after the game has been simulated.
	   * 
	   * @return A contestant object corresponding to the winner of the game
	   */
	  
	  public Contestant getWinner()
	  {
	    return this.da329f;
	  }
}
