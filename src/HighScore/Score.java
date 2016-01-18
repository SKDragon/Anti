package HighScore;


import java.io.Serializable;

/**
 * 
 * @author Gawain Leung
 *
 */
public class Score implements Serializable{

	String name;
	int score;

	public Score(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	private void setScore(int score) {
		this.score = score;
	}
	
}
