package cs355.main;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import cs355.controller.Controller;
import cs355.gui.GUIFunctions;
import cs355.view.View;

/**
 *
 * @author <Put your name here>
 */
public class CS355 
{

	public static final int SCREEN_SIZE = 2048;
	public static final int SCROLL_DEFAULT = 512;
	public static final int DEFAULT_POSIT = 1024 - SCROLL_DEFAULT/2;
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) 
	{
		Controller control = Controller.singleton();
		View view = View.singleton();
		
		GUIFunctions.createCS355Frame(control, view, mouseListener, mouseMotionListener);
		GUIFunctions.changeSelectedColor(Color.BLUE);

		GUIFunctions.setHScrollBarMax(SCREEN_SIZE);
		GUIFunctions.setVScrollBarMax(SCREEN_SIZE);

		GUIFunctions.setVScrollBarPosit(DEFAULT_POSIT);
		GUIFunctions.setHScrollBarPosit(DEFAULT_POSIT);

		GUIFunctions.setHScrollBarKnob(SCROLL_DEFAULT);
		GUIFunctions.setVScrollBarKnob(SCROLL_DEFAULT);
		
		GUIFunctions.refresh();
		
	}
	
	private static MouseMotionListener mouseMotionListener = new MouseMotionListener(){

		@Override
		public void mouseDragged(MouseEvent arg0) {
			Controller.singleton().mouseDragged(new Point(arg0.getX(), arg0.getY()));
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
//			Controller.singleton().mouseMoved(new Point(arg0.getX(), arg0.getY()));
		}
	};

	private static MouseAdapter  mouseListener = new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent arg0) {
			Controller.singleton().mousePressed(new Point(arg0.getX(), arg0.getY()));
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
			Controller.singleton().mouseReleased(new Point(arg0.getX(), arg0.getY()));
		}
		@Override
		public void mouseClicked(MouseEvent arg0) {
			Controller.singleton().mouseClicked(new Point(arg0.getX(), arg0.getY()));
		}
	};
}