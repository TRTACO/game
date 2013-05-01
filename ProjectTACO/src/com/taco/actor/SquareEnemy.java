package com.taco.actor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import com.taco.actor.equipment.Weapon;
import com.taco.geometry.Square;
import com.taco.world.Location;
import com.taco.world.World;

public class SquareEnemy extends Enemy {

	private Square square;
	private static final Random rand = new Random();

	/*
	 * private SquareEnemy() { this(new Square(50, 50, 50), Color.RED); weapon =
	 * new Weapon(this); }
	 */

	public SquareEnemy() {
		this(new Square(World.getRandLocation(), 18), Color.YELLOW);
	}

	public SquareEnemy(Square s, Color c) {
		super(s.getX(), s.getY(), s.getWidth(), s.getHeight());
		square = s;
		color = c;
		bounds = s;
		speed = 2 + (double) ((rand.nextInt(50)) - 25) / 60
				+ ((Math.floor(Math.random() * 10)) / s.getHeight());
		originalSpeed = speed;
		health = 10;
		weapon = new Weapon(this);
		weapon.setDelay(100);
	}

	@Override
	public void draw(Graphics2D g) {
		square.setRect(bounds);
		Color tempColor = g.getColor();
		g.setColor(color);
		g.fill(square);
		g.setColor(Color.YELLOW);
		g.draw(square);
		g.setColor(tempColor);
	}

	@Override
	public void die() {
		w.add(s1 = new MiniSquareEnemy(this));
		w.add(s2 = new MiniSquareEnemy(this));
		super.die();
	}

	private MiniSquareEnemy s1, s2;

	public static class MiniSquareEnemy extends Enemy {

		private SquareEnemy parent;
		private Color c;

		private double turnSpeed;

		private Square square;

		private MiniSquareEnemy(double x, double y, double w, double h) {
			super(x, y, w, h);
			health = 5;
		}

		private MiniSquareEnemy(Square s, Color c) {
			this(s.getX(), s.getY(), s.getWidth(), s.getHeight());
			this.c = c.brighter();
		}

		public MiniSquareEnemy(SquareEnemy parent) {
			this(new Square(0, 0, 0), parent.color);
			this.parent = parent;
			/*
			 * (Original Line) speed = parent.speed * 3.2 - (rand.nextInt(55) /
			 * 60.0);
			 */

			speed = parent./**/originalSpeed/**/* 4.7
					- ((rand.nextInt(55)/**/- 25/**/) / 60);

			originalSpeed = speed;
			bounds = parent.bounds.translate(
					Math.cos(((Math.PI) / 180) * parent.direction)
							* rand.nextInt(15) * speed,
					Math.sin((Math.PI) / 180 * parent.direction)
							* rand.nextInt(15) * speed);
			bounds.setWidth(bounds.getWidth() / 2);
			square = new Square(bounds.getX(), bounds.getY(), bounds.getWidth());
			canMove = true;
			bounds.setRect(square);
			turnSpeed = 3;
		}

		@Override
		public void update(Graphics2D g) {
			// Adapted from Actor.java
			Color c = g.getColor();

			if (health <= 0) {
				die();
				return;
			}

			if (canMove && World.isInWorld(this))
				act();
			else if (!World.isInWorld(this) && !isDead()) {
				if (bounds.getMinX() < 0) {
					bounds.setRect(bounds.translate(-bounds.getMinX() + 1, 0));
				}
				if (bounds.getMinY() < 0) {
					bounds.setRect(bounds.translate(0, -bounds.getMinY() + 1));
				}
				if (bounds.getMaxX() > World.MAX.x) {
					bounds.setRect(bounds.translate(
							World.MAX.x - bounds.getMaxX() - 1, 0));
				}
				if (bounds.getMaxY() > World.MAX.y) {
					bounds.setRect(bounds.translate(0,
							World.MAX.y - bounds.getMaxY() - 1));
				}
				if (player != null)
					setDirection(getDirectionTowards(player));
			}

			square = new Square(bounds.getX(), bounds.getY(), bounds.getWidth());

			if (!isDead()) {
				Location mid = getMidpoint();
				g.rotate(direction, mid.x, mid.y);
				draw(g);
				g.rotate(-direction, mid.x, mid.y);
			}
			g.setColor(c);
		}

		@Override
		public void act() {
			if (player == null || !w.getMap().containsKey(player))
				player = w.getPlayer();
			if (player == null)
				return;
			if (getDistanceTo(player) > player.width / 2 + width / 2) {
				speed = originalSpeed;
			} else {
				speed = 0;
				if (--currentDelay < 0) {
					inflictDamage(player);
					currentDelay = DELAY;
				}
			}
			double d = getDirectionTowards(player);
			direction = (((d + 180) % 360 > direction) ? Math.min(d,
					(direction + turnSpeed) % 360) : Math.max(d,
					(direction - turnSpeed) % 360)) % 360;
			super.move();
		}

		/*
		 * System.out.println(d); if (Math.abs(d - direction) >= 350) { if (d -
		 * turnSpeed < direction) direction += turnSpeed; else if (d + turnSpeed
		 * > direction) direction -= turnSpeed; else if (Math.abs(d - direction)
		 * >= 355) if (d < direction) direction += 0.5; //WHY? else if (d <
		 * direction) direction -= 0.5; } else { if (d - turnSpeed > direction)
		 * direction += turnSpeed; else if (d + turnSpeed < direction) direction
		 * -= turnSpeed; else if (Math.abs(d - direction) >= 0.5) if (d >
		 * direction) direction += 0.5; else if (d < direction) direction -=
		 * 0.5; }
		 * 
		 * speed = originalSpeed;you should not need this here } else if (player
		 * != null) { speed=0; if(--currentDelay<0){ inflictDamage(player);
		 * currentDelay=DELAY; } } else { speed = originalSpeed / 2; direction
		 * += 2;
		 */

		@Override
		public void draw(Graphics2D g) {
			g.setColor(c);
			g.fill(square);
			g.setColor(c.darker());
			g.draw(square);
		}
	}

}
