package cs355.model;

import java.awt.Color;
import java.awt.Point;

public class Line extends Shape {
	
	private Point p1, p2;

	public Line(Point p1, Point p2, Color color) {
		this.p1 = p1;
		this.p2 = p2;
		this.color = color;
		this.type = ShapeType.LINE;
		this.centerPoint = new Point(0, 0);
	}
	
	public Point getP1(){
		return p1;
	}
	
	public Point getP2(){
		return p2;
	}

	public void setP1(Point p1) {
		this.p1 = p1;
	}

	public void setP2(Point p2) {
		this.p2 = p2;
	}
}
