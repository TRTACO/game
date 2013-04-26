package com.taco.actor.equipment;

import com.taco.actor.Entity;

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
			LightWeapon w = new LightWeapon(this) {
				@Override
				public double getDirection() {
					return (owner.getDirection() + (angleRange * thisIndex)
							/ (weapons.length) - 0.5 * angleRange);
				}
			};
			weapons[index] = w;
		}
	}

	@Override
	public void shoot() {
		for (Weapon w : weapons) {
			w.shoot();
		}
		delay = oDelay;
	}

}
