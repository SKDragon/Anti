package projectiles;

import java.awt.Image;
import java.awt.Point;

/**
 * Parent class for all Projectile objects
 * @author Iain/Gavin
 * @version 19/1/16
 */
public class Projectile
{
	// The coordinates of the projectile
	protected Point location;

	// If the projectile has moved off-screen
	protected boolean gone;

	// Movement variables
	protected int xChange;
	protected int yChange;

	// Image representing the projectile
	protected Image icon;

	// Type of projectile
	protected int proType;

	// Integer representing the hit box dimensions
	protected int hitBoxDim;

	/**
	 * Moves the projectile Overwritten in curved pro class
	 */
	public void movePro()
	{
		this.location.translate(xChange, yChange);
	}

	/**
	 * The projectile has left the screen
	 */
	public void hit()
	{
		this.gone = true;
	}

	/**
	 * Checks if the projectile is gone or not
	 * @return gone
	 */
	public boolean isGone()
	{
		return this.gone;
	}

	/**
	 * Gets the dimensions
	 * @return {hitBoxX, hitBoxY}
	 */
	public int getDimensions()
	{
		return this.hitBoxDim;
	}

	/**
	 * Gets the image for the projectile
	 * @return image
	 */
	public Image getImage()
	{
		return this.icon;
	}

	/**
	 * Gets the location of the projectile
	 * @return location
	 */
	public Point getLocation()
	{
		return this.location;
	}

	/**
	 * Gets the type of the bullet
	 * @return proType
	 */
	public int getType()
	{
		return this.proType;
	}

	/**
	 * Gets the change in the x parameter
	 * @return xChange
	 */
	public int getXChange()
	{
		return this.xChange;
	}

	/**
	 * Gets the change in the y parameter
	 * @return yChange
	 */
	public int getYChange()
	{
		return this.yChange;
	}

	/**
	 * Sets the location
	 * @param newLoc the new location
	 */
	public void setLocation(Point newLoc)
	{
		this.location = newLoc;
	}
}
