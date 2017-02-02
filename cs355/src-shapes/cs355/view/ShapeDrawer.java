package cs355.view;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.List;

import cs355.controller.Controller;
import cs355.model.Circle;
import cs355.model.Ellipse;
import cs355.model.Line;
import cs355.model.Rectangle;
import cs355.model.Shape;
import cs355.model.ShapeType;
import cs355.model.Square;
import cs355.model.Triangle;
import cs355.utility.Utility;

public class ShapeDrawer {

	private ShapeDrawer() {	}
	
	private static final int STROKE_SIZE = 3;

	public static void draw(Graphics2D g, List<Shape> shapes){
		new ShapeDrawer().drawShapes(g, shapes);
	}

	private void drawShapes(Graphics2D g, List<Shape> shapes){
		for(Shape s: shapes)
			drawShape(g, s, true);
		Shape selectedShape = Controller.singleton().getSelectedShape();
		if(selectedShape != null && selectedShape.getType() != ShapeType.LINE)
			drawShape(g, selectedShape, false);
	}

	private void drawShape(Graphics2D g, Shape s, boolean fill){
		AffineTransform saveAt = g.getTransform();
		
		AffineTransform matrix = MatrixMaker.getViewportTransform(s.getRotation(), s.getCenterPoint());
		g.setTransform(matrix);
		
		if(fill){
			g.setColor(s.getColor());
			g.setStroke(new BasicStroke((float)(STROKE_SIZE/Controller.singleton().getZoom())));
		}
		else
			g.setColor(s.getInverseColor());
		switch(s.getType()){
		case LINE: 		drawLine(g, (Line)s); 					break;
		case RECTANGLE: drawRectangle(g, (Rectangle)s, fill);	break;
		case SQUARE:	drawSquare(g, (Square)s, fill);			break;
		case ELLIPSE:	drawEllipse(g, (Ellipse)s, fill);		break;
		case CIRCLE:	drawCircle(g, (Circle)s, fill);			break;
		case TRIANGLE: 	drawTriangle(g, (Triangle)s, fill);		break;
		}
		g.setTransform(saveAt);
	}

	private void drawLine(Graphics2D g, Line l){
		Point p1 = Utility.getPoint1(l);
		Point p2 = Utility.getPoint2(l);
		g.setStroke(new BasicStroke(2));
		g.drawLine(p1.x, p1.y, p2.x, p2.y);		
	}

	private void drawSquare(Graphics2D g, Square s, boolean fill){
		Point p = Utility.getTopLeftPoint(s);
		int l = Utility.getWidth(s);
		if(fill) g.fillRect(p.x, p.y, l, l);
		else g.drawRect(p.x, p.y, l, l);
	}

	private void drawRectangle(Graphics2D g, Rectangle r, boolean fill){
		Point p = Utility.getTopLeftPoint(r);
		int w = Utility.getWidth(r);
		int h = Utility.getHeight(r);
		if(fill) g.fillRect(p.x, p.y, w, h);
		else g.drawRect(p.x, p.y, w, h);
	}

	private void drawCircle(Graphics2D g, Circle c, boolean fill){
		Point p = Utility.getTopLeftPoint(c);
		int d = Utility.getWidth(c);
		if(fill) g.fillOval(p.x, p.y, d, d );
		else g.drawOval(p.x, p.y, d, d );
	}

	private void drawEllipse(Graphics2D g, Ellipse e, boolean fill){
		Point p = Utility.getTopLeftPoint(e);
		int w = Utility.getWidth(e);
		int h = Utility.getHeight(e);
		if(fill) g.fillOval(p.x, p.y, w, h);
		else g.drawOval(p.x, p.y, w, h);
	}

	private void drawTriangle(Graphics2D g, Triangle t, boolean fill){
		int[] xPoints = Utility.getTriangleXs(t);
		int[] yPoints = Utility.getTriangleYs(t);
		if(fill) g.fillPolygon(xPoints, yPoints, Triangle.SIDES);
		else g.drawPolygon(xPoints, yPoints, Triangle.SIDES);
	}

}
