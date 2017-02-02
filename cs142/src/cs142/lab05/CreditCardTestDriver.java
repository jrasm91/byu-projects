package cs142.lab05;

import java.text.DecimalFormat;
import java.util.Scanner;

//*******************************************************************************************************************************
//	
//	CreditCardTestDriver.java			Author: Robbie Buck					Date Created: July 19, 2006
//																			Last Edited: June 16, 2009
//					Lab 4 part 1: Testdriver for lab 4 of BYU CS 142
//*******************************************************************************************************************************

public class CreditCardTestDriver {

	public static void main(String[] args) throws InterruptedException
	{
		Scanner scan = new Scanner(System.in);
		DecimalFormat fmt = new DecimalFormat("0.##");	//to format output

		int checkDelay = 450, moveOnDelay = 150; //pauses throughout program
		int finalDelay = 100, finalDelay2 = 200;

		//Account information for three credit cards
		String acct1Name = "Jack Sparrow", acct2Name = "Will Turner", acct3Name = "Elizabeth Swan", acct4Name = "#### #### #### ####";
		double acct1Balance = 50000.00, acct2Balance = 0.00, acct3Balance = 1000.00, acct4Balance = 5000.00;	//initial balances
		double acct1Rate = 2, acct2Rate = 20, acct3Rate = 0, acct4Rate = 15;					//annual interest rates

		//Initial credit card information
		System.out.println("\nAccounts to be created:");

		Thread.sleep(500);	//pause for effect

		System.out.println("\n\tName: " + acct1Name + "\n\tBalance: $" + fmt.format(acct1Balance) +
				"\n\tAccount Status: active" + "\n\tAnnual Interest Rate: " + acct1Rate + " %");

		Thread.sleep(500); //pause for effect

		System.out.println("\n\tName: " + acct2Name + "\n\tBalance: $" + fmt.format(acct2Balance) +
				"\n\tAccount Status: active" + "\n\tAnnual Interest Rate: " + acct2Rate + " %");

		Thread.sleep(500); //pause for effect

		System.out.println("\n\tName: " + acct3Name + "\n\tBalance: $" + fmt.format(acct3Balance) +
				"\n\tAccount Status: active" + "\n\tAnnual Interest Rate: " + acct3Rate + " %");

		Thread.sleep(500); //pause for effect

		System.out.println("\n\tName: " + acct4Name + "\n\tBalance: $" + fmt.format(acct4Balance) +
				"\n\tAccount Status: active" + "\n\tAnnual Interest Rate: " + acct4Rate + " %");

		Thread.sleep(500); //pause for effect

		//Create Credit Card Accounts
		System.out.print("\nCreating Credit Card Accounts");
		CreditCard acct1 = new CreditCardAccount(acct1Name, acct1Balance, acct1Rate);
		CreditCard acct2 = new CreditCardAccount(acct2Name);
		CreditCard acct3 = new CreditCardAccount(acct3Name, acct3Balance, acct3Rate);
		CreditCard acct4 = new CreditCardAccount(acct4Balance, acct4Rate);
		CreditCard acct5 = new CreditCardAccount(acct4Balance, acct4Rate);	//check against acct4 name to check random number

		boolean initialCheck = true; //checks if the names or initial balances are incorrect
		String failedTest = ""; //error message if a test fails

		if(!acct1Name.equals(acct1.getAccountName()))
		{
			failedTest += "\nAccount 1 name incorrect: returned " + acct1.getAccountName() + ", should have been " + acct1Name;
			initialCheck = false;
		}
		if(!acct2Name.equals(acct2.getAccountName()))
		{
			failedTest += "\nAccount 2 name incorrect: returned " + acct2.getAccountName() + ", should have been " + acct2Name;
			initialCheck = false;
		}			
		if(!acct3Name.equals(acct3.getAccountName()))
		{
			failedTest += "\nAccount 3 name incorrect: returned " + acct3.getAccountName() + ", should have been " + acct3Name;
			initialCheck = false;
		}			
		if(acct4Name.length() != acct4.getAccountName().length())
		{
			failedTest += "\nAccount 4 account number incorrect: returned " + acct4.getAccountName() + ", should have been " + acct4Name;
			initialCheck = false;
		}
		else if(acct4.getAccountName().equals(acct5.getAccountName()))
		{
			failedTest += "\nAccount 4 account number is not random!";
			initialCheck = false;				
		}

		if(acct1Balance != acct1.getCurrentBalance())
		{
			failedTest += "\nAccount 1 initial balance incorrect: returned " + fmt.format(acct1.getCurrentBalance()) +
					", should have been " + fmt.format(acct1Balance);
			initialCheck = false;
		}
		if(acct2Balance != acct2.getCurrentBalance())
		{
			failedTest += "\nAccount 2 initial balance incorrect: returned " + fmt.format(acct2.getCurrentBalance()) +
					", should have been " + fmt.format(acct2Balance);
			initialCheck = false;
		}
		if(acct3Balance != acct3.getCurrentBalance())
		{
			failedTest += "\nAccount 3 initial balance incorrect: returned " + fmt.format(acct3.getCurrentBalance()) +
					", should have been " + fmt.format(acct3Balance);
			initialCheck = false;
		}
		if(acct4Balance != acct4.getCurrentBalance())
		{
			failedTest += "\nAccount 4 initial balance incorrect: returned " + fmt.format(acct4.getCurrentBalance()) +
					", should have been " + fmt.format(acct4Balance);
			initialCheck = false;
		}

		//Check initial conditions for credit cards			
		System.out.print("\nChecking Your Initial Values");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");


		if(initialCheck)
			System.out.println("passed");
		else
		{
			System.out.println("failed");
			System.out.println(failedTest);
			failedTest = "";	//reset failed string
		}

		System.out.println("\nPress enter to move to the next test\n");
		scan.nextLine();
		boolean purchaseCheck = true;	//checks if purchases were made correctly

		//make purchases on accounts			
		System.out.print("\nMaking purchase on account 1 of -100.00");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct1.makeAPurchase(-100.00) != false)	//no negative purchases
		{
			System.out.println("purchased");
			System.out.println("\nError! Negative purchase should not be allowed.\n");
			purchaseCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		else
			System.out.println("not purchased");	//end line

		Thread.sleep(moveOnDelay);		

		System.out.print("Making purchase on account 2 of $500.00");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct2.makeAPurchase(500.00) != true)
		{
			System.out.println("not purchased");
			System.out.println("\nError! Purchase should be allowed\n");
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();

		}
		else
		{
			System.out.println("purchased");	//end line
			acct2Balance += 500.00;
		}

		Thread.sleep(moveOnDelay);

		System.out.print("Making purchase on account 3 of $10000.00");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct3.makeAPurchase(10000.00) != true)
		{
			System.out.println("not purchased");
			System.out.println("\nError! Purchase should be allowed\n");
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();

		}
		else
		{
			System.out.println("purchased");	//end line
			acct3Balance += 10000.00;
		}

		Thread.sleep(moveOnDelay);

		System.out.print("Making purchase on account 4 of $399.99");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct4.makeAPurchase(399.99) != true)
		{
			System.out.println("not purchased");
			System.out.println("\nError! Purchase should be allowed\n");
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();

		}
		else
		{
			System.out.println("purchased");	//end line
			acct4Balance += 399.99;
		}

		Thread.sleep(moveOnDelay);

		if(acct1Balance >= acct1.getCurrentBalance()+.0001 || acct1Balance <= acct1.getCurrentBalance()-.0001)
		{
			failedTest += "\nAccount 1 balance incorrect: returned " + fmt.format(acct1.getCurrentBalance()) +
					", should have been " + fmt.format(acct1Balance);
			purchaseCheck = false;
		}
		if(acct2Balance >= acct2.getCurrentBalance()+.0001 || acct2Balance <= acct2.getCurrentBalance()-.0001)
		{
			failedTest += "\nAccount 2 balance incorrect: returned " + fmt.format(acct2.getCurrentBalance()) +
					", should have been " + fmt.format(acct2Balance);
			purchaseCheck = false;
		}
		if(acct3Balance >= acct3.getCurrentBalance()+.0001 || acct3Balance <= acct3.getCurrentBalance()-.0001)
		{
			failedTest += "\nAccount 3 balance incorrect: returned " + fmt.format(acct3.getCurrentBalance()) +
					", should have been " + fmt.format(acct3Balance);
			purchaseCheck = false;
		}
		if(acct4Balance >= acct4.getCurrentBalance()+.0001 || acct4Balance <= acct4.getCurrentBalance()-.0001)
		{
			failedTest += "\nAccount 4 balance incorrect: returned " + fmt.format(acct4.getCurrentBalance()) +
					", should have been " + fmt.format(acct4Balance);
			purchaseCheck = false;
		}

		//check balances
		System.out.print("\nChecking Your Balances");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(purchaseCheck)
			System.out.println("passed");
		else
		{
			System.out.println("failed");
			System.out.println(failedTest);
			failedTest = "";	//reset failed string
		}

		System.out.println("\nPress enter to move to the next test\n");
		scan.nextLine();
		boolean paymentCheck = true;	//checks if payments were made correctly

		//make payments on accounts
		System.out.print("\nMaking payment on account 2 of -75.00");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct1.makeAPayment(-75.00) != false)	//no negative payment
		{
			System.out.println("payment");
			System.out.println("\nError! Negative payment should not be allowed.\n");
			paymentCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		else
			System.out.println("not paid");	//end line


		System.out.print("Making payment on account 2 of 500.01");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct2.makeAPayment(500.01) != false)	//no payments greater than balance of account
		{
			System.out.println("paid");
			System.out.println("\nError! Payments greater than the balance are not accepted.\n");
			paymentCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		else
			System.out.println("not paid");	//end line


		System.out.print("Making payment on account 1 of 10000.00");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct1.makeAPayment(10000.00) == false)	//no payments greater than balance of account
		{
			System.out.println("not paid");
			System.out.println("\nError! Payment should be made.\n");
			paymentCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		else
		{
			System.out.println("paid");	//end line
			acct1Balance -= 10000.00;				//update account 1 balance
			acct1Balance += acct1Balance * acct1Rate/100/12;	//update balance
		}


		System.out.print("Making payment on account 2 of 499.99");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct2.makeAPayment(499.99) == false)	//no payments greater than balance of account
		{
			System.out.println("not paid");
			System.out.println("\nError! Payment should be made.\n");
			paymentCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		else
		{
			System.out.println("paid");	//end line
			acct2Balance -= 499.99;				//update account 1 balance
			acct2Balance += acct2Balance * acct2Rate/100/12;	//update balance
		}


		System.out.print("Making payment on account 3 of 5500.00");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct3.makeAPayment(5500.00) == false)	//no payments greater than balance of account
		{
			System.out.println("not paid");
			System.out.println("\nError! Payment should be made.\n");
			paymentCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		else
		{
			System.out.println("paid");	//end line
			acct3Balance -= 5500.00;				//update account 1 balance
			acct3Balance += acct3Balance * acct3Rate/100/12;	//update balance
		}


		System.out.print("Making payment on account 4 of 156.64");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct4.makeAPayment(156.64) == false)	//no payments greater than balance of account
		{
			System.out.println("not paid");
			System.out.println("\nError! Payment should be made.\n");
			paymentCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		else
		{
			System.out.println("paid");	//end line
			acct4Balance -= 156.64;				//update account 1 balance
			acct4Balance += acct4Balance * acct4Rate/100/12;	//update balance
		}


		if(acct1Balance >= acct1.getCurrentBalance()+.0001 || acct1Balance <= acct1.getCurrentBalance()-.0001)
		{
			failedTest += "\nAccount 1 balance incorrect: returned " + fmt.format(acct1.getCurrentBalance()) +
					", should have been " + fmt.format(acct1Balance);
			paymentCheck = false;
		}
		if(acct2Balance >= acct2.getCurrentBalance()+.0001 || acct2Balance <= acct2.getCurrentBalance()-.0001)
		{
			failedTest += "\nAccount 2 balance incorrect: returned " + fmt.format(acct2.getCurrentBalance()) +
					", should have been " + fmt.format(acct2Balance);
			paymentCheck = false;
		}
		if(acct3Balance >= acct3.getCurrentBalance()+.0001 || acct3Balance <= acct3.getCurrentBalance()-.0001)
		{
			failedTest += "\nAccount 3 balance incorrect: returned " + fmt.format(acct3.getCurrentBalance()) +
					", should have been " + fmt.format(acct3Balance);
			paymentCheck = false;
		}
		if(acct4Balance >= acct4.getCurrentBalance()+.0001 || acct4Balance <= acct4.getCurrentBalance()-.0001)
		{
			failedTest += "\nAccount 4 balance incorrect: returned " + fmt.format(acct4.getCurrentBalance()) +
					", should have been " + fmt.format(acct4Balance);
			paymentCheck = false;
		}

		//check balances
		System.out.print("\nChecking Your Balances");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(paymentCheck)
			System.out.println("passed");
		else
		{
			System.out.println("failed");
			System.out.println(failedTest);
			failedTest = "";	//reset failed string
		}

		System.out.println("\nPress enter to move to the next test\n");
		scan.nextLine();

		boolean paymentDefaultCheck = true;	//checks if default payments were made correctly

		//make default payments on accounts

		System.out.print("\nMaking default payment on account 1");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct1.makeAPayment() == false)
		{
			System.out.println("not paid");
			System.out.println("\nError! Default Payment should be allowed.\n");
			paymentDefaultCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		else
		{
			System.out.println("paid");	//end line
			if(acct1Balance<20)
				acct1Balance = 0;
			else if(20>(acct1Balance*.02))	//if 20 dollars is greater than 2% of the balance
			{
				acct1Balance -= 20;
				acct1Balance += acct1Balance*(acct1Rate/100/12);
			}
			else
			{
				acct1Balance -= acct1Balance*.02;
				acct1Balance += acct1Balance*(acct1Rate/100/12);
			}
		}


		System.out.print("Making default payment on account 2");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct2.makeAPayment() == false)
		{
			System.out.println("not paid");
			System.out.println("\nError! Default Payment should be allowed.\n");
			paymentDefaultCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		else
		{
			System.out.println("paid");	//end line
			if(acct2Balance<20)
				acct2Balance = 0;
			else if(20>(acct2Balance*.02))	//if 20 dollars is greater than 2% of the balance
			{
				acct2Balance -= 20;
				acct2Balance += acct2Balance*(acct2Rate/100/12);
			}
			else
			{
				acct2Balance -= acct2Balance*.02;
				acct2Balance += acct2Balance*(acct2Rate/100/12);
			}
		}


		System.out.print("Making default payment on account 3");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct3.makeAPayment() == false)
		{
			System.out.println("not paid");
			System.out.println("\nError! Default Payment should be allowed.\n");
			paymentDefaultCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		else
		{
			System.out.println("paid");	//end line
			if(acct3Balance<20)
				acct3Balance = 0;
			else if(20>(acct3Balance*.02))	//if 20 dollars is greater than 2% of the balance
			{
				acct3Balance -= 20;
				acct3Balance += acct3Balance*(acct3Rate/100/12);
			}
			else
			{
				acct3Balance -= acct3Balance*.02;
				acct3Balance += acct3Balance*(acct3Rate/100/12);
			}
		}


		System.out.print("Making default payment on account 4");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct4.makeAPayment() == false)
		{
			System.out.println("not paid");
			System.out.println("\nError! Default Payment should be allowed.\n");
			paymentDefaultCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		else
		{
			System.out.println("paid");	//end line
			if(acct4Balance<20)
				acct4Balance = 0;
			else if(20>(acct4Balance*.02))	//if 20 dollars is greater than 2% of the balance
			{
				acct4Balance -= 20;
				acct4Balance += acct4Balance*(acct4Rate/100/12);
			}
			else
			{
				acct4Balance -= acct4Balance*.02;
				acct4Balance += acct4Balance*(acct4Rate/100/12);
			}
		}


		if(acct1Balance >= acct1.getCurrentBalance()+.0001 || acct1Balance <= acct1.getCurrentBalance()-.0001)
		{
			failedTest += "\nAccount 1 balance incorrect: returned " + fmt.format(acct1.getCurrentBalance()) +
					", should have been " + fmt.format(acct1Balance);
			paymentDefaultCheck = false;
		}
		if(acct2Balance >= acct2.getCurrentBalance()+.0001 || acct2Balance <= acct2.getCurrentBalance()-.0001)
		{
			failedTest += "\nAccount 2 balance incorrect: returned " + fmt.format(acct2.getCurrentBalance()) +
					", should have been " + fmt.format(acct2Balance);
			paymentDefaultCheck = false;
		}
		if(acct3Balance >= acct3.getCurrentBalance()+.0001 || acct3Balance <= acct3.getCurrentBalance()-.0001)
		{
			failedTest += "\nAccount 3 balance incorrect: returned " + fmt.format(acct3.getCurrentBalance()) +
					", should have been " + fmt.format(acct3Balance);
			paymentDefaultCheck = false;
		}
		if(acct4Balance >= acct4.getCurrentBalance()+.0001 || acct4Balance <= acct4.getCurrentBalance()-.0001)
		{
			failedTest += "\nAccount 4 balance incorrect: returned " + fmt.format(acct4.getCurrentBalance()) +
					", should have been " + fmt.format(acct4Balance);
			paymentDefaultCheck = false;
		}

		//check balances
		System.out.print("\nChecking Your Balances");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(paymentDefaultCheck)
			System.out.println("passed");
		else
		{
			System.out.println("failed");
			System.out.println(failedTest);
			failedTest = "";	//reset failed string
		}

		//Thread.sleep(moveOnDelay);
		System.out.println("\nPress enter to move to the next test\n");
		scan.nextLine();

		boolean transferCheck = true;	//check if funds transfer properly

		//transfer account 3 to account 2
		System.out.print("\nTransferring funds from account 3 to account 2");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct3.transferBalanceTo(acct2) == false)
		{
			System.out.println("not transferred");
			System.out.println("\nError! Transfer should have been allowed.\n");
			transferCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		else
		{
			System.out.println("transferred");	//end line
			acct2Balance += acct3Balance;
			acct3Balance = 0;
		}


		boolean closeCheck = true;	//check closing accounts

		//making payment on account 4
		System.out.print("Making payment on account 4 of $" + fmt.format(acct4Balance));

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct4.makeAPayment(acct4Balance) == false)
		{
			System.out.println("not paid");
			System.out.println("\nError! Payment should have been allowed.\n");
			closeCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		else
		{
			System.out.println("paid");	//end line
			acct4Balance -= acct4Balance;
		}


		if(acct1Balance >= acct1.getCurrentBalance()+.0001 || acct1Balance <= acct1.getCurrentBalance()-.0001)
		{
			failedTest += "\nAccount 1 balance incorrect: returned " + fmt.format(acct1.getCurrentBalance()) +
					", should have been " + fmt.format(acct1Balance);
			transferCheck = false;
		}
		if(acct2Balance >= acct2.getCurrentBalance()+.0001 || acct2Balance <= acct2.getCurrentBalance()-.0001)
		{
			failedTest += "\nAccount 2 balance incorrect: returned " + fmt.format(acct2.getCurrentBalance()) +
					", should have been " + fmt.format(acct2Balance);
			transferCheck = false;
		}
		if(acct3Balance >= acct3.getCurrentBalance()+.0001 || acct3Balance <= acct3.getCurrentBalance()-.0001)
		{
			failedTest += "\nAccount 3 balance incorrect: returned " + fmt.format(acct3.getCurrentBalance()) +
					", should have been " + fmt.format(acct3Balance);
			transferCheck = false;
		}
		if(acct4Balance >= acct4.getCurrentBalance()+.0001 || acct4Balance <= acct4.getCurrentBalance()-.0001)
		{
			failedTest += "\nAccount 4 balance incorrect: returned " + fmt.format(acct4.getCurrentBalance()) +
					", should have been " + fmt.format(acct4Balance);
			transferCheck = false;
		}

		//check balances
		System.out.print("\nChecking Your Balances");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(transferCheck)
			System.out.println("passed");
		else
		{
			System.out.println("failed");
			System.out.println(failedTest);
			failedTest = "";	//reset failed string
		}

		System.out.println("\nPress enter to move to the next test\n");
		scan.nextLine();

		//closing account 1
		System.out.print("\nClosing Account 1");
		acct1.close();

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(!acct1.isActive())
		{
			System.out.println("closed");
			System.out.println("\nError! Acccount 1 should not have been closed.\n");
			closeCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		else
		{
			System.out.println("not closed");	//end line
		}


		//closing account 4
		System.out.print("Closing Account 4");
		acct4.close();

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct4.isActive())
		{
			System.out.println("not closed");
			System.out.println("\nError! Acccount 4 should have been closed.\n");
			closeCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		else
		{
			System.out.println("closed");	//end line
		}


		if(!acct1.isActive())
		{
			failedTest += "\nAccount 1 should be active.";
			closeCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		if(!acct2.isActive())
		{
			failedTest += "\nAccount 2 should be active.";
			closeCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		if(acct3.isActive())
		{
			failedTest += "\nAccount 3 should be inactive.";
			closeCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		if(acct4.isActive())
		{
			failedTest += "\nAccount 4 should be inactive.";
			closeCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}

		//check account activity
		System.out.print("\nChecking Your Account Activity");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(closeCheck)
			System.out.println("passed");
		else
		{
			System.out.println("failed");
			System.out.println(failedTest);
			failedTest = "";	//reset failed string
		}

		System.out.println("\nPress enter to move to the next test\n");
		scan.nextLine();

		//check transactions on closed accounts
		boolean closedCheck = true;

		//making purchase on account 3
		System.out.print("\nMaking purchase on account 3 of $.01");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct3.makeAPurchase(.01))
		{
			System.out.println("purchased");
			System.out.println("\nError! Purchase should not have been allowed.\n");
			acct3Balance += .01;
			closedCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		else
		{
			System.out.println("not purchased");	//end line
		}


		//making payment on account 4
		System.out.print("Making payment on account 4 of $100000.00");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct4.makeAPayment(acct4Balance))
		{
			System.out.println("paid");
			System.out.println("\nError! Payment should not have been allowed.\n");
			acct4Balance -= 100000.00;
			acct4Balance += acct4Balance * acct4Rate/12/100;
			closedCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		else
		{
			System.out.println("not paid");	//end line
		}


		//making default payment on account 4
		System.out.print("Making default payment on account 4");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct4.makeAPayment())
		{
			System.out.println("paid");
			System.out.println("\nError! Payment should not have been allowed.\n");
			acct4Balance -= 20;
			acct4Balance += acct4Balance * acct4Rate/12/100;
			closedCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		else
		{
			System.out.println("not paid");	//end line
		}


		//transfer to a closed account
		System.out.print("Transferring funds from account 2 to account 4");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct2.transferBalanceTo(acct4))
		{
			System.out.println("transferred");
			System.out.println("\nError! Transfer should not have been allowed.\n");
			acct4Balance += acct2Balance;
			acct4Balance = 0;
			closedCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		else
		{
			System.out.println("not transferred");	//end line
		}


		//transfer from a closed account
		System.out.print("Transferring funds from account 4 to account 1");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(acct4.transferBalanceTo(acct1))
		{
			System.out.println("transferred");
			System.out.println("\nError! Transfer should not have been allowed.\n");
			acct1Balance += acct4Balance;
			acct4Balance = 0;
			closedCheck = false;
			System.out.println("\nPress enter to move to the next test\n");
			scan.nextLine();
		}
		else
		{
			System.out.println("not transferred");	//end line
		}


		if(acct1Balance >= acct1.getCurrentBalance()+.0001 || acct1Balance <= acct1.getCurrentBalance()-.0001)
		{
			failedTest += "\nAccount 1 balance incorrect: returned " + fmt.format(acct1.getCurrentBalance()) +
					", should have been " + fmt.format(acct1Balance);
			closedCheck = false;
		}
		if(acct2Balance >= acct2.getCurrentBalance()+.0001 || acct2Balance <= acct2.getCurrentBalance()-.0001)
		{
			failedTest += "\nAccount 2 balance incorrect: returned " + fmt.format(acct2.getCurrentBalance()) +
					", should have been " + fmt.format(acct2Balance);
			closedCheck = false;
		}
		if(acct3Balance >= acct3.getCurrentBalance()+.0001 || acct3Balance <= acct3.getCurrentBalance()-.0001)
		{
			failedTest += "\nAccount 3 balance incorrect: returned " + fmt.format(acct3.getCurrentBalance()) +
					", should have been " + fmt.format(acct3Balance);
			closedCheck = false;
		}
		if(acct4Balance >= acct4.getCurrentBalance()+.0001 || acct4Balance <= acct4.getCurrentBalance()-.0001)
		{
			failedTest += "\nAccount 4 balance incorrect: returned " + fmt.format(acct4.getCurrentBalance()) +
					", should have been " + fmt.format(acct4Balance);
			closedCheck = false;
		}

		//check balances
		System.out.print("\nChecking Your Final Balances");

		System.out.print(".");
		System.out.print(".");
		System.out.print(".");

		if(closedCheck)
			System.out.println("passed");
		else
		{
			System.out.println("failed");
			System.out.println(failedTest);
			failedTest = "";	//reset failed string
		}

		System.out.println("\nPress enter to move to the next test\n");
		scan.nextLine();

		//Printing final account information
		System.out.println("\nYour final account information");
		Thread.sleep(checkDelay);
		System.out.println(acct1);
		Thread.sleep(checkDelay);
		System.out.println(acct2);
		Thread.sleep(checkDelay);
		System.out.println(acct3);
		Thread.sleep(checkDelay);
		System.out.println(acct4);
		Thread.sleep(checkDelay);

		//final check
		System.out.print("\nFINAL CHECK");
		Thread.sleep(finalDelay); //pause for effect
		System.out.print(".");
		Thread.sleep(finalDelay); //pause for effect
		System.out.print(".");
		Thread.sleep(finalDelay); //pause for effect
		System.out.print(".");
		Thread.sleep(finalDelay2);

		//Print out final pass or fail message
		if(initialCheck && purchaseCheck && paymentCheck && paymentDefaultCheck && transferCheck && closeCheck && closedCheck)
			System.out.println("\n\n\nPASSED all tests! Congratulations!\n");
		else
			System.out.println("\n\n\nFAILED one or more tests. Please try again.\n");
		scan.close();
	}
}