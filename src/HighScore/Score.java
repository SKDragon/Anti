package HighScore;

public class Score {

	String name;
	long score;

	public Score(String name, long score) {
		this.name = name;
		this.score = score;
	}

	private String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	protected long getScore() {
		return score;
	}

	private void setScore(long score) {
		this.score = score;
	}
	
}
