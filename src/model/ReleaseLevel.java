package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/** 
 * An AbstractLevelModel class determines what kind of information all three types of 
 * levels should store inside of them and the kinds of functionality they should have.
 * @author Dylan
 */
public class ReleaseLevel extends AbstractLevelModel{
	//TODO Verify there will only be 6 of any color on board
	ArrayList<Integer> reds = new ArrayList<Integer>();
	ArrayList<Integer> yellows = new ArrayList<Integer>();
	ArrayList<Integer> blues = new ArrayList<Integer>();
	
	//TODO Verify these parameters are needed
	int totalReds;
	int totalYellows;
	int totalBlues;

	ReleaseLevel(File sourceFile, int levelID) {
		super(sourceFile ,levelID, "Release", false);
		// TODO Auto-generated constructor stub
	}

	/**
	 * LoadLevel is a helper method to the constructor. On instantiation, it will attempt to
	 * read any data about the level it can in. If nothing is found inside the file, then no 
	 * fields are set and it's apparent the level is being CREATED in the BUILDER. Setters will 
	 * handle the rest from here out in that case.
	 * 
	 * If the file can't be opened, the error is caught here. 
	 * 
	 * Method for reading: line by line through a buffer. File is closed at end of reading.
	 *TODO Load totalRed/Yellow/Blue
	 */
	boolean loadLevel(){
		try{
			FileReader fileReader = new FileReader(sourceFile);
			BufferedReader br = new BufferedReader(fileReader);
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				//Read the file LINE by LINE and do something with each line
				strLine.length(); //Do something with strLine...
			}
			//End Of File Reached, Close the file
			fileReader.close();
		}catch (IOException e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return true; //TODO replace this
	}	

	@Override
	boolean saveProgressInFile() {
		if(starsEarned > maxStarsEarned){
			//save starsEarned instead of maxStarsEarned
		}
		return false;
	}

	@Override
	boolean saveLevelToFile() {
		
		
		return false;
	}

	/** 
	 * A level is complete if the total number of stars earned is 3, meaning there are no more moves to be made, the player
	 * has achieved the most they can.
	 * 
	 * OR
	 * 
	 * The player is out of pieces to move in the bullpen. This means in order to check this, the level needs to ask the bullpen
	 * if it is empty or not.
	 */
	@Override
	boolean isComplete() {
		if(starsEarned == 3){ //TODO Add this when bullpen class exists: || bullpen.empty()){
			return true;
		}
		
		return false;
	}

	/**
	 * updateProgress occurs after every move is made. This updates the stars earned for the current level if 
	 * a set has been released. Each set is checked in a seperate statement as a way to ensure that if more
	 * that one set was released at a time, the number of stars earned is updated correctly. 
	 * 
	 * After all checks are made, the level is saved if the current playthrough has earned more stars than 
	 * the number tracked on file.
	 */
	@Override
	void updateProgress() {
		if(reds.size() == totalReds){
			starsEarned++;
		}
		
		if(blues.size() == totalBlues){
			starsEarned++;
		}
		
		if(yellows.size() == totalYellows){
			starsEarned++;
		}
	}
	
	/**
	 * Appends the number released to the ArrayList tracking the red integers released. 
	 * To check if all numbers of a type were released, look at the size of the array list
	 * against the total number of that colored number
	 * @param releasedNum Is the number that was released
	 */
	public void addToRedReleased(int releasedNum){
		this.reds.add(releasedNum);
	}
	
	/**
	 * Appends the number released to the ArrayList tracking the blue integers released. 
	 * To check if all numbers of a type were released, look at the size of the array list
	 * against the total number of that colored number
	 * @param releasedNum Is the number that was released
	 */
	public void addToBlueReleased(int releasedNum){
		this.blues.add(releasedNum);
	}
	
	/**
	 * Appends the number released to the ArrayList tracking the yellow integers released. 
	 * To check if all numbers of a type were released, look at the size of the array list
	 * against the total number of that colored number
	 * @param releasedNum Is the number that was released
	 */
	public void addToYellowReleased(int releasedNum){
		this.yellows.add(releasedNum);
	}

}
