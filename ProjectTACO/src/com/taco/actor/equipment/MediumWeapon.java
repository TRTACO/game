package com.taco.actor.equipment;

import com.taco.actor.Entity;

public class MediumWeapon extends Weapon {

	public MediumWeapon(Entity owner) {
		super(owner);
		damageDoes = 5;
		delay = (int) (super.delay * 1.2);
		oDelay = delay;
	}

}
