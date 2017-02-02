package gui.newWindows;

import java.awt.BorderLayout;
import java.awt.Dimension;

import gui.Main.GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class PopUpWindow
	extends JFrame
{
	protected GUI main;
	protected JPanel panel;
	
	public PopUpWindow(GUI main, String title){
		super(title);
		this.main = main;
		init();
	}
	
	public void init(){
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(325, 115));
		
		addPanel();
		
		this.pack();
		this.setVisible(true);
	}
	
	public JPanel getPanel(){
		return panel;
	}
	
	protected abstract void addPanel();

}
