

package cs142.lab11;
import java.awt.Graphics;
import java.awt.Point;


public class Oval extends Shape
{


	public Oval() {
		super();
	}
	public void draw(Graphics page, boolean coordinates, boolean fill) {
		page.setColor(color);
		if(fill)
			page.fillOval((int)getDrawPoint().getX(), (int)getDrawPoint().getY(), width, height);
		else
			page.drawOval((int)getDrawPoint().getX(), (int)getDrawPoint().getY(), width, height);
		super.drawCoord(page, coordinates, fill);
	}
	public boolean containsPoint(Point point){
		double xd = Math.abs(point.getX() - x);
		double yd = Math.abs(point.getY() - y);
		double tempW = width;
		double tempH = height;
		if(((xd*xd)/(tempW*tempW)) + ((yd*yd)/(tempH*tempH)) <=.25)
			return true;
		else
			return false;
	}
}
