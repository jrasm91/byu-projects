//****************************************************************************************************
//
//	CDInterface.java	Authors: Andrew D and Michael		Date Created: September 2007
//				Edited by: Robbie Buck			Last Edited: October 26, 2007
//
//		Purpose: CD storage (each CD has a title, year released, price, and artist)
//
//****************************************************************************************************

package cs142.lab06;
public interface CDInterface
{
	/*
	 * 	Returns the title of the CD
	 */
	public String getTitle();

	/*
	 * 	Return price of the CD
	 */
	public double getPrice();

	/*
	 * 	Return all information about the CD (see example program for a good format)
	 */
	public String toString();
}

