package com.taco.input;

import java.awt.event.KeyListener;

import com.taco.main.Main;

public class Keyboard {

	public static void addKeyListener(KeyListener k) {
		Main.game.addKeyListener(k);
	}

	private Keyboard() {
	}

}