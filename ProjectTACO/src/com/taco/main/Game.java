package com.taco.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.taco.actor.Enemy;
import com.taco.actor.HealthBar;
import com.taco.actor.Player;
import com.taco.actor.SquareEnemy;
import com.taco.geometry.Square;
import com.taco.level.Wave;
import com.taco.world.Location;
import com.taco.world.World;

@SuppressWarnings("serial")
public class Game extends JFrame {

	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
	private static final short REC_FPS = 60;
	private static short fps = 50;
	private static final long FRAME_PERIOD = 1000000000L / fps;
	private static final String NAME = "The Game";
	private int numEnemies = 4;

	private JPanel panel;
	private Image image, bg;
	public static Graphics2D imageGraphics;

	public static StateManager state = new StateManager();

	{
	}

	// private Menu menu;

	private World world;

	public Game() {
		initFrame(); // Initialize Window
		HEIGHT = getHeight();
		WIDTH = getWidth();
		newGame();
	}

	private void newGame() {
		initImage(); // Initialize Graphics
		initModel(); // Initialize Objects
		state.setState(State.GAME);
		wave = 1;
	}

	private void executeGameLoop() {
		long nextFrameStart = System.nanoTime();
		while (true) {

			do {
				// updateModel();
				nextFrameStart += FRAME_PERIOD;
			} while (nextFrameStart < System.nanoTime());

			try {
				renderFrame();
			} catch (Exception e) {
				System.out.println("EXITING DUE TO ERROR");
				e.printStackTrace();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				executeGameLoop();
			}
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
	/*
	 * private void updateModel() {
	 * 
	 * if (state.getState() == State.MENU) { //
	 * state.setState(menu.getGameState()); // menu.update(state); } else if
	 * (state.getState() == State.PAUSE) {
	 * 
	 * } else if (state.getState() == State.GAME) {
	 * 
	 * } else { // state.setState(State.MENU); }
	 * 
	 * }
	 */

	private static int wave = 1;

	public static int getWave() {
		return wave;
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
			if (world.getLiveEnemies().size() < wave + 2) {
				Wave w = new Wave();
				w.add(SquareEnemy.class.getName(), wave + 5);
				world.spawn(w);
				wave++;
			}

			world.update();
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
		 panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		// Fullscreen
		//getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
		setVisible(true);
	}

	// Initialize Graphics
	private void initImage() {
		image = createImage(WIDTH, HEIGHT);
		imageGraphics = (Graphics2D) image.getGraphics();
	}

	public static String prompt(String message) {
		return JOptionPane.showInputDialog(message);
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
	
	public void startAgain() {
		initWorld(); // Initialize World
		world.setScore(0);
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
		startAgain();
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

	public void pause() {
		state.setState(State.PAUSE);
	}

}
