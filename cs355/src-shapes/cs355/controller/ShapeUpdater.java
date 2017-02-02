package cs355.controller;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import cs355.model.Circle;
import cs355.model.Ellipse;
import cs355.model.Line;
import cs355.model.Model;
import cs355.model.Rectangle;
import cs355.model.Shape;
import cs355.model.ShapeType;
import cs355.model.Square;
import cs355.model.Triangle;
import cs355.view.MatrixMaker;

public class ShapeUpdater {

	/**
	 * @param s The type of shape that is now being drawn
	 * @param p1 The first initial point (downClick)
	 * @param c The color of the shape
	 */
	public static void addShape(StateType s, Point p1, Color c){
		switch(s){
		case DRAW_CIRCLE: 	Model.singleton().addShape(new Circle(p1, 0, c));			break;
		case DRAW_ELLIPSE:	Model.singleton().addShape(new Ellipse(p1, 0, 0, c));		break;
		case DRAW_LINE:		Model.singleton().addShape(new Line(p1, p1, c));			break;
		case DRAW_RECTANGLE:Model.singleton().addShape(new Rectangle(p1, 0, 0, c));		break;
		case DRAW_SQUARE:	Model.singleton().addShape(new Square(p1, 0, c));			break;
		case DRAW_TRIANGLE:
		case NONE:
		case SELECT_SHAPE: return;
		}
	}

	/**
	 * Same as above but for a triangle (special case)
	 */
	public static void addShape(List<Point> pts, Color c){
		Triangle t = new Triangle(c);
		Model.singleton().addShape(t);
		updateTriangle(t, pts);
	}

	public static void translateShape(Shape selectedShape, Point p, Point off) {
		if(selectedShape.getType() == ShapeType.LINE){
			Line l = (Line)selectedShape;
			Point p1top2 = new Point(l.getP1().x - l.getP2().x, l.getP1().y - l.getP2().y);
			l.setP1(new Point(p.x - off.x, p.y - off.y));
			l.setP2(new Point(l.getP1().x - p1top2.x, l.getP1().y - p1top2.y));
		} else {
			Point newCenter = new Point(p.x - off.x, p.y - off.y);
			selectedShape.setCenterPoint(newCenter);
		}
	}

	public static void updateLastShape(Point downPoint, Point mousePoint){
		updateShape(Model.singleton().getLastShape(), downPoint, mousePoint);
	}

	public static void updateShapeRotation(Shape shape, double rotation){
		shape.setRotation(rotation);
	}

	public static void updateShape(Shape shape, Point downPointW, Point mousePointW) {
		Point downPoint = MatrixMaker.getObjectFromWorld(downPointW, shape);
		Point mousePoint = MatrixMaker.getObjectFromWorld(mousePointW, shape);
		switch(shape.getType()){
		case CIRCLE: 	updateCircle((Circle)shape, downPoint, mousePoint);			break;
		case ELLIPSE:	updateEllipse((Ellipse)shape, downPoint, mousePoint);		break;
		case LINE:		updateLine((Line)shape, downPoint, mousePoint);				break;
		case RECTANGLE:	updateRectangle((Rectangle)shape, downPoint, mousePoint);	break;
		case SQUARE: 	updateSquare((Square)shape, downPoint, mousePoint);			break;
		case TRIANGLE:	break;
		}
	}

	public static void updateTriangle(Shape s, List<Point> pointsW){
		List<Point> points = null;
		Triangle t = (Triangle)s;
		
		if(s.getCenterPoint() == null || s.getRotation() == 0)
			points = pointsW;
		else{
			points = new ArrayList<Point>();
			for(Point p: pointsW)
				points.add(MatrixMaker.getObjectFromWorld(p, s));
		}
		
		Point p1 = points.get(0);
		Point p2 = points.get(1);
		Point p3 = points.get(2);
		int x = (p1.x + p2.x + p3.x)/3;
		int y = (p1.y + p2.y + p3.y)/3;
		Point newCenter = new Point(x, y);
		
		if(!(s.getCenterPoint() == null || s.getRotation() == 0)){
			Point c = s.getCenterPoint();
			newCenter = new Point(newCenter.x + c.x, newCenter.y + c.y);
		}
		t.setCenterPoint(newCenter);
		t.setP1(new Point(p1.x - x, p1.y - y));
		t.setP2(new Point(p2.x - x, p2.y - y));
		t.setP3(new Point(p3.x - x, p3.y - y));
	}

	private static void updateCircle(Circle c, Point downPoint, Point mousePoint){
		c.setCenterPoint(findBoundedCenterPoint(downPoint, mousePoint, c));
		c.setDiameter(findSize(downPoint, mousePoint));
	}

	private static void updateEllipse(Ellipse e, Point downPoint, Point mousePoint){
		e.setCenterPoint(findCenterPoint(downPoint, mousePoint, e));
		e.setHeight(findDy(downPoint, mousePoint));
		e.setWidth(findDx(downPoint, mousePoint));
	}

	private static void updateLine(Line l, Point downPoint, Point mousePoint){
		l.setP1(downPoint);
		l.setP2(mousePoint);
	}

	private static void updateRectangle(Rectangle r, Point downPoint, Point mousePoint){
		r.setCenterPoint(findCenterPoint(downPoint, mousePoint, r));
		r.setHeight(findDy(downPoint, mousePoint));
		r.setWidth(findDx(downPoint, mousePoint));
	}

	private static void updateSquare(Square s, Point downPoint, Point mousePoint){
		s.setCenterPoint(findBoundedCenterPoint(downPoint, mousePoint, s));
		s.setSize(findSize(downPoint, mousePoint));
	}

	private static Point findBoundedCenterPoint(Point downPoint, Point mousePoint, Shape sh) {
		int s = findSize(downPoint, mousePoint);
		mousePoint.x = mousePoint.x < downPoint.x? downPoint.x - s : mousePoint.x ;
		mousePoint.y = mousePoint.y < downPoint.y? downPoint.y - s : mousePoint.y;
		int minX = (int) Math.min(downPoint.x, mousePoint.x);
		int minY = (int) Math.min(downPoint.y, mousePoint.y);
		Point newCenter = new Point(minX + s/2, minY + s/2);
		return MatrixMaker.getWorldFromObject(newCenter, sh);
	}

	private static Point findCenterPoint(Point downPoint, Point mousePoint, Shape s) {
		int w = findDx(downPoint, mousePoint);
		int h = findDy(downPoint, mousePoint);
		int minX = (int) Math.min(downPoint.x, mousePoint.x);
		int minY = (int) Math.min(downPoint.y, mousePoint.y);
		Point newCenter = new Point(minX + w/2, minY + h/2);
		Point center = MatrixMaker.getWorldFromObject(newCenter, s);
		return center;
	}

	private static int findDx(Point p1, Point p2){
		return (int) Math.abs(p1.x - p2.x);
	}

	private static int findDy(Point p1, Point p2){
		return (int) Math.abs(p1.y - p2.y);
	}

	private static int findSize(Point p1, Point p2){
		return (int)Math.min(findDx(p1, p2), findDy(p1, p2));
	}
}