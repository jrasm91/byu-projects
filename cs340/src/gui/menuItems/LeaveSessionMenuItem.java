package gui.menuItems;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import gui.Main.GUI;

/**
 * Represents the Leave session menu item presented in the Help menu
 * 
 * @author Matthew Smith
 *
 */
public class LeaveSessionMenuItem extends JMenuItem implements ActionListener{
	
	GUI main;
	
	/**
	 * Creates a Leave session menu Item
	 * @param main
	 */
	public LeaveSessionMenuItem (GUI main)
	{
		this.main = main;
		
		init();
	}
	
	/**
	 * initializes the GUI components
	 */
	public void init()
	{
		this.setText("Leave");
		
		this.addActionListener(this);
	}


	/**
	 * Action when menu item is pressed
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Phase 6 -- provide functionality for leaving a session but no terminate the session 
		
	}

}
