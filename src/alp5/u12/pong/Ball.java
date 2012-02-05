package alp5.u12.pong;

import org.lwjgl.opengl.GL11;

public class Ball extends Entity {

	public Ball(Game game, String ref, float x, float y) {
		super(game, ref, x, y, true);
		dx = 0;
		dy = 0;
	}

	// collisions:	none = 0	upper = lower = 1	left = rigth = 2
	public void handleCollision(int val) {
		if ((val & 1) == 1)
			dy = -dy;
		if ((val & 2) == 2)
			dx = -dx;
	}
	
	public void move() {
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
			GL11.glVertex2f(x+8,y+8);
			GL11.glVertex2f(x,y+8);
		GL11.glEnd();
	}
}
