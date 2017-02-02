//****************************************************************************************************
//
//	Author: Andrew Draper
//
//  Uses LibraryLayout2
//
//****************************************************************************************************
package cs142.lab06;
import javax.swing.*;

public class CougarMediaStore
{
	public static void main (String[] args)
	{
		JFrame frame = new JFrame ("Welcome to the Virtual Library");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new MediaDisplay());
		frame.pack();
		frame.setLocation(350, 200);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
