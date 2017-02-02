package cs355.model;

import java.awt.Color;
import java.awt.Point;

public class Square extends Shape {
	
	private int size;
	
	public Square(Point centerPoint, int size, Color color){
		this.centerPoint = centerPoint;
		this.size = size;
		this.color = color;	
		this.type = ShapeType.SQUARE;
	}
	
	public int getSize(){
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}