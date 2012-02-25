package alp5.u12.pong;

import org.lwjgl.util.Rectangle;

public abstract class Entity {
	
	protected float x,y,dx,dy;
	protected Sprite sprite;
	private Rectangle me = new Rectangle();; 
	private Rectangle him = new Rectangle();
	private boolean solid,collides;

	public Entity(Sprite sprite, float x, float y, boolean solid) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
		this.solid = solid;
		collides = false;
	}
	
	public boolean collidesWith(Entity other) {
		me.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
		him.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());
		
		return me.intersects(him);
	}
	
	/**
	 * render the texture associated with that entity
	 */
	public void draw() {
		sprite.draw( (int)x , (int)y );
	}
}
