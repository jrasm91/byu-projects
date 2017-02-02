package cs355.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Model {
	
	private static Model singleton = new Model();
	
	private List<Shape> shapes;
	private int[][] image;
	
	private Model(){
		shapes = new ArrayList<Shape>();
		image = null;
	}
	
	public static Model singleton(){
		return singleton;
	}
	
	public List<Shape> getShapes(){
		return shapes;
	}
	
	public int[][] getImage(){
		return image;
	}
	
	public void setImage(int[][] image){
		this.image = image;
	}
	public void addShape(Shape s){
		assert(shapes != null);
		shapes.add(s);
	}
	
	public Shape getLastShape(){
		assert(shapes != null);
		if(shapes.size() == 0)
			return null;
		
		return shapes.get(shapes.size() - 1);
	}

}
