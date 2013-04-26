package com.taco.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
	
	public void saveHighScore(String name, long scr) throws IOException{
		File highScores = new File("highScores.taco");
		FileWriter writer = new FileWriter(highScores);
		if(highScores.exists()){
			Scanner sc = new Scanner(highScores);
			//Sort Current High Scores (but is commented out).
			//TreeMap<String,String> scoreMap = new TreeMap<Long, String>();
			ArrayList<Long> scores = new ArrayList<Long>();
			ArrayList<String> names = new ArrayList<String>();
			while(sc.hasNextLine()){
				//Get name
				names.add(sc.next());
				
				//Get Score
				long score = sc.nextLong();
				scores.add(score);
				
				//scoreMap.put(score, name);
				
				//Move on to next line
				sc.nextLine();
			}
			for(int i = 0; i < scores.size(); i++){
				if(scores.get(i)>=scr){
					
				}
				else{
					scores.add(i, scr);
					names.add(i,name);
					break;
				}
			}
			for(int i = 0; i < scores.size(); i++){
				writer.write(names.get(i)+" "+scores.get(i));
			}
			writer.close();
			sc.close();
		}
		else{
			writer.write(name+" "+scr);
			writer.close();
		}
	}
	
	
	public ArrayList<String> getHighScores(){
		File highScores = new File("highScores.taco");
		ArrayList<String> scores = new ArrayList<String>();
		if(highScores.exists()){
			try {
				Scanner sc = new Scanner(highScores);
				while(sc.hasNext()){
					scores.add(sc.nextLine());
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				scores.add("High Score file not found");
			}
		}
		else{
			scores.add("No High Scores");
		}
		
		return scores;
	}

}
