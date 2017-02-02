package gui.menuItems;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import gui.Main.GUI;

/**
 * Represents the start session menu item presented in the Help menu
 * 
 * @author Matthew Smith
 *
 */
public class StartSessionMenuItem extends JMenuItem implements ActionListener{
	
	GUI main;
	
	/**
	 * Creates a Start Session menu Item
	 * @param main
	 */
	public StartSessionMenuItem (GUI main)
	{
		this.main = main;
		
		init();
	}
	
	/**
	 * initializes the GUI components
	 */
	public void init()
	{
		this.setText("New");
		
		this.addActionListener(this);
	}

	
	/**
	 * Action when menu item is pressed
	 */
	public void actionPerformed(ActionEvent e) {
		
		// TODO Phase 6 -- provide functionality for starting a new session of the HyPeerWeb
		
	}

}
