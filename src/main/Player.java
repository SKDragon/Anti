package main;

import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import projectiles.*;

/**
 * Player object template
 * @author Iain/Gavin
 * @version 19/1/16
 */
public class Player
{
	// Images to be used when displaying the character and projectiles
	private final static Image PLAYER_IMAGE = new ImageIcon(
			"Pictures/Player/Player.png").getImage();
	private final static Image PLAYER_PRO = new ImageIcon(
			"Pictures/Projectiles/Projectile_1.png").getImage();

	// Left or right barrel when firing
	private int barrel = 1;

	// Movement speeds
	final static int PRO_SPEED = -30;
	final static int MOVE_SPEED = 10;

	// Location of the player
	protected Point location;

	// Dimensions of the character icon
	protected int dim = 10;

	/**
	 * Constructor just sets location at start point
	 */
	public Player()
	{
		this.location = new Point(290, 700);
	}

	/**
	 * Fires a bullet
	 * @return a projectile
	 */
	public LinearPro firePro()
	{
		barrel *= -1;
		if (barrel == 1)
			return new LinearPro(new Point((int) this.location.getX() + 5,
					(int) this.location.getY() - 5), null, 50, 0, PRO_SPEED);
		else
			return new LinearPro(new Point((int) this.location.getX() + 35,
					(int) this.location.getY() - 5), null, 50, 0, PRO_SPEED);
	}

	/**
	 * Gets the dimensions of the player
	 * @return dim
	 */
	public int getDimensions()
	{
		return this.dim;
	}

	/**
	 * Gets the image for the player
	 * @return PLAYER_IMAGE
	 */
	public Image getIcon()
	{
		return PLAYER_IMAGE;
	}

	/**
	 * Gets the location of the player
	 * @return the location of the player
	 */
	public Point getLocation()
	{
		return this.location;
	}

	/**
	 * Gets the move speed of the character
	 * @return MOVE_SPEED
	 */
	public int getMoveSpeed()
	{
		return MOVE_SPEED;
	}

	/**
	 * Gets the icon of the player
	 * @return PLAYER_PRO
	 */
	public Image getProIcon()
	{
		return PLAYER_PRO;
	}
}
