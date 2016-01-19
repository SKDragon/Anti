package HighScore;

import java.io.Serializable;

/**
 * Score Object
 * @author Gavin L/Iain
 * @version January 19, 2016
 */
public class Score implements Serializable
{

	/**
	 * Default Serialization
	 */
	private static final long serialVersionUID = 1L;
	// Variables
	private String name;
	private int score;

	/**
	 * Constructor that creates the Score Object
	 * @param name The Name of player
	 * @param score The Score the player Got
	 */
	public Score(String name, int score)
	{
		this.name = name;
		this.score = score;
	}

	/**
	 * Gets the name from Score Object
	 * @return The name of player
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Gets the score from Score Object
	 * @return The score of player
	 */
	public int getScore()
	{
		return score;
	}

}
