package gui.newWindows;

import gui.Main.GUI;

public class SendWindow extends PopUpWindow {

	public SendWindow(GUI main, String title){
		super(main, title);
	}
	
	@Override
	protected void addPanel() {
		panel = new SendWindowPanel(main);
		this.add(panel);
	}
}
