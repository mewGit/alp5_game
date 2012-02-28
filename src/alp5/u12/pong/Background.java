package alp5.u12.pong;

public class Background extends Entity {

	public Background(Game game, String ref) {
		super(game, game.getSprite(ref), false);
		x = 0;
		y = 0;
	}

	@Override
	public void handleCollision(int val) {
		// TODO Auto-generated method stub

	}

}
