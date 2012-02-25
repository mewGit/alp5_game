package alp5.u12.pong;

import org.lwjgl.input.Keyboard;

public class Player extends Entity {

	protected boolean player1;
	protected int up = 0;
	protected int low = 480;
	protected int left = 0;
	protected int right = 640;
	
	public Player(Game game, String ref, float x, float y, boolean player1) {
		super(game, game.getSprite(ref), x, y, true);
		this.player1 = player1;
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
		if (!player1) {
				if ((ball.x+ball.sprite.getWidth() >= x) &&
				(ball.y+ball.sprite.getHeight() >= y) && 
				(ball.y <= y+sprite.getHeight())) {
					ball.x = x-ball.sprite.getWidth();
					val = 4;
				}
		} else {
				if((ball.x <= x+sprite.getWidth()) && 
					(ball.y+ball.sprite.getHeight() >= y) &&
					(ball.y <= y+sprite.getHeight())) {
						ball.x = x+sprite.getWidth();
						val = 4;
				}
		}
		return val;
	}
	
	public void setSpeed(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
	}
}
