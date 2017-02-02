
package cs142.lab06;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Media implements MediaInterface
{
	//constants
	final int CDS = 0;
	final int BOOKS = 1;
	
	private Book[] bookArray = {};
	private CD[] cdArray = {};

	/*
	 *	Add CDs contained in given file to CD library
	 */
	public void addCDFile(String fileName) throws FileNotFoundException
	{
		File theFile = new File("cds.txt");
		Scanner fileInput = new Scanner (theFile);
		fileInput.useDelimiter("[\t\n]+");
		while(fileInput.hasNext())
		{
			String temp1 = fileInput.next();
			int temp2 = fileInput.nextInt();
			double temp3 = fileInput.nextDouble();
			String temp4 = fileInput.next();
			
			addCD(temp1, temp2, temp3, temp4);
		}
		fileInput.close();
	}

	/*
	 *	Add books contained in given file to book library
	 */
	public void addBookFile(String fileName) throws FileNotFoundException
	{
		File theFile = new File("books.txt");
		Scanner fileInput = new Scanner (theFile);
		fileInput.useDelimiter("[\t\n]+");
		while(fileInput.hasNext())
		{
			
			String temp1 = fileInput.next();
			int temp2 = fileInput.nextInt();
			double temp3 = fileInput.nextDouble();
			String temp4 = fileInput.next();
			addBook(temp1, temp2, temp3, temp4);
		}
		fileInput.close();
	}

	/*
	 *	Store the new CD according to the given CD information
	 *	-be sure to update size of array
	 */
	public void addCD(String name, int year, double price, String artist)
	{
		CD newCD = new CD(name, year, price, artist);
		CD[] temp= new CD[cdArray.length+1];
		for(int i = 0; i<cdArray.length; i++)
		{
			temp[i] = cdArray[i];
		}
		temp[cdArray.length] = newCD;
		cdArray = temp;
	}

	/*
	 *	Store the new book according to the given book information
	 *	-be sure to update size of array
	 */
	public void addBook(String title, int year, double price, String author)
	{
		Book newBook = new Book(title, year, price, author);
		Book[] temp= new Book[bookArray.length+1];
		for(int i = 0; i<bookArray.length; i++)
		{
			temp[i] = bookArray[i];
		}
		temp[bookArray.length] = newBook;
		bookArray = temp;
	}
	
	/*
	 *
	 *	Return an array of all CDs currently in storage
	 */
	public CD[] getCDArr()
	{
		return cdArray;
	}

	/*
	 *	Return an array of all books currently in storage
	 */
	public Book[] getBookArr()
	{
		return bookArray;
	}

	/*
	 * 	Return cd object corresponding to given array index
	 */
	public CD getCD(int index)
	{
		return cdArray[index];
	}

	/*
	 * 	Return book object corresponding to given array index
	 */
	public Book getBook(int index)
	{
		return bookArray[index];
	}

	/*
	 *	Return an integer array containing number of cds and books
	 *	-indexes for the array are according to constants given above
	 */
	public int[] getSize()
	{
		int[] all = new int[2];
		all[CDS] = cdArray.length;
		all[BOOKS] = bookArray.length;
		return all;
	}

	/*
	 * 	Return a string array containing the titles of all the CDs in storage
	 */
	public String[] getCDTitles()
	{
		String[] CDTitles = new String[cdArray.length];
		for(int i = 0; i< cdArray.length; i++)
		{
			CDTitles[i] = cdArray[i].getTitle();
		}
		return CDTitles;
	}

	/*
	 * 	Return a string array containing the titles of all the books in storage
	 */
	public String[] getBookTitles()
	{
		String[] BOOKTitles = new String[bookArray.length];
		for(int i = 0; i< bookArray.length; i++)
		{
			BOOKTitles[i] = bookArray[i].getTitle();
		}
		return BOOKTitles;
	}

	/*
	 * 	Return the cd object with the given title
	 */
	public CD findCD(String title)
	{
		int i = 0;
		while(!title.equals(cdArray[i].getTitle()))
		{
			i++;
		}
		return cdArray[i];
	}

	/*
	 * 	Return the book object with the given title
	 */
	public Book findBook(String title)
	{
		int i = 0;
		while(!title.equals(bookArray[i].getTitle()))
		{
			i++;
		}
		return bookArray[i];
	}

	/*
	 * 	Remove cd with given title from cd storage
	 *	-update size of array accordingly (both integer size and actual size of array)
	 */
	public void removeCD(String title)
	{
		int i = 0;
		while(!title.equals(cdArray[i].getTitle()))
		{
			i++;
		}
		CD[] temp = new CD[cdArray.length-1];
		boolean schmo = false;
		for(int j = 0; j<temp.length;j++)
		{
			if(j == i)
			{
				schmo = true;
				
			}
			if(schmo)
			{
				temp[j] = cdArray[j+1];
			}
			if(!schmo)
			{
				temp[j] = cdArray[j];
			}
		}
		cdArray = temp;
	}

	/*
	 * 	Remove book with given title from book storage
	 *	-update size of array accordingly (both integer size and actual size of array)
	 */
	public void removeBook(String title)
	{
		int i = 0;
		while(!title.equals(bookArray[i].getTitle()))
		{
			i++;
		}
		Book[] temp = new Book[bookArray.length-1];
		boolean schmo = false;
		for(int j = 0; j<temp.length;j++)
		{
			if(j == i)
			{
				schmo = true;
				
			}
			if(schmo)
			{
				temp[j] = bookArray[j+1];
			}
			if(!schmo)
			{
				temp[j] = bookArray[j];
			}
		}
		bookArray = temp;
	}
	/*
	 *	Return net worth of your store (price of all media in storage)
	 */
	public double getNetWorth()
	{
		double netWorth = 0;
		
		for(int i = 0; i< (bookArray.length); i++)
		{
			netWorth = netWorth + bookArray[i].getPrice();
		}
		for(int i = 0; i< (cdArray.length); i++)
		{
			netWorth += cdArray[i].getPrice();
		}
		return netWorth;
	}
}
