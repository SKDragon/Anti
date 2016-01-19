package projectiles;

import java.awt.Image;
import java.awt.Point;

/**
 * Class for projectiles with a curved path
 * @author Iain/Gavin
 * @version 19/1/16
 */
public class CurvedPro extends Projectile
{
	// Variables that add/decrease initial x/y changes, creating a curved
	// trajectory
	private int xIn;
	private int yIn;

	/**
	 * Constructor for curved projectiles
	 * @param location the original location of the projectile
	 * @param icon the icon to be used for the projectile
	 * @param dim the dimensions of the projectile
	 * @param xChange the change in x
	 * @param yChange the change in y
	 * @param xIn the increase in the change of x
	 * @param yIn the increase in the change of y
	 */
	public CurvedPro(Point location, Image icon, int dim, int xChange,
			int yChange, int xIn, int yIn)
	{
		this.location = location;
		this.icon = icon;
		this.hitBoxDim = dim;
		this.xChange = xChange;
		this.yChange = yChange;
		this.xIn = xIn;
		this.yIn = yIn;
	}

	/**
	 * Moves projectile while also curving path
	 */
	public void movePro()
	{
		this.location.translate(xChange, yChange);
		this.xChange += xIn;
		this.yChange += yIn;
	}

	/**
	 * Gets the increase in xChange
	 * @return xIn
	 */
	public int getXIn()
	{
		return this.xIn;
	}

	/**
	 * Gets the increase in yChange
	 * @return yIn
	 */
	public int getYIn()
	{
		return this.yIn;
	}
}
