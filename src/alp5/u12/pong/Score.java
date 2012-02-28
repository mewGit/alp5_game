package alp5.u12.pong;

public class Score {

	private int[] score; // [p1,p2]
	private int maxscore;
	
	
	public Score (int maxscore){
		int[] score = new int[2];
		score[0] = 0;
		score[1] = 0;
		this.maxscore = maxscore;
	}
	private int incScore(boolean player){
		return player? score[0]++ : score[1]++;
	}
	private int[] getScore(boolean player){
		return score;
	}
	private void resetScore(){
		score[0] = 0;
		score[1] = 0;
	}
	
}
