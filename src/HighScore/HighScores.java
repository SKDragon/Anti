package HighScore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HighScores {

	private ArrayList<Score> scores;
	private final String highScoresFile = "HighScores/HighScores.txt";

	ObjectOutputStream output = null;
	ObjectInputStream input = null;

	public HighScores() {
		scores = new ArrayList<Score>();
	}

	public void loadScoreFile() {
		try {
			input = new ObjectInputStream(new FileInputStream(highScoresFile));
			scores = (ArrayList<Score>) input.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e.getMessage());
		} finally {
			try {
				if (output != null) {
					output.flush();
					output.close();
				}
			} catch (IOException e) {
				System.out.println("[Laad] IO Error: " + e.getMessage());
			}
		}
	}

	public void updateScoreFile() {
		try {
			output = new ObjectOutputStream(new FileOutputStream(highScoresFile));
			output.writeObject(scores);
		} catch (FileNotFoundException e) {
			System.out.println("[Update] FNF Error: " + e.getMessage() + ",the program will try and make a new file");
		} catch (IOException e) {
			System.out.println("[Update] IO Error: " + e.getMessage());
		} finally {
			try {
				if (output != null) {
					output.flush();
					output.close();
				}
			} catch (IOException e) {
				System.out.println("[Update] Error: " + e.getMessage());
			}
		}
	}

	public ArrayList<Score> getScores() {
		loadScoreFile();
		sort();
		return scores;
	}

	private void sort() {
		ScoreVergelijken comparator = new ScoreVergelijken();
		Collections.sort(scores, comparator);
	}

	public class ScoreVergelijken implements Comparator<Score> {
		public int compare(Score score1, Score score2) {

			long sc1 = score1.getScore();
			long sc2 = score2.getScore();

			if (sc1 > sc2) {
				return -1; // -1 means first score is bigger then second score
			} else if (sc1 < sc2) {
				return +1; // +1 means that score is lower
			} else {
				return 0; // 0 means score is equal
			}
		}
	}

}
