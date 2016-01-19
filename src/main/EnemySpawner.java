package main;

import java.awt.Point;

import javax.swing.ImageIcon;

import projectiles.*;
import enemies.*;

/**
 * Enemy spawning is handled by this class
 * @author Iain/Gavin
 * @version 19/1/16
 */
public class EnemySpawner implements Runnable
{
	// The enemy to spawn
	protected static Enemy spawned;

	// Amount of enemies spawned
	private long enemiesSpawned = 0;

	// Delay in between spawing of enemies
	private long delay = 1000;

	// Basic speed, to be randomly changed in spawning
	private long enemySpeedX = 1;
	private long enemySpeedY = 1;

	// Health increase with amount of enemies spawned
	private int enemyHealth = 1;

	/**
	 * Runs the spawner
	 */
	public void run()
	{
		// Loops while the game is still playing
		while (!Field.gameOver)
		{
			// Delay during spawn
			try
			{
				Thread.sleep(delay);
			}
			catch (InterruptedException e)
			{
				System.out.println("spawn error");
			}

			// Randomly generates place to spawn and enemy type
			int x = (int) (Math.random() * 200 + 200);
			int enemyType = (int) Math.random() * 2;

			// Enemy type cases
			if (enemyType == 0)
			{
				// Makes bullet Speed
				int bulletX = -1 * (int) (Math.random() * 3 - 1);
				int bulletY = 2 * (int) (Math.random() * 3);

				// Makes a new enemy
				spawned = new ProEnemy(new ImageIcon(
						"Pictures/Enemies/50x50/enemy_1.png").getImage(),
						enemyHealth,
						(int) (enemySpeedX * (Math.random() * 3 - 1)),
						(int) (enemySpeedY * (Math.random() * 2 + 1)), 0, 0,
						new Point(x, 5), 40,
						new LinearPro(new Point(2, 2), new ImageIcon(
								"Pictures/Projectiles/Projectile_2.png")
								.getImage(), 10, bulletX, bulletY), 200);
			}
			else
			{
				// Makes a new enemy
				spawned = new MovingEnemy(
						new ImageIcon(
								"Pictures/Enemies/50x50/enemy_1.png").getImage(),
						enemyHealth,
						(int) (enemySpeedX * (Math.random() * 3 - 1)),
						(int) (enemySpeedX * (Math.random() * 3 - 1)),
						0,
						0,
						new Point(x, 5),
						40);
			}

			// Various checks to increase difficulty
			if (enemiesSpawned % 10 == 0 && delay > 100)
				delay -= 20;
			if (enemiesSpawned % 5 == 0)
				enemyHealth += 50;
			if (enemiesSpawned % 15 == 0)
				enemySpeedX++;
			
			// This number makes enemies harder as time progresses
			enemiesSpawned++;
		}
	}

	/**
	 * Gets the enemy to be spawned
	 * @return spawned
	 */
	public static Enemy getSpawned()
	{
		return spawned;
	}
}
