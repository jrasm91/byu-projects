package gui.printer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import gui.Main.GUI;

/**
 * Main debug printing panel that behaves like a text pane and can even display in html the messages printed by Nodes in the hypeerweb
 * 
 * @author Matthew Smith
 *
 */
public class MaxVerbosePanel extends JPanel implements DPrinter{
	
	/** Determines whether or not, when information is printed to the Panel, the top or bottom of the panel is displayed.*/
	private boolean scrollToBottom = false;

	/** The main pane of printer for printing */
	private JTextPane textPane;
	
	/** Toggles auto scrolling of the text pane */
	private JCheckBox togglePause;
	
	/** Field for adding comments or notes to the text pane */
	private JTextField commenter;
	
	/** Document that acts as the content of the text pane*/
	private StyledDocument doc;
	
	/** container for the text Pane for scrolling */
	private JScrollPane textAreaContainer;
	
	/** container for the check box and commenter */
	private JPanel textAreaController;
	
	/** Flag indicating whether this has a clear button.*/
	private boolean hasClearButton = false;
	
	/** The clear button.*/
	private JButton clearButton = null;
	
	/** Root of the GUI */
	private GUI main;
	
	/**
	 * Creates a Max Verbose panel 
	 * @param main
	 */
	public MaxVerbosePanel(GUI main, boolean scrollToBottom, boolean hasClearButton) {
		this.main = main;
		this.scrollToBottom = scrollToBottom;
		this.hasClearButton = hasClearButton;
		
		init();
		
		//println("HyPeerWeb debug session ["+getDateTime()+"]");
	}
	
	/**
	 * Initializes GUI componenets
	 */
	public void init(){
		this.setLayout(new BorderLayout());
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		doc = textPane.getStyledDocument();
		
		textAreaContainer = new JScrollPane(textPane);
		textAreaContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		this.add(textAreaContainer,BorderLayout.CENTER);
		
		if(hasClearButton){
			//Build the clear button
			clearButton = new JButton("Clear");
			clearButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					clearButtonPressed();				
				} });
			clearButton.setMnemonic(KeyEvent.VK_L);
			
			this.add(clearButton, BorderLayout.SOUTH);
		}
	}
	
	public void print(Object msg)	{
		try {
			doc.insertString(doc.getLength(), msg.toString(), null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		scrollToBottom();
	}
	
	public void println()	{
		print("\n");
	}
	
	public void println(Object msg)	{
		print(msg);
		print("\n");
	}
	
	public void set(Object msg){
		textPane.setText(textPane.getText() + msg.toString() + "\n");
	}
	
	/** 
	 * Returns a time stamp in the form of a string
	 * @return
	 */
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
	
	/** if auto scrolling is enabled, move window to the base */
	private void scrollToBottom()
	{
		//if(togglePause.isSelected()){
		if(scrollToBottom){
			JViewport window = textAreaContainer.getViewport();
			window.setViewPosition(new Point(0,textPane.getHeight()));
		}
	}
	
	private void clearButtonPressed(){
		textPane.setText("");
	}
	
	
}
