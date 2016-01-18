package main;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import projectiles.*;
import enemies.*;

public class EnemySpawner implements Runnable
{
	protected static Enemy spawned;
	private long enemiesSpawned = 0;
	private long delay = 1000;
	private long enemySpeedX = 1;
	private long enemySpeedY = 1;
	private int enemyHealth = 1;

	public void run()
	{
		while (!Field.gameOver)
		{
			try
			{
				Thread.sleep(delay);
			}
			catch (InterruptedException e)
			{
				System.out.println("spawn error");
			}

			int x = (int) (Math.random() * 200 + 200);
			int enemyType = (int) Math.random() * 2;

			if (enemyType == 0)
			{
				int bulletX =  -1 *(int) (Math.random() * 3 - 1);
				int bulletY =  -2 *(int) (Math.random() * 3);
				
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
			enemiesSpawned++;
		}
	}

	public static Enemy getSpawned()
	{
		return spawned;
	}
}
