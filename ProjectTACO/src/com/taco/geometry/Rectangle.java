package com.taco.geometry;

import java.awt.geom.Rectangle2D;

import com.taco.world.Location;

public class Rectangle extends java.awt.geom.Rectangle2D {

	protected double topXL, topXR, bottomXL, bottomXR, topYL, topYR, bottomYL,
			bottomYR;
	protected double width, height;
	protected double midX, midY;

	// Creates a blank rectangle with all fields initialized to 0
	public Rectangle() {
	}

	public Rectangle(double topLeftX, double topLeftY, double width,
			double height) {
		topXL = bottomXL = topLeftX;
		topYL = topYR = topLeftY;
		topXR = bottomXR = topXL + width;
		bottomYL = bottomYR = topYL + height;
		this.width = width;
		this.height = height;
		updateMidpoint();
	}

	public Rectangle(Rectangle r) {
		this(r.topXL, r.topYL, r.width, r.height);
	}

	public Location getMidpoint() {
		return new Location(midX, midY);
	}

	public double getArea() {
		return (topXR - topXL) * (bottomYR - topYR);
	}

	// Sets the width of this rectangle, changing the right coordinates of the
	// rectangle by delta width.
	public void setWidth(double width) {
		this.width = width;
		bottomXR = topXR = topXL + width;
	}

	// Sets the height of this rectangle, changing the bottom coordinates of the
	// rectangle by delta height.
	public void setHeight(double height) {
		this.height = height;
		bottomYR = bottomYL = topYL + height;
	}

	// If you make changes to the coordinate of anything then call this to
	// update the midpoint location
	protected void updateMidpoint() {
		midX = (topXL + topXR) / 2;
		midY = (topYL + bottomYL) / 2;
	}

	// Moves a clone of the rectangle down by y and right by x
	// and then returns the new rectangle
	public Rectangle translate(double x, double y) {
		Rectangle temp = clone();
		temp.topXL += x;
		temp.topYL += y;
		updateAllPointsFromTopLeft(temp);
		return temp;
	}

	// Moves a clone of the rectangle to (y, x)
	// by its top-left point and then returns the new rectangle
	public Rectangle moveTo(double x, double y) {
		Rectangle temp = clone();
		temp.topXL = x;
		temp.topYL = y;
		updateAllPointsFromTopLeft(temp);
		return temp;
	}

	public Rectangle moveTo(Location l) {
		return moveTo(l.x, l.y);
	}

	public Rectangle moveToByMidpoint(double x, double y) {
		Rectangle temp = clone();
		temp.topXL = x - width / 2;
		temp.topYL = y - height / 2;
		updateAllPointsFromTopLeft(temp);
		return temp;
	}

	public Rectangle moveToByMidpoint(Location l) {
		return moveToByMidpoint(l.x, l.y);
	}

	// Moves top-left coordinate to 0 and adjusts other points accordingly.
	// Does not change the rectangles width or height.
	public Rectangle moveToOrigin() {
		Rectangle temp = clone();
		temp.topYR = temp.topYL = 0;
		temp.topXL = temp.bottomXL = 0;
		updateAllPointsFromTopLeft(temp);
		return temp;
	}

	// Checks if this rectangle is in r. If this rectangle touches any edge or
	// is not in the rectangle at all then this will return false.
	public boolean isIn(Rectangle r) {
		if (r.topXR > topXR && r.topXL < topXL && r.topYL < topYL
				&& r.bottomYL < bottomYL)
			return true;
		else
			return false;
	}

	// Combines this rectangle and r, forming the largest possible rectangle
	// from all coordinates (x and y) of this rectangle and r. Returns the new
	// rectangle.
	public Rectangle union(Rectangle r) {
		return new Rectangle(
		// Top left x coord
				(this.topXL < r.topXL) ? (this.topXL) : (r.topXL),
				// Top left y coord
				(this.topYL < r.topYL) ? (this.topYL) : (r.topYL),
				// Width
				((this.topXR > r.topXR) ? (this.topXR) : (r.topXR))
						- ((this.topXL < r.topXL) ? (this.topXL) : (r.topXL)),
				// Height
				((this.bottomYL > r.bottomYL) ? (this.bottomYL) : (r.bottomYL))
						- ((this.topYL < r.topYL) ? (this.topYL) : (r.topYL)));
	}

	public Rectangle union(Rectangle2D r) {
		return new Rectangle(
		// Top left x coord
				(this.topXL < r.getX()) ? (this.topXL) : (r.getX()),
				// Top left y coord
				(this.topYL < r.getY()) ? (this.topYL) : (r.getY()),
				// Width
				((this.topXR > r.getWidth() + r.getX()) ? (this.topXR) : (r
						.getWidth() + r.getX()))
						- ((this.topXL < r.getX()) ? (this.topXL) : (r.getX())),
				// Height
				((this.bottomYL > r.getY() + r.getHeight()) ? (this.bottomYL)
						: (r.getY() + r.getHeight()))
						- ((this.topYL < r.getY()) ? (this.topYL) : (r.getY())));
	}

	public Location getUpperLeftPoint() {
		return new Location(topXL, topYL);
	}

	public Location getUpperRightPoint() {
		return new Location(topXR, topYR);
	}

	public Location getBottomLeftPoint() {
		return new Location(bottomXL, bottomYL);
	}

	public Location getBottomRightPoint() {
		return new Location(bottomXR, bottomYR);
	}

	// Returns a clone of this rectangle
	@Override
	public Rectangle clone() {
		return new Rectangle(topXL, topYL, width, height);
	}

	// Returns all coordinates of this rectangle in the format
	// (topLeftX, topLeftY) (topRightX, topRightY) (bottomLeft... )
	// (bottomRight... )
	@Override
	public String toString() {
		return "(" + topXL + ", " + topYL + ") " + "(" + topXR + ", " + topYR
				+ ") " + "(" + bottomXL + ", " + bottomYL + ") " + "("
				+ bottomXR + ", " + bottomYR + ")";
	}

	// Checks if o is equal to this rectangle. Only compares the top-left
	// coordinate, the width, and the height.
	@Override
	public boolean equals(Object o) {
		if (o instanceof Rectangle) {
			Rectangle r = (Rectangle) o;
			return (topXL == r.topXL && topYL == r.topYL && width == r.width && height == r.height) ? (true)
					: (false);
		}
		return false;
	}

	// If you move the top-left coordinate and want all of the other ones to
	// move the same distance then this will update all of those points.
	protected static void updateAllPointsFromTopLeft(Rectangle r) {
		r.bottomXL = r.topXL;
		r.topYR = r.topYL;
		r.topXR = r.bottomXR = r.topXL + r.width;
		r.bottomYR = r.bottomYL = r.topYL + r.height;
		r.updateMidpoint();
	}

	// Implemented exactly the same as createUnion(Rectangle2D r) for now
	@Override
	public Rectangle2D createIntersection(Rectangle2D r) {
		// TODO Auto-generated method stub
		return union(r);
	}

	@Override
	public Rectangle2D createUnion(Rectangle2D r) {
		return union(r);
	}

	// Not implemented from Rectangle2D
	@Override
	public int outcode(double x, double y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRect(double x, double y, double w, double h) {
		topXL = x;
		topYL = y;
		width = w;
		height = h;
		updateAllPointsFromTopLeft(this);
	}

	@Override
	public double getHeight() {
		return height;
	}

	@Override
	public double getWidth() {
		return width;
	}

	// Simply returns the top-left x coord of the rect.
	@Override
	public double getX() {
		return getUpperLeftPoint().x;
	}

	// Simply returns the top-left y coord of the rect.
	@Override
	public double getY() {
		return getUpperLeftPoint().y;
	}

	@Override
	public boolean isEmpty() {
		if (getArea() > 0)
			return true;
		else
			return false;
	}

}
