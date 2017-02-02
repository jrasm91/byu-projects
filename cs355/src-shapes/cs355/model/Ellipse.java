package cs355.model;

import java.awt.Color;
import java.awt.Point;

public class Ellipse extends Shape {

	private int height, width;

	public Ellipse(Point centerPoint, int height, int width, Color color) {
		this.centerPoint = centerPoint;
		this.height = height;
		this.width = width;
		this.color = color;
		this.type = ShapeType.ELLIPSE;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
