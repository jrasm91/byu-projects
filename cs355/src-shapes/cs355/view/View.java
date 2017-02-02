package cs355.view;

import java.awt.Graphics2D;

import cs355.controller.Controller;
import cs355.controller.Controller3D;
import cs355.controller.ControllerImg;
import cs355.gui.ViewRefresher;
import cs355.model.Model;

public class View implements ViewRefresher{

	private static View singleton = new View();

	public static View singleton(){	return singleton; }

	@Override
	public void refreshView(Graphics2D g2d) {
		if(ControllerImg.singleton().showImage())
			ImageDrawer.draw(g2d, Model.singleton().getImage());
		ShapeDrawer.draw(g2d, Model.singleton().getShapes());
		HandleDrawer.draw(g2d, Controller.singleton().getSelectedShape());
		if(Controller3D.singleton().isDrawHouse())
			HouseDrawer.draw(g2d, Controller3D.singleton().getHouseLines());
		
	}
}
