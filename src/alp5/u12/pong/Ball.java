package alp5.u12.pong;

public class Ball extends Entity {

	public Ball(Game game, String ref, float x, float y) {
		super(game, game.getSprite(ref), true);
		this.x = x;
		this.y = y;
		dx = 0;
		dy = 0;
	}

	// collisions:	none = 0	upper = lower = 1	left = right = 2
	public void handleCollision(int val) {
		if ((val & 1) == 1)
			dy = -dy;
		if ((val & 2) == 2)
			dx = -dx;
		if ((val & 4) == 4){ // ball hits player (both in same direction)
			dx = -dx*1.15f;
			if (dy > Math.abs(3.2))
				dy = (float)Math.sqrt(dy);
			else dy = dy*0.8f;
			System.out.println("same");
		}
		if ((val & 8) == 8){ // ball hits player without movement
			dx = -dx;
			if (dy > Math.abs(3.2))
				dy = (float)Math.sqrt(dy);
			System.out.println("no move");
		}	
		if ((val & 16) == 16){ // ball hits player (both in different directions)
			dx = -dx*0.9f;
			if (dy > Math.abs(3.2))
				dy = (float)Math.sqrt(dy);
			else
			dy = dy*1.4f;
			System.out.println("different");
		}
	}
	
	public void move() {
		float f = 1.0f;
		int spriteWidth = sprite.getWidth();
		int spriteHeight = sprite.getHeight();
		if (x+spriteWidth + dx > width)
			f = (width-(x+spriteWidth))/dx;
		else if (x + dx < 0)
			f = x/-dx;
		if (y+spriteHeight + dy > height)
			f = (height-(y+spriteHeight))/dy;
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
