package gui.newWindows;

import gui.Main.GUI;

public class BroadcastWindow extends PopUpWindow {

	public BroadcastWindow(GUI main, String title){
		super(main, title);
	}
	
	@Override
	protected void addPanel() {
		panel = new BroadcastWindowPanel(main);
		this.add(panel);
	}
}
