package com.taco.world;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.taco.actor.Enemy;
import com.taco.actor.Entity;
import com.taco.actor.Player;
import com.taco.geometry.Rectangle;
import com.taco.level.Wave;
import com.taco.main.Game;
import com.taco.main.Main;

public class World {

	private Map<Entity, Location> charMap = new LinkedHashMap<Entity, Location>();
	private static Graphics2D imageGraphics = Game.imageGraphics;
	public final static Location MIN = new Location(0, 0);
	private static int WIDTH = Game.WIDTH, HEIGHT = Game.HEIGHT;
	public final static Location MAX = new Location(WIDTH, HEIGHT);
	public static final Rectangle BOUNDS = new Rectangle(0, 0, WIDTH, HEIGHT);
	private int score = 0;

	public static boolean isInWorld(Entity e) {
		if (e.isDead())
			return false;
		return BOUNDS.contains(e.getMidpoint());
	}

	public World() {

	}

	public void setBackground() {

	}

	public void add(Entity e) {
		charMap.put(e, e.getMidpoint());
	}

	public void remove(Entity e) {
		charMap.values().remove(e);
	}

	public ArrayList<Location> getOccupiedLocations() {
		return new ArrayList<Location>(charMap.values());
	}

	public Map<Entity, Location> getMap() {
		return charMap;
	}

	private void drawBackground() {
		imageGraphics = Game.imageGraphics;
		imageGraphics.fillRect(0, 0, WIDTH, HEIGHT);
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int s) {
		score = s;
	}
	
	
	public void update() {

		if (Main.game.getState() == com.taco.main.State.GAME) {
			// Draw Background
			drawBackground();

			Entity[] entities = new Entity[] {};
			entities = charMap.keySet().toArray(entities);
			for (int i = 0; i < entities.length; i++) {
				entities[i].update(imageGraphics);
				
				charMap.remove(entities[i]);
				if (entities[i].isDead())
					;
				else
					charMap.put(entities[i], entities[i].getLocation());

				if (entities[i].isDead() && entities[i] instanceof Enemy)
					score++;
				
				

				imageGraphics.setColor(new Color(158, 59, 51));
				Font tempF = imageGraphics.getFont();
				imageGraphics.setFont(new Font("SansSerif", Font.BOLD, 100));
				imageGraphics.drawString("" + score, 20, 100);
				imageGraphics.setFont(tempF);
				imageGraphics.setColor(Color.BLACK);
			}
			// Remove all dead entities.

			charMap.keySet().remove(null);
			while (charMap.values().remove(null))
				;
		}

	}

	public Player getPlayer() {
		for (Entity e : charMap.keySet()) {
			if (e instanceof Player)
				return (Player) e;
		}
		return null;
	}

	public void removeEntity(Entity e) {
		charMap.remove(e);
	}

	public static Location getRandLocation() {
		Random r = new Random();
		return new Location(r.nextInt(WIDTH), r.nextInt(HEIGHT));
	}

	public void spawn(Wave w) {
		for (Enemy e : w) {
			add(e);
		}
	}

	public Set<Enemy> getLiveEnemies() {
		HashSet<Enemy> s = new HashSet<Enemy>();
		Entity[] entities = new Entity[] {};
		entities = charMap.keySet().toArray(entities);
		for (Entity e : entities) {
			if (e instanceof Enemy)
				s.add((Enemy) e);
		}
		return s;
	}

}
