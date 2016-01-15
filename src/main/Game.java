package main;

import java.util.ArrayList;
import levels.*;

/**
 * Game object class, run from the GUI upon game start
 * @author Iain/Gavin
 * @version 8/1/16
 */
public class Game
{
	// ArrayList of different levels
	ArrayList<Level> levels = null;

	/**
	 * Launches the game
	 */
	public Game()
	{
		go();
	}

	/**
	 * Runs the game
	 */
	private void go()
	{
		loadLevels();

	}

	private void loadLevels()
	{

	}
}
