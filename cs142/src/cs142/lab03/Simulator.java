package cs142.lab03;


import java.util.InputMismatchException;
import java.util.Scanner;

public class Simulator {


	public static void main(String[] args) 
	{
		Scanner scan = new Scanner(System.in);
		String playerName;
		int skillValue;
		int maxTurnTime;
		System.out.println("Enter Player One's name: ");
		playerName = scan.next();
		while(true){
			try{
				System.out.println("Enter Player One's skill: ");
				skillValue = scan.nextInt();
				if(skillValue >= 0 && skillValue <= 10)
					break;
			} catch(InputMismatchException e){
				scan.next();
			}
			System.out.println("\nInvalid Argument- Try Again.");
		}
		while(true){
			try{
				System.out.println("Enter Player One's maximum turn time: ");
				maxTurnTime = scan.nextInt();
				break;
			} catch(InputMismatchException e){
				scan.next();
			}
			System.out.println("\nInvalid Argument- Try Again.");
		}
		Contestant contestant1 = new Contestant(playerName, skillValue, maxTurnTime);

		System.out.println("Enter Player Two's name: ");
		playerName = scan.next();
		while(true){
			try{
				System.out.println("Enter Player Two's skill: ");
				skillValue = scan.nextInt();
				if(skillValue >= 0 && skillValue <= 10)
					break;
			} catch(InputMismatchException e){
				scan.next();
			}
			System.out.println("\nInvalid Argument- Try Again.");
		}
		while(true){
			try{
				System.out.println("Enter Player Two's maximum turn time: ");
				maxTurnTime = scan.nextInt();
				break;
			} catch(InputMismatchException e){
				scan.next();
			}
			System.out.println("\nInvalid Argument- Try Again.");
		}
		Contestant contestant2 = new Contestant(playerName, skillValue, maxTurnTime);
		int maxtime;
		while(true){
			try{

				System.out.println("Enter initial clock time: ");
				maxtime = scan.nextInt();
				break;
			}catch(InputMismatchException e){
				scan.next();
			}
			System.out.println("\nInvalid Argument- Try Again");
		}

		CheckersTable game = new CheckersTable(contestant1, contestant2, maxtime);
		game.simulateGame();
		System.out.println(game.getMoveHistory());
		System.out.println("Winner is: " + game.getWinner().getName());
		System.out.println("Pieces Remaining: " + (game.getWinner()).getNumPieces());
		System.out.println("Clock time remaining: " + (game.getWinner()).getClockTime());
		scan.close();

	}

}
