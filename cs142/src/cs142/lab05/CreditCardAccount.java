package cs142.lab05;

//**********************************************************************************************************************************************************
//	
//	CreditCardAccount.java			Author: Robbie Buck					Date Created: July 24, 2006
//													Last Edited: January 6, 2008
//				Lab 5 part 2: Implementation of CreditCard, created in part 1
//**********************************************************************************************************************************************************

import java.text.DecimalFormat;
import java.util.Random;

public class CreditCardAccount implements CreditCard
{
	private String accountName;
	private double  balance;	//balance of account
	private double annualInterestRate;		//annual interest rate
	private boolean open;				//determines if account is open

	public CreditCardAccount(String name)
	{
		accountName = name;
		balance = 0.00;
		annualInterestRate = .2;
		open = true;
	}

	public CreditCardAccount(String name, double originalBalance, double annualRate)
	{
		accountName = name;
		balance = originalBalance;
		annualInterestRate = annualRate/100;
		open = true;
	}

	public CreditCardAccount(double originalBalance, double annualRate)
	{
		//create account name
		accountName = "";
		Random generator = new Random();

		for(int i=1; i<=16; i++)	//generate random 16 digit account number (account name)
		{
			accountName += generator.nextInt(10);
			if(i%4==0 && i!=16)	//formatting
				accountName += " ";
		}

		balance = originalBalance;
		annualInterestRate = annualRate/100;
		open = true;
	}

	/*
	 *	-Make a purchase on the credit card according to the purchase amount
	 *	-Do not allow negative purchases
	 *	-Do not allow purchases on a closed account
	 *	-Return true if a purchase is made
	 */
	public boolean makeAPurchase(double purchaseAmount)
	{
		if(!open)	//if account is closed, can't make a purchase
		{
			return false;
		}

		if(purchaseAmount<0)	//invalid purchase amount
		{
			return false;
		}
		else
		{
			balance += purchaseAmount;
		}

		return true;
	}
	/*
	 *	-Make a payment on the credit card according to the payment amount
	 *	-Do not allow negative payments
	 *	-Do not allow a payment larger than the credit card debt
	 *	-Do not allow a payment on a closed account
	 *	-Add on the monthly interest after a payment is made
	 *	-Return true if a payment is made
	 */
	public boolean makeAPayment(double paymentAmount)
	{
		if(!open)	//if account is closed, can't make a payment
		{
			return false;
		}

		if(paymentAmount>balance)
		{
			return false;
		}

		if(paymentAmount <0)
		{
			return false;
		}
		else
		{
			balance -= paymentAmount;
			balance += balance*(annualInterestRate/12);
		}

		return true;
	}

	/*
	 *	-Make a default payment on the credit card:
	 * 		-if credit card debt is less than $20 then pay off remaining debt
	 * 		-if credit card debt is greater than $20 make a payment of $20 or 2% of the credit card balance, whichever is greater
	 * 	-Do not allow a payment on a closed account
	 * 	-Add on the monthly interest after a payment is made
	 * 	-Return true if a payment is made
	 */
	public boolean makeAPayment()
	{
		if(!open)	//if account is closed, can't make a payment
		{
			return false;
		}

		if(balance<20)
		{
			balance = 0;
		}
		else if(20>(balance*.02))	//if 20 dollars is greater than 2% of the balance
		{
			balance -= 20;
		}
		else
		{
			balance -= balance*.02;				
		}

		balance += balance*(annualInterestRate/12); //Charge interest after payment

		return true;
	}

	/*
	 *	-Transfer the credit card balance from the current account to the account given
	 *	-Do not allow the transfer if either account is closed
	 *	-Return true if money is transferred
	 */
	public boolean transferBalanceTo(CreditCard otherAccount)
	{
		if(!open || !otherAccount.isActive())	//if either account is closed, can't transfer balance
		{
			return false;
		}

		otherAccount.makeAPurchase(balance);
		makeAPayment(balance);
		close();

		return true;
	}

	/*
	 * 	-Close current account if balance is 0
	 */
	public void close()
	{
		if(balance==0)
		{
			open = false;
		}
	}

	/*
	 * 	-Return whether the current account is active or not
	 */
	public boolean isActive()
	{
		return open;
	}

	/*
	 *	-Return the name of the account
	 */
	public String getAccountName()
	{
		return accountName;
	}

	/*
	 * 	-Return the credit card balance of the current account
	 */
	public double getCurrentBalance()
	{
		return balance;
	}

	/*
	 * 	-Return a string that includes the following
	 * 		-the name of the credit card account
	 * 		-the credit card debt of the current account
	 * 		-the annual interest rate of the current account
	 */
	public String toString()
	{
		DecimalFormat fmt = new DecimalFormat("0.##");	//formatting for double output
		String active;	//determine account status

		if(open)
		{
			active = "active";
		}
		else
		{
			active = "inactive";
		}

		return "\n\tName: " + accountName + "\n\tBalance: $" + fmt.format(balance) + "\n\tAccount Status: " + active +
		"\n\tAnnual Interest Rate: " + fmt.format((annualInterestRate*100)) + "%";
	}
}
