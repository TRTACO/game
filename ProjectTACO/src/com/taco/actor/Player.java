package com.taco.actor;

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_R;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.VK_W;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.HashSet;

import com.taco.actor.equipment.Weapon;
import com.taco.input.Keyboard;
import com.taco.input.Mouse;
import com.taco.main.Game;
import com.taco.main.Main;
import com.taco.world.Location;

public class Player extends Actor implements KeyListener, MouseMotionListener {

	// private double turnSpeed;
	private Weapon w;
	HashSet<Integer> keysPressed = new HashSet<Integer>();

	// TODO: Create Player's ability to map own keys
	// private Map<Character, Integer> keyMap = new HashMap<Character,
	// Integer>();

	public Player(double x, double y, double w, double h) {
		super(x, y, w, h);
		health = 150;
		oHealth = health;
		speed = 8.5;

		originalSpeed = speed;
		// turnSpeed = 3;
		this.w = new Weapon(this) {
			@Override
			public double getDirection() {
				return direction;
			}
		};
		this.w.setDelay(10);
		Mouse.addMouseMotionListener(this);
		Keyboard.addKeyListener(this);
	}

	public Player() {
		super();
	}

	@Override
	public void move() {

		/*
		 * //ORIGINAL CODE if(KEYS[KeyEvent.VK_S]) { if(KEYS[KeyEvent.VK_A]) {
		 * direction=90+45; } else if(KEYS[KeyEvent.VK_D]) { direction=90-45; }
		 * else direction=90; speed = originalSpeed; } else
		 * if(KEYS[KeyEvent.VK_W]) { if(KEYS[KeyEvent.VK_A]) { direction=270-45;
		 * } else if(KEYS[KeyEvent.VK_D]) { direction=270+45;
		 * 
		 * } else direction=270; speed = originalSpeed; } else
		 * if(KEYS[KeyEvent.VK_A]){ direction=180; speed = originalSpeed; } else
		 * if(KEYS[KeyEvent.VK_D]){ direction=0; speed = originalSpeed; }
		 */

		/*
		 * if (pressed(VK_A)) direction -= turnSpeed; if (pressed(VK_D))
		 * direction += turnSpeed;
		 * 
		 * if (pressed(VK_W)) { speed = originalSpeed; super.move();
		 * btnLastPressed = "W";
		 * 
		 * } if (pressed(VK_S)) { speed = originalSpeed; super.move(direction -
		 * 180); btnLastPressed = "S"; }
		 */
		if (pressed(VK_A)) {
			if (pressed(VK_W))
				direction = 225;
			else if (pressed(VK_S))
				direction = 135;
			else
				direction = 180;
			speed = originalSpeed;
			super.move();
		} else if (pressed(VK_D)) {
			if (pressed(VK_W))
				direction = 225 + 90;
			else if (pressed(VK_S))
				direction = 135 - 90;
			else
				direction = 0;
			speed = originalSpeed;
			super.move();
		} else if (pressed(VK_W)) {
			direction = 270;
			speed = originalSpeed;
			super.move();
			// btnLastPressed = "W";

		} else if (pressed(VK_S)) {
			direction = 90;
			speed = originalSpeed;
			super.move();
			// super.move(direction - 180);
			// btnLastPressed = "S";
		} else
			speed = 0;

	}

	// private String btnLastPressed = "W";

	@Override
	public void act() {
		super.act();
	};

	@Override
	public void update(Graphics2D g) {
		direction %= 360;
		/*
		 * if (pressed(VK_SPACE) && (pressed(VK_W) ||
		 * btnLastPressed.equalsIgnoreCase("W"))) { w.direction = direction;
		 * w.update(g); } else if (pressed(VK_SPACE) && (pressed(VK_S) ||
		 * btnLastPressed.equalsIgnoreCase("S"))) { w.direction = direction -
		 * 180; w.update(g); } else ;
		 */

		if (health < oHealth)
			health += (numberKilled - numKilledLastCheck) * 5;
		numKilledLastCheck = numberKilled;

		if (pressed(VK_SPACE) && mouseLocation != null) {
			w.direction = getDirectionTowards(mouseLocation);
			w.update(g);
		} else if (mouseLocation == null)
			mouseLocation = Mouse.getPointerLocation();

		super.update(g);
	}

	@Override
	public void draw(Graphics2D g) {
		Color temp = g.getColor();
		g.setColor(Color.BLUE);
		g.fill(bounds);
		g.setColor(temp);
	}

	@Override
	public void die() {
		Graphics2D g = Game.imageGraphics;
		
		Color temp = g.getColor();
		g.setColor(new Color(158, 59, 51));
		Font tempF = g.getFont();
		g.setFont(new Font("SansSerif", Font.BOLD, 50));
		g.drawString("Game Over!\nEnemies Killed: " + numberKilled
				+ "\nWave Number: " + Game.getWave()
				+ "\n\nPress 'R' to restart.\n(RESTART NOT WORKING!)",
				35, Game.HEIGHT / 2);		
		g.setFont(tempF);
		g.setColor(temp);

		
		Main.game.pause();
		try {
			Game.state.saveHighScore(Game.prompt("What's your name?"),
					numberKilled);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		super.die();

		return;

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		keysPressed.add(k);
		// btnLastPressed=(k==controls[0]||k==controls[2])?k:btnLastPressed;
		if (k == VK_R)
			Main.game.restart();
		else
			;
	}

	public int numberKilled;
	private int numKilledLastCheck;

	@Override
	public void keyReleased(KeyEvent e) {
		keysPressed.remove(e.getKeyCode());
	}

	private boolean pressed(int a) {
		return keysPressed.contains(a);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	private Location mouseLocation;

	@Override
	public void mouseMoved(MouseEvent e) {
		if (pressed(VK_SPACE)) {
			Location fromCorner = new Location(Main.game.getLocationOnScreen());
			Location mPos = new Location(e.getLocationOnScreen());
			mouseLocation = new Location(mPos.x - fromCorner.x, mPos.y
					- fromCorner.y);
		}
		// System.out.println(e.getLocationOnScreen());

	}

	public double getDirectionTowardsMouse() {
		return getDirectionTowards(mouseLocation);
	}

}
