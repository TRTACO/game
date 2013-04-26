package com.taco.level;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.taco.actor.Enemy;
import com.taco.world.World;

public class Wave extends HashSet<Enemy> {

	public Wave() {
		this(null);
	}

	public Wave(Collection<Enemy> enemies) {
		if (enemies == null)
			return;
		addAll(enemies);
	}

	public void randomize() {
		for (Enemy e : this) {
			e.moveToByMidpoint(World.getRandLocation());
		}
	}

	public Set<Enemy> getEnemies() {
		return Collections.synchronizedSet(this);
	}

	public boolean add(String cls, int times) {
		try {
			Class e = Class.forName(cls);
			if (!Enemy.class.isAssignableFrom(e))
				return false;
			else {
				while (times > 0) {
					add((Enemy) e.newInstance());
				}
			}
		} catch (ClassNotFoundException e) {
			return false;
		} catch (InstantiationException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Enemy[] toArray() {
		Object[] arr = super.toArray();
		Enemy[] ens = new Enemy[arr.length];
		for (int i = 0; i < arr.length; i++) {
			ens[i] = (Enemy) arr[i];
		}
		return ens;
	}

	@Override
	public void finalize() throws Throwable {
		super.finalize();
		clear();
	}

}