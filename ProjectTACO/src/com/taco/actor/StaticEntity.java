package com.taco.actor;

import java.awt.Graphics2D;

public class StaticEntity extends Entity {

	public StaticEntity(double x, double y, double w, double h) {
		super(x, y, w, h);
	}

	public StaticEntity() {
		super();
	}

	@Override
	public boolean canMove() {
		return false;
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);

	}

}
