package cs355.view;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import cs355.controller.Controller;

public class ImageDrawer {
	public static void draw(Graphics2D g, int[][] img){
		if(img == null) return;
	
	
		double zoom = Controller.singleton().getZoom();
		Point viewport = Controller.singleton().getViewport();
		
		int center = 1024;
		int width = img[0].length;
		int height = img.length;
		
		int count = 0;
		int[] pixels = new int[width * height];
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				pixels[count++] = img[y][x];
			}
		}
		
		System.out.println(width + ", " + height + ", " + width * height + ", " + pixels.length);
		
		AffineTransform saveAt = g.getTransform();
		AffineTransform trans = new AffineTransform(1, 0, 0, 1, center - width/2, center - height/2);
		AffineTransform scale = new AffineTransform(zoom, 0, 0, zoom, 0, 0);
		AffineTransform screen = new AffineTransform(1, 0, 0, 1, -viewport.x, -viewport.y);
		screen.concatenate(trans);
		scale.concatenate(screen);
		g.setTransform(scale);
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = image.getRaster();
		raster.setPixels(0, 0, width, height, pixels);
		g.drawImage(image, 0, 0, null);
		
		g.setTransform(saveAt);
	}

}
