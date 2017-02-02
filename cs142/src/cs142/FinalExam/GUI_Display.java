package cs142.FinalExam;

/*****************************************
 *    CS 142 Fall '09 Final Exam         *
 *    Name: Jason Rasmussen              *
 *    Student ID: 30-984-1901            *
 *    Jason.Rasmussen@comcast.net        *
 *    PIN: 9328                          *
 ****************************************/

import java.awt.Dimension;

import javax.swing.*;


public class GUI_Display extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main (String[] args)
	{
		JFrame frame = new JFrame ("The Greater Provo Railroad Railroad Station");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new RailRoad_JPanel());
		frame.setPreferredSize(new Dimension(600,350));
		frame.pack();
		frame.setLocation(0,0);
		frame.setResizable(true);
		frame.setVisible(true);
	}



}
