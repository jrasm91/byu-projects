package cs355.controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs355.model.Circle;
import cs355.model.Ellipse;
import cs355.model.Line;
import cs355.model.Model;
import cs355.model.Rectangle;
import cs355.model.Shape;
import cs355.model.Square;
import cs355.model.Triangle;
import cs355.utility.Utility;
import cs355.view.MatrixMaker;

public class ShapeHitTester {

	private static ShapeHitTester singleton = new ShapeHitTester();
	private static double LINE_THRESHOLD = 16;

	private ShapeHitTester() { }

	public static Shape findSelectedShape(Point point){
		return singleton.hitShapeTest(point);
	}

	/**
	 * Hit test Point p against the Model's list of shapes
	 * 
	 * @param p The point to test
	 * @return The last drawn shape "hit" by the point, or null if no shape was hit
	 */
	public Shape hitShapeTest(Point worldP) {
		List<Shape> shapes = new ArrayList<Shape>(Model.singleton().getShapes());
		Collections.reverse(shapes);
		for(Shape shape: shapes){
			Point p = MatrixMaker.getObjectFromWorld(worldP, shape);
			switch(shape.getType()){
			case CIRCLE:   if(hitCircle(p, (Circle)shape)) 		return shape;		break;
			case ELLIPSE:  if(hitEllipse(p, (Ellipse)shape))	return shape;		break;
			case LINE:     if(hitLine(p, (Line)shape)) 			return shape;		break;
			case RECTANGLE:if(hitRectangle(p, (Rectangle)shape))return shape; 		break;
			case SQUARE:   if(hitSquare(p, (Square)shape)) 		return shape;		break;
			case TRIANGLE: if(hitTriangle(p, (Triangle)shape)) 	return shape;		break;
			}
		}
		return null;
	}

	private boolean hitCircle(Point p, Circle cir){
		int r = cir.getDiameter()/2;
		return ((double)(p.x * p.x)/ (r * r)) + 
				((double)(p.y * p.y)/ (r * r))	<= 1.0;
	}

	private boolean hitEllipse(Point p, Ellipse e){
		int h = e.getHeight()/2;
		int w = e.getWidth()/2;
		return ((double)(p.x * p.x)/ (w * w)) + 
				((double)(p.y * p.y)/ (h * h))	<= 1.0;
	}

	private boolean hitRectangle(Point p, Rectangle r){
		int width = Utility.getWidth(r);
		int height = Utility.getHeight(r);
		return (Math.abs(p.x) < width/2 && Math.abs(p.y) < height/2);
	}

	private boolean hitSquare(Point p, Square s){
		int size = Utility.getWidth(s);
		return (Math.abs(p.x) < size/2 && Math.abs(p.y) < size/2);
	}

	private boolean hitTriangle(Point p, Triangle t){
		return hitPolygon(new Point[]{t.getP1(), t.getP2(), t.getP3()}, p);
	}

	private boolean hitLine(Point p, Line l){
		double distance = minimumDistance(l.getP1(), l.getP2(), p);
		return distance < LINE_THRESHOLD;
	}
	
	private double minimumDistance(Point v, Point w, Point p){
		double length = distance(v, w);
		if (length == 0) 
			return distance(p, w);
		
		double t = dot(minus(p, v), minus(w, v)) / length;
		if (t < 0.0) 
			return distance(p, v);      
		else if (t > 1.0) 
			return distance(p, w);
		Point projection = minus(w, v);
		projection.x = (int) (projection.x * t + v.x);
		projection.y = (int) (projection.y * t + v.y);
		return distance(p, projection);
	}

	private double distance(Point p1, Point p2){
		return square(p2.x - p1.x) + square((p2.y - p1.y));
	}

	private double square(double x){
		return x*x;
	}
	
	private Point minus(Point p1, Point p2){
		return new Point(p1.x - p2.x, p1.y - p2.y);
	}
	
	private double dot(Point p1, Point p2){
		return p1.x * p2.x + p1.y * p2.y;
	}

	private boolean hitPolygon(Point[] vert, Point test){
		boolean c = false;
		for (int i = 0, j = vert.length - 1; i < vert.length; j = i++) {
			if (((vert[i].y > test.y) != (vert[j].y > test.y))
					&& (test.x < (vert[j].x - vert[i].x) * (test.y - vert[i].y)
							/ (vert[j].y - vert[i].y) + vert[i].x))
				c = !c;
		}
		return c;
	}

}
