package com.taco.actor.equipment;

import com.taco.actor.Entity;
import com.taco.geometry.Path;
import com.taco.world.Location;

public class HomingWeapon extends Weapon {
	
	public HomingWeapon(Entity owner) {
		super(owner);
		delay = 45;
		oDelay = delay;
	}
	
	@Override
	public void shoot() {
		Entity target = w.getPlayer();
		Location l = target.getLocation();
		Location lI;
		if(l.x > getLocation().x && l.y < getLocation().y)
			lI = Location.getLocation(l.x-10, l.y-10);
		else if(l.x < getLocation().x && l.y < getLocation().y)
			lI = Location.getLocation(l.x+10, l.y-10);
		else if(l.x < getLocation().x && l.y > getLocation().y)
			lI = Location.getLocation(l.x+10, l.y+10);
		else if(l.x > getLocation().x && l.y > getLocation().y)
			lI = Location.getLocation(l.x-10, l.y+10);
		else
			lI = l;
		Path p = new Path();
		p.addPoint(lI);
		p.addPoint(l);
		Bullet b = new Bullet(owner, p);
		bullets.add(b);
		w.add(b);
		delay=oDelay;
	}

}
