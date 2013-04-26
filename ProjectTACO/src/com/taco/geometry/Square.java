package com.taco.geometry;

import java.awt.geom.Rectangle2D;

import com.taco.world.Location;

public class Square extends Rectangle {

	protected double sideLength;
	
	public Square(double x, double y, double sideLength){
		super(x,y,sideLength,sideLength);
		this.sideLength = sideLength;
	}
	
	public Square(Location l, double sideLength){
		this(l.x,l.y,sideLength);
	}
	
	@Override
	public void setHeight(double h){
		height=h;
		width=h;
		sideLength=h;
		updateAllPointsFromTopLeft(this);
	}
	
	@Override
	public void setWidth(double w){
		height=w;
		width=w;
		sideLength=w;
		updateAllPointsFromTopLeft(this);
	}
	
	public void setRect(Rectangle r){
		bottomXL=r.bottomXL;
		bottomXR=r.bottomXR;
		bottomYL=r.bottomYL;
		bottomYR=r.bottomYR;
		height=r.height;
		sideLength=r.height;
		width=r.height;
		topXL=r.topXL;
		topXR=r.topXR;
		topYL=r.topYL;
		topYR=r.topYR;
		updateAllPointsFromTopLeft(this);
	}
	
	public void setRect(Rectangle2D r){
		super.setRect(r);
		width=r.getHeight();
		updateAllPointsFromTopLeft(this);
	}
	
}
