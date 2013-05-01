package com.taco.actor.equipment;

import com.taco.actor.Entity;

public class HeavyWeapon extends Weapon {

	public HeavyWeapon(Entity owner) {
		super(owner);
		damageDoes = 10;
		delay = (short) (super.delay * 1.5);
		oDelay = delay;
	}

}
