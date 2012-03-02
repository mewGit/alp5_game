package alp5.u12.pong;

public class Score {

	protected int p1, p2; // [p1,p2]
	protected boolean changed;
	private int maxscore;
	private boolean winner;
	
	public Score (int maxscore){
		p1 = 0;
		p2 = 0;
		changed = false;
		winner = false;
		this.maxscore = maxscore;
	}
	protected boolean incScore(int player){
		if (player == 1) p1++;
		else p2++;
		winner = (p1 == maxscore || p2 == maxscore);
		// used for network score sync
		changed = true;
		return winner; 
	}

	protected void resetScore(){
		p1 = 0;
		p2 = 0;
		winner = false;
	}
	
}
