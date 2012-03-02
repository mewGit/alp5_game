package alp5.u12.pong.entitys;

import alp5.u12.pong.Game;

public class Background extends Entity {

	public Background(Game game, String ref) {
		super(game, getSprite(ref));
		x = 0;
		y = 0;
	}
	
	public Background(Game game, String ref, float x, float y, boolean centered) {
		super(game, getSprite(ref));
		this.x = (centered)? x-boxWidth/2 : x;
		this.y = y;
	}
	
	public void drawAsTile(int w, int h) {
		sprite.draw(x,y,w,h);
	}
}
