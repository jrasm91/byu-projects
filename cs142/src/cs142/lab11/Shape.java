
package cs142.lab11;
import java.awt.*;
import java.util.Random;


public abstract class Shape
{
	// width, height, color of objcet
	protected int width;
	protected int height;
	protected Color color;
	//x y -> midpoint
	protected int x;
	protected int y;
	
	// width and height buffer
	final protected int offsetx = -27;
	final protected int offsety = 3;
	
	public Shape(){
		Random r = new Random();
		height = r.nextInt(100)+ 25;
		width = r.nextInt(100) + 25;
		x = r.nextInt(714-width);
		y = r.nextInt(638-height);
		color = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
	}
	//getter/setter for width
	public int getWidth() {
		return width;
		}
	public void setWidth(int width){
		this.width = Math.abs(width);
		}
	//getter/setter for height
	public int getHeight(){
		return height;
		}
	public void setHeight(int height){
		this.height = Math.abs(height);
		}
	public void setWidthHeight(int width, int height){
		setWidth(width);
		setHeight(height);
	}
	//getter/setter for color
	public Color getColor(){
		return color;
		}
	public void setColor(Color color){
		this.color = color;
		}
	//getter/setter for x,y corrdinates (midpoint)
	public Point getMidPoint(){
		return new Point(x,y);
		}
	public void setMidPoint(Point point){
		this.x = point.x;
		this.y = point.y;
	}
	//getter for width top left corner of shape
	public Point getDrawPoint(){
		return new Point(x-(width/2), y-(height/2));
		}
	// draw method to bo implemented among other classes
	public abstract void draw(Graphics page, boolean coordinates, boolean fill);
	public void drawCoord(Graphics page, boolean coordinates, boolean fill){
		if(coordinates){
			//if(fill)
			//	page.setColor(Color.black);
			//else
			page.setColor(Color.white);
			page.drawString("("+x+", "+y+")", (int)getMidPoint().getX()+offsetx, (int)getMidPoint().getY()+ offsety);
		}
	}
	// to String method, which prints out desired Shape characteristics
	public String toString(){
		String returner = "\n\nShape Type: " + this.getClass();
		//returner += "\nColor: " + color;
		//returner += "\nDraw-Coordinate: " + getDrawPoint();
		//returner += "\nWidth: " + width + "\tHeight: " + height;
		returner += "\nMidpoint: " + getMidPoint();
		return returner;
	}
	public abstract boolean containsPoint(Point point);
	
}
