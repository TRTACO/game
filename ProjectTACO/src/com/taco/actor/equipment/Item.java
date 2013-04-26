package com.taco.actor.equipment;

import java.awt.Graphics2D;

import com.taco.actor.*;

public abstract class Item extends Entity {

	protected Entity owner;

	public Item(double x, double y, double w, double h) {
		super(x, y, w, h);
	}

	public Item() {

	}

	@Override
	public abstract void draw(Graphics2D g);

}
