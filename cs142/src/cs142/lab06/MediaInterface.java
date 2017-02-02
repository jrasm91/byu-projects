//****************************************************************************************************
//
//	MediaInterface.java	Authors: Andrew D and Michael		Date Created: September 2007
//				Edited by: Robbie Buck			Last Edited: August 20, 2009
//
//		Purpose: Interface between the GUI code and media storage
//
//****************************************************************************************************
package cs142.lab06;
import java.io.FileNotFoundException;

public interface MediaInterface
{
	//constants
	final int CDS = 0;
	final int BOOKS = 1;

	/*
	 *	Add CDs contained in given file to CD library
	 */
	public void addCDFile(String fileName) throws FileNotFoundException;

	/*
	 *	Add books contained in given file to book library
	 */
	public void addBookFile(String fileName) throws FileNotFoundException;

	/*
	 *	Store the new CD according to the given CD information
	 *	-be sure to update size of array
	 */
	public void addCD(String name, int year, double price, String artist);

	/*
	 *	Store the new book according to the given book information
	 *	-be sure to update size of array
	 */
	public void addBook(String title, int year, double price, String author);
	
	/*
	 *
	 *	Return an array of all CDs currently in storage
	 */
	public CD[] getCDArr();

	/*
	 *	Return an array of all books currently in storage
	 */
	public Book[] getBookArr();

	/*
	 * 	Return cd object corresponding to given array index
	 */
	public CD getCD(int index);

	/*
	 * 	Return book object corresponding to given array index
	 */
	public Book getBook(int index);

	/*
	 *	Return an integer array containing number of cds and books
	 *	-indexes for the array are according to constants given above
	 */
	public int[] getSize();

	/*
	 * 	Return a string array containing the titles of all the CDs in storage
	 */
	public String[] getCDTitles();

	/*
	 * 	Return a string array containing the titles of all the books in storage
	 */
	public String[] getBookTitles();

	/*
	 * 	Return the cd object with the given title
	 */
	public CD findCD(String title);

	/*
	 * 	Return the book object with the given title
	 */
	public Book findBook(String title);

	/*
	 * 	Remove cd with given title from cd storage
	 *	-update size of array accordingly (both integer size and actual size of array)
	 */
	public void removeCD(String titlle);

	/*
	 * 	Remove book with given title from book storage
	 *	-update size of array accordingly (both integer size and actual size of array)
	 */
	public void removeBook(String title);

	/*
	 *	Return net worth of your store (price of all media in storage)
	 */
	public double getNetWorth();
}

