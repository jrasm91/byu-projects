package cs355.model;

import java.awt.Color;
import java.awt.Point;

public class Triangle extends Shape {

	public static final int SIDES = 3;
	private Point p1, p2, p3;
	
	
	public Triangle(Color c){ 
		this(new Point(0, 0), null, null, null, c);
	};
	
	public Triangle(Point centerPoint, Point p1, Point p2, Point p3, Color c) {
		this.centerPoint = centerPoint;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.color = c;
		this.type = ShapeType.TRIANGLE;
	}
	
	public Point getP1(){
		return p1;
	}
	
	public Point getP2(){
		return p2;
	}
	
	public Point getP3(){
		return p3;
	}

	public void setP1(Point p1) {
		this.p1 = p1;
	}

	public void setP2(Point p2) {
		this.p2 = p2;
	}

	public void setP3(Point p3) {
		this.p3 = p3;
	}
	

}
