package cs355.LWJGL;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex3d;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import org.lwjgl.input.Keyboard;

public class StudentLWJGLController implements CS355LWJGLController {
	private final float UNIT = .2f;
	private final float ROT_UNIT = .5f;
	private final float VIEW_ANGLE = 45f;
	private final float CLIP_NEAR = 0.1f;
	private final float CLIP_FAR = 100f;
	private final double FACTOR = 48;
	
	private final double MOVE_FORWARD = 0;
	private final double MOVE_RIGHT = 90;
	private final double MOVE_BACK = 180;
	private final double MOVE_LEFT = 270;
	
	private final float RATIO = LWJGLSandbox.DISPLAY_WIDTH
			/ LWJGLSandbox.DISPLAY_HEIGHT;

	private HouseModel model = new HouseModel();
	private double rotAngle;
	private Point3D pos;

	@Override
	public void resizeGL() {
		reset();
		glViewport(0, 0, LWJGLSandbox.DISPLAY_WIDTH,
				LWJGLSandbox.DISPLAY_HEIGHT);
		loadPerspective();
	}

	private void reset() {
		pos = new Point3D(0, 0, -30);
		rotAngle = 0;
	}

	private void loadPerspective() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(VIEW_ANGLE, RATIO, CLIP_NEAR, CLIP_FAR);
		glMatrixMode(GL_MODELVIEW); // world to camera
	}

	private void loadOrthogonal() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-LWJGLSandbox.DISPLAY_WIDTH / FACTOR,
				LWJGLSandbox.DISPLAY_WIDTH / FACTOR,
				-LWJGLSandbox.DISPLAY_HEIGHT / FACTOR,
				LWJGLSandbox.DISPLAY_HEIGHT / FACTOR,
				CLIP_NEAR, 
				CLIP_FAR);
		glMatrixMode(GL_MODELVIEW);
	}

	@Override
	public void render() {
		glLoadIdentity();
		glRotated(rotAngle, 0, 1, 0);
		glTranslated(pos.x, pos.y, pos.z);

		glClear(GL_COLOR_BUFFER_BIT);
		glBegin(GL_LINES);

		for (Line3D l : model.getLines()) {
			glColor3d(1.0, 0.5, 0.0);
			glVertex3d(l.start.x, l.start.y, l.start.z);
			glColor3d(1.0,1.0, 1.0);
			glVertex3d(l.end.x, l.end.y, l.end.z);
		}
		glEnd();
	}

	@Override
	public void update() {
		
	}
	
	private void updatePos(double off){
		pos.x -= UNIT * Math.sin(Math.toRadians(rotAngle + off));
		pos.z += UNIT * Math.cos(Math.toRadians(rotAngle + off));
	}

	@Override
	public void updateKeyboard() {
		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN))
			reset();
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) 
			updatePos(MOVE_LEFT);
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) 
			updatePos(MOVE_RIGHT);
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) 
			updatePos(MOVE_FORWARD);
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) 
			updatePos(MOVE_BACK);
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) 
			rotAngle -= ROT_UNIT;
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) 
			rotAngle += ROT_UNIT;
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) 
			pos.y -= UNIT;
		if (Keyboard.isKeyDown(Keyboard.KEY_F)) 
			pos.y += UNIT;
		if (Keyboard.isKeyDown(Keyboard.KEY_O)) 
			loadOrthogonal();
		if (Keyboard.isKeyDown(Keyboard.KEY_P)) 
			loadPerspective();
	}
}
