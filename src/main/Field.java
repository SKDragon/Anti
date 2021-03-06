package main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import projectiles.*;
import enemies.*;

/**
 * Field class containing everything on the game screen
 * @author Iain/Gavin
 * @version 19/1/16
 */
public class Field
{
	// If the game is still occurring or not
	// If the game is over, will stop all threads
	// If the player has fired a projectile
	// If an enemy has been spawned
	// If an enemy has fired a projectile
	private boolean playerAlive = true;
	public static boolean gameOver = false;
	public static boolean proFired = false;
	public static boolean enemySpawned = false;
	public static boolean enemyProFired = false;

	// Player object information,some are preset to avoid errors
	private static Player player;
	public static Point playerLoc = new Point(0, 0);
	private static int playerMove;
	public static int playerDim = 0;

	// Player score
	private int score = 0;

	// Time variables for moving and firing
	private static long moveTimeElapsed = System.currentTimeMillis();
	private static long fireTimeElapsed = System.currentTimeMillis();

	// All player projectiles
	private static ArrayList<Projectile> playerPro = new ArrayList<Projectile>();

	// ArrayList of all enemy projectiles, enemies
	private static ArrayList<Projectile> enemyPro = new ArrayList<Projectile>();
	private static ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	/**
	 * Constructor, creates player object and makes quick references to them
	 */
	public Field()
	{
		// Creates the player according to the player's choice of character
		player = new Player();

		// Creates references to the player variables
		playerLoc = player.getLocation();
		playerMove = player.getMoveSpeed();
		playerDim = player.getDimensions();
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

		// Loops while the player is still alive
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
	}

	/**
	 * Goes through linked lists of projectiles and enemies to determine if the
	 * player has come into contact with any of them
	 * @return if the player has been hit
	 */
	private boolean isCharCollision()
	{
		// References to character location
		double playerX = playerLoc.getX();
		double playerY = playerLoc.getY();

		synchronized (enemies)
		{
			// Loops through enemies to check for collisions
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

						// Check for collisions using location and dimensions
						if ((Math.abs(playerX - checkX) <= checkDim
								|| Math.abs(checkX - playerX) <= playerDim)
								&& (Math.abs(playerY - checkY) <= checkDim
								|| Math.abs(checkY - playerY) <= playerDim))
							return true;
					}
				}
			}
		}

		synchronized (enemyPro)
		{
			// Loops through enemy projectiles to see if any collisions have
			// occurred
			for (Projectile checking : enemyPro)
			{
				synchronized (checking)
				{
					// References to X and Y to use in reducing projectiles to
					// check
					double checkX = checking.getLocation().getX();
					double checkY = checking.getLocation().getY();

					// If projectile is too far away to even be close to
					// hitting,ignore it
					if (Math.abs(playerY - checkY) < 70
							&& Math.abs(playerX - checkX) < 70)
					{
						// Creating references to dimensions of the player and
						// projectile for collision check
						int playerDim = player.getDimensions();
						int checkDim = checking.getDimensions();

						// Checks for player collisions with overlapping
						// hitboxes
						if ((Math.abs(playerX - checkX) <= checkDim || Math
								.abs(checkX - playerX) <= playerDim)
								&& (Math.abs(playerY - checkY) <= checkDim || Math
										.abs(checkY - playerY) <= playerDim))
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
	private void isEnemyCollision()
	{
		synchronized (enemies)
		{
			// Loops through enemy ArrayList, determines if they were hit with
			// any player projectiles
			for (int enemy = 0; enemy < enemies.size(); enemy++)
			{
				// To fix any possible index out of bounds errors
				if (enemy > -1)
				{
					Enemy checking = enemies.get(enemy);
					synchronized (checking)
					{
						// Makes references to the enemy location
						double checkX = checking.getLocation().getX();
						double checkY = checking.getLocation().getY();

						synchronized (playerPro)
						{
							// Loops through all player projectiles
							for (int playerPros = 0; playerPros < playerPro
									.size(); playerPros++)
							{
								Projectile charPro = playerPro.get(playerPros);

								// Makes sure the reference is not null
								if (charPro != null)
								{
									synchronized (charPro)
									{
										double proX = charPro.getLocation()
												.getX();
										double proY = charPro.getLocation()
												.getY();

										// If the projectile is very far away,
										// do not bother checking it
										if (Math.abs(checkX - proX) < 30
												&& Math.abs(checkY - proY) < 30)
										{
											int checkDim = checking
													.getDimensions();
											int proDim = charPro
													.getDimensions();

											// If collision occurred, get rid of
											// the enemy
											if ((Math.abs(checkX - proX) < proDim || Math
													.abs(proX - checkX) < checkDim)
													&& (Math.abs(checkY - proY) < proDim || Math
															.abs(proY - checkY) < checkDim))
											{
												checking.hit();

												// Checks to see if the enemy is
												// dead
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
	 * @param direction2 the secondary direction to move in
	 * @param firing if the player is firing
	 */
	public static synchronized void moveChar(int direction, int direction2,
			boolean firing)
	{
		// Checks for movement delay first
		if (System.currentTimeMillis() - moveTimeElapsed > 30)
		{
			// Moves player according to buttons pressed
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

			// Resets movement delay
			moveTimeElapsed = System.currentTimeMillis();
		}

		// If the player is firing and the firing delay has passed
		if (firing && System.currentTimeMillis() - fireTimeElapsed > 60)
		{
			playerPro.add(player.firePro());
			proFired = true;
			fireTimeElapsed = System.currentTimeMillis();
		}
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
	 * Gets all on-screen enemies
	 * @return enemies
	 */
	public ArrayList<Enemy> getEnemies()
	{
		return enemies;
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
	 * Gets the player
	 * @return player
	 */
	public Player getPlayer()
	{
		return player;
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
	 * Creates the enemies and manages player projectiles
	 * @author Iain/Gavin
	 * @version 19/1/16
	 */
	private class FieldManager implements Runnable
	{
		/**
		 * Runs the field manager
		 */
		public void run()
		{
			// Creates the spawning thread
			Thread spawner = new Thread(new EnemySpawner());
			spawner.start();

			// Manages enemies while ones still need to spawn
			while (!gameOver)
			{
				// Moves character projectiles
				moveCharPro();

				// Checks if any enemies were spawned, then adds them
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

				try
				{
					Thread.sleep(30);
				}
				catch (InterruptedException e)
				{
					System.out.println("Error in field manager sleep");
				}
			}
		}

		/**
		 * Moves the enemies and projectiles on-screen Also handles player
		 * projectiles to keep everything in this thread
		 */
		private void moveCharPro()
		{
			synchronized (playerPro)
			{
				// Loops through all player projectiles and moves them
				try
				{
					for (Projectile playerPro : playerPro)
					{
						synchronized (playerPro)
						{
							playerPro.movePro();
						}
					}
				}
				catch (ConcurrentModificationException e)
				{
					System.out.println("charpro comod error");
				}
			}

			synchronized (playerPro)
			{
				// Loops through things again to check if any objects have gone
				// off-screen
				for (int charPro = 0; charPro < playerPro.size(); charPro++)
				{
					// For error checking
					if (charPro > -1)
					{
						Point checkLoc = playerPro.get(charPro).getLocation();

						// Checks if the projectile has gone offscreen
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
	}

	/**
	 * Manages a given enemy
	 * @author Iain/Gavin
	 * @version 19/1/16
	 */
	private class EnemyManager implements Runnable
	{
		// The enmy the class will manage
		private Enemy toManage;

		/**
		 * Constructor only needs the enemy
		 * @param toManage the enemy to manage
		 */
		protected EnemyManager(Enemy toManage)
		{
			this.toManage = toManage;
		}

		/**
		 * Runs the enemy manager
		 */
		public void run()
		{
			// Loops while the enemy is alive
			while (!toManage.isDestroyed())
			{
				synchronized (toManage)
				{
					toManage.moveEnemy();

					// If the enemy is a ProEnemy and has not fired its
					// projectile, then fire it
					if (toManage.getType() == 2 && !toManage.proFired())
					{
						Projectile bullet = toManage.firePro();
						Thread manageBullet = new Thread(
								new ProjectileManager(this.toManage,
										bullet, toManage.getDelay()));
						manageBullet.start();
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

				// Checks to see if the enemy has gone off-screen
				synchronized (toManage)
				{
					Point currentLoc = toManage.getLocation();
					if (currentLoc.getX() > 600 || currentLoc.getX() < -50
							|| currentLoc.getY() > 800 || currentLoc.getY() < 0)
					{
						// If the enemy has gone off-screen, get rid of it
						synchronized (enemies)
						{
							enemies.remove(toManage);
						}
						toManage.destroyed();
					}
				}
			}
		}
	}

	/**
	 * Manages a given projectile
	 * @author Iain/Gavin
	 * @version 19/1/16
	 */
	private class ProjectileManager implements Runnable
	{
		// Enemy that fired the projectile
		private Enemy firedBy;

		// Projectile to manage
		private Projectile toManage;

		// Delay before firing
		private long delay = 0;

		/**
		 * Constructor
		 * @param firedBy enemy that fired the projectile
		 * @param pro the projectile to track
		 * @param delay the delay before spawning
		 */
		protected ProjectileManager(Enemy firedBy, Projectile pro, long delay)
		{
			this.firedBy = firedBy;
			this.toManage = pro;
			this.delay = delay;
		}

		/**
		 * Runs the projectile manager
		 */
		public void run()
		{
			// Delays until needs to be fired
			try
			{
				Thread.sleep(this.delay);
			}
			catch (InterruptedException e)
			{
				System.out.println("pro delay error");
			}

			// Only fires if the enemy has not been destroyed
			if (!firedBy.isDestroyed())
			{
				toManage.setLocation(new Point((int) firedBy.getLocation()
						.getX(), (int) firedBy.getLocation().getY()));
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
							// If the projectile has gone off-screen get rid of
							// it
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
	 * @version 19/1/16
	 */
	private class SecondsScore implements Runnable
	{
		/**
		 * Runs the score timer
		 */
		public void run()
		{
			// Loops while the game is still going
			while (!gameOver)
			{
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
					System.out.println("sleep score error");
				}
				score += 10;
			}
		}
	}
}