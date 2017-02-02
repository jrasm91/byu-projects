package gui.Main;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.Main.GUI;
import gui.printer.DebugPrinter;
/**
 * Base panel that provides status information as to the progress or actions that the Program is doing
 * 
 * @author Matthew Smith
 *
 */
public class DebugStatus extends JPanel {

	/** Root to the GUI */
	private GUI main;
	
	private DebugPrinter content;
	
	/** Creates a status bar for debugging */
	public DebugStatus(GUI main) {
		this.main = main;
		
		init();
	}
	
	/** Initializes GUI components */
	public void init()	{
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		
		JPanel status = new JPanel();
		status.setBorder(BorderFactory.createLoweredBevelBorder());
		content = new DebugPrinter(main, "Errors", false, true);
		//status.add(new JLabel("Nothing Running"));
		//status.add(content);
		
		add(content);
	}
	
	public void setContent(String newContent){
		content.set(newContent);		
	}
}
