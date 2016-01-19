package HighScore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Highscores System
 * @author Gavin L/Iain
 * @version January 19, 2016
 */
public class HighScores
{
	// Global Variables
	private ArrayList<Score> scores;
	private String file = "HighScores/HighScores.txt";
	private String lineBreak = "//";

	/**
	 * Main Constructor
	 */
	public HighScores()
	{
		scores = new ArrayList<Score>();
	}

	/**
	 * Adds Score to ArrayList
	 * @param name The name of player
	 * @param score The score of player
	 */
	public void addScore(String name, int score)
	{
		scores.add(new Score(name, score));
		sort();
	}

	/**
	 * Gets the array of Scores
	 * @return The current Array of scores
	 */
	public ArrayList<Score> getArray()
	{
		return this.scores;
	}

	/**
	 * Clears the current Array of scores
	 */
	public void clear()
	{
		scores = new ArrayList<Score>();
	}

	/**
	 * Loads in scores from Highscore file
	 */
	public void loadScoreFile()
	{
		try
		{
			// Variables
			Scanner reader = new Scanner(new File(file));
			String nextLine = "";

			// Reads in each line from file
			while (reader.hasNextLine())
			{
				nextLine = reader.nextLine();
				// Separates name and score and adds to current array
				StringTokenizer st = new StringTokenizer(nextLine, lineBreak);
				while (st.hasMoreElements())
				{
					String name = (String) st.nextElement();
					String score = (String) st.nextElement();
					int scr = Integer.parseInt(score);
					scores.add(new Score(name, scr));
				}
			}
			reader.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Updates the Highscores file with current array
	 */
	public void updateScoreFile()
	{
		try
		{
			// Variables
			PrintWriter fileOut = new PrintWriter(new File(file));
			// Prints to file
			for (Score s : scores)
			{
				String all = s.getName() + lineBreak
						+ Integer.toString(s.getScore());
				fileOut.println(all);
			}
			fileOut.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Sort method that sorts the current array from highest to lowest score
	 * @return THe new sorted array
	 */
	protected ArrayList<Score> sort()
	{
		for (int i = 0; i < scores.size() - 1; i++)
		{
			for (int j = 0; j < scores.size() - 1; j++)
			{
				if (scores.get(j).getScore() < scores.get(j + 1).getScore())
				{
					Score temp = scores.get(j);
					scores.set(j, scores.get(j + 1));
					scores.set(j + 1, temp);
				}
			}
		}
		return scores;
	}

}
