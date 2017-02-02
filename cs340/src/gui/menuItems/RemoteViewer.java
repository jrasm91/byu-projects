package gui.menuItems;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gui.printer.DebugPrinter;

import gui.Main.GUI;

/**
 * Frame that remotely views messages being printed by an exterior node 
 * @author Matthew Smith
 *
 */
public class RemoteViewer extends JFrame{
	
	/** Root of the GUI */
	private GUI main;
	
	/** Debug printer for printing messages from the remote node */
	private DebugPrinter content;
	
	/** constants for creating Remote viewers */
	public final int Width =400;
	public final int Hieght =150;
	
	//How many frames before repeating pattern
	public final int repeat = 16;
	
	// Offset new frames from old frames
	public final int offsetX = 30;	
	public final int offsetY = 30;
	
	//Where frames should start from
	public final int initialOffsetX = 400;
	public final int initialOffsetY = 30;
	
	//How many frames have been created
	private static int count = 0;
	
	/**
	 * Creates a Remote viewer
	 * @param main
	 */
	public RemoteViewer(GUI main, String title) {
		this.main = main;
		
		content = new DebugPrinter(main, title, false, false);
		
		init();
	}
	
	/** Initializes GUI components */
	public void init()	{
		this.setTitle("Node Information");
		
		this.getContentPane().add(content);
		
		int X = (count % repeat) * offsetX + initialOffsetX;
		int Y = (count % repeat) * offsetY + initialOffsetY;
		
		count++;
		
		this.setLocation(X, Y);
		
		this.setSize(new Dimension(Width,Hieght));
	}

	public DebugPrinter getContent(){
		return content;
	}
		
}
