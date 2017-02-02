
package cs142.lab08;

import java.awt.Dimension;

import javax.swing.*;


public class BYUBookStoreClothing 
{
	public static void main (String[] args)
	{
		JFrame frame = new JFrame ("BYU BookstoreClothing Aisle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new ClothingDisplay());
		frame.setPreferredSize(new Dimension(1000,750));
		frame.pack();
		frame.setLocation(0,0);
		frame.setResizable(true);
		frame.setVisible(true);
	}



}
