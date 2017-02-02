package gui.menuItems;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import gui.Main.GUI;
/**
 * Represents the end session menu item presented in the Help menu
 * 
 * @author Matthew Smith
 *
 */
public class EndSessionMenuItem extends JMenuItem implements ActionListener{

	GUI main;
	
	/**
	 * Creates an End session menu Item
	 * @param main
	 */
	public EndSessionMenuItem (GUI main)
	{
		this.main = main;
		
		init();
	}
	
	/**
	 * initializes the GUI components
	 */
	public void init()
	{
		this.setText("End");
		
		this.addActionListener(this);
	}
	
	/**
	 * Action when menu item is pressed
	 */
	public void actionPerformed(ActionEvent e) {
		
		// TODO Phase 6 -- provide functionality for terminating an existing HyPeerWeb
		
	}

}
