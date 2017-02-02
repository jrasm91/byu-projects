package cs355.model;

import java.awt.Color;
import java.awt.Point;

public class Circle extends Shape {

	
	private int diameter;
	
	public Circle(Point centerPoint, int diameter, Color color) {
		this.centerPoint = centerPoint;
		this.diameter = diameter;
		this.color = color;
		this.type = ShapeType.CIRCLE;
	}
	
	public int getDiameter(){
		return diameter;
	}

	public void setDiameter(int diameter){
		this.diameter = diameter;
	}
}
