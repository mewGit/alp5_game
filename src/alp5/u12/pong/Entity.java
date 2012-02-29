package alp5.u12.pong;

import org.lwjgl.util.Rectangle;

public abstract class Entity {
	
	protected float x,y,dx,dy;
	protected int boxWidth,boxHeight;
	protected Sprite sprite;
	protected int width,height;
	protected Game game;
	private Rectangle me = new Rectangle();; 
	private Rectangle him = new Rectangle();
	private boolean solid,collides;

	public Entity(Game game, Sprite sprite, boolean solid) {
		this.game = game;
		width = game.width;
		height = game.height;
		this.sprite = sprite;
		// later this will be the hit box for collision
		boxWidth = this.sprite.getWidth();
		boxHeight = this.sprite.getHeight();
		this.solid = solid;
		collides = false;
	}
	
	public boolean collidesWith(Entity other) {
		me.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
		him.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());
		
		return me.intersects(him);
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void handleCollision(int val);
	
	/**
	 * render the texture associated with that entity
	 */
	public void draw() {
		sprite.draw( (int)x , (int)y );
	}
}
