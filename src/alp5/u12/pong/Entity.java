package alp5.u12.pong;

import org.lwjgl.util.Rectangle;

public abstract class Entity {
	
	protected float x,y,dx,dy;
	protected Sprite sprite;
	protected Rectangle me;
	private boolean solid,collides;
	protected Game game;
	

	public Entity(Game game, String ref, float x, float y, boolean solid) {
		this.game = game;
		sprite = SpriteStore.get().getSprite(ref);
		this.x = x;
		this.y = y;
		this.solid = solid;
		collides = false;
		me = new Rectangle();
		me.setSize(sprite.getWidth(), sprite.getHeight());
	}
	
	public boolean collidesWith(Entity other) {
		return me.intersects(other.me);
	}
	
	public abstract void draw();
}
