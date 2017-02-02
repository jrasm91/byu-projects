package cs355.model;

import java.awt.Color;
import java.awt.Point;

public abstract class Shape {

	protected Color color;
	protected ShapeType type;
	protected Point centerPoint;
	protected double rotationAngle;
	
	protected Shape(){
		centerPoint = new Point(0, 0);
		rotationAngle = 0;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ShapeType getType() {
		return type;
	}

	public void setType(ShapeType type) {
		this.type = type;
	}

	public Point getCenterPoint() {
		return centerPoint;
	}

	public void setCenterPoint(Point centerPoint) {
		this.centerPoint = centerPoint;
	}

	public double getRotation() {
		return rotationAngle;
	}

	public void setRotation(double rotationAngle) {
		this.rotationAngle = rotationAngle;
	}

	@Override
	public String toString() {
		return "Shape [type=" + type + ", centerPoint="
				+ centerPoint + "]";
	}

	public Color getInverseColor() {
		return new Color(255 - color.getRed(), 255-color.getGreen(), 255 - color.getBlue());
	}
}
