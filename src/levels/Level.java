package levels;

import java.util.ArrayList;

import enemies.*;

/**
 * Parent Class for all levels
 * @author Iain/Gavin
 * @version 11/1/16
 */
public class Level
{
	// ArrayList of all enemies to be generated, as well as a parallel ArrayList
	// with the timing
	protected ArrayList<Enemy> enemies;

	/**
	 * Checks to see if any enemies must be spawned
	 * @return Enemies to spawn
	 */
	public ArrayList<Enemy> checkSpawn()
	{
		// Loops through enemy ArrayList and determines if any enemies need
		// to be spawned
		

		return null;
	}
	
	/**
	 * Gets the enemies to spawn
	 * @return enemies
	 */
	public ArrayList<Enemy> getEnemies()
	{
		return this.enemies;
	}
}
