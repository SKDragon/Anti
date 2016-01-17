package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.Border;

import main.Field;
import projectiles.Projectile;
import HighScore.HighScores;
import enemies.Enemy;

/**
 * MainMenu class
 * 
 * @author Gavin L
 * @version January 11, 2016
 */
public class MainMenu extends JPanel implements MouseListener, KeyListener {
	// Global Variables
	private Image mainMenuBG, gameScreenBG, instructionsBG, gameOverBG;
	private Border raisedBevel, loweredBevel, compound, blackline;
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

	//
	private static Field field;
	private static Image playerIcon;
	private static Image playerProIcon;

	// HighScores
	private HighScores hs = new HighScores();
	String playerScore;
	
	Font font;

	// Stuff
	ArrayList<Projectile> charProjectiles;
	ArrayList<Enemy> enemies;

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
		MAIN_MENU, GAME, INSTRUCTIONS, HIGHSCORES, GAMEOVER
	};

	protected STATE State = STATE.MAIN_MENU;

	MainMenu() {
		super(new GridBagLayout());
		setPreferredSize(new Dimension(1000, 800));
		setFocusable(true);
		addMouseListener(this);
		addKeyListener(this);
		borderLoad();
		loadImages();
		//loadFonts();
	}

	public void repaint() {
		super.repaint();
	}

	public void addNotify() {
		super.addNotify();
		requestFocus();
	}

	private synchronized void update() {
		// GameOver Update

		// Background
		gameBG_Y1 += gameBG_move;
		if (gameBG_Y1 >= -1200) {
			gameBG_Y2 += gameBG_move;
		}
		if (gameBG_Y2 == -200) {
			gameBG_Y1 = -1200;
		} else if (gameBG_Y1 == -200) {
			gameBG_Y2 = -1200;
		}
		//
		// Fixed It without using time :P
		//
		//
		// //////////////////////////////////////////////////////////////////////////
		// 8 Cardinal Directions with SHOOT
		// ONLY UP
		if (UP_Pressed && !DOWN_Pressed && !LEFT_Pressed && !RIGHT_Pressed) {
			// Adds SHOOT
			if (SHOOT_Pressed) {
				// System.out.println("UP and SHOOT");
				Field.moveChar(0, 1, true);
			} else {
				// System.out.println("UP");
				Field.moveChar(0, 1, false);
			}

		}
		// UP and RIGHT
		if (UP_Pressed && !DOWN_Pressed && !LEFT_Pressed && RIGHT_Pressed) {
			// Adds SHOOT
			if (SHOOT_Pressed) {
				// System.out.println("UP and RIGHT and SHOOT");
				Field.moveChar(1, 4, true);
			} else {
				// System.out.println("UP and RIGHT");
				Field.moveChar(1, 4, false);

			}
		}
		// ONLY RIGHT
		if (RIGHT_Pressed && !DOWN_Pressed && !UP_Pressed && !LEFT_Pressed) {
			// Adds SHOOT
			if (SHOOT_Pressed) {
				// System.out.println("RIGHT and SHOOT");
				Field.moveChar(0, 4, true);
			} else {
				// System.out.println("RIGHT");
				Field.moveChar(0, 4, false);
			}
		}
		// DOWN and RIGHT
		if (DOWN_Pressed && !LEFT_Pressed && !UP_Pressed && RIGHT_Pressed) {

			// Adds SHOOT
			if (SHOOT_Pressed) {
				// System.out.println("DOWN and RIGHT and SHOOT");
				Field.moveChar(2, 4, true);
			} else {
				Field.moveChar(2, 4, false);
				// System.out.println("DOWN and RIGHT");

			}
		}
		// ONLY DOWN
		if (DOWN_Pressed && !LEFT_Pressed && !UP_Pressed && !RIGHT_Pressed) {

			// Adds SHOOT
			if (SHOOT_Pressed) {
				// System.out.println("DOWN and SHOOT");
				Field.moveChar(2, 0, true);
			} else {
				// System.out.println("DOWN");
				Field.moveChar(2, 0, false);

			}
		}
		// DOWN and LEFT
		if (DOWN_Pressed && LEFT_Pressed && !UP_Pressed && !RIGHT_Pressed) {

			// Adds SHOOT
			if (SHOOT_Pressed) {
				// System.out.println("DOWN and LEFT and SHOOT");
				Field.moveChar(2, 3, true);
			} else {
				// System.out.println("DOWN and LEFT");
				Field.moveChar(2, 3, false);

			}
		}
		// ONLY LEFT
		if (LEFT_Pressed && !DOWN_Pressed && !UP_Pressed && !RIGHT_Pressed) {
			// Adds SHOOT
			if (SHOOT_Pressed) {
				// System.out.println("LEFT and SHOOT");
				Field.moveChar(0, 3, true);
			} else {
				// System.out.println("LEFT");
				Field.moveChar(0, 3, false);
			}
		}
		// UP and LEFT
		if (UP_Pressed && !DOWN_Pressed && LEFT_Pressed && !RIGHT_Pressed) {
			// Adds SHOOT
			if (SHOOT_Pressed) {
				// System.out.println("UP and LEFT and SHOOT");
				Field.moveChar(1, 3, true);
			} else {
				// System.out.println("UP and LEFT");
				Field.moveChar(1, 3, false);
			}
		}
		//
		// //////////////////////////////////////////////////////////////////////////
		// Just SHOOT or BOMB
		if (!UP_Pressed && SHOOT_Pressed && !DOWN_Pressed && !LEFT_Pressed && !RIGHT_Pressed) {
			// .println("SHOOT");
			Field.moveChar(0, 0, true);
		}

		// String th = field.toString();
		// System.out.println(th);
		// System.out.println(field);

		// Arrays
		if (field.proFired)
			charProjectiles = field.getCharProjectiles();
		if (field.enemySpawned)
			enemies = field.getEnemies();

	}

	void borderLoad() {
		blackline = BorderFactory.createLineBorder(Color.black);
		raisedBevel = BorderFactory.createRaisedBevelBorder();
		loweredBevel = BorderFactory.createLoweredBevelBorder();
		compound = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
	}

	void loadImages() {
		mainMenuBG = new ImageIcon("Pictures/Menu Backgrounds/MainMenuBG.png").getImage();
		instructionsBG = new ImageIcon("Pictures/Menu Backgrounds/InstructionsBGEdited.png").getImage();
		gameScreenBG = new ImageIcon("Pictures/Game Backgrounds/GameScreenBG.png").getImage();
		gameOverBG = new ImageIcon("Pictures/Menu Backgrounds/GameOverBG.png").getImage();
	}

	void loadFonts() {
		InputStream is = getClass().getResourceAsStream("Fonts/Alien_Resurrection.ttf");
		 try {
			font = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		font = font.deriveFont(25);
	}

	// Instructions Render
	public void renderInstructionsScreen(Graphics g) {
		super.paintComponent(g);
		g.drawImage(instructionsBG, 0, 0, this);
	}

	// HighScores Render
	public void renderHighScoresScreen(Graphics g) {
		super.paintComponent(g);
		// g.drawImage(instructionsBG, 0, 0, this);
	}

	// GameOver Render
	public void renderGameOverScreen(Graphics g) {
		super.paintComponent(g);
		setSize(1000, 800);
		g.drawImage(gameOverBG, 0, 0, this);

		playerScore = Integer.toString(field.getScore());
		g.setColor(Color.MAGENTA);
		g.setFont(font);
		g.drawString(playerScore, 345, 235);

		// g.drawString("500", 100, 200);
	}

	// MainMenu Render
	public void renderMainMenu(Graphics g) {
		super.paintComponent(g);
		g.drawImage(mainMenuBG, 0, 0, this);
	}

	// GameScreen Render
	public void renderGameScreen(Graphics g) {
		super.paintComponent(g);
		setSize(600, 800);
		setBorder(compound);
		addKeyListener(this);
		g.drawImage(gameScreenBG, gameBG_X1, gameBG_Y1, this);
		if ((gameBG_Y1 >= -1200 && gameBG_Y1 <= 800)) {
			g.drawImage(gameScreenBG, gameBG_X2, gameBG_Y2, this);
		}

		// Player
		Point playerLoc = field.playerLoc;
		int dim = field.playerDim;
		g.setColor(Color.PINK);
		// g.drawRect((int) playerLoc.getX(), (int) playerLoc.getY(), dim, dim);

		g.drawImage(playerIcon, (int) playerLoc.getX(), (int) playerLoc.getY(), null);

		// testing
		// g.fillRect(x, y, 100, 100);

		// Paint arrays

		if (charProjectiles != null && charProjectiles.size() > 0) {
			// System.out.println("main " + charProjectiles.size());
			synchronized (charProjectiles) {
				for (Projectile charPro : charProjectiles) {
					synchronized (charPro) {
						Point pp = charPro.getLocation();
						int x = (int) pp.getX();
						int y = (int) pp.getY();
						g.drawImage(playerProIcon, x, y, null);
						// System.out.println(charPro.getLocation().getY());
						// g.fillRect(x, y, 10, 10);
					}
				}
			}
		}

		if (enemies != null && enemies.size() > 0) {
			synchronized (enemies) {
				for (Enemy toDraw : enemies) {
					synchronized (toDraw) {
						Point pp = toDraw.getLocation();
						int x = (int) pp.getX();
						int y = (int) pp.getY();
						g.drawImage(toDraw.getIcon(), x, y, null);
					}
				}
			}
		}
	}

	// Main Graphics Render
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (State == STATE.MAIN_MENU) {
			renderMainMenu(g);
		} else if (State == STATE.GAME) {
			if (!repaintThreadState) {

				Thread fieldManage = new Thread(new FieldManager());
				fieldManage.start();
				gameRepaint.start();
				repaintThreadState = true;
				running = true;
				// gameLoop();
			}
			// gameLoop();
			renderGameScreen(g);
		} else if (State == STATE.INSTRUCTIONS) {
			renderInstructionsScreen(g);
		} else if (State == STATE.HIGHSCORES) {
			renderHighScoresScreen(g);
		} else if (State == STATE.GAMEOVER) {
			renderGameOverScreen(g);
		}
		// repaint();
	}

	// Mouse Events
	public void mouseClicked(MouseEvent event) {
		int mx = event.getX();
		int my = event.getY();

		if (State == STATE.MAIN_MENU) {
			// Play
			if (mx >= 40 && mx <= 329 && my >= 385 && my <= 426) {
				State = STATE.GAME;
				repaint();
			}
			// Instructions
			else if (mx >= 32 && mx <= 598 && my >= 473 && my <= 560) {
				State = STATE.INSTRUCTIONS;
				repaint();

			}
			// Highscores
			else if (mx >= 35 && mx <= 532 && my >= 604 && my <= 644) {
				State = STATE.HIGHSCORES;
				repaint();

			}
		} else if (State == STATE.INSTRUCTIONS) {
			if (mx > 24 && mx < 127 && my > 730 && my < 779) {
				State = STATE.MAIN_MENU;
				repaint();
			}
		}

	}

	public void mouseEntered(MouseEvent event) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent event) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent event) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent event) {
		// Up = 38
		// Left = 37
		// Down = 40
		// Right = 39
		// Z = 90
		// X = 88

		int key = event.getKeyCode();

		// UP
		if (key == 38) {
			UP_Pressed = true;
		}
		// LEFT
		if (key == 37) {
			LEFT_Pressed = true;
		}
		// DOWN
		if (key == 40) {
			DOWN_Pressed = true;
		}
		// RIGHT
		if (key == 39) {
			RIGHT_Pressed = true;
		}
		// SHOOT
		if (key == 90) {
			SHOOT_Pressed = true;
		}

		// }
		//
		// //////////////////////////////////////////////////////////////////////////
		// repaint();
	}

	@Override
	public void keyReleased(KeyEvent event) {
		int key = event.getKeyCode();

		// Up = 38
		// Left = 37
		// Down = 40
		// Right = 39
		// Z = 90
		// X = 88

		// UP
		if (key == 38) {
			UP_Pressed = false;
		}
		// SHOOT
		if (key == 90) {
			SHOOT_Pressed = false;
		}
		// LEFT
		if (key == 37) {
			LEFT_Pressed = false;
		}
		// DOWN
		if (key == 40) {
			DOWN_Pressed = false;
		}
		// RIGHT
		if (key == 39) {
			RIGHT_Pressed = false;
		}

		// repaint();
	}

	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
	}

	class repaintThread implements Runnable {
		public void run() {
			while (!Field.gameOver) {
				update();
				repaint();
				try {
					Thread.sleep(30);
				} catch (InterruptedException ex) {
				}
			}

			// Call for gameOver Screen
			State = STATE.GAMEOVER;
			repaint();
		}
	}

	class FieldManager implements Runnable {
		public void run() {
			field = new Field();
			playerIcon = field.getPlayer().getIcon();
			playerProIcon = field.getPlayer().getProIcon();
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
