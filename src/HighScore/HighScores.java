package HighScore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class HighScores {

	private ArrayList<Score> scores;
	String file = "HighScores/HighScores.txt";
	String lineBreak = "//";

	public HighScores() {
		scores = new ArrayList<Score>();
	}

	public void addScore(String name, int score) {
		scores.add(new Score(name, score));
		sort();
	}

	public ArrayList<Score> getArray() {
		return this.scores;
	}

	public void clear(){
		scores = new ArrayList<Score>();
	}
	
	public void loadScoreFile() {
		try {
			Scanner reader = new Scanner(new File(file));
			String nextLine = "";

			while (reader.hasNextLine()) {
				nextLine = reader.nextLine();
				StringTokenizer st = new StringTokenizer(nextLine, lineBreak);
				while (st.hasMoreElements()) {
					String name = (String) st.nextElement();
					String score = (String) st.nextElement();
					int scr = Integer.parseInt(score);
					//int score = (int) st.nextElement();
					scores.add(new Score(name, scr));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void updateScoreFile() {
		try {
			PrintWriter fileOut = new PrintWriter(new File(file));
			for (Score s : scores) {
				String all = s.getName() + lineBreak + Integer.toString(s.getScore());
				// String scr = Integer.toString(s.getScore());
				// String nm = s.getName();
				fileOut.println(all);
			}
			fileOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected ArrayList<Score> sort() {
		for (int i = 0; i < scores.size() - 1; i++) {
			for (int j = 0; j < scores.size() - 1; j++) {
				if (scores.get(j).getScore() < scores.get(j + 1).getScore()) {
					Score temp = scores.get(j);
					scores.set(j, scores.get(j + 1));
					scores.set(j + 1, temp);
				}
			}
		}
		return scores;
	}

}
