package com.taco.geometry;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Iterator;

import com.taco.world.Location;

public class Path implements Iterable<Location> {

	protected ArrayList<Double> xs = new ArrayList<Double>();
	protected ArrayList<Double> ys = new ArrayList<Double>();

	private boolean finalized = false;

	public Path() {

	}

	public Path(Location[] locs) {
		for (Location l : locs) {
			xs.add(l.x);
			ys.add(l.y);
		}
	}

	public Path(Path p) {
		for (Location l : p) {
			xs.add(l.x);
			ys.add(l.y);
		}
	}

	public Rectangle getBounds() {
		double minX = Double.MAX_VALUE, maxX = 0, minY = Double.MAX_VALUE, maxY = 0;
		for (double x : xs) {
			if (x > maxX)
				maxX = x;
			else if (x < minX)
				minX = x;
		}
		for (double y : ys) {
			if (y > maxY)
				maxY = y;
			else if (y < minY)
				minY = y;
		}
		return new Rectangle(minX, minY, maxX - minX, maxY - minY);
	}

	public void add(Path p) {
		if (!finalized)
			for (Location l : p) {
				addPoint(l.x, l.y);
			}
		else
			throw new java.lang.RuntimeException(
					"Path has already been closed. Will not add. Make a new path with Path(Path p)");
	}

	public void addPoint(double x, double y) {
		if (!finalized) {
			xs.add(x);
			ys.add(y);
		} else
			throw new java.lang.RuntimeException(
					"Path has already been closed. Will not add. Make a new path with Path(Path p)");
	}

	public void addPoint(Location l) {
		if (!finalized) {
			xs.add(l.x);
			ys.add(l.y);
		} else
			throw new java.lang.RuntimeException(
					"Path has already been closed. Will not add. Make a new path with Path(Path p)");
	}

	@Override
	public String toString() {
		String s = "";
		for (Location l : this) {
			s += "(" + l.x + ", " + l.y + ") ";
		}
		s = s.substring(s.length() - 1);
		return s;
	}

	@Override
	public Iterator<Location> iterator() {
		return new Iterator<Location>() {

			Iterator<Double> x;
			Iterator<Double> y;

			{
				x = xs.iterator();
				y = ys.iterator();
			}

			@Override
			public boolean hasNext() {
				return (x.hasNext() && y.hasNext());
			}

			@Override
			public Location next() {
				return new Location(x.next(), y.next());
			}

			@Override
			public void remove() {

			}

		};
	}

	public Polygon asPolygon(){
		Polygon p = new Polygon();
		for(Location l : this){
			p.addPoint((int)Math.round(l.x), (int)Math.round(l.y));
		}
		return p;
	}
	
	public void close() {
		addPoint(xs.get(0), ys.get(0));
		finalized = true;
	}
	
	public boolean hasNext(){
		return !xs.isEmpty() && !ys.isEmpty();
	}
	
	public Location next(){
		return new Location(xs.remove(0),ys.remove(0));
	}

	@Override
	public Path clone() {
		Path p = new Path(this);
		p.finalized = finalized;
		return p;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Path) {
			Path p = (Path) o;
			return p.xs.equals(xs) && p.ys.equals(ys);
		}
		return false;
	}

}
