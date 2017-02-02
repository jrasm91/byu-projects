package cs355.utility;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs355.model.Circle;
import cs355.model.Ellipse;
import cs355.model.Line;
import cs355.model.Rectangle;
import cs355.model.Shape;
import cs355.model.ShapeType;
import cs355.model.Square;
import cs355.model.Triangle;

public class Utility {

	public static Point getTopLeftPoint(Shape s) {
		int width = getWidth(s);
		int height = getHeight(s);
		return new Point(-width/2, -height/2);
	}

	public  static Point getTopLeftHandle(Shape s){
		return getTopLeftPoint(s);
	}

	public  static Point getTopRightHandle(Shape s){
		Point p = getTopLeftPoint(s);
		int w = getWidth(s);
		return new Point(p.x + w, p.y);
	}

	public static Point getBottomLeftHandle(Shape s){
		Point p = getTopLeftPoint(s);
		int h = getHeight(s);
		return new Point(p.x, p.y + h);
	}

	public  static Point getBottomRightHandle(Shape s){
		Point p = getTopLeftPoint(s);
		int w = getWidth(s);
		int h = getHeight(s);
		return new Point(p.x + w, p.y + h);
	}

	public static int getWidth(Shape s){
		switch(s.getType()){
		case CIRCLE:   return ((Circle)s).getDiameter();
		case ELLIPSE:  return ((Ellipse)s).getWidth();
		case RECTANGLE:return ((Rectangle)s).getWidth();
		case SQUARE:   return ((Square)s).getSize();
		default:
			throw new IllegalStateException("Not a Valid Shape: " + s.getType());
		}
	}

	public static int getHeight(Shape s){
		switch(s.getType()){
		case CIRCLE:   return ((Circle)s).getDiameter();
		case ELLIPSE:  return ((Ellipse)s).getHeight();
		case RECTANGLE:return ((Rectangle)s).getHeight();
		case SQUARE:   return ((Square)s).getSize();
		default:
			throw new IllegalStateException("Not a Valid Shape: " + s.getType());
		}
	}

	public  static Point getRotationHandle(Shape s){
		if(s.getType() == ShapeType.TRIANGLE) {
			Triangle t = (Triangle)s;
			return new Point(0, Math.min(Math.min(t.getP1().y, t.getP2().y), t.getP3().y) - 25);
		} else {
			Point p = getTopLeftPoint(s);
			int w = getWidth(s);
			int h = getHeight(s);
			return new Point(p.x + w/2, p.y - h/5);
		}
	}

	public static List<Point> getTriangleHandles(Triangle t){
		return new ArrayList<Point>(Arrays.asList(new Point[]{t.getP1(), t.getP2(), t.getP3()}));
	}

	public static List<Point> getLineHandles(Line l){
		return new ArrayList<Point>(Arrays.asList(new Point[]{l.getP1(), l.getP2()}));
	}

	public static Point getPoint1(Line l) {
		return l.getP1();
	}
	
	public static Point getPoint2(Line l) {
		return l.getP2();
	}

	public static int[] getTriangleXs(Triangle t) {
		int[] xPoints = new int[3];
		xPoints[0] = t.getP1().x;
		xPoints[1] = t.getP2().x;
		xPoints[2] = t.getP3().x;
		return xPoints;
	}
	
	public static int[] getTriangleYs(Triangle t) {
		int[] yPoints = new int[3];
		yPoints[0] = t.getP1().y;
		yPoints[1] = t.getP2().y;
		yPoints[2] = t.getP3().y;
		return yPoints;
	}

	public static double calcRotationAngle(Shape selectedShape, Point p) {
		Point c = selectedShape.getCenterPoint();
		Point diff = new Point(p.x - c.x, p.y - c.y);
		return Math.atan2(diff.y, diff.x) + Math.PI/2;
	}
}

