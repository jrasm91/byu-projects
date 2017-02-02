package cs355.controller;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import cs355.gui.GUIFunctions;
import cs355.model.HouseModel;
import cs355.model.Line3D;
import cs355.model.Point3D;
import cs355.view.MatrixMaker;

public class Controller3D {

	private static final float UNIT = .5f;
	private static final float ROT_UNIT = 1f;
	private static Controller3D singleton = new Controller3D();

	private double rotAngle;
	private boolean drawHouse;
	private Point3D pos;
	private HouseModel model = new HouseModel();
	private double[][] clipMatrix = {
			{Math.tan(Math.toRadians(30)), 0, 0, 0},
			{0, Math.tan(Math.toRadians(30)), 0, 0},
			{0, 0, 1001/999, -2000/999},
			{0, 0, 1, 0},
	};

	public static Controller3D singleton() { return singleton; };

	private Controller3D(){
		drawHouse = false;
		reset();
	}

	public void reset(){
		pos = new Point3D(0, 0, -25);
		rotAngle = 0;
	}

	public void keyPressed(Iterator<Integer> iterator) {
		if(!drawHouse)
			return;
		while(iterator.hasNext()){
			String type = KeyEvent.getKeyText(iterator.next());
			switch(type){
			case "H": reset(); 					break;
			case "A": updatePos(90); 			break;
			case "D": updatePos(-90); 			break;
			case "W": updatePos(0); 			break;
			case "S": updatePos(180); 			break;
			case "Q": rotAngle += ROT_UNIT;	 	break;
			case "E": rotAngle -= ROT_UNIT; 	break;
			case "R": pos.y -= UNIT;			break;
			case "F": pos.y += UNIT;			break;
			}
		}
		GUIFunctions.refresh();
	}

	private void updatePos(double off){
		pos.x -= UNIT * Math.sin(Math.toRadians(rotAngle + off));
		pos.z += UNIT * Math.cos(Math.toRadians(rotAngle + off));
	}

	public boolean isDrawHouse() { return drawHouse; }
	public void changeDrawHouse(){ drawHouse = !drawHouse; }

	public List<Point2D.Double[]> getHouseLines(){
		Iterator<Line3D> iter = model.getLines();
		ArrayList<Point2D.Double[]> finalPoints = new ArrayList<Point2D.Double[]>();
		while(iter.hasNext()){
			Line3D line = iter.next();
			Point2D.Double[] newLine = get2DLine(line);
			if(newLine != null)
				finalPoints.add(newLine);
		}
		return finalPoints;
	}


	private Point2D.Double[] get2DLine(Line3D line){

		double[][] world = {{1024, 0, 1024}, {0, -1024, 1024}, {0, 0, 1}};
		double[][] start = firstPart(line.start);
		double[][] end = firstPart(line.end);
		
		if (!cull(start, end))
			return null;

		start = secondPart(start);
		end = secondPart(end);

		start = MatrixMaker.matrixMultiply(world, start);
		end = MatrixMaker.matrixMultiply(world, end);
		
		return new Point2D.Double[]{new Point2D.Double(start[0][0], start[1][0]), new Point2D.Double(end[0][0], end[1][0])};
	}

	private double[][] firstPart(Point3D point){
		double[][] m;
		double[][] pointMatrix = {{point.x}, {point.y}, {point.z}, {1}};
		double[][] translateMatrix = { {1, 0, 0, pos.x}, {0, 1, 0, pos.y}, {0, 0, 1, pos.z}, {0, 0, 0, 1}, };
		double[][] rotationMatrix = 
			{ 
				{-Math.sin(Math.toRadians(rotAngle + 90)), 0, Math.cos(Math.toRadians(rotAngle + 90)), 0}, 
				{0, 1, 0, 0}, 
				{Math.sin(Math.toRadians(rotAngle)), 0, -Math.cos(Math.toRadians(rotAngle)), 0},
				{0, 0, 0, 1}, 
			};
		m = MatrixMaker.matrixMultiply(translateMatrix, pointMatrix);
		m = MatrixMaker.matrixMultiply(rotationMatrix, m);
		m = MatrixMaker.matrixMultiply(clipMatrix, m);
		return m;
	}
	
	private double[][] secondPart(double[][] matrix){
		double[][] result = new double[matrix.length - 1][1];
		for(int i = 0; i < matrix.length - 1; i++)
			result[i][0] = matrix[i][0]/matrix[matrix.length - 1][0];
		result[matrix.length - 2][0] = 1;
	return result;
	}
	
	private boolean cull(double[][] start, double[][] end){
		double x1 = start[0][0];
		double y1 = start[1][0];
		double z1 = start[2][0];
		double w1 = start[3][0];
		
		double x2 = end[0][0];
		double y2 = end[1][0];
		double z2 = end[2][0];
		double w2 = end[3][0];
		
		if(x1 < -w1 && x2 < -w2)
			return false;
		if(x1 > w1 && x2 > w2)
			return false;
		if(y1 < -w1 && y2 < -w2)
			return false;
		if(y1 > w1 && y2 > w2)
			return false;
		if(z1 < -w1 || z2 < -w2)
			return false;
		if(z1 > w1 && z2 > w2)
			return false;
		
		return true;
	}
}
