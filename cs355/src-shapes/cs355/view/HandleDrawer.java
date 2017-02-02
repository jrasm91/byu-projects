package cs355.view;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import cs355.controller.Controller;
import cs355.model.Line;
import cs355.model.Shape;
import cs355.model.Triangle;
import cs355.utility.Utility;

public class HandleDrawer {

	public static final int H_SIZE = 12;
	private static HandleDrawer singleton = new HandleDrawer();

	private HandleDrawer() {  }

	public static void draw(Graphics2D g, Shape s){
		singleton.drawHandles(g, s);
	}

	private void drawHandles(Graphics2D g, Shape s){
		if(s == null) return;
		AffineTransform saveAt = g.getTransform();
		AffineTransform matrix = MatrixMaker.getViewportTransform(s.getRotation(), s.getCenterPoint());
		g.setTransform(matrix);
		drawPoints(g, makeHandlePoints(s), s);
		g.setTransform(saveAt);
	}

	public List<Point> makeHandlePoints(Shape s){
		switch(s.getType()){
		case LINE: 		return makePoints((Line)s); 	
		case TRIANGLE: 	return makePoints((Triangle)s);		
		case RECTANGLE: return makePoints(s);
		case SQUARE:	return makePoints(s);
		case ELLIPSE:	return makePoints(s);
		case CIRCLE:	return makePoints(s);
		}
		return null;
	}

	private List<Point> makePoints(Shape s){
		List<Point> points = new ArrayList<Point>();
		points.add(Utility.getTopLeftHandle(s));
		points.add(Utility.getRotationHandle(s));
		points.add(Utility.getTopRightHandle(s));
		points.add(Utility.getBottomLeftHandle(s));
		points.add(Utility.getBottomRightHandle(s));
		return points;
	}

	private List<Point> makePoints(Line l){
		return Utility.getLineHandles(l);
	}

	private List<Point> makePoints(Triangle t){
		List<Point> points = Utility.getTriangleHandles(t);
		Point handle = Utility.getRotationHandle(t);
		points.add(handle);
		return points;
	}

	private void drawPoints(Graphics2D g, List<Point> points, Shape s){
		int actualSize = (int)(H_SIZE/Controller.singleton().getZoom());
		for(Point p: points){
			g.setColor(s.getColor());
			g.drawOval(p.x - actualSize/2, p.y - actualSize/2 , actualSize, actualSize);
			g.setColor(s.getInverseColor());
			g.fillOval(p.x - actualSize/2, p.y - actualSize/2 , actualSize, actualSize);
		}
	}
}
