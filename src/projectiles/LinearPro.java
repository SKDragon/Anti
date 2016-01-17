package projectiles;

import java.awt.Point;

import javax.swing.ImageIcon;

/**
 * Object class for a linear projectile
 * @author Iain/Gavin
 * @version 7/1/16
 */
public class LinearPro extends Projectile
{
	// Integer denoting if the speed of the projectile changes at any point in
	// time during its trajectory, also when it changes speed
	private int speedChange;
	private int timeAfter;

	/**
	 * Constructor for the linear projectile object
	 * @param location the original location of the object
	 * @param icon the icon to be used with the projectile
	 * @param dim the dimension of the hitbox
	 * @param xChange the change in x coordinates per movement
	 * @param yChange the change in y coordinates per movement
	 */
	public LinearPro(Point location, ImageIcon icon, int dim, int xChange,
			int yChange)
	{
		this.location = location;
		this.icon = icon;
		this.hitBoxDim = dim;
		this.xChange = xChange;
		this.yChange = yChange;
	}

	/**
	 * Constructor for the linear projectile object
	 * @param location the original location of the object
	 * @param icon the icon to be used with the projectile
	 * @param dim the dimension of the hitbox
	 * @param xChange the change in x coordinates per movement
	 * @param yChange the change in y coordinates per movement
	 * @param speed the additions to make the speed
	 */
	public LinearPro(Point location, ImageIcon icon, int dim, int xChange,
			int yChange, int speed, int timeAfter)
	{
		this.location = location;
		this.icon = icon;
		this.hitBoxDim = dim;
		this.xChange = xChange;
		this.yChange = yChange;
		this.speedChange = speed;
		this.timeAfter = timeAfter;
	}

	/**
	 * Gets the change in speed
	 * @return speedChange
	 */
	public int getSpeedChange()
	{
		return this.speedChange;
	}

	/**
	 * Gets the time after which the speed will change
	 * @return timeAfter
	 */
	public int getTime()
	{
		return this.timeAfter;
	}

	/**
	 * Changes the x/y changes of the projectile, making it go faster or slower
	 */
	public void changeSpeed()
	{
		this.xChange += speedChange;
		this.yChange += speedChange;
	}
}
