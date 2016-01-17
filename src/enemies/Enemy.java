package enemies;

import java.awt.Image;
import java.awt.Point;

import projectiles.*;

/**
 * Parent class for all enemies
 * @author Iain/Gavin
 * @version 12/1/16
 */
public class Enemy
{
	// Location of the enemy, movement
	protected Point location;
	protected int xChange;
	protected int yChange;
	protected int xIn;
	protected int yIn;
	protected boolean destroyed;

	// Icon image
	protected Image icon;
	protected long spawnTime;

	// Type of enemy: 1=moving, 2=onePro, 3=odd, 4=boss
	protected int enemyType;

	// Health measurements
	protected int health;

	// Dimensions of the icon
	protected int dim;

	// Projectile information
	protected Projectile fire;
	protected int timeFire;

	/**
	 * Moves the enemy
	 */
	public void moveEnemy()
	{
		this.location.translate(xChange, yChange);
		xChange += xIn;
		yChange += yIn;
	}

	public boolean firedPro()
	{
		return false;
	}

	public Projectile firePro()
	{
		return null;
	}

	public void destroyed()
	{
		this.destroyed = true;
	}

	/**
	 * Gets the location of the enemy
	 * @return location
	 */
	public Point getLocation()
	{
		return this.location;
	}

	/**
	 * Gets the dimensions of the enemy
	 * @return dim
	 */
	public int getDimensions()
	{
		return this.dim;
	}

	/**
	 * Gets the type of enemy
	 * @return enemyType
	 */
	public int getType()
	{
		return this.enemyType;
	}

	/**
	 * Gets the time to spawn the enemies
	 * @return spawnTime
	 */
	public long getSpawn()
	{
		return this.spawnTime;
	}

	public boolean isDestroyed()
	{
		return this.destroyed;
	}

	public Image getIcon()
	{
		return this.icon;
	}
}
