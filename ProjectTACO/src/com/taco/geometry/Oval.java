package com.taco.geometry;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Oval extends Ellipse2D {

	public Oval() {
		bounds = new Rectangle();
	}

	public Oval(Rectangle r) {
		this();
		bounds = new Rectangle(r);
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

	@Override
	public void setFrame(double x, double y, double w, double h) {
		bounds.setFrame(x, y, w, h);
	}

}
