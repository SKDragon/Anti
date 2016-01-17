package enemies;

import java.awt.Image;
import java.awt.Point;

import projectiles.*;

/**
 * Class for enemies that shoot one projectile
 * @author Iain/Gavin
 * @version 12/1/16
 *
 */
public class ProEnemy extends Enemy
{
	// Type of enemy
	private static final int TYPE = 2;
	private long spawnAt;
	private boolean fired = false;

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
	 * @param spawn the time to spawn the enemy
	 * @param proj the projectile to fire
	 * @param timing time after spawn to fire projectile
	 */
	public ProEnemy(Image icon, int health, int xChange, int yChange, int xIn,
			int yIn, Point location, int dim, long spawn, Projectile proj,
			long timing)
	{
		this.icon = icon;
		this.enemyType = TYPE;
		this.health = health;
		this.xChange = xChange;
		this.yChange = yChange;
		this.xIn = xIn;
		this.yIn = yIn;
		this.location = location;
		this.dim = dim;
		this.spawnTime = spawn;
		this.fire = proj;
		this.timeFire = timing;
		this.spawnAt = System.currentTimeMillis();
	}

	public Projectile firePro()
	{
		if (timeFire >= System.currentTimeMillis() - this.spawnAt)
		{
			this.fired = true;
			System.out.println("fired");
			return this.fire;
		}
		return null;
	}
	
	public boolean firedPro()
	{
		return this.fired;
	}
}
