package alp5.u12.pong.entitys;

import alp5.u12.pong.Game;
import alp5.u12.pong.texture.Sprite;


public class MenuElement extends Entity {

	private Sprite[] font = new Sprite[11];
	
	// for buttons
	public MenuElement(Game game, String ref, float x, float y) {
		super(game, getSprite(ref));
		this.x = x;
		this.y = y;
	}

	// for waiting for host message
	public MenuElement(Game game, String ref) {
		super(game, getSprite(ref));
		x = game.width/2 - boxWidth/2;
		y = game.height/2;
	}
	
	// for the font
	public MenuElement(Game game, String ref, int x, int y, int w, int h) {
		super(game, getSprite(ref));
		this.x = 0;
		this.y = 0;
		loadFont(ref, x, y, w, h);
	}
	
	private void loadFont(String ref, int x, int y, int w, int h) {
		for (int i = 0; i < font.length; i++) {
			font[i] = getSprite(ref, x+i*w, y, w, h);
		}
	}
	
	public void draw(String msg, float x, float y, float size) {
		// XXX: ugly hack
		for (int i= 0 , k = 0 ; i < msg.length(); i++) {
			k = msg.charAt(i);
			if (k >= 48 && k <= 57)
				font[k-48].draw(x+i*12*size, y, size);
			else if (k == 46)
				font[10].draw(x+i*12*size, y, size);
		}
	}
}
