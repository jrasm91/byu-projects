package gui.commander;

import gui.Main.GUI;
import gui.Main.GUIProxy;
import gui.mapper.NodeListing;
import gui.newWindows.BroadcastWindow;
import gui.newWindows.SendWindow;
import hypeerweb.HyPeerWeb;
import hypeerweb.HyPeerWebProxy;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import distributed.GlobalObjectId;
import distributed.LocalObjectId;
import distributed.PortNumber;

/**
 * Standard Commands are a basic set of commands that are needed to test a
 * HyPeerWeb. A open command line is available which may allow for more specific
 * commands as is appropriate.
 * 
 * @author Matthew Smith
 * 
 * @domain Buttons - Actions taken on the HyPeerWeb
 * @domain Command Bar - Use of embedded functions to affect the HyPeerWeb
 * 
 */
public class StandardCommands extends JPanel {
	/* Root of the GUI */
	private GUI main;



	/* Container for the Command Field and Execute Button */
	private JPanel fieldPanel;

	/* Command Field for inputed Commands */
	// private JTextField commandField;

	/* Button to execute a command in the command Field */
	// private JButton executeButton;

	/* Conatainer for all basic command buttons */
	private JPanel buttonPanel;

	/* Basic buttons commands */
	private JButton insertNode, removeNode, sendNode, broadcastNode;

	/* List nodes to display as targets */
	private Object[] nodeList;

	SendWindow sendWindow = null;
	BroadcastWindow broadcastWindow = null;

	/**
	 * Creates and intailizes the panel of basic commands as well as gathers a
	 * list of nodes
	 * 
	 * @param main
	 *            - root of the GUI
	 */
	public StandardCommands(GUI main) {
		this.main = main;

		init();

		updateList();
	}

	public SendWindow getSendWindow() {
		return sendWindow;
	}

	public BroadcastWindow getBroadcastWindow() {
		return broadcastWindow;
	}

	public void setSendWindowToNull() {
		sendWindow = null;
	}

	public void setBroadcastWindowToNull() {
		broadcastWindow = null;
	}

	/**
	 * Initializes and sets up the GUI objects of the Class
	 */
	public void init() {
		this.setLayout(new BorderLayout());

		// Build the open command Area
		fieldPanel = new JPanel(new BorderLayout());

		// Build the Basic Button Area
		buttonPanel = new JPanel(new GridLayout(1, 4));

		// Build the insert button
		insertNode = new JButton("(+) Insert Node");
		insertNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertButtonPressed();
			}
		});
		insertNode.setMnemonic(KeyEvent.VK_ADD);
		buttonPanel.add(insertNode);

		// Build the remove button
		removeNode = new JButton("(-) Delete Node");
		removeNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeButtonPressed();
			}
		});
		removeNode.setMnemonic(KeyEvent.VK_SUBTRACT);
		buttonPanel.add(removeNode);

		// Build the send button
		sendNode = new JButton("Send Message");
		sendNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendButtonPressed();
			}
		});
		sendNode.setMnemonic(KeyEvent.VK_S);
		buttonPanel.add(sendNode);

		// Build the broadcast button
		broadcastNode = new JButton("Broadcast");
		broadcastNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				broadcastButtonPressed();
			}
		});
		broadcastNode.setMnemonic(KeyEvent.VK_B);
		buttonPanel.add(broadcastNode);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * Gathers information of the nodes in the HyPeerWeb and stores them in a
	 * list of Nodes
	 */
	public void updateList() {
		nodeList = new Object[30];
		for (int i = 0; i < 30; i++) {
			nodeList[i] = Integer.toBinaryString(i);
		}
	}

	/**
	 * Inserts a Node into the HyPeerWeb
	 */
	public void insertButtonPressed() {

		NodeListing listing = main.getHyPeerWebDebugger().getMapper()
				.getNodeListing();
		if (listing.listSize() >= NodeListing.MAX_NUMBER_OF_NODES) {
			main.getHyPeerWebDebugger()
			.getStatus()
			.setContent(
					"You already have "
							+ NodeListing.MAX_NUMBER_OF_NODES
							+ " in the HyPeerWeb");
			return;
		}
		int selectedIndex = listing.getSelectedIndex();

		GlobalObjectId ghwebid = main.getOtherHyPeerWebAddress();
		HyPeerWeb proxy = new HyPeerWebProxy(ghwebid);
		GUIProxy otherGUI = new GUIProxy(new GlobalObjectId(
				ghwebid.getMachineAddr(), new PortNumber(ghwebid.getPortNumber().getValue() + 1),
				new LocalObjectId(ghwebid.getLocalObjectId().getId() - 1)));


		//makes milestone 3 part 1 work
		if(GUI.milestone3) {
			GlobalObjectId testID = new GlobalObjectId();
			if(testID.getPortNumber().getValue() == 49201){
				main.getHyPeerWeb().addNewNodeToHyPeerWeb(true);
				proxy.addNewNodeToHyPeerWeb(false);
			} 	else {
				main.getHyPeerWeb().addNewNodeToHyPeerWeb(false);
				proxy.addNewNodeToHyPeerWeb(true);
			}
		}

		//		makes milestone 2 work
		if(GUI.milestone2){
			main.getHyPeerWeb().addNewNodeToHyPeerWeb(true);
			proxy.addNewNodeToHyPeerWeb(false);
		}

		if(GUI.disconnected){
			main.getHyPeerWeb().addNewNodeToHyPeerWeb(true);
			main.increaseSize();
		} if(!GUI.disconnected){
			main.getHyPeerWeb().addNewNodeToHyPeerWeb(true);
			proxy.addNewNodeToHyPeerWeb(false);
			otherGUI.increaseSize();
			main.increaseSize();
		}
	}

	/**
	 * Removes a node from the HyPeerWeb
	 */
	public void removeButtonPressed() {
		NodeListing listing = main.getHyPeerWebDebugger().getMapper()
				.getNodeListing();
		int selectedIndex = listing.getSelectedIndex();
		if (selectedIndex == -1 || selectedIndex >= listing.listSize()) {
			main.getHyPeerWebDebugger()
			.getStatus()
			.setContent(
					"You must selected a node to remove from HyPeerWeb");
		} else if (listing.listSize() <= 1) {
			main.getHyPeerWebDebugger()
			.getStatus()
			.setContent(
					"You must have at least 2 nodes in HyPeerWeb to delete");
		} else {

			//makes milestone 2 work
			GlobalObjectId ghwebid = main.getOtherHyPeerWebAddress();
			HyPeerWeb proxy = new HyPeerWebProxy(ghwebid);

			if(GUI.milestone2){
				main.getHyPeerWeb().removeFromHyPeerWeb(true);
				proxy.removeFromHyPeerWeb(false);
			}

			if(GUI.milestone3){
				GlobalObjectId testID = new GlobalObjectId();
				if(testID.getPortNumber().getValue() == 49201){
					main.getHyPeerWeb().removeFromHyPeerWeb(true);
					proxy.removeFromHyPeerWeb(false);
				} 	else {
					main.getHyPeerWeb().removeFromHyPeerWeb(false);
					proxy.removeFromHyPeerWeb(true);
				}
			}
			main.decreaseSize();

			GUIProxy otherGUI = new GUIProxy(new GlobalObjectId(
					ghwebid.getMachineAddr(), new PortNumber(ghwebid.getPortNumber().getValue() + 1),
					new LocalObjectId(ghwebid.getLocalObjectId().getId() - 1)));
			otherGUI.decreaseSize();
		}
	}


	/**
	 * Sends a message through the HyPeerWeb
	 */
	public void sendButtonPressed() {
		sendWindow = new SendWindow(main, "Send Message");
	}

	/**
	 * Broadcasts a message through the HyPeerWeb
	 */
	public void broadcastButtonPressed() {
		broadcastWindow = new BroadcastWindow(main, "Broadcast Message");
	}

}
