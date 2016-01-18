package main;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import projectiles.LinearPro;
import projectiles.Projectile;
import enemies.Enemy;
import enemies.MovingEnemy;
import enemies.ProEnemy;

/**
 * Field class containing everything on the game screen
 * @author Iain/Gavin
 * @version 12/1/16
 */
public class Field
{
	// Boolean to determine if the game is still occurring or not
	// Boolean to determine if the game is over, will stop all threads
	private boolean playerAlive = true;
	public static boolean gameOver = false;
	public static boolean proFired = false;
	public static boolean enemySpawned = false;
	public static boolean enemyProFired = false;

	// Player object information
	private static Player player;
	public static Point playerLoc = new Point(0, 0);
	private static int playerMove;
	public static int playerDim = 0;

	// Gameplay information
	private int score = 0;
	public static long timeStart;

	private static long timeElapsed = System.currentTimeMillis();

	// All player projectiles
	private static ArrayList<Projectile> playerPro = new ArrayList<Projectile>();

	// ArrayList of all enemy projectiles, enemies
	private static ArrayList<Projectile> enemyPro = new ArrayList<Projectile>();
	private static ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	/**
	 * Constructor, only needs player's choice of character
	 * @param charChoice the character to use
	 */
	public Field()
	{
		// Creates the player according to the player's choice of character
		player = new Player();

		// Creates references to the player variables
		playerLoc = player.getLoc();
		playerMove = player.getMoveSpeed();
		playerDim = player.getDimensions();

		// Set timing for firing, movement
		timeStart = System.currentTimeMillis();
	}

	/**
	 * Runs while the game is still going
	 */
	public void manageField()
	{
		// Creates and starts enemy spawning
		Thread enemySpawner = new Thread(new FieldManager());
		enemySpawner.start();

		// Creates and starts score based on how many seconds survived
		Thread timeScore = new Thread(new SecondsScore());
		timeScore.start();

		// While the player is still alive...
		while (!gameOver)
		{
			// Checks for a player collision
			playerAlive = !isCharCollision();

			// If the player was hit
			if (!playerAlive)
				gameOver = true;

			// Checks for character projectiles hitting enemies
			isEnemyCollision();

			try
			{
				Thread.sleep(30);
			}
			catch (InterruptedException e)
			{
			}
		}
		System.out.println("game over");
	}

	/**
	 * Goes through linked lists of projectiles and enemies to determine if the
	 * player has come into contact with any of them
	 * @return if the player has been hit
	 */
	private synchronized boolean isCharCollision()
	{
		// Ref to character location
		double playerX = playerLoc.getX();
		double playerY = playerLoc.getY();

		// Loops through enemies to check for collisions
		synchronized (enemies)
		{
			for (Enemy checking : enemies)
			{
				synchronized (checking)
				{
					// References to X and Y to use in reducing checking
					double checkX = checking.getLocation().getX();
					double checkY = checking.getLocation().getY();

					// If the enemy is too far away, ignore it
					if (Math.abs(playerY - checkY) < 70
							&& Math.abs(playerX - checkX) < 70)
					{
						// Creating references to dimensions of the player and
						// projectile for collision check
						int playerDim = player.getDimensions();
						int checkDim = checking.getDimensions();

						if ((Math.abs(playerX - checkX) <= checkDim
								|| Math.abs(checkX - playerX) <= playerDim)
								&& (Math.abs(playerY - checkY) < checkDim
								|| Math.abs(checkY - playerY) < playerDim))
							return true;
					}
				}
			}
		}

		synchronized (enemyPro)
		{
			// Loops through projectiles to see if any collisions have occurred
			for (Projectile checking : enemyPro)
			{
				synchronized (checking)
				{
					// References to X and Y to use in reducing projectiles to
					// check
					double checkX = checking.getLocation().getX();
					double checkY = checking.getLocation().getY();

					// If projectile is too far away to even be close to
					// hitting, then don't bother checking it
					if (Math.abs(playerY - checkY) < 70
							&& Math.abs(playerX - checkX) < 70)
					{
						// Creating references to dimensions of the player and
						// projectile for collision check
						int playerDim = player.getDimensions();
						int checkDim = checking.getDimensions();

						if ((Math.abs(playerX - checkX) <= checkDim || Math
								.abs(checkX - playerX) <= playerDim)
								&& (Math.abs(playerY - checkY) < checkDim || Math
										.abs(checkY - playerY) < playerDim))
							return true;
					}
				}
			}
		}

		// If no collision occurred for the character
		return false;
	}

	/**
	 * Checks to see if any collisions with enemies have occurred
	 */
	private synchronized void isEnemyCollision()
	{
		// Loops through enemy ArrayList, determines if they were hit with any
		// player projectiles
		synchronized (enemies)
		{
			for (int enemy = 0; enemy < enemies.size(); enemy++)
			{
				if (enemy > -1)
				{
					Enemy checking = enemies.get(enemy);
					synchronized (checking)
					{
						double checkX = checking.getLocation().getX();
						double checkY = checking.getLocation().getY();

						synchronized (playerPro)
						{
							for (int playerPros = 0; playerPros < playerPro
									.size(); playerPros++)
							{
								Projectile charPro = playerPro
										.get(playerPros);
								if (charPro != null)
								{
									synchronized (charPro)
									{
										double proX = charPro.getLocation()
												.getX();
										double proY = charPro.getLocation()
												.getY();

										// If the projectile is very far away,
										// do
										// not
										// bother checking it
										if (Math.abs(checkX - proX) < 20
												&& Math.abs(checkY - proY) < 20)
										{
											int checkDim = checking
													.getDimensions();
											int proDim = charPro
													.getDimensions();

											// If collision occurred, get rid of
											// the
											// enemy
											if ((checkX - proX < proDim || proX
													- checkX < checkDim)
													&& (checkY - proY < proDim || proY
															- checkY < checkDim))
											{
												checking.hit();
												if (checking.getHealth() <= 0)
												{
													enemies.remove(checking);
													checking.destroyed();
													enemy--;
													score += 100;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Moves the character(1 = up, 2 = down, 3 = left, 4 = right) Accessed only
	 * by the GUI, field class does not use this method
	 * @param direction the direction to move the character in
	 */
	public static synchronized void moveChar(int direction, int direction2,
			boolean firing)
	{
		if (System.currentTimeMillis() - timeElapsed > 30)
		{
			if (direction == 1 && playerLoc.getY() > 10)
				playerLoc.translate(0, -playerMove);
			if (direction == 2 && playerLoc.getY() < 740)
				playerLoc.translate(0, playerMove);
			if (direction == 3 && playerLoc.getX() > 10)
				playerLoc.translate(-playerMove, 0);
			if (direction == 4 && playerLoc.getX() < 540)
				playerLoc.translate(playerMove, 0);

			if (direction2 == 1 && playerLoc.getY() > 10)
				playerLoc.translate(0, -playerMove);
			if (direction2 == 2 && playerLoc.getY() < 740)
				playerLoc.translate(0, playerMove);
			if (direction2 == 3 && playerLoc.getX() > 10)
				playerLoc.translate(-playerMove, 0);
			if (direction2 == 4 && playerLoc.getX() < 540)
				playerLoc.translate(playerMove, 0);

			if (firing)
			{
				playerPro.add(player.firePro());
				proFired = true;
			}
			timeElapsed = System.currentTimeMillis();
		}
	}

	/**
	 * Gets the score
	 * @return score
	 */
	public int getScore()
	{
		return this.score;
	}

	/**
	 * Gets all on-screen player projectiles
	 * @return playerPro
	 */
	public ArrayList<Projectile> getCharProjectiles()
	{
		synchronized (playerPro)
		{
			return playerPro;
		}
	}

	/**
	 * Gets all on-screen enemy projectiles
	 * @return enemyPro
	 */
	public ArrayList<Projectile> getEnemyProjectiles()
	{
		return enemyPro;
	}

	/**
	 * Gets all on-screen enemies
	 * @return enemies
	 */
	public ArrayList<Enemy> getEnemies()
	{
		return enemies;
	}

	/**
	 * Gets the player
	 * @return player
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * Based on time passed, spawns enemies according to a pre-coded level
	 * script
	 * @author Iain/Gavin
	 * @version 10/1/16
	 */
	private class FieldManager implements Runnable
	{
		public void run()
		{
			Enemy thing = new ProEnemy(new ImageIcon(
					"Pictures/Enemies/50x50/enemy_1.png").getImage(), 1, 1, 0,
					0, 0, new Point(10, 9), 40, new LinearPro(new Point(
							2,
							2),
							new ImageIcon(
									"Pictures/Projectiles/Projectile_2.png")
									.getImage(), 10, 1, 5), 3000);
			synchronized (enemies)
			{
				enemies.add(thing);
				Thread manageEnemy = new Thread(new EnemyManager(thing));
				manageEnemy.start();
				enemySpawned = true;
			}

			Enemy thing2 = new MovingEnemy(new ImageIcon(
					"Pictures/Enemies/50x50/enemy_1.png").getImage(), 100, 0,
					0,
					0, 0, new Point(100, 90), 40);

			synchronized (enemies)
			{
				enemies.add(thing2);
				Thread manageEnemy = new Thread(new EnemyManager(thing2));
				manageEnemy.start();
				enemySpawned = true;
			}

			// Creates the spawning thread
			Thread spawner = new Thread(new EnemySpawner());
			spawner.start();

			// Manages enemies while ones still need to spawn
			while (!gameOver)
			{
				// Moves character projectiles
				moveCharPro();

				// Checks if any enemies were spawned
				Enemy toSpawn = EnemySpawner.getSpawned();
				if (toSpawn != null)
				{
					synchronized (enemies)
					{
						enemies.add(toSpawn);
						Thread manageEnemy = new Thread(new EnemyManager(
								toSpawn));
						manageEnemy.start();
						enemySpawned = true;
					}

				}

				// System.out.println(enemies.size());
				// System.out.println(playerPro.size());
				// System.out.println(enemyPro.size());
				try
				{
					Thread.sleep(30);
				}
				catch (InterruptedException e)
				{
					System.out.println("Error in enemy manager");
				}
			}
		}

		/**
		 * Moves the enemies and projectiles on-screen Also handles player
		 * projectiles to keep everything in this thread
		 */
		private synchronized void moveCharPro()
		{
			synchronized (playerPro)
			{
				// Loops through all player projectiles and moves them
				for (Projectile playerPro : playerPro)
				{
					synchronized (playerPro)
					{
						playerPro.movePro();
					}
				}
			}

			// Loops through things again to check if any objects have gone
			// off-screen
			synchronized (playerPro)
			{
				for (int charPro = 0; charPro < playerPro.size(); charPro++)
				{
					Point checkLoc = playerPro.get(charPro).getLocation();
					if (checkLoc.getX() > 600 || checkLoc.getX() < 0
							|| checkLoc.getY() > 800 || checkLoc.getY() < 0)
					{
						playerPro.remove(charPro);
						charPro--;
					}
				}
			}
		}
	}

	private class EnemyManager implements Runnable
	{
		private Enemy toManage;

		protected EnemyManager(Enemy toManage)
		{
			this.toManage = toManage;
		}

		public void run()
		{
			while (!toManage.isDestroyed())
			{
				synchronized (toManage)
				{
					toManage.moveEnemy();

					if (toManage.getType() == 2)
					{
						if (!toManage.proFired())
						{
							Projectile bullet = toManage.firePro();
							Thread manageBullet = new Thread(
									new ProjectileManager(this.toManage,
											bullet, toManage.getDelay()));
							manageBullet.start();
						}
					}
				}
				try
				{
					Thread.sleep(30);
				}
				catch (InterruptedException e)
				{
					System.out.println("enemy manage sleep");
				}

				// // Checks to see if the enemy has gone off-screen
				// synchronized (toManage)
				// {
				// Point currentLoc = toManage.getLocation();
				// if (currentLoc.getX() > 600 || currentLoc.getX() < 0
				// || currentLoc.getY() > 800 || currentLoc.getY() < 0)
				// {
				// synchronized (enemies)
				// {
				// enemies.remove(toManage);
				// }
				// toManage.destroyed();
				// }
				// }
			}
		}
	}

	private class ProjectileManager implements Runnable
	{
		private Enemy firedBy;
		private Projectile toManage;
		private long delay = 0;

		// protected ProjectileManager(Enemy firedBy, Projectile pro)
		// {
		// this.firedBy = firedBy;
		// this.toManage = pro;
		// }

		protected ProjectileManager(Enemy firedBy, Projectile pro, long delay)
		{
			this.firedBy = firedBy;
			this.toManage = pro;
			this.delay = delay;
		}

		public void run()
		{
			try
			{
				Thread.sleep(this.delay);
			}
			catch (InterruptedException e)
			{
				System.out.println("pro delay error");
			}

			if (!firedBy.isDestroyed())
			{

				toManage.setLocation(new Point((int) firedBy.getLocation()
						.getX(),
						(int) firedBy.getLocation().getY()));
				enemyPro.add(toManage);
				enemyProFired = true;
				while (!this.toManage.isGone())
				{
					synchronized (toManage)
					{
						toManage.movePro();
					}

					try
					{
						Thread.sleep(30);
					}
					catch (InterruptedException e)
					{
						System.out.println("Pro manage sleep");
					}

					// Checks to see if the projectile has gone off-screen
					synchronized (toManage)
					{
						Point currentLoc = toManage.getLocation();
						if (currentLoc.getX() > 600 || currentLoc.getX() < 0
								|| currentLoc.getY() > 800
								|| currentLoc.getY() < 0)
						{
							synchronized (enemyPro)
							{
								enemyPro.remove(toManage);
							}
							toManage.hit();
						}
					}
				}
			}
		}
	}

	/**
	 * Simple extra thread that just adds to score based on survival time
	 * @author Iain/Gavin
	 * @version 10/1/16
	 */
	private class SecondsScore implements Runnable
	{
		public void run()
		{
			while (!gameOver)
			{
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
					System.out.println("How could u screw up a sleep u turd");
				}
				score += 10;
			}
		}
	}

}
