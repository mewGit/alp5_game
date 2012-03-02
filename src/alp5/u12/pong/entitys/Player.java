package alp5.u12.pong.entitys;

import org.lwjgl.input.Keyboard;

import alp5.u12.pong.Game;

public class Player extends Entity {

	protected boolean player1;
	public int keyLastMove = 0;
	private float speed = 4.0f;
	
	public Player(Game game, String ref, boolean player1) {
		super(game, getSprite(ref));
		this.player1 = player1;
		x = (this.player1) ? 0+16 : game.width-boxWidth-16;
		y = (game.height-boxHeight)/2;
		dx = 0;
		dy = 0;
	}

	public void move() {
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) { 
			y = (y-speed > 0) ? y-speed : 0;
			keyLastMove = -1;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			y = (y+speed < height-boxHeight) ? y+speed : height-boxHeight;
			keyLastMove = 1;
		} else
			keyLastMove = 0;
	}
	
	public int collides(Ball ball) {
		int val = 0;
		//right player
		if (!player1) { 
			if ((ball.x+ball.boxWidth >= x) && (ball.y+ball.boxHeight >= y) && (ball.y <= y+boxHeight)) {
				ball.x = x-ball.boxWidth;
				if ((keyLastMove == -1 && ball.dy < 0) || (keyLastMove == 1 && ball.dy > 0)) {
					val = 4;
				} else if ((keyLastMove == -1 && ball.dy > 0) || (keyLastMove == 1 && ball.dy < 0)) {
					val = 16;
				} else val = 8;
				
			}
		//left player	
		} else {
			if((ball.x <= x+boxWidth) && (ball.y+ball.boxHeight >= y) && (ball.y <= y+boxHeight)) {
				ball.x = x+boxWidth;
				if ((keyLastMove == -1 && ball.dy < 0) || (keyLastMove == 1 && ball.dy > 0)) {
					val = 4;
				} else if ((keyLastMove == -1 && ball.dy > 0) || (keyLastMove == 1 && ball.dy < 0)) {
					val = 16;
				} else val = 8;
			}
		}
		return val;
	}
	
	public void setSpeed(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
	}
}
