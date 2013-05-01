package com.taco.world;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.hypot;
import static java.lang.Math.round;

import java.awt.Point;
import java.awt.geom.Point2D;

import com.taco.actor.Entity;

public class Location extends Point2D {

	public double x, y;

	private Location() {
		x = 0;
		y = 0;
	}

	public Location(Location l) {
		x = l.x;
		y = l.y;
	}

	public Location(Point p) {
		x = p.x;
		y = p.y;
	}

	public Location(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Location(int x, int y) {
		this((double) x, (double) y);
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public static Location getLocation(double x, double y) {
		return new Location(x, y);
	}

	public static Location getBlankLocation() {
		return new Location();
	}

	public double getDistanceTo(Entity e) {
		if (e != null && !e.isDead())
			return hypot(x - e.getMidpoint().x, y - e.getMidpoint().y);
		else
			return 0;
	}

	public double getDistanceTo(Location l) {
		return hypot(x - l.x, y - l.y);
	}

	public double getDirectionTowards(Entity e) {
		Location l = e.getMidpoint();
		return getDirectionTowards(l);
	}

	public double getDirectionTowards(Location l) {
		if (l == null)
			return Math.random() * 360;
		double dir = ((atan2((l.y - y), (l.x - x))));
		dir = dir * (180 / PI);
		dir *= 10000;
		dir = round(dir);
		dir /= 10000;
		if (dir < 0)
			dir += 360;
		// System.out.println(dir);
		return dir;
	}

	@Override
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}

}
