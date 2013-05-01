package com.taco.geometry;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import com.taco.world.Location;

public class Oval extends Ellipse2D {

	public Oval() {
		super();
		bounds = new Rectangle();
	}

	public Oval(Rectangle r) {
		this();
		bounds.setFrame(r);
	}

	public Oval(double x, double y, double width, double height) {
		this();
		bounds.setFrame(x, y, width, height);
	}

	private Rectangle bounds;

	@Override
	public Rectangle2D getBounds2D() {
		return bounds;
	}

	@Override
	public double getHeight() {
		return bounds.height;
	}

	@Override
	public double getWidth() {
		return bounds.width;
	}

	@Override
	public double getX() {
		return bounds.getX();
	}

	@Override
	public double getY() {
		return bounds.getY();
	}

	@Override
	public boolean isEmpty() {
		return bounds.isEmpty();
	}

	public Location getMidpoint() {
		return new Location(getCenterX(), getCenterY());
	}

	@Override
	public void setFrame(double x, double y, double w, double h) {
		bounds.setFrame(x, y, w, h);
	}

	@Override
	public Rectangle2D getFrame() {
		return bounds;
	}

	@Override
	public void setFrame(Rectangle2D r) {
		this.setFrame(new Rectangle(r));
	}

	public void setFrame(Rectangle r) {
		bounds.setRect(r);
	}

	public Oval get() {
		return this.clone();
	}

	@Override
	public Oval clone() {
		return new Oval(bounds);
	}

	public void set(Oval o) {
		bounds = o.bounds;
	}

}
