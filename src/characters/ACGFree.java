package characters;

import java.awt.Point;

import projectiles.LinearPro;

/**
 * ACGFree character class, projectiles start slow then speed up, higher speed
 * @author Iain/Gavin
 * @version 10/1/16
 */
public class ACGFree extends Player
{
	// Character image for ACG
	// final static Image ACG_FREE null;

	// Projectile speed
	final static int PRO_SPEED = 10;
	final static int PRO_SPEED2 = 50;
	final static int TIME_CHANGE = 100;

	// Movement speed
	final static int MOVE_SPEED = 20;

	/**
	 * Constructor for ACGFree character
	 */
	public ACGFree()
	{
		this.location = new Point(140, 365);
		// this.image = ACG_FREE;
		this.dim = 20;
		this.moveSpeed = MOVE_SPEED;
	}

	/**
	 * Creates a projectile at the location of the player
	 */
	public LinearPro firePro()
	{
		return new LinearPro(this.location, null, 10, 0, PRO_SPEED, PRO_SPEED2,
				TIME_CHANGE);
	}

	/**
	 * Bombs
	 */
	public void bomb()
	{
		// TODO bomb behaviour
	}
}
