package enemies;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import projectiles.*;

/**
 * Clas for enemies that fire projectiles in various ways that are not normal
 * @author Iain/Gavin
 * @version 12/1/16
 */
public class OddEnemy extends Enemy
{
	// Type of enemy
	private static final int TYPE = 3;

	// Projectile ArrayList, timing
	private ArrayList<Projectile> bullets;
	private ArrayList<Projectile> fired = new ArrayList<Projectile>();
	private ArrayList<Integer> timing;

	/**
	 * Basic Constructor
	 * @param icon the image to use
	 * @param health how much health
	 * @param xChange x-trajectory
	 * @param yChange y-trajectory
	 * @param xIn used for parabolic movement
	 * @param yIn used for parabolic movement
	 * @param location location of the enemy
	 * @param dim dimensions of the enemy
	 * @param spawn time to spawn the enemy
	 */
	public OddEnemy(Image icon, int health, int xChange, int yChange,
			int xIn, int yIn, Point location, int dim,
			ArrayList<Projectile> bullets, ArrayList<Integer> timing)
	{
		// this.icon = image
		this.enemyType = TYPE;
		this.health = health;
		this.xChange = xChange;
		this.yChange = yChange;
		this.xIn = xIn;
		this.yIn = yIn;
		this.location = location;
		this.dim = dim;
		this.bullets = bullets;
		this.timing = timing;
	}

	public void checkFiring(int timeMillis)
	{
		for (int bullet = 0; bullet < bullets.size(); bullet++)
		{
			if (timeMillis >= timing.get(bullet))
				fired.add(bullets.remove(bullet));
		}
	}

	/**
	 * These enemies and bosses are the only ones to manage their own bullets
	 * due to the irregular behaviour
	 */
	public void manageBullets()
	{
		for (Projectile moving : fired)
		{
			moving.getLocation().translate(moving.getXChange(),
					moving.getYChange());
		}
	}

	/**
	 * Gets all the projectiles on screen
	 * @return fired
	 */
	public ArrayList<Projectile> getFired()
	{
		ArrayList<Projectile> tempAdd = this.fired;
		this.fired = new ArrayList<Projectile>();
		return tempAdd;
	}
}