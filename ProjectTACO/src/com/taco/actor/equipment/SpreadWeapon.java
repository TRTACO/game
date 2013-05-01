package com.taco.actor.equipment;

import java.awt.Graphics2D;

import com.taco.actor.Entity;
import com.taco.actor.Player;

public class SpreadWeapon extends LightWeapon {

	private final static int DEFAULT_NUM_SPREAD = 3;
	private LightWeapon[] weapons;
	private double angleRange;

	public SpreadWeapon(Entity owner) {
		super(owner);
		weapons = new LightWeapon[DEFAULT_NUM_SPREAD];
		angleRange = 90;
		initWeapons();
	}

	public SpreadWeapon(Entity owner, int numSpreadWeapons) {
		super(owner);
		weapons = new LightWeapon[numSpreadWeapons];
		angleRange = 30 * numSpreadWeapons;
		initWeapons();
	}

	public SpreadWeapon(Entity owner, int numSpreadWeapons, double angleRange) {
		super(owner);
		weapons = new LightWeapon[numSpreadWeapons];
		this.angleRange = angleRange;
		initWeapons();
	}

	private void initWeapons() {
		for (int index = 0; index < weapons.length; index++) {
			final int thisIndex = index;
			final double t = 0.5 * angleRange - (angleRange * thisIndex)
					/ (weapons.length);
			LightWeapon w = new LightWeapon(owner);
			weapons[index] = w;
		}
	}

	@Override
	public void shoot() {
		int i = 0;
		for (Weapon w : weapons) {
			w.setDirection(((owner instanceof Player) ? ((Player) (owner))
					.getDirectionTowardsMouse()
					+ 0.5
					* angleRange
					- (angleRange * i) / (weapons.length) : (owner
					.getDirection() + 0.5 * angleRange - (angleRange * i)
					/ (weapons.length))));
			w.shoot();
			i++;
		}
		delay = oDelay;
	}

	@Override
	public void update(Graphics2D g) {
		super.update(g);
	}

}
