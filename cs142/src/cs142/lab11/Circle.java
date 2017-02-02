package cs142.lab11;

import java.awt.Graphics;


public class Circle extends Oval{

	public Circle(){
		super();
		width = height;
	}


	public void setWidthHeight(int width, int height){
		height = Math.abs(height);
		width = Math.abs(width);
		if(height > width){
			setHeight(height);
			setWidth(height);
		}
		if(height < width){
			setHeight(width);
			setWidth(width);
		}
	}
	
	public void draw(Graphics page, boolean coordinates, boolean fill) {
		if(height > width)
			height = width;
		else
			width = height;
		page.setColor(color);
		if(fill)
			page.fillOval((int)getDrawPoint().getX(), (int)getDrawPoint().getY(), width, height);
		else
			page.drawOval((int)getDrawPoint().getX(), (int)getDrawPoint().getY(), width, height);
		super.drawCoord(page, coordinates, fill);

	}
}
