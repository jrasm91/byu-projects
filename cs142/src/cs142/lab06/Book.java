package cs142.lab06;



public class Book implements BookInterface
{
	private String title;
	private int year;
	private double price;
	private String author;
	
	public Book(String bookTitle, int bookYear, double bookPrice, String bookAuthor)
	{
		title = bookTitle;
		year = bookYear;
		price = bookPrice;
		author = bookAuthor;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public int getYear()
	{
		return year;
	}	
	
	public double getPrice()
	{
		return price;
	}
	
	public String getAuthor()
	{
		return author;
	}
	
	public String toString()
	{
		 return "Title: " + title + "\nYear: " + year + "\nAuthor: " + author + "\nPrice: " + price;
	}

}
