package alp5.u12.pong.entitys;

import alp5.u12.pong.Game;


public class Button extends Entity {

	public Button(Game game, String ref, int x, int y) {
		super(game, game.getSprite(ref), false);
		this.x = x;
		this.y = y;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleCollision(int val) {
		// TODO Auto-generated method stub

	}

}
