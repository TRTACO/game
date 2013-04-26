package com.taco.actor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.taco.actor.equipment.Item;

public class Player extends Actor {
	
	private Item item;
	
	private final boolean[] KEYS;

	
	public Player(double x, double y, double w, double h, boolean[] k) {
		super(x, y, w, h);
		health = 100000;
		speed=1.7;
		KEYS = k;
	}

	@Override
	public void move(){
		if(KEYS[KeyEvent.VK_W]) {
			moveToByMidpoint(getMidPoint().x, getMidPoint().y - speed);
		}
		if(KEYS[KeyEvent.VK_S]) {
			moveToByMidpoint(getMidPoint().x, getMidPoint().y + speed);
		}
		if(KEYS[KeyEvent.VK_A]) {
			moveToByMidpoint(getMidPoint().x - speed, getMidPoint().y);
		}
		if(KEYS[KeyEvent.VK_D]) {
			moveToByMidpoint(getMidPoint().x + speed, getMidPoint().y);
		}
	}
	

	public static final int BOY = 0;
	public static final int GIRL = 1;
	
	
	
	@Override
	public void draw(Graphics2D g) {
		Color temp = g.getColor();
		g.setColor(Color.BLUE);
		g.fill(r);
		g.setColor(temp);
	}
	
	public void processKeyEvent(KeyEvent e) {
		KEYS[e.getKeyCode()] = e.getID() == 401;
	}
	

}
