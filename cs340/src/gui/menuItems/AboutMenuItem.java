package gui.menuItems;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import gui.Main.GUI;
/**
 * Represents the About menu item presented in the Help menu
 * 
 * @author Matthew Smith
 *
 */
public class AboutMenuItem extends JMenuItem implements ActionListener{

	GUI main;
	
	/**
	 * Creates an About menu Item
	 * @param main
	 */
	public AboutMenuItem (GUI main)
	{
		this.main = main;
		
		init();
	}
	
	/**
	 * initializes the GUI components
	 */
	public void init()
	{
		this.setText("About");
		
		this.addActionListener(this);
	}
	
	/**
	 * Action when menu item is pressed
	 */
	public void actionPerformed(ActionEvent e) {		
		JOptionPane.showMessageDialog(main, "<html><H1>HyPeerWeb Debugger V 1.1</H1><p> GUI developed by Matthew Smith, March 2010 and modified by Dr. Woodfield Nov. 2010.</p></body></html>", "About The HyPeerWeb Debugger", JOptionPane.QUESTION_MESSAGE);
	}

}
