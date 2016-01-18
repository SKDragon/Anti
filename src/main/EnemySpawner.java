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

	public void run()
	{
		while (!Field.gameOver)
		{
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				System.out.println("spawn error");
			}

			if (enemiesSpawned % 10 == 0 && delay > 100)
				delay -= 20;
			enemiesSpawned ++;
		}
	}

	
	//spawned = new ProEnemy(new ImageIcon(
//			"Pictures/Enemies/50x50/enemy_1.png").getImage(), 1, 0, 0,
//			0, 0, new Point(10, 9), 40, new LinearPro(new Point(
//					2,
//					2),
//					new ImageIcon(
//							"Pictures/Projectiles/Projectile_2.png")
//							.getImage(), 10, 1, 5), 3000);
	public static Enemy getSpawned()
	{
		return spawned;
	}
}
