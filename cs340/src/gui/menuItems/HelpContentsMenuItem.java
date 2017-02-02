package gui.menuItems;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import gui.Main.GUI;

/**
 * Represents the Help menu item presented in the Help menu
 * 
 * @author Matthew Smith
 *
 */
public class HelpContentsMenuItem extends JMenuItem implements ActionListener{

	GUI main;
	
	/**
	 * Creates a Help menu Item
	 * @param main
	 */
	public HelpContentsMenuItem (GUI main)
	{
		this.main = main;
		
		init();
	}
	
	/**
	 * initializes the GUI components
	 */
	public void init()
	{
		this.setText("Contents");
		
		this.addActionListener(this);
	}
	
	/**
	 * Action when menu item is pressed
	 */
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(main, "You do NOT need to implement this");
	}

}
