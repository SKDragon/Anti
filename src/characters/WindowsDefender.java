package characters;

import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import projectiles.LinearPro;

/**
 * Windows Defender character, regular fire patterns, average speed
 * @author Iain/Gavin
 * @version 10/1/16
 */
public class WindowsDefender extends Player
{
	// Character image for WinDef
	private final static Image WIN_DEF = new ImageIcon(
			"Pictures/Player/Player.png").getImage();
	private final static Image WIN_DEF_PRO = new ImageIcon(
			"Pictures/Projectiles/Projectile_1.png").getImage();

	// Projectile Speed
	final static int PRO_SPEED = -30;
	final static int MOVE_SPEED = 10;

	/**
	 * Constructor for WindowsDefender character
	 */
	public WindowsDefender()
	{
		this.location = new Point(290, 700);
		this.icon = WIN_DEF;
		this.proIcon = WIN_DEF_PRO;
		this.dim = 50;
		this.moveSpeed = MOVE_SPEED;
	}

	/**
	 * Creates a projectile at the location of the player
	 * @return returns the new projectile
	 */
	public LinearPro firePro()
	{

		return new LinearPro(new Point((int) this.location.getX() + 5,
				(int) this.location.getY() - 10), null, 50, 0, PRO_SPEED);
	}
}
