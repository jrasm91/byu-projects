package cs142.lab05;


public class CreditCardAccountDONE implements CreditCard
{
	private String name;
	private double balance;
	private double interestRate;
	private Boolean active;


	public CreditCardAccountDONE(String accountName)
	{
		name = accountName;
		balance = 0;
		interestRate = .20;
		active = true;
	}

	public CreditCardAccountDONE(String accountName, double accountBalance, double accountInterestRate)
	{
		name = accountName;
		balance = accountBalance;
		interestRate = accountInterestRate / 100;
		active = true;
	}

	public CreditCardAccountDONE(double accountBalance, double accountInterestRate)
	{
		String userName = "";
		for(int i = 1; i<= 16;i++)
		{
			userName += (int)(Math.random()*10);
			if(i%4==0)
			{
				if(i!=8)
					userName +=" ";
			}
		}
		name = userName;
		balance = accountBalance;
		interestRate = accountInterestRate/100;
		active = true;
	}

	public boolean makeAPurchase(double purchaseAmount)
	{
		if((active)&&(purchaseAmount>=0))
		{
			balance += purchaseAmount;
			return true;
		}
		else
			return false;
	}

	public boolean makeAPayment(double paymentAmount)
	{
		if((paymentAmount < 0) || (!active) || (paymentAmount > balance))
		{
			return false;
		}
		else
		{
			balance -= paymentAmount;
			balance += balance*interestRate/12;
			return true;
		}
	}

	public boolean makeAPayment()
	{
		if(active)
		{
			if(balance <= 20)
			{
				balance = 0;
				return true;
			}
			else
			{
				if(balance*.02 > 20)
				{
					balance -= balance*.02;	
					balance += balance*interestRate/12;
					return true;
				}
				else
				{
					balance -= 20;
					balance += balance*interestRate/12;
					return true;
				}
			}
		}
		else
			return false;
	}

	public boolean transferBalanceTo(CreditCard otherAccount)
	{
		if(otherAccount.isActive()&& isActive())
		{
			otherAccount.makeAPurchase(balance);
			balance = 0;
			active = false;
			return true;
		}
		else
		{
			return false;
		}
	}

	public void close()
	{
		if(balance == 0)
		{
			active = false;
		}
	}

	public boolean isActive()
	{
		return active;
	}

	public String getAccountName()
	{
		return name;
	}

	public double getCurrentBalance()
	{
		return balance;
	}

	public String toString()
	{
		return "Name: " + name + "\nDebt :" + balance + "\nAnnual Interest Rate: " + interestRate + "%";
	}
}



