package gui.Main;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

import gui.printer.DebugPrinter;

import gui.mapper.DebugMapper;
import gui.menus.DebugMenu;

import gui.commander.StandardCommands;

/**
 * Primary panel for Debugging of a HyPeerWeb 
 * 
 * @author Matthew Smith
 *
 */
public class HyPeerWebDebugger extends JPanel{
	
	public final int WIDTH = 500;	
	public final int HEIGHT = 400;
	public final int TRACE_PANEL_HEIGHT = 100;
	public final int SUB_BASE_HEIGHT = 20;
	
//	public final int WIDTH = 600;	
//	public final int HEIGHT = 1000/3;
//	public final int TRACE_PANEL_HEIGHT = 425;
//	public final int SUB_BASE_HEIGHT = 20;
	
	/* Root of GUI */
	private GUI main;
	
	/* Main Menu Bar */
	private JMenuBar debugMenu;
	
	/* HyPeerWeb Mapping Region */
	private DebugMapper debugMapper;
	
	/* HyPeerWeb Printing Region */
	private DebugPrinter tracePanel;
	
	/* HyPeerWeb Command Region */
	//private DebugCommander debugCommander;	
	
	/* HyPeerWeb Commands */
	private StandardCommands standardCommands;
	
	/* Main Status Bar */
	private DebugStatus debugStatus;
	
	/**
	 * Creates and intializes the HyPeerWebDebugger
	 * 
	 * @param main root of the GUI
	 */
	public HyPeerWebDebugger(GUI main)	{
		this.main = main;
		
		init();
	}
	
	/**
	 * Creates and builds the GUI components of the HyPeerWebDebugger
	 */
	public void init()	{
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		JPanel subTop = new JPanel(new BorderLayout());
		
		debugMenu = new DebugMenu(main);
		subTop.add(debugMenu, BorderLayout.NORTH);
		
		debugMapper = new DebugMapper(main);
		subTop.add(debugMapper, BorderLayout.CENTER);
		
		this.add(subTop, BorderLayout.NORTH);
		
		JPanel subBase = new JPanel(new BorderLayout());
		
		standardCommands = new StandardCommands(main);
		subBase.add(standardCommands,BorderLayout.NORTH);
		
		debugStatus = new DebugStatus(main);
		subBase.add(debugStatus,BorderLayout.CENTER);
		
		this.add(subBase, BorderLayout.CENTER);
		
		tracePanel = new DebugPrinter(main, "Trace Information", true, true);
		tracePanel.setPreferredSize(new Dimension(WIDTH, TRACE_PANEL_HEIGHT));
		this.add(tracePanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Retrieves the DebugMapper of the HyPeerWebDebugger 
	 * @return the DebugMapper of the HyPeerWebDebugger
	 */
	public DebugMapper getMapper() {
		return debugMapper;
	}
	
	/**
	 * Retrieves the DebugPrinter of the HyPeerWebDebugger 
	 * @return the DebugPrinter of the HyPeerWebDebugger
	 */
	public DebugPrinter getPrinter() {
		return tracePanel;
	}

	/**
	 * Retrieves the DebugCommander of the HyPeerWebDebugger 
	 * @return the DebugCommander of the HyPeerWebDebugger
	 */
	/*
	public DebugCommander getCommander() {
		return debugCommander;
	}
	*/
	
	/**
	 * Retrieves the StandardCommands of the HyPeerWebDebugger 
	 * @return the StandardCommands of the HyPeerWebDebugger
	 */
	public StandardCommands getStandardCommands() {
		return standardCommands;
	}
	
	/**
	 * Retrieves the DebugStatus of the HyPeerWebDebugger 
	 * @return the DebugStatus of the HyPeerWebDebugger
	 */
	public DebugStatus getStatus() {
		return debugStatus;
	}
	
	public DebugPrinter getTracePanel(){
		return tracePanel;
	}
}
