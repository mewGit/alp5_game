package alp5.u12.pong;

import org.lwjgl.input.Keyboard;

public class Player extends Entity {

	protected int up = 0;
	protected int low = 480;
	protected int left = 0;
	protected int right = 640;
	
	public Player(Game game, String ref, float x, float y) {
		super(game, game.getSprite(ref), x, y, true);
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
}
