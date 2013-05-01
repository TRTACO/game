package com.taco.actor;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.Graphics2D;

import com.taco.world.Location;
import com.taco.world.World;

public class Actor extends Entity {

	{
		canMove = true;
		canTakeDamage = true;
	}

	public Actor(double x, double y, double w, double h) {
		super(x, y, w, h);
	}

	public Actor() {
		super();
	}

	protected double speed = 2;
	protected double originalSpeed = speed;

	@Override
	public boolean canMove() {
		return canMove;
	}

	public void move() {
		Location newLocation = getMidpoint();
		// IGNORE FOR NOW: sin, instead of cos because of compass directions
		newLocation.x += cos((PI / 180) * (direction)) * speed;
		newLocation.y += sin((PI / 180) * (direction)) * speed;
		moveToByMidpoint(newLocation);
	}

	protected void move(double direction) {
		Location newLocation = getMidpoint();
		// IGNORE FOR NOW: sin, instead of cos because of compass directions
		newLocation.x += cos((PI / 180) * (direction)) * speed;
		newLocation.y += sin(((PI) / 180) * (direction)) * speed;
		moveToByMidpoint(newLocation);
	}

	@Override
	public void update(Graphics2D g) {
		if (health <= 0) {
			die();
			return;
		}

		if (canMove && World.isInWorld(this))
			act();
		else if (!World.isInWorld(this) && !isDead()) {
			if (bounds.getMinX() < 0)
				bounds.setRect(bounds.translate(-bounds.getMinX() + 1, 0));
			if (bounds.getMinY() < 0)
				bounds.setRect(bounds.translate(0, -bounds.getMinY() + 1));
			if (bounds.getMaxX() > World.MAX.x)
				bounds.setRect(bounds.translate(World.MAX.x - bounds.getMaxX() - 1, 0));
			if (bounds.getMaxY() > World.MAX.y)
				bounds.setRect(bounds.translate(0, World.MAX.y - bounds.getMaxY() - 1));
			// speed*=-1;

		}
		if (!isDead())
			super.update(g);
	}

	public void act() {
		move();
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double s) {
		speed = s;
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}

}
