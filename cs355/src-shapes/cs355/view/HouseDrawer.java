package cs355.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

public class HouseDrawer {

	public static void draw(Graphics2D g, List<Point2D.Double[]> lines) {
		AffineTransform saved = g.getTransform();
		AffineTransform m = MatrixMaker.getViewportTransform(0, new Point(0,  0));
		g.setTransform(m);
		g.setColor(Color.ORANGE);
		for(Point2D.Double[] l: lines)
			g.draw(new Line2D.Double(l[0].x, l[0].y, l[1].x, l[1].y));	
		g.setTransform(saved);
	}
}
