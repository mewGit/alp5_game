package alp5.u12.pong;

public class Ball extends Entity {

	public Ball(Game game, String ref, float x, float y) {
		super(game.getSprite(ref), x, y, true);
		dx = 0;
		dy = 0;
	}

	// collisions:	none = 0	upper = lower = 1	left = right = 2
	public void handleCollision(int val) {
		if ((val & 1) == 1)
			dy = -dy;
		if ((val & 2) == 2)
			dx = -dx;
	}
	
	public void move() {
		// TODO: make positions relative
		float f = 1.0f;
		int width = sprite.getWidth();
		int height = sprite.getHeight();
		if (x+width + dx > 640)
			f = (640-(x+width))/dx;
		else if (x + dx < 0)
			f = x/-dx;
		if (y+height + dy > 480)
			f = (480-(y+height))/dy;
		else if (y + dy < 0)
			f = y/-dy;
		x += dx*f;
		y += dy*f;
	}
	
	public void setSpeed(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
	}
}
