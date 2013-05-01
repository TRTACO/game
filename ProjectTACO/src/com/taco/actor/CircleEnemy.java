package com.taco.actor;

import java.awt.Graphics2D;

public class CircleEnemy extends Enemy {

	public CircleEnemy() {

	}

	public void draw(Graphics2D g) {
		g.draw(bounds);
	}

}
