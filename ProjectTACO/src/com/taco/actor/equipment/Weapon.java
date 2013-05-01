package com.taco.actor.equipment;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Random;

import com.taco.actor.Entity;
import com.taco.world.Location;

public class Weapon extends Item {

	protected Random rand = new Random();

	protected short delay = 6;
	protected short oDelay = delay;

	public double direction = -100000;

	protected LinkedList<Bullet> bullets = new LinkedList<Bullet>();

	private Weapon(double x, double y, double w, double h) {
		super(x, y, w, h);
	}

	public Weapon(Entity owner) {
		this.owner = owner;
	}

	public void setDelay(short d) {
		delay = d;
		oDelay = d;
	}

	public void setDelay(int d) {
		this.setDelay((short) d);
	}

	@Override
	public double getDirection() {
		if (direction != -100000)
			return direction;
		return owner.getDirection();
	}

	@Override
	public void setDirection(double d) {
		direction = d;
	}

	@Override
	public Location getLocation() {
		return owner.getLocation();
	}

	public void shoot() {
		Bullet b = new Bullet(owner, getDirection());
		bullets.add(b);
		w.add(b);
		delay = oDelay;
	}

	@Override
	public void update(Graphics2D g) {
		delay--;
		// uncomment delay in the if below for something cool!
		if (!owner.isDead() && delay <= 0) {
			shoot();
			removeDeadBullets();
		}
		super.update(g);
	}

	protected void removeDeadBullets() {
		Bullet[] bs = new Bullet[] {};
		bs = bullets.toArray(bs);
		bullets.clear();
		for (int i = 0; i < bs.length; i++) {
			if (!bs[i].isDead())
				bullets.add(bs[i]);
		}
		bs = null;
	}

	@Override
	public void draw(Graphics2D g) {

	}

}
