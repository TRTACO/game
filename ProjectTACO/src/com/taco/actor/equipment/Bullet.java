package com.taco.actor.equipment;

import static java.lang.Math.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import com.taco.actor.Enemy;
import com.taco.actor.Entity;
import com.taco.actor.Player;
import com.taco.geometry.Path;
import com.taco.geometry.Rectangle;
import com.taco.world.Location;
import com.taco.world.World;

public class Bullet extends Item {

	protected Path path;
	protected double direction;
	protected double speed = 20;
	private Location headingTowards;
	//private Iterator<Location> locs;

	{
		width = 10;
		height = 10;
	}

	/*private Bullet(double x, double y, double w, double h) {
		super(x, y, w, h);
	}*/

	private Bullet() {
		super();
		damageDoes = 5;
		health = 1000000;
	}

	public Bullet(Location initLoc, Path p) {
		this();
		this.path = p;
		r.setRect(new Rectangle(initLoc.x - width / 2, initLoc.y - height / 2,
				width, height));
		headingTowards = path.next();
		this.direction = getLocation().getDirectionTowards(headingTowards);
	}

	public Bullet(Location initLoc, double direction) {
		this();
		r.setRect(new Rectangle(initLoc.x - width / 2, initLoc.y - height / 2,
				width, height));
		this.direction = direction;
	}
	
	public Bullet(Entity owner, double direction){
		this(owner.getLocation(),direction);
		this.owner = owner;
	}
	
	public Bullet(Entity owner, Path p){
		this(owner.getLocation(), p);
		this.owner = owner;
	}
	
	public void move() {

		// Hurt anything within range of bullet.
		HashSet<Entity> set = new HashSet<Entity>(w.getMap().keySet());
		set.remove(this);
		set.remove(owner);
		Entity[] e ={};
		e=set.toArray(e);
		for(int i = 0; i < e.length; i++){
			//Entity.class.
			if(owner.getClass().isAssignableFrom(Player.class))
				;
			else if(e[i] instanceof Enemy){
				e[i]=null;
			}
			else;
		}
		set.clear();
		set.addAll(Arrays.asList(e));
		set.remove(null);
		Iterator<Entity> it = set.iterator();
		while (it.hasNext()) {
			try {
				Entity entity = it.next();
				if (entity.getDistanceTo(this) < (entity.getBounds()
						.getHeight() + entity.getBounds().getWidth())
						/ 4
						+ (width + height) / 4) {
					inflictDamage(entity);
					die();
					return;
				}
			} catch (ConcurrentModificationException err) {
				it.remove();
			}
		}

		// Follow on a path or on a direction
		if (path == null) {
			Location newLocation = getMidpoint();
			newLocation.x += cos((PI / 180) * (direction)) * speed;
			newLocation.y += sin((PI / 180) * (direction)) * speed;
			moveToByMidpoint(newLocation);
		} else {
			if (headingTowards == null && path.hasNext())
				headingTowards = path.next();
			else if (headingTowards.getDistanceTo(getLocation()) <= speed
					+ (height + width) / 4) {
				if (path.hasNext())
					headingTowards = path.next();
				else{
					die();
					return;
				}
			}
			
			double turnSpeed = 5;
			
			double d = getDirectionTowards(headingTowards);
			
			direction%=360;
			
			if (abs(d - direction) >= 350) {
				if (d - turnSpeed < direction)
					direction += turnSpeed;
				else if (d + turnSpeed > direction)
					direction -= turnSpeed;
				else if (Math.abs(d - direction) >= 355)
					if (d < direction)
						direction += 0.5;
					else if (d < direction)
						direction -= 0.5;
			} else {
				if (d - turnSpeed > direction)
					direction += turnSpeed;
				else if (d + turnSpeed < direction)
					direction -= turnSpeed;
				else if (Math.abs(d - direction) >= 0.5)
					if (d > direction)
						direction += 0.5;
					else if (d < direction)
						direction -= 0.5;
			}
			Location newLocation = getMidpoint();
			direction = (abs(getDirectionTowards(headingTowards)-direction)>=10)?direction:direction;
			newLocation.x += cos((PI / 180) * (direction)) * speed;
			newLocation.y += sin((PI / 180) * (direction)) * speed;
			moveToByMidpoint(newLocation);
		}
	}

	@Override
	public void update(Graphics2D g) {
		if (World.isInWorld(this)) {
			move();
		} else {
			health = 0;
			die();
		}
		if (!isDead())
			super.update(g);
	}

	@Override
	public void draw(Graphics2D g) {
		Color temp = g.getColor();
		g.setColor(Color.WHITE);
		g.fillOval((int) round(getMidpoint().x - width / 2),
				(int) round(getMidpoint().y - height / 2), (int) round(width),
				(int) round(height));
		g.setColor(temp);
	}

}
