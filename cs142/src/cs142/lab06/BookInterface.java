//****************************************************************************************************
//
//	BookInterface.java	Authors: Andrew D and Michael		Date Created: September 2007
//				Edited by: Robbie Buck			Last Edited: October 26, 2007
//
//		Purpose: Book storage (each Book has a title, year first published, price, and author)
//
//****************************************************************************************************
package cs142.lab06;

public interface BookInterface
{
	/*
	 * 	Returns the title of the book
	 */
	public String getTitle();

	/*
	 * 	Return price of the book
	 */
	public double getPrice();

	/*
	 * 	Return all information about the book (see example program for a good format)
	 */
	public String toString();
}

