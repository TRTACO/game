package com.taco.input;

import static java.awt.MouseInfo.getPointerInfo;

import java.awt.Cursor;
import java.awt.event.MouseMotionListener;

import com.taco.main.Main;
import com.taco.world.Location;

public class Mouse {

	private static Cursor cursor;

	public static Location getPointerLocation() {
		return new Location(getPointerInfo().getLocation());
	}

	public static void addMouseMotionListener(MouseMotionListener m) {
		Main.game.addMouseMotionListener(m);
	}

	public static void setCursor(Cursor c) {
		cursor = c;
	}

	public static Cursor getCursor() {
		return cursor;
	}

	private Mouse() {

	}

}
