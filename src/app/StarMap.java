package app;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

/**
 * StarMap is the functionality of two TreeMaps merged into one, where one TreeMap holds a higher importance over the other.
 * One TreeMap associates a Key to a Value, while the other TreeMap associates the same set of Keys to different values.
 * The second TreeMap, however, cannot add a Key,Value pair unless the first map has the key.
 * 
 * @author Dylan
 *
 * @param <Integer> Generic Key
 * @param <String> Generic Value
 */
public class StarMap implements Serializable{
	/**This class is saved to disk to store the max number of stars earned for a level**/
	private static final long serialVersionUID = 7576231489845563260L;

	/**TreeMap that stores the ID of a level (Key) with the maximum stars earned for that level (Value)**/
	TreeMap<Integer, Integer> stars = new TreeMap<Integer, Integer>();

	/**TreeMap stores levelID with levelType**/
	TreeMap<Integer, String> levelData = new TreeMap<Integer, String>();

	/**Path to Directory assigned in the constructor to save the StarMap serialization**/
	transient String directory;

	StarMap(String directory){
		this.directory = directory;
		populateEmptyMap();
	}

	/**
	 * Put method for adding levelData. Stores the Key, Value pair in a TreeMap
	 * Also initializes the key in the stars TreeMap, to prevent null pointer
	 * exceptions when trying to access a maxStarsEarned amount that hasn't been set
	 * yet
	 * @param key - should be levelID
	 * @param value - should be levelType
	 */
	public void put(Integer key, String value){
		if(!levelData.containsKey(key)){
			levelData.put(key, value);
			stars.put(key, 0);
		}
	}

	/**
	 * Get the type associated with the levelID
	 * Returns null if the key does not have a value associated with it
	 * Throws a null pointer exception if the key does not exist in the map
	 * @param key
	 * @return value associated with key - the level type
	 */
	public String get(Integer key){
		return levelData.get(key);
	}

	/**
	 * Sets the stars of the given Key, if that key is registered in the levelData map
	 * @param key - should be LevelID
	 * @param starsEarned - should be maximum number of stars earned
	 * @return true if the stars could be stored, false if level did not exist
	 */
	public boolean setMaxStars(Integer key, Integer starsEarned){
		if(levelData.containsKey(key)){
			stars.put(key, starsEarned);
			return true;
		}	
		return false;
	}

	/**
	 * Get the maximumStarsEarned associated with the levelID
	 * Returns null if the key does not have a value associated with it
	 * Throws a null pointer exception if the key does not exist in the map
	 * @param key
	 * @return value associated with key - the maxStarsEarned or null if the levelID does not have a value associated with it
	 */
	public Integer getMaxStars(Integer key){
		return stars.get(key);
	}

	/**
	 * Returns an iterator of keys in the StarMap
	 * @return Iterator of keys in levelData
	 */
	public Set<Integer> keySet(){
		return levelData.keySet();
	}

	/**
	 * Unlocked Levels returns an iterator over the levelIDs that counts as unlocked
	 * This fact is computed by checking the stars earned for a levelID inside the stars
	 * treemap. If the levelID has a value associated with it that is greater than 0, it
	 * counts as unlocked.
	 * @return Iterator of integers - levelIDs
	 */
	public Set<Integer> unlockedLevels(){
		Set<Integer> keys = levelData.keySet();
		for(Integer key: keys){
			if(stars.get(key) == 0){ keys.remove(key);}
		}
		return keys;
	}

	/**
	 * Returns the last key in the tree - the highest value key.
	 * @return Highest valued Key
	 */
	public Integer lastKey(){
		try{
			return levelData.lastKey();
		}catch(NoSuchElementException e){
			return 0;
		}
	}

	/**
	 * Tells whether the levelData is empty or not
	 * @return true if levelData is empty
	 */
	public boolean isEmpty(){
		return levelData.isEmpty();
	}

	public boolean containsKey(int key) {
		return levelData.containsKey(key);
	}

	public String keyToString(){
		return this.keySet().toString();
	}

	public String toString(){
		StringBuilder s = new StringBuilder();
		for(int k: this.keySet()){
			s.append("["+k+","+levelData.get(k)+","+stars.get(k)+"],");			
		}
		s.deleteCharAt(s.length()-1);
		return s.toString();
	}

	/**
	 * When a StarMap is deserialized, it needs to synchronize with the levels
	 * available in the directory (in case an outside user deleted a level file).
	 * To account for this, the StarMap loads all LevelIDs from the directory and compares
	 * against its keyset. If the keyset contains an integer NOT in the directory, the key
	 * is deleted;
	 */
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
		in.defaultReadObject();

		File[] folder = (new File(directory)).listFiles();
		ArrayList<Integer> keys = new ArrayList<Integer>();
		String levelNum;

		for (File f: folder) {
			if(f.getName().equals("StarMap.storage")){ continue;}

			levelNum = f.getName().substring(0, f.getName().lastIndexOf("_"));
			int levelID;
			try{
				levelID = Integer.parseInt(levelNum);
				keys.add(levelID);
			}catch(NumberFormatException e){
				System.err.println("Could not parse in from file, skipping...");
				continue;
			}
		}

		for(Integer k: this.keySet()){
			if(!keys.contains(k)){
				levelData.remove(k);
				stars.remove(k);
			}
		}
	}

	/**
	 * A new StarMap is made if the map can't be deserialized in an attempt to continue play. The only
	 * information lost in such an event is the player's earned stars progress. The rest of the map
	 * is populated with levelIDs found and their types.
	 */
	void populateEmptyMap(){
		System.out.println("Populating Empty Map @"+directory);
		File[] folder = (new File(directory)).listFiles();
		String levelNum;
		String levelType;

		for (File f: folder) {
			//If the folder is empty, because no level files exist, dont populate anything
			//in the star map
			if(folder.length == 0){ break;}
			if(f.getName().equals("StarMap.storage")){ continue;}

			levelNum = f.getName().substring(0, f.getName().lastIndexOf("_"));
			levelType = f.getName().substring(f.getName().lastIndexOf("_")+1, f.getName().lastIndexOf("."));
			int levelID;
			try{
				levelID = Integer.parseInt(levelNum);
				this.put(levelID, levelType);
				this.setMaxStars(levelID, 0);
			}catch(NumberFormatException e){
				System.err.println("Could not parse in from file, skipping...");
				continue;
			}
		}
	}
}