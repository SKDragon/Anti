package main;

import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import projectiles.*;

/**
 * Player object template
 * @author Iain/Gavin
 * @version 10/1/16
 */
public class Player
{
	// Image to be used when displaying the character
	private final static Image PLAYER_IMAGE = new ImageIcon(
			"Pictures/Player/Player.png").getImage();
	private final static Image PLAYER_PRO = new ImageIcon(
			"Pictures/Projectiles/Projectile_1.png").getImage();

	// Left or right barrel
	private int barrel = 1;
	final static int PRO_SPEED = -30;
	final static int MOVE_SPEED = 10;

	// Location of the player
	protected Point location;

	// Dimensions of the character icon
	protected int dim = 10;

	protected int moveSpeed;

	public Player()
	{
		this.location = new Point(290,700);
	}
	
	/**
	 * Overwritten in all character classes
	 * @return A generated standard LinearPro object
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
		return MOVE_SPEED;
	}

	public int getDimensions()
	{
		return this.dim;
	}

	public Image getIcon()
	{
		return PLAYER_IMAGE;
	}

	public Image getProIcon()
	{
		return PLAYER_PRO;
	}
}
