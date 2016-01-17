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
	protected long startTime;

	/**
	 * Checks to see if any enemies must be spawned
	 * @return Enemies to spawn
	 */
	public ArrayList<Enemy> checkSpawn()
	{
		ArrayList<Enemy> toSpawn = new ArrayList<Enemy>();

		// System.out.println("checking spawn");
		// Loops through enemy ArrayList and determines if any enemies need
		// to be spawned
		synchronized (enemies)
		{
			for (Enemy spawnCheck : enemies)
			{
				synchronized (spawnCheck)
				{
					if (spawnCheck.getSpawn() <= System.currentTimeMillis()
							- startTime)
					{
						toSpawn.add(enemies.remove(0));
						System.out.println(spawnCheck.getSpawn() + "spawned");
					}
				}
			}
		}

		return toSpawn;
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
