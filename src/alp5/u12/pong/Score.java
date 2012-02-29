package alp5.u12.pong;

public class Score {

	protected int[] score; // [p1,p2]
	protected boolean changed = false;
	private int maxscore;
	
	public Score (int maxscore){
		score = new int[2];
		score[0] = 0;
		score[1] = 0;
		this.maxscore = maxscore;
	}
	protected int incScore(int player){
		changed = true;
		return ++score[player-1];
	}
	protected int[] getScore(){
		return score;
	}
	protected void resetScore(){
		score[0] = 0;
		score[1] = 0;
	}
	
}
