package enemies;

/**
 * Parent class for bosses
 * @author Iain/Gavin
 * @version 8/1/16
 */
public class Boss extends Enemy
{
	// Amount of stages to the boss fight
	protected int noOfStages;
	
	// Movement speed
	protected int moveSpeed;
	
	/**
	 * Overwritten for every boss class
	 */
	public void move()
	{	
	}
}
