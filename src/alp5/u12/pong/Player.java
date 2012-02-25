package alp5.u12.pong;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Player extends Entity {

	protected int up = 0;
	protected int low = 480;
	protected int left = 0;
	protected int right = 640;
	
	public Player(Game game, String ref, float x, float y) {
		super(game.getSprite(ref), x, y, true);
		dx = 0;
		dy = 0;
	}

	// collisions:	none = 0	upper = lower = 1	left = rigth = 2
//	public void handleCollision(int val) {
//		if ((val & 1) == 1)
//			dy = -dy;
//		if ((val & 2) == 2)
//			dx = -dx;
//	}
	
	public void move() {
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) 
			y += -3;
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) 
			y += 3;
		
		float f = 1.0f;
		if (x+8 + dx > 640)
			f = (640-(x+8))/dx;
		else if (x + dx < 0)
			f = x/-dx;
		if (y+8 + dy > 480)
			f = (480-(y+8))/dy;
		else if (y + dy < 0)
			f = y/-dy;
		x += dx*f;
		y += dy*f;
	}
	
	// collisions:	none = 0	upper = lower = 1	left = rigth = 2
	public int collides(Ball ball) {
		int val = 0;
		if (ball.x <= left)
			val = 2;
		else if (ball.x+8 >= right)
			val = 2;
		if (ball.y <= up)
			val += 1;
		else if (ball.y+8 >= low)
			val += 1;
		return val;
	}
	
	public void setSpeed(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public void draw() {
		//set the color of the quad (R,G,B)
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		//draw quad
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x,y);
			GL11.glVertex2f(x+8,y);
			GL11.glVertex2f(x+8,y+64);
			GL11.glVertex2f(x,y+64);
		GL11.glEnd();
	}
}
