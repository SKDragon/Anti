package levels;

import java.awt.Point;
import java.util.ArrayList;

import enemies.*;

/**
 * Object for the first level of the game
 * @author Iain/Gavin
 * @version 11/1/16
 */
public class LevelOne extends Level
{
	/**
	 * Constructor, loads in all enemy data
	 */
	public LevelOne()
	{
		// TODO fill out enemy spawning info
		this.enemies = new ArrayList<Enemy>();
		this.enemies.add(new MovingEnemy(1, 2, 2, 1, 1, new Point(10, 9), 10, 5000));
	}
}
