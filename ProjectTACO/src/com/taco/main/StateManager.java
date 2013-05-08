package com.taco.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.*;
import java.io.*;

public class StateManager {

	private int currentState;

	public StateManager() {
		currentState = State.MENU;
	}

	public int getState() {
		return currentState;
	}

	public void setState(int newState) {
		currentState = newState;
	}


	public void saveHighScore(String name, long scr) throws IOException {
		File highScores = new File("highScores.taco");
		// FileWriter writerF = new FileWriter(highScores);

		
		  try { Scanner sc = new Scanner(highScores); List<String> names = new
		  ArrayList<String>(); List<Integer> scores = new ArrayList<Integer>();
		  while (sc.hasNextLine()) { names.add(sc.next());
		  scores.add(sc.nextInt()); sc.nextLine(); }
		  
		  if (scores.size() >= 10 && scores.get(10) < scr) { int n =
		  scores.get(10); for (int i = scores.size(); i > 0; i--) {
		  
		  } }
		  
		  else if (scores.size() < 10) {
		  
		  }
		  
		  else { return; }
		  
		  } catch (FileNotFoundException e) {
		  
		  PrintStream out = new PrintStream(highScores);
		  
		  out.println(name + " " + scr + "\n"); out.close(); }
		 

	}

	public ArrayList<String> getHighScores() {
		File highScores = new File("highScores.taco");
		ArrayList<String> scores = new ArrayList<String>();
		if (highScores.exists()) {
			try {
				Scanner sc = new Scanner(highScores);
				while (sc.hasNext()) {
					scores.add(sc.nextLine());
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				scores.add("High Score file not found");
			}
		} else {
			scores.add("No High Scores");
		}

		return scores;
	}

}
