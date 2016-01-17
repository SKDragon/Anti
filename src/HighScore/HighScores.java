package HighScore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class HighScores {

	private ArrayList<Score> scores;
	String file = "HighScores.txt";
	String lineBreak = "//";

	public HighScores() {
		scores = new ArrayList<Score>();
	}

	void addScore(String name, int score) {
		scores.add(new Score(name, score));
	}

	void loadScoreFile() {
		try {
			Scanner reader = new Scanner(new File(file));
			String nextLine = "";

			while (reader.hasNextLine()) {
				nextLine = reader.nextLine();
				StringTokenizer st = new StringTokenizer(nextLine, lineBreak);
				while (st.hasMoreElements()) {
					String name = (String) st.nextElement();
					int score = (int) st.nextElement();
					// System.out.println("Name" + st.nextElement());
					// System.out.println("Score" + st.nextElement());
					scores.add(new Score(name, score));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void updateScoreFile() {
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

}
