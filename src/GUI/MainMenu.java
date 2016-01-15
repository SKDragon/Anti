package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.Border;

import main.Field;
import projectiles.Projectile;

/**
 * MainMenu class
 * 
 * @author Gavin L
 * @version January 11, 2016
 */
public class MainMenu extends JPanel implements MouseListener, KeyListener
{
	private Image mainMenuBG, gameScreenBG;
	Image player;
	private Border raisedBevel, loweredBevel, compound, blackline;
	private GridBagConstraints GB = new GridBagConstraints();
	private int gameBG_X1 = 0;
	private int gameBG_Y1 = -200;
	private int gameBG_X2 = 0;
	private int gameBG_Y2 = -1000;
	private int gameBG_move = 10;

	private boolean UP_Pressed = false;
	private boolean LEFT_Pressed = false;
	private boolean DOWN_Pressed = false;
	private boolean RIGHT_Pressed = false;
	private boolean SHOOT_Pressed = false;
	private boolean BOMB_Pressed = false;
	private static Field field;

	// Stuff
	ArrayList<Projectile> charProjectiles;

	// private long moveTimeDelay = System.currentTimeMillis();
	// private long fireTimeDelay = System.currentTimeMillis();
	// private long lastTime;
	// private long currentTime;

	static boolean running = false;
	// int track = 0;

	// int x = 300;
	// int y = 400;

	Thread gameRepaint = new Thread(new repaintThread());
	private boolean repaintThreadState = false;

	protected enum STATE {
		MAIN_MENU, GAME, INSTRUCTIONS, EXIT
	};

	protected STATE State = STATE.MAIN_MENU;

	MainMenu()
	{
		super(new GridBagLayout());
		setPreferredSize(new Dimension(1000, 800));
		setFocusable(true);
		addMouseListener(this);
		addKeyListener(this);
		borderLoad();
		loadImages();
	}

	public void repaint()
	{
		super.repaint();
	}

	public void addNotify()
	{
		super.addNotify();
		requestFocus();
	}

	// public void gameLoop()
	// {
	// long lastLoopTime = System.nanoTime();
	// final int TARGET_FPS = 60;
	// final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	// long lastFpsTime = 0;
	// int fps = 0;
	//
	// // keep looping round til the game ends
	// while (running)
	// {
	// // work out how long its been since the last update, this
	// // will be used to calculate how far the entities should
	// // move this loop
	// long now = System.nanoTime();
	// long updateLength = now - lastLoopTime;
	// lastLoopTime = now;
	// double delta = updateLength / ((double) OPTIMAL_TIME);
	//
	// // update the frame counter
	// lastFpsTime += updateLength;
	// fps++;
	//
	// // update our FPS counter if a second has passed since
	// // we last recorded
	// if (lastFpsTime >= 1000000000)
	// {
	// System.out.println("(FPS: " + fps + ")");
	// lastFpsTime = 0;
	// fps = 0;
	// }
	//
	// // update the game logic
	// update();
	//
	// // draw everyting
	// repaint();
	// // renderGameScreen();
	//
	// // we want each frame to take 10 milliseconds, to do this
	// // we've recorded when we started the frame. We add 10 milliseconds
	// // to this and then factor in the current time to give
	// // us our final value to wait for
	// // remember this is in ms, whereas our lastLoopTime etc. vars are in
	// // ns.
	// try
	// {
	// Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME)
	// / 1000000);
	// }
	// catch (InterruptedException ex)
	// {
	//
	// }
	//
	// }
	//
	// }
	//
	// public void loop()
	// {
	// long lastLoop = System.currentTimeMillis();
	// while (true)
	// {
	// long timeNow = System.currentTimeMillis();
	// // if (System.currentTimeMillis() - timeNow >= 1000) {
	// update();
	// repaint();
	// timeNow = System.currentTimeMillis();
	// // }
	// }
	// }

	private synchronized void update()
	{
		// Background
		gameBG_Y1 += gameBG_move;
		if (gameBG_Y1 >= -1200)
		{
			gameBG_Y2 += gameBG_move;
		}
		if (gameBG_Y2 == -200)
		{
			gameBG_Y1 = -1200;
		}
		else if (gameBG_Y1 == -200)
		{
			gameBG_Y2 = -1200;
		}
		//
		// Fixed It without using time :P
		//
		//
		// //////////////////////////////////////////////////////////////////////////
		// 8 Cardinal Directions with SHOOT
		// ONLY UP
		if (UP_Pressed && !DOWN_Pressed && !LEFT_Pressed && !RIGHT_Pressed)
		{
			// Adds SHOOT
			if (SHOOT_Pressed)
			{
				// System.out.println("UP and SHOOT");
				Field.moveChar(0, 1, true);
			}
			else
			{
				// System.out.println("UP");
				Field.moveChar(0, 1, false);
			}

		}
		// UP and RIGHT
		if (UP_Pressed && !DOWN_Pressed && !LEFT_Pressed && RIGHT_Pressed)
		{
			// Adds SHOOT
			if (SHOOT_Pressed)
			{
				// System.out.println("UP and RIGHT and SHOOT");
				Field.moveChar(1, 4, true);
			}
			else
			{
				// System.out.println("UP and RIGHT");
				Field.moveChar(1, 4, false);

			}
		}
		// ONLY RIGHT
		if (RIGHT_Pressed && !DOWN_Pressed && !UP_Pressed && !LEFT_Pressed
				&& !BOMB_Pressed)
		{
			// Adds SHOOT
			if (SHOOT_Pressed)
			{
				// System.out.println("RIGHT and SHOOT");
				Field.moveChar(0, 4, true);
			}
			else
			{
				// System.out.println("RIGHT");
				Field.moveChar(0, 4, false);
			}
		}
		// DOWN and RIGHT
		if (DOWN_Pressed && !LEFT_Pressed && !UP_Pressed && RIGHT_Pressed)
		{

			// Adds SHOOT
			if (SHOOT_Pressed)
			{
				// System.out.println("DOWN and RIGHT and SHOOT");
				Field.moveChar(2, 4, true);
			}
			else
			{
				Field.moveChar(2, 4, false);
				// System.out.println("DOWN and RIGHT");

			}
		}
		// ONLY DOWN
		if (DOWN_Pressed && !LEFT_Pressed && !UP_Pressed && !RIGHT_Pressed)
		{

			// Adds SHOOT
			if (SHOOT_Pressed)
			{
				// System.out.println("DOWN and SHOOT");
				Field.moveChar(2, 0, true);
			}
			else
			{
				// System.out.println("DOWN");
				Field.moveChar(2, 0, false);

			}
		}
		// DOWN and LEFT
		if (DOWN_Pressed && LEFT_Pressed && !UP_Pressed && !RIGHT_Pressed)
		{

			// Adds SHOOT
			if (SHOOT_Pressed)
			{
				// System.out.println("DOWN and LEFT and SHOOT");
				Field.moveChar(2, 3, true);
			}
			else
			{
				// System.out.println("DOWN and LEFT");
				Field.moveChar(2, 3, false);

			}
		}
		// ONLY LEFT
		if (LEFT_Pressed && !DOWN_Pressed && !UP_Pressed && !RIGHT_Pressed)
		{
			// Adds SHOOT
			if (SHOOT_Pressed)
			{
				// System.out.println("LEFT and SHOOT");
				Field.moveChar(0, 3, true);
			}
			else
			{
				// System.out.println("LEFT");
				Field.moveChar(0, 3, false);
			}
		}
		// UP and LEFT
		if (UP_Pressed && !DOWN_Pressed && LEFT_Pressed && !RIGHT_Pressed)
		{
			// Adds SHOOT
			if (SHOOT_Pressed)
			{
				// System.out.println("UP and LEFT and SHOOT");
				Field.moveChar(1, 3, true);
			}
			else
			{
				// System.out.println("UP and LEFT");
				Field.moveChar(1, 3, false);
			}
		}
		//
		// //////////////////////////////////////////////////////////////////////////
		// Just SHOOT or BOMB
		if (!UP_Pressed && SHOOT_Pressed && !DOWN_Pressed && !LEFT_Pressed
				&& !RIGHT_Pressed)
		{
			// .println("SHOOT");
			Field.moveChar(0, 0, true);
		}

		// String th = field.toString();
		// System.out.println(th);
		// System.out.println(field);

		// Arrays
		if (field.started)
			charProjectiles = field.getCharProjectiles();
	}

	void borderLoad()
	{
		blackline = BorderFactory.createLineBorder(Color.black);
		raisedBevel = BorderFactory.createRaisedBevelBorder();
		loweredBevel = BorderFactory.createLoweredBevelBorder();
		compound = BorderFactory.createCompoundBorder(raisedBevel,
				loweredBevel);
	}

	void loadImages()
	{
		mainMenuBG = new ImageIcon("Pictures/Menu Backgrounds/MainMenuBG.png")
				.getImage();
		gameScreenBG = new ImageIcon(
				"Pictures/Game Backgrounds/GameScreenBG.png").getImage();
	}

	// MainMenu Render
	public void renderMainMenu(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(mainMenuBG, 0, 0, this);
	}

	// GameScreen Render
	public void renderGameScreen(Graphics g)
	{
		super.paintComponent(g);
		setSize(600, 800);
		setBorder(compound);
		addKeyListener(this);
		g.drawImage(gameScreenBG, gameBG_X1, gameBG_Y1, this);
		if ((gameBG_Y1 >= -1200 && gameBG_Y1 <= 800))
		{
			g.drawImage(gameScreenBG, gameBG_X2, gameBG_Y2, this);
		}

		// PLayer
		Point playerLoc = field.playerLoc;
		int dim = field.playerDim;
		g.setColor(Color.PINK);
		g.drawRect((int) playerLoc.getX(), (int) playerLoc.getY(), dim, dim);

		// testing
		// g.fillRect(x, y, 100, 100);

		// Paint arrays

		if (charProjectiles != null && charProjectiles.size() > 0)
		{
			// System.out.println("main " + charProjectiles.size());
			for (Projectile charPro : charProjectiles)
			{
				// Image p = charProjectiles.get(i).getImage();
				Point pp = charPro.getLocation();
				int x = (int) pp.getX();
				int y = (int) pp.getY();
				// g.drawImage(p, x, y, null);
				// System.out.println(charPro.getLocation().getY());
				g.fillRect(x, y, 10, 10);
			}
		}
	}

	// Main Graphics Render
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		if (State == STATE.MAIN_MENU)
		{
			renderMainMenu(g);
		}
		else if (State == STATE.GAME)
		{
			if (!repaintThreadState)
			{

				Thread fieldManage = new Thread(new FieldManager());
				fieldManage.start();
				gameRepaint.start();
				repaintThreadState = true;
				running = true;
				// gameLoop();
			}
			// gameLoop();
			renderGameScreen(g);
		}
		// repaint();
	}

	// Mouse Events
	public void mouseClicked(MouseEvent event)
	{
		int mx = event.getX();
		int my = event.getY();

		// Play
		if (mx >= 40 && mx <= 329 && my >= 385 && my <= 426)
		{
			State = STATE.GAME;
			repaint();
		}
		// Instructions
		else if (mx >= 32 && mx <= 598 && my >= 473 && my <= 560)
		{

		}
		// Highscores
		else if (mx >= 35 && mx <= 532 && my >= 604 && my <= 644)
		{

		}

	}

	public void mouseEntered(MouseEvent event)
	{
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent event)
	{
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent event)
	{
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent event)
	{
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent event)
	{
		// Up = 38
		// Left = 37
		// Down = 40
		// Right = 39
		// Z = 90
		// X = 88

		int key = event.getKeyCode();

		// UP
		if (key == 38)
		{
			UP_Pressed = true;
		}
		// LEFT
		if (key == 37)
		{
			LEFT_Pressed = true;
		}
		// DOWN
		if (key == 40)
		{
			DOWN_Pressed = true;
		}
		// RIGHT
		if (key == 39)
		{
			RIGHT_Pressed = true;
		}
		// SHOOT
		if (key == 90)
		{
			SHOOT_Pressed = true;
		}

		// }
		//
		// //////////////////////////////////////////////////////////////////////////
		// repaint();
	}

	@Override
	public void keyReleased(KeyEvent event)
	{
		int key = event.getKeyCode();

		// Up = 38
		// Left = 37
		// Down = 40
		// Right = 39
		// Z = 90
		// X = 88

		// UP
		if (key == 38)
		{
			UP_Pressed = false;
		}
		// SHOOT
		if (key == 90)
		{
			SHOOT_Pressed = false;
		}
		// LEFT
		if (key == 37)
		{
			LEFT_Pressed = false;
		}
		// DOWN
		if (key == 40)
		{
			DOWN_Pressed = false;
		}
		// RIGHT
		if (key == 39)
		{
			RIGHT_Pressed = false;
		}

		// repaint();
	}

	public void keyTyped(KeyEvent event)
	{
		// TODO Auto-generated method stub
	}

	class repaintThread implements Runnable
	{
		public void run()
		{
			while (true)
			{
				update();
				repaint();
				try
				{
					Thread.sleep(30);
				}
				catch (InterruptedException ex)
				{
				}
			}
		}
	}

	class FieldManager implements Runnable
	{
		public void run()
		{
			field = new Field(1);
			field.manageField(1);
		}
	}

	// @Override
	// public void keyPressed(KeyEvent e)
	// {
	// int key = e.getKeyCode();
	// if (key == KeyEvent.VK_UP)
	// {
	// Field.moveChar(0, 1, false);
	// }
	// else if (key == KeyEvent.VK_DOWN)
	// {
	// Field.moveChar(2, 0, false);
	// }
	// else if (key == KeyEvent.VK_RIGHT)
	// {
	// Field.moveChar(0, 4, false);
	// }
	// else if (key == KeyEvent.VK_LEFT)
	// {
	// Field.moveChar(0, 3, false);
	// }
	//
	// }
	//
	// @Override
	// public void keyReleased(KeyEvent e)
	// {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void keyTyped(KeyEvent e)
	// {
	// // TODO Auto-generated method stub
	//
	// }

}
