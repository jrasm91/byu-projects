package cs142.MidExam2;

import java.awt.Dimension;

import javax.swing.*;


public class WardDisplay 
{
	// main method which creates, packs, and displays a new WardDisplayPanel
	public static void main (String[] args)
	{
		int Width = 500;
		int Height = 600;
		JFrame frame = new JFrame ("The Ward Info");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new WardDisplayPanel());
		frame.setPreferredSize(new Dimension(Width, Height));
		frame.pack();
		frame.setLocation(380,150);
		frame.setResizable(true);
		frame.setVisible(true);
	}



}
