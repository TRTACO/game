package com.taco.actor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import com.taco.geometry.Rectangle;
import com.taco.main.Main;
import com.taco.world.Location;
import com.taco.world.World;

public class Entity {

	protected boolean canMove, canTakeDamage;
	protected Rectangle r;
	protected Image i;
	protected double direction;
	protected World w = Main.game.getWorld();
	protected int damageDoes;
	protected int health = 100;
	protected int oHealth = health;
	protected Color color;

	protected double width, height;

	public Location getLocation() {
		return getMidpoint();
	}

	public Entity(double x, double y, double w, double h) {
		// Initiate r to the image's height and width HERE.
		this();

		width = w;
		height = h;
		r = new Rectangle(x, y, w, h);
	}

	public Entity() {
		r = new Rectangle();
		oHealth = health;
	}

	public void update(Graphics2D g) {
		Color c = g.getColor();
		Location mid = getMidpoint();
		g.rotate(direction * ((Math.PI * 2) / 360), mid.x, mid.y);
		draw(g);
		g.rotate(-direction * ((Math.PI * 2) / 360), mid.x, mid.y);
		g.setColor(c);
	}

	public void draw(Graphics2D g) {
		g.draw(r);
	}

	public void moveTo(Location l) {
		r.setRect(r.moveTo(l));
	}

	public void moveTo(double x, double y) {
		r.setRect(r.moveTo(x, y));
	}

	public void moveToByMidpoint(double x, double y) {
		r.setRect(r.moveToByMidpoint(x, y));
	}

	public void moveToByMidpoint(Location l) {
		r.setRect(r.moveToByMidpoint(l));
	}

	public Rectangle getBounds() {
		return r;
	}

	public Location getMidpoint() {
		try {
			return r.getMidpoint();
		} catch (NullPointerException e) {
			return null;
		}
	}

	@Override
	protected Entity clone() throws CloneNotSupportedException {
		Entity e;
		if (this instanceof Player)
			e = new Player();
		else if (this instanceof Actor)
			e = new Actor();
		else
			e = new Entity();
		e.r = this.r.moveTo(w.getRandLocation());
		e.canMove = this.canMove;
		e.color = this.color;
		e.damageDoes = this.damageDoes;
		e.oHealth = this.oHealth;
		e.width = this.width;
		e.height = this.height;
		e.canTakeDamage = this.canTakeDamage;
		return e;
	}

	public boolean canMove() {
		return canMove;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double d) {
		direction = d;
	}

	public double getDirectionTowards(Entity e) {
		return r.getMidpoint().getDirectionTowards(e);
	}

	public double getDirectionTowards(Location l) {
		return r.getMidpoint().getDirectionTowards(l);
	}

	public double getDistanceTo(Entity e) {
		return r.getMidpoint().getDistanceTo(e);
	}

	public boolean doesDamage() {
		return damageDoes > 0;
	}

	public int getHealth() {
		return health;
	}

	public boolean canTakeDamage() {
		return canTakeDamage;
	}

	public boolean takeDamage(int damage) {
		if (health <= 0)
			die();
		if (canTakeDamage) {
			health -= damage;
			return true;
		} else {
			return false;
		}
	}

	public boolean inflictDamage(Entity e) {
		return e.takeDamage(damageDoes);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color c) {
		color = c;
	}

	public void die() {
		r = null;
		width = 0;
		height = 0;
		canMove = false;
		health = 0;
		canTakeDamage = false;
		damageDoes = 0;
		i = null;
		color = null;
		w.removeEntity(this);
	}

	public boolean isDead() {
		return (health <= 0);
	}

}
