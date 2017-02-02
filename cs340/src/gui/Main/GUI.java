package gui.Main;

import hypeerweb.HyPeerWeb;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import distributed.GlobalObjectId;
import distributed.LocalObjectId;
import distributed.ObjectDB;
/***
 * The central GUI used to display information about the HyPeerWeb and debug information
 * 
 * @author Matthew Smith
 *
 */
public class GUI extends JFrame
{
	private static GUI singleton = null;
	private GlobalObjectId otherHyPeerWebAddress;
	private LocalObjectId localObjectId;

	/** Main Debugger Panel**/
	private HyPeerWebDebugger debugger;

	private HyPeerWeb hypeerweb;
	private JScrollPane scrollPane;

	/**
	 * Creates and initializes the GUI as being the root
	 */
	private GUI(HyPeerWeb hypeerweb){
		this.hypeerweb = hypeerweb;
		this.setTitle("HyPeerWeb DEBUGGER V 1.1");
		localObjectId = new LocalObjectId(-2);
		ObjectDB.getSingleton().store(new LocalObjectId(-2), this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				shutdown();
				System.exit(0);
			}
		});

		debugger = new HyPeerWebDebugger(this);
		scrollPane = new JScrollPane(debugger);
		scrollPane.setPreferredSize(new Dimension(debugger.WIDTH+20, debugger.HEIGHT));

		this.getContentPane().add(scrollPane);
		this.pack();
	}

	private void shutdown(){
		hypeerweb.close();
	}

	protected GUI(int i){
		// do nothing
	}

	public static GUI getSingleton(HyPeerWeb hypeerweb){
		if(singleton == null){
			try{
				singleton = new GUI(hypeerweb);
				singleton.setVisible(true);
			}
			catch(Exception e)	{
				JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				hypeerweb.close();
				System.exit(1);
			}
		}
		return singleton;
	}

	public static boolean milestone2 = false;
	public static boolean milestone3 = false;
	public static boolean disconnected = false;
	
	/**
	 * Start Point of the Program
	 */
	public static void main (String[] args){
		HyPeerWeb hw = HyPeerWeb.getSingleton();
		getSingleton(hw);
	}

	/**
	 * Retrieves the HyPeerWeb Debugging Panel
	 * @return HyPeerWebDebugger
	 */
	public GlobalObjectId getOtherHyPeerWebAddress() {
		assert otherHyPeerWebAddress != null;
		return otherHyPeerWebAddress;
	}

	public void setOtherHyPeerWebAddress(GlobalObjectId otherHyPeerWebAddress) {
		this.otherHyPeerWebAddress = otherHyPeerWebAddress;
	}

	public HyPeerWebDebugger getHyPeerWebDebugger(){
		return this.debugger;
	}

	public HyPeerWeb getHyPeerWeb(){
		return hypeerweb;
	}

	public void printToTracePanel(Object msg){
		debugger.getTracePanel().print(msg);
	}

	public void finalize(){
		hypeerweb.close();
	}

	public void increaseSize(){
		getHyPeerWebDebugger().getMapper().getNodeListing().increaseListSize();
	}
	public void decreaseSize(){
		getHyPeerWebDebugger().getMapper().getNodeListing().decreaseListSize();
	}

}
