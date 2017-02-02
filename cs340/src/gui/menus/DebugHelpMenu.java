package gui.menus;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import gui.menuItems.*;

import gui.Main.GUI;

/**
 * Represents the Debug help menu in the GUI
 * 
 * @author msmith52
 *
 */
public class DebugHelpMenu extends JMenu {
	
	/** Root of the GUI */
	public GUI main;
	
	/** About menu item */
	private JMenuItem about;
	
	/** Contents menu item */
	private JMenuItem contents;
	
	/**
	 * Create a debug help menu
	 * @param main
	 */
	public DebugHelpMenu(GUI main)
	{
		this.main = main;
		
		init();
	}
	
	/**
	 * initializes GUI components
	 */
	public void init()
	{
		this.setText("Help");
		
		contents = new HelpContentsMenuItem(main);
		this.add(contents);
		
		about = new AboutMenuItem(main);
		this.add(about);
	}
}
