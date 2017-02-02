package gui.menuItems;

import gui.Main.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

/**
 * Represents the Observe menu item presented in the Help menu
 * 
 * @author Matthew Smith
 *
 */
public class ObserverMenuItem extends JMenuItem implements ActionListener {

	private static final long serialVersionUID = -4260790181319083434L;

	private GUI main;
	
	@SuppressWarnings("unused")
	private RemoteViewer remoteViewer;
	
	/**
	 * Creates an Observe menu Item
	 * @param main
	 */
	public ObserverMenuItem(GUI main)	{
		this.main = main;
		
		init();
	}
	
	
	/**
	 * initializes the GUI components
	 */
	public void init()
	{
		this.setText("Observe");
		
		this.addActionListener(this);
	}
	
	/**
	 * Action when menu item is pressed
	 */
	public void actionPerformed(ActionEvent e) {
		
		remoteViewer = new RemoteViewer(main, "");
		//TODO Phase 6 -- Provide functionality for observing a node in the HyPeerWeb.
		//Get the index of the most recently selected item in the nodeListing;
		//If the index is less than the size of the nodeListing, get the correspoinding node from the HyPeerWeb.
		//If there isn't one print an appropriate error message.
		//Otherwise get a representation of the node, and call the print operation on the content of the remoteViewer.
		//It is then setVisible ("remoteViewer.setVisible(true);")

	}
	
}
