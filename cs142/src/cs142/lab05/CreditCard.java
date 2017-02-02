package cs142.lab05;

//**********************************************************************************************************************************************************
//		 	CreditCard.java		Author: David Tanner		Date Created: December 2005
//		 				Edited by: Robbie Buck		Last Edited: January 6, 2008
//
//					Purpose: Interface created for lab 4 of BYU CS 142 class
//**********************************************************************************************************************************************************				
interface CreditCard
{
	/*
	 *	-Make a purchase on the credit card according to the purchase amount
	 *	-Do not allow negative purchases
	 *	-Do not allow purchases on a closed account
	 *	-Return true if a purchase is made
	 */
	public boolean makeAPurchase(double purchaseAmount);

	/*
	 *	-Make a payment on the credit card according to the payment amount
	 *	-Do not allow negative payments
	 *	-Do not allow a payment larger than the credit card debt
	 *	-Do not allow a payment on a closed account
	 *	-Add on the monthly interest after a payment is made
	 *	-Return true if a payment is made
	 */
	public boolean makeAPayment(double paymentAmount);

	/*
	 *	-Make a default payment on the credit card:
	 * 		-if credit card debt is less than $20 then pay off remaining debt
	 * 		-if credit card debt is greater than $20 make a payment of $20 or 2% of the credit card balance, whichever is greater
	 * 	-Do not allow a payment on a closed account
	 * 	-Add on the monthly interest after a payment is made
	 * 	-Return true if a payment is made
	 */
	public boolean makeAPayment();

	/*
	 *	-Transfer the credit card balance from the current account to the account given
	 *	-Do not allow the transfer if either account is closed
	 *	-Close account if balance is transferred
	 *	-Return true if money is transferred
	 */
	public boolean transferBalanceTo(CreditCard otherAccount);

	/*
	 * 	-Close current account if balance is 0
	 */
	public void close();

	/*
	 * 	-Return whether the current account is active or not
	 */
	public boolean isActive();

	/*
	 *	-Return the name of the account
	 */
	public String getAccountName();

	/*
	 * 	-Return the credit card balance of the current account
	 */
	public double getCurrentBalance();

	/**
	 * 	-Return a string that includes the following
	 * 		-the name of the credit card account
	 * 		-the credit card debt of the current account
	 * 		-the annual interest rate of the current account
	 */
	public String toString();
}
