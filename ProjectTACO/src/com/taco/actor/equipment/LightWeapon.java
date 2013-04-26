package com.taco.actor.equipment;

import com.taco.actor.Entity;

public class LightWeapon extends Weapon {

	public LightWeapon(Entity owner) {
		super(owner);
		damageDoes = 2;
		delay = (int) (super.delay * 0.25);
		oDelay = delay;
	}

}
