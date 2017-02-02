package cs355.controller;

import java.awt.Point;
import java.util.List;

import cs355.model.Line;
import cs355.model.Shape;
import cs355.model.ShapeType;
import cs355.model.Triangle;
import cs355.utility.Utility;
import cs355.view.HandleDrawer;
import cs355.view.MatrixMaker;

public class HandleHitTester {

	public static boolean isHittingRotationHandle(Point worldP, Shape s) {
		if(s == null || s.getType() == ShapeType.LINE) return false;
		Point h = Utility.getRotationHandle(s);
		Point p = MatrixMaker.getObjectFromWorld(worldP, s);
		return hitHandle(p, h);
	}
	
	public static boolean isHittingLineHandles(Point p, Shape s) {
		if(s == null || s.getType() != ShapeType.LINE) return false;
		return hitHandle(p, ((Line)s).getP1()) || hitHandle(p, ((Line)s).getP2());
	}
	
	public  static Point getOppositeLineHandle(Point p, Shape s) {
		Line l = (Line)s;
		Point result = null;
		if(hitHandle(p, l.getP1())) result = l.getP2();
		else if(hitHandle(p, l.getP2())) result = l.getP1();
		return result;
	}

	public static boolean isHittingTriangleHandles(Point worldP, Shape s) {
		if (s == null || s.getType() != ShapeType.TRIANGLE) return false;
		Point p = MatrixMaker.getObjectFromWorld(worldP, s);
		boolean result = false;
		for(Point h: Utility.getTriangleHandles((Triangle)s))
				result |= hitHandle(p, h);
		return result;
	}
	
	public static List<Point> getOppositeTriangleHandles(Point worldP, Shape s) {
		List<Point> points = Utility.getTriangleHandles((Triangle)s);
		Point p = MatrixMaker.getObjectFromWorld(worldP, s);
		for(Point h: points)
			if(hitHandle(p, h)){
				points.remove(h);
				break;
			}
		return MatrixMaker.getWorldFromObject(points, s);
	}

	public static boolean isHittingBoxHandles(Point worldP, Shape s) {
		if (s == null) return false;
		if(s.getType() == ShapeType.TRIANGLE) return false;
		if(s.getType() == ShapeType.LINE) return false;
		Point p = MatrixMaker.getObjectFromWorld(worldP, s);
		boolean result= hitHandle(p, Utility.getTopLeftHandle(s)) ||
				hitHandle(p, Utility.getTopRightHandle(s)) ||
				hitHandle(p, Utility.getBottomLeftHandle(s)) ||
				hitHandle(p, Utility.getBottomRightHandle(s));
		return result;
	}
	
	public  static Point getOppositeBoxHandle(Point worldP, Shape s) {
		Point result = null;
		Point p = MatrixMaker.getObjectFromWorld(worldP, s);
		if(hitHandle(p, Utility.getTopLeftHandle(s))) result = Utility.getBottomRightHandle(s);
		if(hitHandle(p, Utility.getTopRightHandle(s))) result = Utility.getBottomLeftHandle(s);
		if(hitHandle(p, Utility.getBottomLeftHandle(s))) result = Utility.getTopRightHandle(s);
		if(hitHandle(p, Utility.getBottomRightHandle(s))) result = Utility.getTopLeftHandle(s);
		return MatrixMaker.getWorldFromObject(result, s);
	}

	private static boolean hitHandle(Point p, Point h){
		if(p == null || h == null) return false;
		int r = (int)(HandleDrawer.H_SIZE/Controller.singleton().getZoom());
		Point normal = new Point(p.x - h.x, p.y - h.y);
		return ((double)(normal.x * normal.x)/ (r * r)) + 
				((double)(normal.y * normal.y)/ (r * r)) <= 1.0;
	}

}
