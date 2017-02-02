package cs355.view;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs355.controller.Controller;
import cs355.model.Shape;

public class MatrixMaker {

	/**
	 * @param r rotation angle
	 * @param c center point 
	 * @return transformation for object --> viewport
	 */
	public static AffineTransform getViewportTransform(double r, Point c) {
		Point v = Controller.singleton().getViewport();
		double s = Controller.singleton().getZoom();

		AffineTransform matrix = new AffineTransform();
		AffineTransform rotMatrix = getRotationMatrix(r);
		AffineTransform transMatrix = getTranslationMatrix(c);
		AffineTransform viewMatrix = getViewportMatrix(v);
		AffineTransform scaleMatrix = getScaleMatrix(s);

		matrix.concatenate(scaleMatrix);
		matrix.concatenate(viewMatrix);
		matrix.concatenate(transMatrix);
		matrix.concatenate(rotMatrix);
		return matrix;
	}

	public static Point getWorldFromViewport(Point pointV) {
		Point viewport = Controller.singleton().getViewport();
		double zoom = Controller.singleton().getZoom();
		AffineTransform v = getViewportMatrix(viewport);
		AffineTransform s = getScaleMatrix(zoom);
		AffineTransform matrix = new AffineTransform();
		try {
			matrix.concatenate(v.createInverse());
			matrix.concatenate(s.createInverse());
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		Point pointW = new Point();
		matrix.transform(pointV, pointW);
		return pointW;
	}
	
	public static Point getViewportFromWorld(Point pointW) {
		Point viewport = Controller.singleton().getViewport();
		double zoom = Controller.singleton().getZoom();
		AffineTransform v = getViewportMatrix(viewport);
		AffineTransform s = getScaleMatrix(zoom);
		AffineTransform matrix = new AffineTransform();
		try {
			matrix.concatenate(s.createInverse());
			matrix.concatenate(v.createInverse());
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		Point pointV = new Point();
		matrix.transform(pointW, pointV);
		return pointV;
	}

	public static Point getObjectFromWorld(Point pointW, Shape s) {
		AffineTransform t = getTranslationMatrix(s.getCenterPoint());
		AffineTransform r = getRotationMatrix(s.getRotation());
		AffineTransform matrix = new AffineTransform();
		try {
			matrix.concatenate(r.createInverse());
			matrix.concatenate(t.createInverse());
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		Point pointO = new Point();
		matrix.transform(pointW, pointO);
		return pointO;
	}

	public static Point getWorldFromObject(Point pointO, Shape s) {
		AffineTransform t = getTranslationMatrix(s.getCenterPoint());
		AffineTransform r = getRotationMatrix(s.getRotation());
		AffineTransform matrix = new AffineTransform();
		matrix.concatenate(t);
		matrix.concatenate(r);
		Point pointW = new Point();
		matrix.transform(pointO, pointW);
		return pointW;
	}
	
	public static List<Point> getWorldFromObject(List<Point> pointsW, Shape s) {
		List<Point> points = new ArrayList<Point>();
		for(Point p: pointsW)
			points.add(getWorldFromObject(p, s));
		return points;
	}

	private static AffineTransform getRotationMatrix(double r){
		double[] rValues = new double[] { Math.cos(r), Math.sin(r), -Math.sin(r), Math.cos(r), 0, 0 };
		return new AffineTransform(rValues);
	}

	private static AffineTransform getTranslationMatrix(Point t){
		double[] tValues = new double[] { 1, 0, 0, 1, t.x, t.y};
		return new AffineTransform(tValues);
	}

	private static AffineTransform getViewportMatrix(Point t){
		double[] tValues = { 1, 0, 0, 1, -t.x, -t.y};
		return new AffineTransform(tValues);
	}

	private static AffineTransform getScaleMatrix(double s){
		double[] tValues = { s, 0, 0, s, 0, 0};
		return new AffineTransform(tValues);
	}
	
	public static double[][] matrixMultiply(double[][] a, double[][] b){
		double[][] c = new double[a.length][b[0].length];
		for(int i = 0; i < a.length; i++)
			for(int j = 0; j < b[0].length; j++)
				for(int k = 0; k < a[0].length; k++)
					c[i][j] += a[i][k] * b[k][j];
		return c;
	}
}
