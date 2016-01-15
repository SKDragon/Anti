package characters;

import java.awt.Image;
import java.awt.Point;

import projectiles.*;

/**
 * Player object template
 * @author Iain/Gavin
 * @version 10/1/16
 */
public class Player
{
	// Image to be used when displaying the character
	protected Image icon;

	// Location of the player
	protected Point location;

	// Dimensions of the character icon
	protected int dim;

	protected int moveSpeed;

	/**
	 * Overwritten in all character classes
	 * @return A generated standard LinearPro object
	 */
	public LinearPro firePro()
	{
		return null;
	}

	/**
	 * Gets the location of the player
	 * @return the location of the player
	 */
	public Point getLoc()
	{
		return this.location;
	}

	/**
	 * Gets the move speed of the character
	 * @return moveSpeed
	 */
	public int getMoveSpeed()
	{
		return this.moveSpeed;
	}

	public int getDimensions()
	{
		return this.dim;
	}

	public Image getIcon()
	{
		return this.icon;
	}
	
	public void resetLoc()
	{
		this.location = new Point(140, 365);
	}
}
