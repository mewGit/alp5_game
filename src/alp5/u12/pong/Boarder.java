package alp5.u12.pong;

public class Boarder {
	
	private int up,low,left,right;
	
	public Boarder() {
		up = 0;
		low = 480;
		left = 0;
		right = 640;
	}

	// collisions:	none = 0	upper = lower = 1	left = rigth = 2
	public int collides(Ball ball) {
		int val = 0;
		if (ball.x <= left)
			val = 2;
		else if (ball.x+8 >= right)
			val = 2;
		if (ball.y <= up)
			val += 1;
		else if (ball.y+8 >= low)
			val += 1;
		return val;
	}
}
