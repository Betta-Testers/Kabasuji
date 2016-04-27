/**
 * 
 */
package model;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * @author hejohnson
 *
 */
public class PieceFactory {

	private static PieceFactory instance = new PieceFactory();
	TreeMap<Integer, Piece> pieces;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PieceFactory pf = new PieceFactory();

	}
	
	private PieceFactory() {
		loadFiles();
		System.out.println(pieces.toString());
	}
	
	public static PieceFactory getInstance() {
		return instance;
	}
	
	void loadFiles() {
		pieces = new TreeMap<Integer, Piece>();
		File piecesDir = new File("./pieces/");
		File[] pieceFiles = piecesDir.listFiles();
		for (File pieceFile : pieceFiles) {
			try {
				ArrayList<String> contents = new ArrayList<String>();
				String line;
	            
	            FileReader fileReader = new FileReader(pieceFile);
	
	            BufferedReader bufferedReader = new BufferedReader(fileReader);
	
	            while((line = bufferedReader.readLine()) != null) {
	                contents.add(line);
	            }  
	            
	            int ID = Integer.parseInt(pieceFile.getName().substring(0, pieceFile.getName().indexOf(".")));
	            
	            parsePiece(ID, contents);
	            contents.clear();
	
	            // Always close files.
	            bufferedReader.close();         
	        }
	        catch(FileNotFoundException ex) {
	            System.out.println("Unable to open file");              
	        }
	        catch(IOException ex) {
	            ex.printStackTrace();
	        }
		}
	}

	/**
	 * 
	 * @param id The new piece's ID
	 * @param pieceData The string containing the color and tile coordinates
	 */
	void parsePiece(int id, ArrayList<String> pieceData) {
		Piece p = new Piece(id);
		Color c = null;
		for (String line : pieceData) {
			String[] parts = line.split(",");
			for (int i = 0; i<parts.length; i++) {
				parts[i] = parts[i].trim();
			}
			switch (parts.length) {
			case 2:
				if (p != null) {
					if (!(Integer.parseInt(parts[0]) == 0 && Integer.parseInt(parts[1]) == 0)) { // Ignore the origin tile
						p.addTile(new PieceTile(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), p));
					}
				}
				break;
			case 3:
				if (p != null) {
					c = new Color(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
				}
				break;
			default:
				throw new RuntimeException("PieceFactory::Malformed piece file, ID: " + id);
			}
		}

		p.setColor(c);
		pieces.put(p.getID(), p);
	}
	
	/**
	 * @param pID The desired piece's ID
	 * @return The piece with that ID
	 */
	public Piece getPiece(int pID) {
		if (pieces.containsKey(pID)) {
			return pieces.get(pID).makeCopy();
		} else {
			throw new RuntimeException("That piece ID does not exist");
		}
	}
	
	public boolean pieceExists(int id) {
		return pieces.containsKey(id);
	}
	
	public int getHighestNumberedPiece() {
		return pieces.lastKey();
	}
}