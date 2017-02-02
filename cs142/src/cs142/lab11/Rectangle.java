
package cs142.lab11;
import java.awt.Graphics;
import java.awt.Point;


public class Rectangle extends Shape {

	public Rectangle(){
		super();
	}

	public void draw(Graphics page, boolean coordinates, boolean fill) {
		page.setColor(color);
		if(fill)
			page.fillRect((int)getDrawPoint().getX(), (int)getDrawPoint().getY(), width, height);
		else
			page.drawRect((int)getDrawPoint().getX(), (int)getDrawPoint().getY(), width, height);
		
		super.drawCoord(page, coordinates, fill);

	}

	
	public boolean containsPoint(Point point){
		int compareX = (int)point.getX();
		int compareY = (int)point.getY();
		if( (compareX < x+(width/2))&&
			(compareX > x-(width/2))&&
			(compareY < y+(height/2)&&
			(compareY > y-(height/2))))
			return true;
		else
			return false;
	}

}
