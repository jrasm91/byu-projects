package cs355.controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cs355.gui.CS355Controller;
import cs355.gui.GUIFunctions;
import cs355.main.CS355;
import cs355.model.Line;
import cs355.model.Shape;
import cs355.model.ShapeType;
import cs355.utility.Utility;
import cs355.view.MatrixMaker;

public class Controller implements CS355Controller {

	private static Controller singleton = new Controller();
	
	public static final double ZOOM_IN = 2;
	public static final double ZOOM_OUT = .5;
	public static final double ZOOM_MIN = .25;
	public static final double ZOOM_MAX = 4;

	private StateType selectedState;
	private double zoom;

	private Color selectedColor;
	private Shape selectedShape;

	private boolean selectedHandle;
	private boolean selectedDragging;
	private boolean selectedRotation;
	private boolean selectedTriangle;

	private boolean myself;

	private Point draggingOffset;
	private Point viewport;

	private List<Point> trianglePoints;
	private Point downPoint;
	private Controller3D cont3d;

	private Controller(){
		trianglePoints = new ArrayList<Point>();
		selectedColor = Color.BLUE;
		selectedState = StateType.NONE;
		viewport = new Point(0, 0);
		myself = false;
		zoom = 1;
		cont3d = Controller3D.singleton();
	}

	public static Controller singleton(){ return singleton; }
	public Shape getSelectedShape() { return selectedShape; }
	public double getZoom() { return zoom; }
	public Point getViewport() { return viewport; }
	
	private void setZoom(double value){
		myself = true;
		// value is 2 or 1/2 depending on zoom in or out

		// find screen width before zoom changes
		int prevWidth = (int) (CS355.SCROLL_DEFAULT/zoom);
		Point viewportW = MatrixMaker.getWorldFromViewport(new Point(0, 0));
		Point worldCenter = new Point(viewportW.x + prevWidth/2, viewportW.y + prevWidth/2);

		// change zoom level
		zoom *= value;

		// check for zoom max / zoom min cases
		if(zoom < ZOOM_MIN) zoom = ZOOM_MIN;
		if(zoom > ZOOM_MAX) zoom = ZOOM_MAX;

		// find screen width after zoom changes
		int width = (int) (CS355.SCROLL_DEFAULT/zoom);

		// find new viewport coordinates (relative to world coordinates)
		Point newViewport = new Point(worldCenter.x - width/2, worldCenter.y - width/2);

		// check if viewport gives you negative coordinates
		// screen_size = 1024
		if(newViewport.x < 0) newViewport.x = 0;
		if(newViewport.y < 0) newViewport.y = 0;
		if(newViewport.x + width > CS355.SCREEN_SIZE) newViewport.x = CS355.SCREEN_SIZE - width;
		if(newViewport.y + width > CS355.SCREEN_SIZE) newViewport.y = CS355.SCREEN_SIZE - width;

		if(prevWidth == CS355.SCREEN_SIZE){
			GUIFunctions.setHScrollBarKnob(width);
			GUIFunctions.setVScrollBarKnob(width);
		}

		GUIFunctions.setHScrollBarPosit(newViewport.x);
		GUIFunctions.setVScrollBarPosit(newViewport.y);

		GUIFunctions.setHScrollBarKnob(width);
		GUIFunctions.setVScrollBarKnob(width);

		GUIFunctions.refresh();
		myself = false;
	}

	/**
	 * Resets all states, variables
	 */
	private void reset(){
		selectedState = StateType.NONE;
		selectedHandle = false;
		selectedRotation = false;
		selectedDragging = false;
		selectedTriangle = false;
		trianglePoints.clear();
		downPoint = null;
		selectedShape = null;
		draggingOffset = new Point(0, 0);
		GUIFunctions.refresh();
	}

	@Override
	public void colorButtonHit(Color c) {
		if(c == null) return;
		selectedColor = c;	
		if(selectedShape != null)
			selectedShape.setColor(selectedColor);
		GUIFunctions.changeSelectedColor(c);
		GUIFunctions.refresh();
	}

	@Override
	public void selectButtonHit() {
		reset();
		selectedState = StateType.SELECT_SHAPE;
		GUIFunctions.refresh();
	}

	/* (non-Javadoc)
	 * @see cs355.gui.CS355Controller()
	 */
	public void triangleButtonHit() { reset(); selectedState = StateType.DRAW_TRIANGLE; }
	public void squareButtonHit() { reset(); selectedState = StateType.DRAW_SQUARE;	}
	public void rectangleButtonHit() { reset(); selectedState = StateType.DRAW_RECTANGLE; }
	public void circleButtonHit() { reset(); selectedState = StateType.DRAW_CIRCLE; }
	public void ellipseButtonHit() { reset(); selectedState = StateType.DRAW_ELLIPSE; }
	public void lineButtonHit() { reset(); selectedState = StateType.DRAW_LINE; }
	public void zoomInButtonHit() { setZoom(ZOOM_IN); }
	public void zoomOutButtonHit() { setZoom(ZOOM_OUT); }
	public void hScrollbarChanged(int value) { viewport.x = value; if(!myself) GUIFunctions.refresh(); } 
	public void vScrollbarChanged(int value) { viewport.y = value; if(!myself) GUIFunctions.refresh(); }
	public void toggle3DModelDisplay() { reset(); cont3d.changeDrawHouse(); GUIFunctions.refresh();}
	public void keyPressed(Iterator<Integer> iterator) { cont3d.keyPressed(iterator); }

	public void mouseClicked(Point pointV) {
		Point point = MatrixMaker.getWorldFromViewport(pointV);
		switch(selectedState){
		case NONE:
			break;
		case DRAW_TRIANGLE:
			if(trianglePoints.contains(point)) return;
			trianglePoints.add(point);
			if(trianglePoints.size() == 3){
				ShapeUpdater.addShape(trianglePoints, selectedColor);
				trianglePoints.clear();
			}
			break;
		case SELECT_SHAPE:
			if(HandleHitTester.isHittingBoxHandles(point, selectedShape))
				return;
			selectedShape = ShapeHitTester.findSelectedShape(point);
			if(selectedShape != null) 
				colorButtonHit(selectedShape.getColor());
			break;
		default:
		}
		GUIFunctions.refresh();
	}

	public void mousePressed(Point pointV) {
		this.downPoint = MatrixMaker.getWorldFromViewport(pointV);

		switch(selectedState){
		case NONE:
			// Do nothing
			break;
		case SELECT_SHAPE:
			if(selectedShape == null) return;
			if(HandleHitTester.isHittingLineHandles(downPoint, selectedShape)){
				downPoint = HandleHitTester.getOppositeLineHandle(downPoint, selectedShape);
				selectedHandle = true;
			} else if(HandleHitTester.isHittingTriangleHandles(downPoint, selectedShape)){
				selectedTriangle = true;
				trianglePoints = HandleHitTester.getOppositeTriangleHandles(downPoint, selectedShape);
			} else if(HandleHitTester.isHittingBoxHandles(downPoint, selectedShape)){
				downPoint = HandleHitTester.getOppositeBoxHandle(downPoint, selectedShape);
				selectedHandle = true;
			} else if(HandleHitTester.isHittingRotationHandle(downPoint, selectedShape)){
				selectedRotation = true;
			} else if(selectedShape == ShapeHitTester.findSelectedShape(downPoint)){
				selectedDragging = true;
				draggingOffset = new Point(
						(downPoint.x - selectedShape.getCenterPoint().x),
						(downPoint.y - selectedShape.getCenterPoint().y));
				if(selectedShape.getType() == ShapeType.LINE)
					draggingOffset = new Point(
							downPoint.x - ((Line)selectedShape).getP1().x,
							downPoint.y - ((Line)selectedShape).getP1().y
							);
			}
			GUIFunctions.refresh();
			break;
		default:
			// Store downPoint and add a new shape
			ShapeUpdater.addShape(selectedState, downPoint, selectedColor);
			GUIFunctions.refresh();
		}
	}

	public void mouseDragged(Point pointV) {
		Point point = MatrixMaker.getWorldFromViewport(pointV);
		switch(selectedState){
		case NONE:
			// Do nothing
			break;
		case SELECT_SHAPE:
			if(selectedShape == null) return;
			if(selectedHandle){
				ShapeUpdater.updateShape(selectedShape, downPoint, point);
			} else if(selectedRotation){
				selectedShape.setRotation(Utility.calcRotationAngle(selectedShape, point));
			} else if(selectedTriangle){
				trianglePoints.add(point);
				ShapeUpdater.updateTriangle(selectedShape, trianglePoints);
				trianglePoints.remove(point);
			} else if(selectedDragging){
				ShapeUpdater.translateShape(selectedShape, point, draggingOffset);
			}
			break;
		default:
			// Update drawing shape (rest of the cases are DRAW_SOMETHING ...)
			ShapeUpdater.updateLastShape(downPoint, point);
		}
		GUIFunctions.refresh();
	}

	public void mouseReleased(Point point) {
		downPoint = null;
		draggingOffset = null;
		selectedHandle = false;
		selectedRotation = false;
		selectedDragging = false;
		selectedTriangle = false;
		GUIFunctions.refresh();
	}

	public void doEdgeDetection() { ControllerImg.singleton().doEdgeDetection(); }
	public void doSharpen() { ControllerImg.singleton().doSharpen(); }
	public void doMedianBlur() { ControllerImg.singleton().doMedianBlur(); }
	public void doUniformBlur() { ControllerImg.singleton().doUniformBlur(); }
	public void doChangeContrast(int val) { ControllerImg.singleton().doChangeContrast(val); }
	public void doChangeBrightness(int val) { ControllerImg.singleton().doChangeBrightness(val); }
	public void doLoadImage(BufferedImage buff) { ControllerImg.singleton().doLoadImage(buff); }
	public void toggleBackgroundDisplay() { ControllerImg.singleton().toggleBackgroundDisplay(); }
}

