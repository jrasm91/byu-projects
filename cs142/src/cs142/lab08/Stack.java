package cs142.lab08;

import javax.swing.*;

public class Stack
{
	private int size;
	private ImageIcon shirt1;
	private ImageIcon shirt2;
	private ImageIcon shirt3;
	private ImageIcon shirt4;
	private ImageIcon shirt5;
	
	
	public Stack()
	{
		size= 0;
		shirt1 = null;
		shirt2 = null;
		shirt3 = null;
		shirt4 = null;
		shirt5 = null;
	}

	
	
	public boolean add(ImageIcon icon)
	{
		if(shirt5 == null)
		{
			shirt5 = shirt4;
			shirt4 = shirt3;
			shirt3 = shirt2;
			shirt2 = shirt1;
			shirt1 = icon;
			size++;
			return true;
		}
		
		else
		{
			return false;
		}
	}
	
	public ImageIcon remove()
	{
		ImageIcon temp = shirt1;
		shirt1 = shirt2;
		shirt2 = shirt3;
		shirt3 = shirt4;
		shirt4 = shirt5;
		shirt5 = null;
		size--;
		return temp;
		
	}
	
	public ImageIcon getTopShirt()
	{
		return shirt1;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public int checkOut()
	{
		int temp = getSize();
		shirt1 = null;
		shirt2 = null;
		shirt3 = null;
		shirt4 = null;
		shirt5 = null;
		size = 0;
		return temp;
	}
	
}
