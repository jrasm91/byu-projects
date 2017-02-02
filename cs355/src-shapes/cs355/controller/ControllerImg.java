package cs355.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import cs355.gui.GUIFunctions;
import cs355.model.Model;

public class ControllerImg {

	private static ControllerImg singleton = new ControllerImg();

	public static ControllerImg singleton() { return singleton; };

	public boolean showImage;

	private ControllerImg(){
		showImage = false;
	}

	public void doEdgeDetection() {
		int[][] newImage = new TransformImage(Model.singleton().getImage()) {
			public int operation(int x, int y) {
				if(isEdge(x, y)) return oldImage[y][x];
				int gx = 
						-oldImage[y-1][x-1] + 
						-2 * oldImage[y][x-1] +  + 
						-oldImage[y+1][x-1] + 
						oldImage[y-1][x+ 1] + 
						2 * oldImage[y][x+1]  + 
						oldImage[y+1][x+1];
				int gy = 
						-oldImage[y-1][x-1] + 
						-2 *oldImage[y-1][x] + 
						-oldImage[y-1][x+ 1] + 
						oldImage[y+1][x-1] + 
						oldImage[y+1][x] + 
						oldImage[y+1][x+1];
				gx /= 8;
				gy /= 8;
				return (int)(Math.sqrt(gx*gx + gy*gy));
			}
		}.transform();
		Model.singleton().setImage(newImage);
		GUIFunctions.refresh();
	}

	public void doSharpen() {
		int[][] newImage = new TransformImage(Model.singleton().getImage()) {
			public int operation(int x, int y) {
				if(isEdge(x, y)) return oldImage[y][x];
				int newValue = 
						-oldImage[y-1][x-1] + 
						-oldImage[y-1][x+ 1] + 
						6*oldImage[y][x] + 
						-oldImage[y+1][x-1] + 
						-oldImage[y+1][x+1];
				newValue /= 2;
				return newValue;
			}
		}.transform();
		Model.singleton().setImage(newImage);
		GUIFunctions.refresh();
	}

	public void doMedianBlur() {
		int[][] newImage = new TransformImage(Model.singleton().getImage()) {
			public int operation(int x, int y) {
				if(isEdge(x, y)) return oldImage[y][x];
				int[] values = { 
						oldImage[y-1][x-1],
						oldImage[y-1][x],
						oldImage[y-1][x+ 1],
						oldImage[y][x-1], 
						oldImage[y][x] ,
						oldImage[y][x+1],
						oldImage[y+1][x-1],
						oldImage[y+1][x],
						oldImage[y+1][x+1]
				};
				Arrays.sort(values);
				return values[4];
			}
		}.transform();
		Model.singleton().setImage(newImage);
		GUIFunctions.refresh();
	}

	public void doUniformBlur() {
		int[][] newImage = new TransformImage(Model.singleton().getImage()) {
			public int operation(int x, int y) {
				if(isEdge(x, y)) return oldImage[y][x];
				int newValue = 
						oldImage[y-1][x-1] + 
						oldImage[y-1][x] + 
						oldImage[y-1][x+ 1] + 
						oldImage[y][x-1] +  + 
						oldImage[y][x] + 
						oldImage[y][x+1]  + 
						oldImage[y+1][x-1] + 
						oldImage[y+1][x] + 
						oldImage[y+1][x+1];
				newValue /= 9;
				return newValue;
			}
		}.transform();
		Model.singleton().setImage(newImage);
		GUIFunctions.refresh();
	}

	public void doChangeContrast(final int contrastAmountNum) {
		int[][] newImage = new TransformImage(Model.singleton().getImage()) {
			public int operation(int x, int y) {
				double value = ((double)contrastAmountNum + 100d)/100d;
				return (int)(value*value*value*value*(oldImage[y][x] -128) + 128);
			}
		}.transform();
		Model.singleton().setImage(newImage);
		GUIFunctions.refresh();
	}

	public void doChangeBrightness(final int brightnessAmountNum) {
		if(!showImage) return;
		int[][] newImage = new TransformImage(Model.singleton().getImage()) {
			public int operation(int x, int y) {
				return oldImage[y][x] + brightnessAmountNum;
			}
		}.transform();
		Model.singleton().setImage(newImage);		
		GUIFunctions.refresh();
	}

	public void doLoadImage(BufferedImage openImage) {
		int[][] image = new int[openImage.getHeight()][openImage.getWidth()];
		for(int x = 0; x < openImage.getWidth(); x++)
			for(int y = 0; y < openImage.getHeight(); y++)
				image[y][x] = new Color(openImage.getRGB(x, y)).getGreen();
		Model.singleton().setImage(image);
		showImage = true;
		GUIFunctions.refresh();
	}

	public void toggleBackgroundDisplay() {
		showImage = !showImage;
		GUIFunctions.refresh();
	}

	public boolean showImage(){
		return showImage;
	}

	private abstract class TransformImage {
		private int[][] newImage;
		protected int[][] oldImage;

		public TransformImage(int[][] image){
			if(image == null) return;
			oldImage = image;
			newImage = new int[image.length][image[0].length];
		}

		public int[][] transform(){
			if(oldImage == null) return null;
			for(int y = 0; y < oldImage.length; y++)
				for(int x = 0; x < oldImage[0].length; x++){
					int newValue = operation(x, y);
					if(newValue > 255) newValue = 255;
					if(newValue < 0) newValue = 0;
					newImage[y][x] = newValue;
				}
			return newImage;
		}

		public boolean isEdge(int x, int y){
			return (x == 0 || y == 0 || x == oldImage[0].length-1 || y == oldImage.length-1);
		}

		public abstract int operation(int x, int y);
	}
}
