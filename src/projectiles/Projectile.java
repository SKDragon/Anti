package projectiles;

import java.awt.Image;
import java.awt.Point;

/**
 * Parent class for all Projectile objects
 * @author Iain/Gavin
 * @version 7/1/16
 */
public class Projectile
{
	// The coordinates of the projectile
	// Point is public to save a get/set method, faster processing
	protected Point location;
	protected boolean gone;

	// Changes in position to be used in calculating position changes
	protected int xChange;
	protected int yChange;

	// Image representing the projectile
	protected Image icon;
	protected int proType;

	// Integer representing the hit box dimensions
	protected int hitBoxDim;

	/**
	 * Overwritten in curved pro class
	 */
	public void movePro()
	{
		this.location.translate(xChange, yChange);
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
	 * Gets the dimensions
	 * @return {hitBoxX, hitBoxY}
	 */
	public int getDimensions()
	{
		return this.hitBoxDim;
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
	 * Gets the location of the projectile
	 * @return location
	 */
	public Point getLocation()
	{
		return this.location;
	}

	public void setLocation(Point newLoc)
	{
		this.location = newLoc;
	}

	public int getType()
	{
		return this.proType;
	}

	public void hit()
	{
		this.gone = true;
	}

	public boolean isGone()
	{
		return this.gone;
	}
}
