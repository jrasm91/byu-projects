package cs142.lab06;

public class CD implements CDInterface
{
	private String title;
	private int year;
	private double price;
	private String artist;
	
	public CD(String cdName, int cdYear, double cdPrice, String cdArtist)
	{
		title = cdName;
		year = cdYear;
		price = cdPrice;
		artist = cdArtist;
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
	
	public String getArtist()
	{
		return artist;
	}
	
	public String toString()
	{
		 return "Title: "+title+"\nYear: "+year+"\nArtist: "+artist+"\nPrice: "+price;
	}

	
	
	
}
