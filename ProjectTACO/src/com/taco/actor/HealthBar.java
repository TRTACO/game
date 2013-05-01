package com.taco.actor;

import java.awt.Color;
import java.awt.Graphics2D;

import com.taco.geometry.Rectangle;
import com.taco.world.*;

public class HealthBar extends Entity {
	
	protected Entity owner;
	protected double originalWidth;
	protected double originalHealth;
	private Rectangle originalBar;
	
	public HealthBar(Entity e, Location l){
		super();
		width=200;
		bounds = new Rectangle(l.x,l.y,width,40);
		originalWidth=width;
		owner=e;
		originalHealth = e.health;
		originalBar=new Rectangle(bounds);
		canMove=false;
	}

	@Override
	public void draw(Graphics2D g) {
		bounds.setWidth((owner.health/originalHealth)*200);
		
		g.setColor(Color.DARK_GRAY);
		g.fill(originalBar);
		
		g.setColor(Color.GREEN);
		g.fill(bounds);
	}

}
