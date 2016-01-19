package enemies;

import java.awt.Image;
import java.awt.Point;

import projectiles.*;

/**
 * Parent class for all enemies
 * @author Iain/Gavin
 * @version 19/1/16
 */
public class Enemy
{
	// Location of the enemy, movement variables
	protected Point location;
	protected int xChange;
	protected int yChange;
	protected int xIn;
	protected int yIn;

	// If the enemy has been destroyed or not
	protected boolean destroyed;

	// Icon image
	protected Image icon;

	// Type of enemy: 1=moving, 2=pro
	protected int enemyType;

	// Health measurements
	protected int health;

	// Dimensions of the icon
	protected int dim;

	// Projectile information
	protected Projectile fire;
	protected long fireDelay = 0;

	/**
	 * Moves the enemy based on movement variables
	 */
	public void moveEnemy()
	{
		this.location.translate(xChange, yChange);
		xChange += xIn;
		yChange += yIn;
	}

	/**
	 * Overwritten in ProEnemy class
	 * @return no projectile fired
	 */
	public boolean firedPro()
	{
		return false;
	}

	/**
	 * Overwritten in ProEnemy class
	 * @return no projectile to be fired
	 */
	public Projectile firePro()
	{
		return null;
	}

	/**
	 * Enemy was hit by a player bullet
	 */
	public void hit()
	{
		this.health -= 10;
	}

	/**
	 * The enemy has been destroyed
	 */
	public void destroyed()
	{
		this.destroyed = true;
	}

	/**
	 * Checks if the enemy has been destroyed
	 * @return destroyed
	 */
	public boolean isDestroyed()
	{
		return this.destroyed;
	}

	/**
	 * Overwritten in ProEnemy class
	 * @return true
	 */
	public boolean proFired()
	{
		return true;
	}

	/**
	 * Overwritten in ProEnemy class
	 * @return fireDelay
	 */
	public long getDelay()
	{
		return this.fireDelay;
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
	 * Gets the health of the enemy
	 * @return health
	 */
	public int getHealth()
	{
		return this.health;
	}

	/**
	 * Gets the image for the enemy
	 * @return icon
	 */
	public Image getIcon()
	{
		return this.icon;
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
	 * Gets the type of enemy
	 * @return enemyType
	 */
	public int getType()
	{
		return this.enemyType;
	}

}
