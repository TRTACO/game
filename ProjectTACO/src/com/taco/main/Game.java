package com.taco.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.taco.actor.Enemy;
import com.taco.actor.HealthBar;
import com.taco.actor.Player;
import com.taco.actor.SquareEnemy;
import com.taco.geometry.Square;
import com.taco.world.Location;
import com.taco.world.World;

@SuppressWarnings("serial")
public class Game extends JFrame {

	public static int WIDTH; // = 1280;
	public static int HEIGHT;// = 720;
	private static final int FRAMES_PER_SECOND = 50;
	private static final long FRAME_PERIOD = 1000000000L / FRAMES_PER_SECOND;
	private static final String NAME = "The Game";
	private int numEnemies = 4;

	private JPanel panel;
	private Image image, bg;
	public volatile static Graphics2D imageGraphics;

	public volatile StateManager state = new StateManager();

	{
	}

	// private Menu menu;

	private World world;

	public Game() {
		newGame();
	}

	private void newGame() {
		initFrame(); // Initialize Window
		HEIGHT = getHeight();
		WIDTH = getWidth();
		initImage(); // Initialize Graphics
		initModel(); // Initialize Objects
		state.setState(State.GAME);
	}

	private void executeGameLoop() {
		long nextFrameStart = System.nanoTime();
		while (true) {
			do {
				updateModel();
				nextFrameStart += FRAME_PERIOD;
			} while (nextFrameStart < System.nanoTime());
			renderFrame();
			long remaining = nextFrameStart - System.nanoTime();
			if (remaining > 0) {
				try {
					Thread.sleep(remaining / 1000000);
				} catch (Throwable t) {
				}
			}
		}
	}

	/**
	 * Where you tell the program to actually update the object.
	 */
	private void updateModel() {
		if (state.getState() == State.MENU) {
			// state.setState(menu.getGameState());
			// menu.update(state);
		} else if (state.getState() == State.PAUSE) {

		} else if (state.getState() == State.GAME) {

		} else {
			// state.setState(State.MENU);
		}
	}

	/**
	 * Where you actually say render to screen. <br/>
	 * Remember! Order of render matters!
	 */
	private synchronized void renderFrame() {
		Graphics panelGraphics = panel.getGraphics();
		// Draw Black Background
		if (imageGraphics != null)
			;
		imageGraphics.drawImage(bg, 0, 0, null);

		if (state.getState() == State.MENU) {
			// menu.render();
		} else if (state.getState() == State.PAUSE) {
			Color temp = imageGraphics.getColor();
			imageGraphics.setColor(Color.WHITE);
			imageGraphics.setBackground(Color.RED);
			imageGraphics.drawString("Paused", 200, 200);
			imageGraphics.setColor(temp);
		} else if (state.getState() == State.GAME) {
			// HAS BEEN MOVED TO THE WORLD CLASS
			// imageGraphics.fillRect(0, 0, WIDTH, HEIGHT);
			try {
				world.update();
			} catch (Exception e) {
				return;
			}
		} else {
			// state.setState(State.MENU);
		}

		// Draw to screen
		if (panelGraphics != null) {
			panelGraphics.drawImage(image, 0, 0, null);
			panelGraphics.dispose();
		}
	}

	// Initialize Window
	private void initFrame() {
		setTitle(NAME);
		setResizable(false);
		panel = (JPanel) getContentPane();
		// panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		// Fullscreen
		getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
		setVisible(true);
	}

	// Initialize Graphics
	private void initImage() {
		image = createImage(WIDTH, HEIGHT);
		imageGraphics = (Graphics2D) image.getGraphics();
	}

	// Initialize Objects
	private void initModel() {
		// menu = new Menu(image, state);
		// addMouseListener(menu);
	}

	private void initWorld() {
		world = new World();
		// Add things to the world
		List<SquareEnemy> enemies = new LinkedList<SquareEnemy>();
		for (int i = 0; i < numEnemies; i++) {
			enemies.add(new SquareEnemy(
					new Square(World.getRandLocation(), 40), Color.RED));
		}
		Player p = new Player(100, 250, 10, 10);

		world.add(p);
		Square s = new Square(10, 10, 50);
		// SquareEnemy sq = new SquareEnemy(s, Color.RED);
		// world.add(sq);
		for (Enemy e : enemies)
			world.add(e);

		HealthBar hBar = new HealthBar(p, new Location(WIDTH - 300, 10));
		world.add(hBar);

	}

	// Keyboard Buttons
	@Override
	public void processKeyEvent(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_P && e.getID() == 401)
			togglePause();
		KeyListener[] l = getKeyListeners();
		for (KeyListener k : l)
			if (e.getID() == 401)
				k.keyPressed(e);
			else
				k.keyReleased(e);
	}

	public void start() {
		initWorld(); // Initialize World
		executeGameLoop(); // Starts Loop
	}

	public World getWorld() {
		return world;
	}

	// BIG ERROR HERE!!!!
	public void restart() {
		world = null;
		panel.removeAll();
		System.gc();
		newGame();
		start();
	}

	@Override
	public int getState() {
		return state.getState();
	}

	public void togglePause() {
		if (state.getState() == State.PAUSE) {
			state.setState(State.GAME);
			imageGraphics.setBackground(Color.BLACK);
		} else
			state.setState(State.PAUSE);
	}

}
