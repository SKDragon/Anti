package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import main.Field;
import projectiles.Projectile;
import HighScore.HighScores;
import HighScore.Score;
import enemies.Enemy;

/**
 * MainPanel class that handles painting graphics onto the screen
 * 
 * @author Gavin L/Iain
 * @version January 19, 2016
 */
public class MainPanel extends JPanel implements MouseListener, KeyListener
{
	/**
	 * Default Serialization
	 */
	private static final long serialVersionUID = 1L;

	// Global Variables
	private Image mainMenuBG, gameScreenBG, instructionsBG, gameOverBG,
			highScoresBG;
	private Border raisedBevel, loweredBevel, compound;
	private GridBagConstraints GB = new GridBagConstraints();

	// GameScreen Background Variables
	private int gameBG_X1 = 0;
	private int gameBG_Y1 = -200;
	private int gameBG_X2 = 0;
	private int gameBG_Y2 = -1000;
	private int gameBG_move = 10;

	// User Input Booleans
	private boolean UP_Pressed = false;
	private boolean LEFT_Pressed = false;
	private boolean DOWN_Pressed = false;
	private boolean RIGHT_Pressed = false;
	private boolean SHOOT_Pressed = false;

	// Field Variables
	private static Field field;
	private static Image playerIcon;
	private static Image playerProIcon;

	// HighScores Variables
	private JTextField nameField = new JTextField();
	private GamePanel gamePanel = new GamePanel();
	private HighScores hs = new HighScores();
	private String playerScore;
	private boolean nameFieldSetting = false;

	// Game Object Arrays
	private ArrayList<Projectile> charProjectiles;
	private ArrayList<Enemy> enemies;
	private ArrayList<Projectile> enemyProjectiles;

	// Thread Variables
	private Thread gameRepaint = new Thread(new repaintThread());
	private boolean repaintThreadState = false;

	// Game States
	protected enum STATE {
		MAIN_MENU, GAME, INSTRUCTIONS, HIGHSCORES, GAMEOVER
	};

	protected STATE State = STATE.MAIN_MENU;

	/**
	 * MainPanel Constructor
	 */
	public MainPanel()
	{
		super(new GridBagLayout());
		setPreferredSize(new Dimension(1000, 800));
		setFocusable(true);
		addMouseListener(this);
		addKeyListener(this);
		borderLoad();
		loadImages();
	}

	/**
	 * Custom repaint
	 */
	public void repaint()
	{
		super.repaint();
	}

	/**
	 * Displays the user input field when in GameOver State
	 */
	private void nameFieldSet()
	{
		// TextField Settings
		nameField.setFont(new Font("Arial", Font.BOLD, 100));
		nameField.setForeground(Color.MAGENTA);
		nameField.setBackground(Color.GRAY);
		nameField.setHorizontalAlignment(JTextField.CENTER);
		JTextFieldLimit lim = new JTextFieldLimit();
		lim.setLimit(3);
		nameField.setDocument(lim);

		// GridBag Alignments
		GB.ipady = 100;
		GB.weightx = 0.1;
		GB.gridx = 0;
		GB.gridy = 0;
		GB.gridwidth = 2;
		GB.gridheight = 2;
		GB.fill = GridBagConstraints.HORIZONTAL;
		GB.insets = new Insets(280, 235, 0, 200);
		add(nameField, GB);
	}

	/**
	 * Notifies frame to focus on panel
	 */
	public void addNotify()
	{
		super.addNotify();
		requestFocus();
	}

	/**
	 * Update method that updates key events during gameplay, used in thread
	 * loop
	 */
	private synchronized void update()
	{
		// Background Scrolling
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
		// //////////////////////////////////////////////////////////////////////////
		// 8 Cardinal Directions with SHOOT
		// ONLY UP
		if (UP_Pressed && !DOWN_Pressed && !LEFT_Pressed && !RIGHT_Pressed)
		{
			// Adds SHOOT
			if (SHOOT_Pressed)
			{
				Field.moveChar(0, 1, true);
			}
			else
			{
				Field.moveChar(0, 1, false);
			}

		}
		// UP and RIGHT
		if (UP_Pressed && !DOWN_Pressed && !LEFT_Pressed && RIGHT_Pressed)
		{
			// Adds SHOOT
			if (SHOOT_Pressed)
			{
				Field.moveChar(1, 4, true);
			}
			else
			{
				Field.moveChar(1, 4, false);

			}
		}
		// ONLY RIGHT
		if (RIGHT_Pressed && !DOWN_Pressed && !UP_Pressed && !LEFT_Pressed)
		{
			// Adds SHOOT
			if (SHOOT_Pressed)
			{
				Field.moveChar(0, 4, true);
			}
			else
			{
				Field.moveChar(0, 4, false);
			}
		}
		// DOWN and RIGHT
		if (DOWN_Pressed && !LEFT_Pressed && !UP_Pressed && RIGHT_Pressed)
		{

			// Adds SHOOT
			if (SHOOT_Pressed)
			{
				Field.moveChar(2, 4, true);
			}
			else
			{
				Field.moveChar(2, 4, false);
			}
		}
		// ONLY DOWN
		if (DOWN_Pressed && !LEFT_Pressed && !UP_Pressed && !RIGHT_Pressed)
		{

			// Adds SHOOT
			if (SHOOT_Pressed)
			{
				Field.moveChar(2, 0, true);
			}
			else
			{
				Field.moveChar(2, 0, false);

			}
		}
		// DOWN and LEFT
		if (DOWN_Pressed && LEFT_Pressed && !UP_Pressed && !RIGHT_Pressed)
		{

			// Adds SHOOT
			if (SHOOT_Pressed)
			{
				Field.moveChar(2, 3, true);
			}
			else
			{
				Field.moveChar(2, 3, false);

			}
		}
		// ONLY LEFT
		if (LEFT_Pressed && !DOWN_Pressed && !UP_Pressed && !RIGHT_Pressed)
		{
			// Adds SHOOT
			if (SHOOT_Pressed)
			{
				Field.moveChar(0, 3, true);
			}
			else
			{
				Field.moveChar(0, 3, false);
			}
		}
		// UP and LEFT
		if (UP_Pressed && !DOWN_Pressed && LEFT_Pressed && !RIGHT_Pressed)
		{
			// Adds SHOOT
			if (SHOOT_Pressed)
			{
				Field.moveChar(1, 3, true);
			}
			else
			{
				Field.moveChar(1, 3, false);
			}
		}
		//
		// //////////////////////////////////////////////////////////////////////////
		// Just SHOOT
		if (!UP_Pressed && SHOOT_Pressed && !DOWN_Pressed && !LEFT_Pressed
				&& !RIGHT_Pressed)
		{
			Field.moveChar(0, 0, true);
		}

		// Updates Arrays
		if (Field.proFired)
			charProjectiles = field.getCharProjectiles();
		if (Field.enemySpawned)
			enemies = field.getEnemies();
		if (Field.enemyProFired)
			enemyProjectiles = field.getEnemyProjectiles();

	}

	/**
	 * Creates and loads custom borders
	 */
	private void borderLoad()
	{
		BorderFactory.createLineBorder(Color.black);
		raisedBevel = BorderFactory.createRaisedBevelBorder();
		loweredBevel = BorderFactory.createLoweredBevelBorder();
		compound = BorderFactory.createCompoundBorder(raisedBevel,
				loweredBevel);
	}

	/**
	 * Loads in menu backgrounds
	 */
	private void loadImages()
	{
		mainMenuBG = new ImageIcon("Pictures/Menu Backgrounds/MainMenuBG.png")
				.getImage();
		instructionsBG = new ImageIcon(
				"Pictures/Menu Backgrounds/InstructionsBGEdited.png")
						.getImage();
		gameScreenBG = new ImageIcon(
				"Pictures/Game Backgrounds/GameScreenBG.png").getImage();
		gameOverBG = new ImageIcon("Pictures/Menu Backgrounds/GameOverBG.png")
				.getImage();
		highScoresBG = new ImageIcon(
				"Pictures/Menu Backgrounds/HighScoresBG.png").getImage();
	}

	/**
	 * Paint method that paints the Instructions Screen
	 * @param g The Graphics
	 */
	public void renderInstructionsScreen(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(instructionsBG, 0, 0, this);
	}

	/**
	 * Paint method that paints the Highscores Screen
	 * @param g The Graphics
	 */
	public void renderHighScoresScreen(Graphics g)
	{
		super.paintComponent(g);
		// Painting Variables
		int nx = 200;
		int y = 175;
		int sx = 370;
		int size = 0;
		ArrayList<Score> array = new ArrayList<Score>();

		// Paint settings
		g.drawImage(highScoresBG, 0, 0, this);
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Arial", Font.BOLD, 50));

		// Highscores System
		hs.clear();
		hs.loadScoreFile();
		array = hs.getArray();

		// Painting the sores
		if (array.size() > 10)
		{
			size = 10;
		}
		else
			size = array.size();
		for (int i = 0; i < size; i++)
		{
			String name = array.get(i).getName();
			String score = Integer.toString(array.get(i).getScore());
			g.drawString(name, nx, y);
			g.drawString(score, sx, y);
			y += 64;
		}

	}

	/**
	 * Paint method that paints the GameOver Screen
	 * @param g The Graphics
	 */
	public void renderGameOverScreen(Graphics g)
	{
		super.paintComponent(g);
		// Panel Settings
		setSize(1000, 800);
		setLayout(new GridBagLayout());
		setBorder(new EmptyBorder(0, 0, 0, 0));
		gamePanel.setVisible(false);
		g.drawImage(gameOverBG, 0, 0, this);

		// Grab Score
		playerScore = Integer.toString(field.getScore());

		// Display Score
		g.setColor(Color.MAGENTA);
		g.setFont(new Font("Arial", Font.BOLD, 50));
		g.drawString(playerScore, 345, 235);

		// Name Field
		if (nameFieldSetting == false)
		{
			hs.clear();
			hs.loadScoreFile();
			nameField.setVisible(true);
			nameFieldSet();
			nameFieldSetting = true;
		}

	}

	/**
	 * Paint method that paints the MainMenu Screen
	 * @param g The Graphics
	 */
	public void renderMainMenu(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(mainMenuBG, 0, 0, this);
	}

	/**
	 * Paint method that paints the Game Screen
	 * @param g The Graphics
	 */
	public void renderGameScreen(Graphics g)
	{
		super.paintComponent(g);
		// Panel Settings
		setBackground(Color.black);
		gamePanel.setVisible(true);
		gamePanel.setSize(600, 800);
		gamePanel.setBorder(compound);
		gamePanel.addKeyListener(this);
		GB.insets = new Insets(0, 0, 700, 0);
		add(gamePanel, GB);

		// Display Player Current Score
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Arial", Font.BOLD, 50));
		g.drawString("SCORE", 730, 50);
		try
		{
			String score = Integer.toString(field.getScore());
			g.setColor(Color.MAGENTA);
			g.drawString(score, 700, 100);
		}
		catch (NullPointerException e)
		{
		}
	}

	/**
	 * Main paint Method that will be called
	 */
	@SuppressWarnings("deprecation")
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		// Paints depending on Game State
		// MainMenu
		if (State == STATE.MAIN_MENU)
		{
			renderMainMenu(g);
		}
		// Game Screen
		else if (State == STATE.GAME)
		{
			// Initialize the game once
			if (!repaintThreadState)
			{
				Thread fieldManage = new Thread(new FieldManager());
				fieldManage.start();
				gameRepaint.start();
				repaintThreadState = true;
			}
			else
			{
				gameRepaint.resume();
				Field.gameOver = false;
			}
			renderGameScreen(g);
		}
		// Instructions Screen
		else if (State == STATE.INSTRUCTIONS)
		{
			renderInstructionsScreen(g);
		}
		// Highscores Screen
		else if (State == STATE.HIGHSCORES)
		{
			renderHighScoresScreen(g);
		}
		// GameOver Screen
		else if (State == STATE.GAMEOVER)
		{
			renderGameOverScreen(g);
		}
	}

	/**
	 * Mouse Events Listiner
	 */
	public void mouseClicked(MouseEvent event)
	{
		// X and Y variables
		int mx = event.getX();
		int my = event.getY();

		// When on MainMenu Screen
		if (State == STATE.MAIN_MENU)
		{
			// Play
			if (mx >= 40 && mx <= 329 && my >= 385 && my <= 426)
			{
				State = STATE.GAME;
				repaint();
			}
			// Instructions
			else if (mx >= 32 && mx <= 598 && my >= 473 && my <= 560)
			{
				State = STATE.INSTRUCTIONS;
				repaint();

			}
			// Highscores
			else if (mx >= 35 && mx <= 532 && my >= 604 && my <= 644)
			{
				State = STATE.HIGHSCORES;
				repaint();
			}
		}
		// When on Instructions Screen
		else if (State == STATE.INSTRUCTIONS)
		{
			// Back Button
			if (mx > 24 && mx < 127 && my > 730 && my < 779)
			{
				State = STATE.MAIN_MENU;
				repaint();
			}
		}
		// When on GameOver Screen
		else if (State == STATE.GAMEOVER)
		{
			// Enter Button
			if (mx > 394 && mx < 694 && my > 700 && my < 800)
			{
				// Handles if user enters blank name, defaults to "AAA". Then
				// updates highscores file
				String name = nameField.getText();
				if (name.compareTo(" ") == -1)
				{
					hs.addScore("AAA", field.getScore());
				}
				else
					hs.addScore(name, field.getScore());
				hs.updateScoreFile();
				State = STATE.MAIN_MENU;
				nameField.setVisible(false);
				repaint();
			}
		}
		// When on Highscors Screen
		else if (State == STATE.HIGHSCORES)
		{
			// Back Button
			if (mx > 0 && mx < 106 && my > 751 && my < 800)
			{
				State = STATE.MAIN_MENU;
				repaint();
			}
		}

	}

	/**
	 * Unused Implemented Mouse Listener Method
	 */
	public void mouseEntered(MouseEvent event)
	{
	}

	/**
	 * Unused Implemented Mouse Listener Method
	 */
	public void mouseExited(MouseEvent event)
	{
	}

	/**
	 * Unused Implemented Mouse Listener Method
	 */
	public void mousePressed(MouseEvent event)
	{
	}

	/**
	 * Unused Implemented Mouse Listener Method
	 */
	public void mouseReleased(MouseEvent event)
	{
	}

	/**
	 * Key Listener Method (KeyPressed)
	 */
	public void keyPressed(KeyEvent event)
	{
		// Key Variable
		int key = event.getKeyCode();
		// Handles which key is pressed during each State
		if (State == STATE.GAME)
		{
			// Key Codes: Up = 38, Left = 37, Down = 40, Right = 39, Z = 90
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
		}
	}

	/**
	 * Key Listener Method (KeyReleased)
	 */
	public void keyReleased(KeyEvent event)
	{
		// Key Variable
		int key = event.getKeyCode();
		// Handles which key is released during each State
		if (State == STATE.GAME)
		{
			// Key Codes: Up = 38, Left = 37, Down = 40, Right = 39, Z = 90
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
		}
	}

	/**
	 * Unused Implemented Key Listener Method
	 */
	public void keyTyped(KeyEvent event)
	{
	}

	/**
	 * Main game panel class that extends JPanel
	 * @author Gavin L/Iain
	 * @version January 19, 2016
	 */
	class GamePanel extends JPanel
	{

		/**
		 * Default Serialization
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * GamePanel Constructor
		 */
		private GamePanel()
		{
			super();
		}

		/**
		 * GamePanel Paint method
		 */
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			// Draws the scrolling background
			g.drawImage(gameScreenBG, gameBG_X1, gameBG_Y1, this);
			if ((gameBG_Y1 >= -1200 && gameBG_Y1 <= 800))
			{
				g.drawImage(gameScreenBG, gameBG_X2, gameBG_Y2, this);
			}

			// Draws Player
			Point playerLoc = Field.playerLoc;
			g.setColor(Color.PINK);
			g.drawImage(playerIcon, (int) playerLoc.getX(),
					(int) playerLoc.getY(), null);

			// Paint objects within object arrays
			// Player Projectiles
			if (charProjectiles != null && charProjectiles.size() > 0)
			{
				synchronized (charProjectiles)
				{
					for (int charPros = 0; charPros < charProjectiles
							.size(); charPros++)
					{
						Projectile charPro = charProjectiles.get(charPros);
						synchronized (charPro)
						{
							Point pp = charPro.getLocation();
							int x = (int) pp.getX();
							int y = (int) pp.getY();
							g.drawImage(playerProIcon, x, y, null);
						}
					}
				}
			}
			// Enemy objects
			if (enemies != null && enemies.size() > 0)
			{
				synchronized (enemies)
				{
					for (Enemy toDraw : enemies)
					{
						synchronized (toDraw)
						{
							Point pp = toDraw.getLocation();
							int x = (int) pp.getX();
							int y = (int) pp.getY();
							g.drawImage(toDraw.getIcon(), x, y, null);
						}
					}
				}
			}
			// Enemy Projectiles
			if (enemyProjectiles != null && enemyProjectiles.size() > 0)
			{
				synchronized (enemyProjectiles)
				{
					for (Projectile toDraw : enemyProjectiles)
					{
						synchronized (toDraw)
						{
							Point pp = toDraw.getLocation();
							int x = (int) pp.getX();
							int y = (int) pp.getY();
							g.drawImage(toDraw.getImage(), x, y, null);
						}
					}
				}
			}
		}
	}

	/**
	 * Paint thread that calls repaints constantly
	 * @author Gavin L/Iain
	 * @version January 19, 2016
	 */
	class repaintThread implements Runnable
	{
		// Variables
		boolean suspended = false;

		/**
		 * Main run Method loop
		 */
		public void run()
		{
			try
			{
				// Main repaint loop
				while (true)
				{
					update();
					repaint();
					Thread.sleep(30);
					
					// When Gameover State
					if (Field.gameOver)
					{
						State = STATE.GAMEOVER;
						repaint();
						suspend();
					}
					// Suspeneded State
					synchronized (this)
					{
						while (suspended)
						{
							wait();
						}
					}
				}
			}
			catch (InterruptedException ex)
			{
			}
		}

		/**
		 * Calls for suspend thread state
		 */
		void suspend()
		{
			suspended = true;
		}

		/**
		 * Calls for resuming thread state
		 */
		synchronized void resume()
		{
			suspended = false;
			notify();
		}
	}

	/**
	 * Thread that manages Field object
	 * @author Gavin L/Iain
	 * @version January 19, 2016
	 */
	class FieldManager implements Runnable
	{
		/**
		 * Main Run Method
		 */
		public void run()
		{
			field = new Field();
			playerIcon = field.getPlayer().getIcon();
			playerProIcon = field.getPlayer().getProIcon();
			field.manageField();
		}
	}

	/**
	 * Class that customizes text field settings
	 * @author Gavin L/Iain
	 * @version January 19, 2016
	 */
	public class JTextFieldLimit extends PlainDocument
	{
		/**
		 * Default Serialization
		 */
		private static final long serialVersionUID = 1L;
		// Variable
		private int limit;

		/**
		 * Displays the inputed string text
		 */
		public void insertString(int offset, String str, AttributeSet attr)
				throws BadLocationException
		{
			if ((getLength() + str.length()) <= limit)
			{
				// Changes text to all UpperCase
				super.insertString(offset, str.toUpperCase(), attr);
			}
		}

		/**
		 * Sets character limit on text field
		 * @param i The character limit
		 */
		public void setLimit(int i)
		{
			this.limit = i;
		}
	}
}
