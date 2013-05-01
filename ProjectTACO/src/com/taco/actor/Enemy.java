package com.taco.actor;

import java.awt.Graphics2D;

import com.taco.actor.equipment.Weapon;

public class Enemy extends Actor {

	protected Player player;
	protected double originalSpeed;
	protected Weapon weapon;

	public Enemy(double x, double y, double w, double h) {
		super(x, y, w, h);
		originalSpeed = speed;
		damageDoes = 1;
	}

	public Enemy() {
		this(0, 0, 0, 0);
	}

	@Override
	public void update(Graphics2D g) {
		if (weapon != null)
			weapon.update(g);
		super.update(g);
	}

	protected final short DELAY = 5;
	protected int currentDelay;

	// Constantly move towards player by updating direction towards it
	@Override
	public void act() {
		if (player == null || !w.getMap().containsKey(player))
			player = w.getPlayer();
		if (player != null
				&& getDistanceTo(player) > player.width / 2 + width / 2) {
			setDirection(getDirectionTowards(player));
			speed = originalSpeed;
		} else if (player != null) {
			speed = 0;
			if (--currentDelay < 0) {
				inflictDamage(player);
				currentDelay = DELAY;
			}
		} else {
			speed = originalSpeed / 2;
			direction += 2;
		}
		super.act();
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}

}
