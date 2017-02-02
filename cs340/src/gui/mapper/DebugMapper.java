package gui.mapper;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import gui.printer.MaxVerbosePanel;

import gui.Main.GUI;

public class DebugMapper extends JPanel {
	
	//private JTabbedPane content;
	
	private NodeListing mainTab;
	
	public DebugMapper(GUI main) {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("List Of Nodes In HyPeerWeb"));
		
		//content = new JTabbedPane();
		
		mainTab = new NodeListing(main);
		
		//content.addTab("Node Listing",mainTab);
		
		//this.add(content);
		this.add(mainTab);
	}
	
	public NodeListing getNodeListing(){
		return mainTab;
	}

}
