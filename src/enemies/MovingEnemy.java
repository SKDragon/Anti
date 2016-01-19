package enemies;

import java.awt.Image;
import java.awt.Point;

/**
 * Object for enemies that just move across the screen
 * @author Iain/Gavin
 * @version 19/1/16
 */
public class MovingEnemy extends Enemy
{
	// Type of enemy
	private static final int TYPE = 1;

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
	 */
	public MovingEnemy(Image icon, int health, int xChange, int yChange,
			int xIn, int yIn, Point location, int dim)
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
	}
}
