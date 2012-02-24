package alp5.u12.pong;

import org.lwjgl.util.Rectangle;

public abstract class Entity {
	
	protected float x,y,dx,dy;
	private boolean solid,collides;
	protected Game game;
	

	public Entity(Game game, float x, float y, boolean solid) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.solid = solid;
		collides = false;
	}
	
	public abstract void draw();
}
