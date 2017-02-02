package gui.commander;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import gui.printer.MaxVerbosePanel;

import gui.Main.GUI;

/**
 * Debug Commander is a set of categorical command sets for testing the HyPeerWeb seperated by tabs
 * 
 * @author Matthew Smith
 * @domain Tabs - Categories of commands
 *
 */

public class DebugCommander extends JPanel {
	
	/* Root of the GUI */
	private GUI main;
	
	/* Tabbing */
	//private JTabbedPane content;
	
	/* Default starting tab */
	private JPanel mainTab;
	
	/**
	 * Creates and intializes the Debug Commander
	 * 
	 * @param main - Root of the GUI
	 */
	public DebugCommander(GUI main) {
		this.main = main;
		
		init();
	}
	
	/**
	 * Intializes and Builds the GUI compenents of the Debug Commander
	 */
	public void init()
	{
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("Commands"));
		
		//content = new JTabbedPane();
		
		mainTab = new StandardCommands(main);
		
		//content.addTab("Standard",mainTab);
		
		//this.add(content);
		this.add(mainTab);
	}
	
	public StandardCommands getStandardCommands(){
		return (StandardCommands)mainTab;
	}
	
}
