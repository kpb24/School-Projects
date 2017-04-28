
/**
 *	Hnefatafl
 *	CS 1530 Spring 2017
 *	Group Cool People
 *	@author Keri Bookleiner <kpb24@pitt.edu>
 *	@author Vivien Chang <vsc5@pitt.edu>
 *	@author Maxwell Garber <mbg21@pitt.edu>
 *	@author Jennifer Zysk <jez20@pitt.edu>
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.Serializable;
import java.io.InputStream.*;
import java.io.OutputStream.*;
import java.util.*;
import javax.swing.*;
import java.util.Arrays;
	import java.util.ArrayList;

public class Controller implements Serializable {
	GameMove model;
	boolean defendersSurrounded;
	String[][] pieces;
	String[][] seen;
	ArrayList<String> defenderRepeat;
	ArrayList<String> attackerRepeat;

	/**
	 *	constructor
	 */
	public Controller() {
		model = new GameMove();
		pieces = new String[11][11]; //current locations in the board, locations match up with seen array
		seen = new String[11][11]; //array of what has been seen..used to determined if completely surrounded
		defenderRepeat = new ArrayList<String>(); //holds the three most recent configurations
		attackerRepeat = new ArrayList<String>();
		defendersSurrounded = false; // default to false to ensure a non-null state/return value	
	}

	/**
	 *  Method that determines if the player is moving their own game piece.
	 *  @param player the string of the image on the game piece clicked
	 *  @return boolean value
	 */
	public boolean checkTurn() {
		int turn = model.getTurn();
		String player = model.getPlayer();
		if (((turn % 2 == 1) && player.equals("./src/main/resources/blackbutton.png")) ||
		((turn % 2 == 0) && (player.equals("./src/main/resources/whitebutton.png") ||
		player.equals("./src/main/resources/king.png")))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 *  Method that checks if this is a restricted square, and if the piece
	 *  is not the king.
	 *  @param Component comp : the component where the piece is being moved
	 *  @return boolean : true if the square is restricted or if it is occupied by the king
	 */
	public boolean checkRestricted(Component comp) {
		if (comp.getBackground().equals(Color.decode("#004d00"))) {
			if (model.getPlayer().equals("./src/main/resources/king.png")) {
				return true;
			}
			return false;
		}
		return true;
	}

	/**
	 * Method that determines if a player has won.
	 * @param Component comp : the component of the board
	 * @param Jpanel gameBoard : the board itself
	 * @return String : determine if there is a winner
	 *		{ "Attackers" | "Defenders" | "false" }
	 */
	public String checkWinner(Component comp, JPanel gameBoard) {
		if (checkKingCaptured(gameBoard)) {
			return "Attackers";
		}

		//if the attackers surround the king and all defenders, the attackers win
		if(defendersSurrounded(gameBoard)){
			return "Attackers";
		}

		//If the king has reached a corner, the defenders have won
		if (comp instanceof JPanel) {
			if ((model.getPlayer().equals("./src/main/resources/king.png")) &&
			(comp.getBackground().equals(Color.decode("#004d00")))) {
				//Ensure this is not the center square
				if((comp.getX() != 250) && (comp.getY() != 260)) {
					model.setGameOver(true);
					return "Defenders";
				}
			}
		} else {
		//If the king has been captured, the attackers have won
		JLabel oldPiece = (JLabel)comp;
		if (oldPiece.getIcon().toString().equals("./src/main/resources/king.png")) {
			model.setGameOver(true);
			return "Attackers";
		}
		}
		return "false";
	}

	/**
	 * Method that sets up the board configuration when the game starts
	 * The string is added to the defenderRepeat array to check if the board
	 * configuration is repeated.
	 * @param String s: the string representing the board configuration
	 */
	public void setUpConfig(String s){
		defenderRepeat.add(s);
	}
	
	/**
	 * Method that sets up the board configuration when the game starts
	 * The string is added to the attackerRepeat array to check if the board
	 * configuration is repeated.
	 * @param String s: the string representing the board configuration
	 */
	public void setUpConfigA(String s){
		attackerRepeat.add(s);
	}
	
	/**
	 * Method that determines if the defenders have repeated the same board
	 * position 3 times
	 * @param Jpanel gameBoard : the board itself
	 * @return boolean: true if repeated 3 times
	 */
	public String checkRepetition(JPanel gameBoard){
		//must have at least 4 defenders and a king
		String config = "";
		String configA = "";
		int numDefenders = 0;
		boolean kingOnBoard = false;
	
		//Get all gameBoard components- these are the background tiles
		Component [] myComponents = gameBoard.getComponents();
		//Iterate through background tiles
		for (int i = 0; i < myComponents.length; i++) {
			Component currentComp = myComponents[i];
			//make a 2d array of piece locations to work with seen array
			if (currentComp instanceof JPanel) {
				//Cast to JPanel and get its subcomponents- this is where a game piece will be
				JPanel source = (JPanel)currentComp;
				Component [] subComponents = source.getComponents();
				//If there is a subcomponent, check to see if it is the king
				if (subComponents.length == 1) {
					Component innerComponent = subComponents[0];
					if (innerComponent instanceof JLabel) {
						JLabel currentButton = (JLabel)innerComponent;
						if (currentButton.getIcon().toString().equals("./src/main/resources/king.png")) {
							config+="k";
						} // end if
						if (currentButton.getIcon().toString().equals("./src/main/resources/whitebutton.png")) {
							config+="d";
						} // end else-if
						if (currentButton.getIcon().toString().equals("./src/main/resources/blackbutton.png")) {
							configA+="a";
						} // end else-if
						else{
							config+="n";
							configA+="n";
						}
					}//end-if
				} //end if
			} //end if
		}//end for
		defenderRepeat.add(config);
		attackerRepeat.add(configA);
		
		if(defenderRepeat.size() == 6){
			if(defenderRepeat.get(0).equals(defenderRepeat.get(2))){
				if(defenderRepeat.get(2).equals(defenderRepeat.get(4))){
					//if they're all the same then there haven't been any captures
					//and board configuration is the same
					return "defenders";
				}//end-if
			}//end-if
			else if(defenderRepeat.get(1).equals(defenderRepeat.get(3))){
				if(defenderRepeat.get(3).equals(defenderRepeat.get(5))){
					return "defenders";
					
				}//end-if
			}//end-else-of
			else{
				defenderRepeat.remove(0);
			}
		}//end-if
		if(attackerRepeat.size() == 6){
			if(attackerRepeat.get(0).equals(attackerRepeat.get(2))){
				if(attackerRepeat.get(2).equals(attackerRepeat.get(4))){
					//if they're all the same then there haven't been any captures
					//and board configuration is the same
					return "attackers";
				}//end-if
			}//end-if
			else if(attackerRepeat.get(1).equals(attackerRepeat.get(3))){
				if(attackerRepeat.get(3).equals(attackerRepeat.get(5))){
					return "attackers";
				}//end-if
			}//end-else-of
			else{
				attackerRepeat.remove(0);
			}
		}//end-if
		return "none";
	}//end

	/**
	 * Method that determines if the defenders have created a draw fort
	 * @param Jpanel gameBoard : the board itself
	 * @return boolean: true if draw fort created
	 */
	public boolean checkDrawFort(JPanel gameBoard){
		int kingCount = 0;
		int attackerCount = 0;
		int defenderCount = 0;
		int kingPosition = -1;
		int numNeighbors = 0;
		int type = 0;
				
		//Get all gameBoard components- these are the background tiles
		Component [] myComponents = gameBoard.getComponents();
		//Iterate through background tiles
		for (int i = 0; i < myComponents.length; i++) {
			Component currentComp = myComponents[i];
			//make a 2d array of piece locations to work with seen array
			if (currentComp instanceof JPanel) {
				//Cast to JPanel and get its subcomponents- this is where a game piece will be
				JPanel source = (JPanel)currentComp;
				Component [] subComponents = source.getComponents();
				//If there is a subcomponent, check to see if it is the king
				if (subComponents.length == 1) {
					Component innerComponent = subComponents[0];
					if (innerComponent instanceof JLabel) {
						JLabel currentButton = (JLabel)innerComponent;
						if (currentButton.getIcon().toString().equals("./src/main/resources/king.png")) {
							kingCount++;
							kingPosition = i;
						} // end if
						if (currentButton.getIcon().toString().equals("./src/main/resources/whitebutton.png")) {
							defenderCount++;
						} // end else-if
						if (currentButton.getIcon().toString().equals("./src/main/resources/blackbutton.png")) {
							attackerCount++;
						} // end else-if
					}//end-if
				} //end if
			} //end if
		}//end for
	
		//check if less than 4 defenders and king is still on the board
		if(kingCount == 0 || defenderCount < 4){
			return false;
		}
	
		//check to see how many white pieces are surrounding the king - to determine if defender's position
		Component topNeighbor = myComponents[kingPosition - 11];
		Component bottomNeighbor = myComponents[kingPosition + 11];
		Component leftNeighbor = myComponents[kingPosition - 1];
		Component rightNeighbor = myComponents[kingPosition + 1];

		Component [] neighboringComponents = new Component [] {topNeighbor, bottomNeighbor, leftNeighbor, rightNeighbor};
		//Iterate through neighbors. If any neighbor is NOT a black piece or the throne, break.
		for (int i = 0; i < neighboringComponents.length; i++) {
			Component currentComp = neighboringComponents[i];

			if (currentComp instanceof JPanel) {
				//Cast to JPanel and get its subcomponents- this is where a game piece will be
				JPanel source = (JPanel)currentComp;
				Component [] subComponents = source.getComponents();

				//If there is a subcomponent, check to see if it is a black piece
				if (subComponents.length == 1) {
					Component innerComponent = subComponents[0];

					if (innerComponent instanceof JLabel) {
						JLabel currentButton = (JLabel)innerComponent;

						if (currentButton.getIcon().toString().equals("./src/main/resources/whitebutton.png")) {
							numNeighbors++;
						}
						else if(currentButton.getIcon().toString().equals("./src/main/resources/blackbutton.png")){
							return false;
						}
						else{
						if(i == 0){
							type = kingPosition - 11;
						}
						else if(i == 1){
							type = kingPosition + 11;
						}
						else if(i == 2){
							type = kingPosition - 1;
						}
						else if(i == 3){	
							type = kingPosition + 1;
						}
						}
					}
				} 
			}
		}
		//possibility for draw fort
		if(numNeighbors == 3){
		 	if(checkEdge(type)){
		 		return true;
		 	}
		}
		return false;
	}
	
	/**
	 * Method that determines if all defenders and the king are surrounded by attackers.
	 * @param Jpanel gameBoard : the board itself
	 * @return boolean : true if the defenders are surrounded, false if not
	 */
	public boolean defendersSurrounded(JPanel gameBoard) {
		defendersSurrounded = true;
		int row = 0 , column = 0;
		//reset all values in seen and pieces array 
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				seen[i][j] = "unseen";
				pieces[i][j] = "empty";
			}//end-for
		}//end-for
		//Get all gameBoard components- these are the background tiles
		Component [] myComponents = gameBoard.getComponents();
		//Iterate through background tiles
		for (int i = 0; i < myComponents.length; i++) {
			Component currentComp = myComponents[i];
			//make a 2d array of piece locations to work with seen array
			if (currentComp instanceof JPanel) {
				//Cast to JPanel and get its subcomponents- this is where a game piece will be
				JPanel source = (JPanel)currentComp;
				Component [] subComponents = source.getComponents();
				//If there is a subcomponent, check to see if it is the king
				if (subComponents.length == 1) {
					Component innerComponent = subComponents[0];
					if (innerComponent instanceof JLabel) {
						JLabel currentButton = (JLabel)innerComponent;
						if (currentButton.getIcon().toString().equals("./src/main/resources/king.png")) {
							if(column >= 11) {
								row++;
								column = 0;
							}//end-if
							pieces[row][column] = "defender"; //consider this a defender
							column++;
						} // end if
						else if (currentButton.getIcon().toString().equals("./src/main/resources/whitebutton.png")) {
							if(column >= 11) {
								row++;
								column = 0;
							}//end-if
							pieces[row][column] = "defender";
							column++;
						} // end if
						else if (currentButton.getIcon().toString().equals("./src/main/resources/blackbutton.png")) {
							if (column >= 11) {
								row++;
								column = 0;
							}//end-if
							pieces[row][column] = "attacker";
							column++;
						} // end if
					} // end if
				} else {
					if(column >= 11) {
						row++;
						column = 0;
					 }//end-if
					pieces[row][column] = "empty"; //empty spot- no piece here
					column++;
				}//end-else
			} //end if
		} //end for
		
		//check if all defenders are blocked in by attackers
		for (int x = 0; x < 11; x++){
			for (int y = 0; y < 11; y++) {
				if (pieces[x][y].equals("defender")) {
					if (seen[x][y].equals("unseen") || seen[x][y].equals("defender")) {
						if (blocked(x, y)) {
							defendersSurrounded = false;
							return false;
						}//end-if
					}//end-if
				}//end-if
			}//end-for
		}//end-for
		if (!defendersSurrounded) {
			return false;
		}//end-if
		return true;
	}//end- defendersSurrounded

	/**
	 * Method that determines if a defender is blocked in by attackers
	 * @param i : x coordinate in array position to check
	 * @param j : y coordinate in array of position to check
	 * @return boolean : true if the defender surrounded, false if not
	 */
	public boolean blocked(int x, int y) {
		int[][] possibleMoves = new int[4][2]; //four possible (right, left, up, down)
		int[][] moves;
		int posRight = x + 1;
		int posLeft = x - 1;
		int posAbove = y - 1;
		int posBelow = y + 1;
		seen[x][y] = "seen"; //mark as having been seen
		int row = 0;
		int column = 0;
		//check position to the right
		if (posRight < 11) {
			if(seen[posRight][y].equals("unseen")) {
				updateSeen(posRight, y); //update the seen array
			}//end-if
		} else {
			defendersSurrounded = false;
 			return true; //not blocked in
		}//end-else
		//check position to the left
		if (posLeft >= 0) {
			if(seen[posLeft][y].equals("unseen")) {
				updateSeen(posLeft, y); //update the seen array
			}//end-if
		} else {
			defendersSurrounded = false;
 			return true; //not blocked in
		}//end-else
		//check position below
		if (posBelow < 11) {
			if(seen[x][posBelow].equals("unseen")) {
				updateSeen(x, posBelow); //update the seen array
			}//end-if
		} else {
			defendersSurrounded = false;
 			return true; //not blocked in
		}//end-else
		//check position above
		if (posAbove >= 0){
			if (seen[x][posAbove].equals("unseen")) {
				updateSeen(x, posAbove); //update the seen array
			}//end-if
		} else {
			defendersSurrounded = false;
 			return true; //not blocked in
		}//end-else
		//check if piece can move to the right, if so it is a possible move
		if (seen[posRight][y].equals("empty") || seen[posRight][y].equals("defender")) {
			possibleMoves[row][column] = posRight;
			possibleMoves[row][column + 1] = y;
			row++;
		}//end-if
		//check if piece can move to the left, if so it is a possible move
		if (seen[posLeft][y].equals("empty") || seen[posLeft][y].equals("defender")) {
			possibleMoves[row][column] = posLeft;
			possibleMoves[row][column + 1] = y;
			row++;
		}//end-if
		//check if piece can move down, if so it is a possible move
		if (seen[x][posBelow].equals("empty") || seen[x][posBelow].equals("defender")) {
			possibleMoves[row][column] = x;
			possibleMoves[row][column + 1] = posBelow;
			row++;
		}//end-if
		//check if piece can move up, if so it is a possible move
		if (seen[x][posAbove].equals("empty") || seen[x][posAbove].equals("defender")) {
			possibleMoves[row][column] = x;
			possibleMoves[row][column + 1] = posAbove;
			row++;
		}//end-if
		//contains the array of all the moves we could try since may be less than 4
		//doesn't include pieces that are attackers
		moves= new int[row][column + 2];
		for (int k = 0; k < row; k++) {
			//transfer to new array we can use to test available moves
			moves[k][column] = possibleMoves[k][column];
			moves[k][column + 1] = possibleMoves[k][column + 1];
		}//end-for
		//keeps moving until determines if all defenders are surrounded
		for (int k = 0; k < moves.length; k++) {
			blocked(moves[k][0], moves[k][1]);
		}//end-for
		return false; //not surrounded
	}

	/**
	 * Method that keeps the seen array updated using the array of game piece location
	 * @param i : x coordinate of location in 2d array
	 * @param j : y coordinate of location in 2d array
	 * 
	 */
	public void updateSeen(int x, int y) {
		if (pieces[x][y].equals("attacker")) {
			seen[x][y] = "attacker";
		}
		else if (pieces[x][y].equals("defender")) {
			seen[x][y] = "defender";
		}
		else if (pieces[x][y].equals("empty")) {
			seen[x][y] = "empty";
		}
	}

	/**
	 *  Method that checks to see if the king has been captured.
	 *  First finds where the king is on the board, then checks its neighbors.
	 *  If the neighbors are all black pieces, or 3 black pieces and the throne,
	 *  the king has been captured.
	 *  @param JPanel gameBoard : the game board
	 *	@return boolean : true if the king is captured
	 */
	public boolean checkKingCaptured(JPanel gameBoard) {
		int kingPosition = -1;

		//Get all gameBoard components- these are the background tiles
		Component [] myComponents = gameBoard.getComponents();

		//Iterate through background tiles
		for (int i = 0; i < myComponents.length; i++) {
			Component currentComp = myComponents[i];

			if (currentComp instanceof JPanel) {
				//Cast to JPanel and get its subcomponents- this is where a game piece will be
				JPanel source = (JPanel)currentComp;
				Component [] subComponents = source.getComponents();

				//If there is a subcomponent, check to see if it is the king
				if (subComponents.length == 1) {
					Component innerComponent = subComponents[0];

					if (innerComponent instanceof JLabel) {
						JLabel currentButton = (JLabel)innerComponent;

						if (currentButton.getIcon().toString().equals("./src/main/resources/king.png")) {
							kingPosition = i;
						} // end if
					} // end if
				} //end if
			} //end if
		} //end for

		//Check to see if this is an edge
		boolean edge = checkEdge(kingPosition);
		if (edge) {
			return false;
		}

		//If not, check to see if the king is surrounded. Check 4 surrounding components
		Component topNeighbor = myComponents[kingPosition - 11];
		Component bottomNeighbor = myComponents[kingPosition + 11];
		Component leftNeighbor = myComponents[kingPosition - 1];
		Component rightNeighbor = myComponents[kingPosition + 1];

		Component [] neighboringComponents = new Component [] {topNeighbor, bottomNeighbor, leftNeighbor, rightNeighbor};

		boolean isSurrounded = checkNeighbors(neighboringComponents, gameBoard);
		return isSurrounded;
	} //end-func-checkKingCaptured

	/**
	 *  Method that checks to see if the king is on the edge of the board
	 *  If the king is on an edge, we do not need to check its neighbors.
	 *  Returns true if the king is on the edge, if it's not, return false
	 *  @param int kingPosition : the position of the king on the board (linear index)
	 *  @return boolean : true if the king is on the edge of the board, false otherwise
	 */
	public boolean checkEdge(int kingPosition) {

		if ((0 <= kingPosition) && (kingPosition <= 10)) {       //top edge of board
			return true;
		}

		if ((110 <= kingPosition) && (kingPosition <= 120)) {    //bottom edge of board
			return true;
		}

		if (kingPosition % 11 == 0) {        //left edge of board
			return true;
		}

		if ((kingPosition + 1) % 11 == 0) {    //right edge of board
			return true;
		}

		return false;
	} //end-func-checkEdge

	/**
	 *  Method that checks to see if the king is surrounded
	 *  Looks at left, right, top and bottom neighbors. If they are
	 *  all attackers, or 3 attackers and the throne, return true. If they
	 *  are not all attackers, return false.
	 *  @param Component[] neighboringComponents : all of the components neighboring the king
	 *  @return boolean : true if all neighboring components are attackers or three attackers
	 *		and the throne. false otherwise
	 */
	public boolean checkNeighbors(Component [] neighboringComponents, JPanel gameBoard) {
		//Iterate through neighbors. If any neighbor is NOT a black piece or the throne, break.
		for (int i = 0; i < neighboringComponents.length; i++) {
			Component currentComp = neighboringComponents[i];

			if (currentComp instanceof JPanel) {
				//Cast to JPanel and get its subcomponents- this is where a game piece will be
				JPanel source = (JPanel)currentComp;
				Component [] subComponents = source.getComponents();

				//If there is a subcomponent, check to see if it is a black piece
				if (subComponents.length == 1) {
					Component innerComponent = subComponents[0];

					if (innerComponent instanceof JLabel) {
						JLabel currentButton = (JLabel)innerComponent;

						if (!currentButton.getIcon().toString().equals("./src/main/resources/blackbutton.png")) {
							return false;
						}
					}
				//If not, check to see if this is a restricted square
				} else {
					if (!currentComp.getBackground().equals(Color.decode("#004d00"))) {
					return false;
					}
				}
			}
		}
		return true;
	} //end-func-checkNeighbors

	/**
	 *	method to determine if a move on the board is valid (permissible)
	 *	@param Point destination : the destination position
	 *	@param int xOriginal : the initial x (horizontal) graphics position on the board
	 *	@param int yOriginal : the initial y (vertical) grpahics position on the board
	 *	@param JPanel gameBoard : the game board itself
	 *	@return boolean : true if the move is allowable
	 */
	public boolean isValid(Point destination, int xOriginal, int yOriginal, JPanel gameBoard) {
		//The move is only valid if it is in the same row and column as it was originally
		//Additionally no pieces can be in the path of the moving piece
		int xDiff = destination.x - xOriginal;
		int yDiff = destination.y - yOriginal;
		ArrayList<Integer> pieces = new ArrayList<Integer>();
		Component [] board = gameBoard.getComponents();
		for (int i = 0; i < board.length; i++) {
			Component area = (JPanel) board[i];
			if (area != null) {
				Component [] placedPieces = ((Container)area).getComponents();
				if (placedPieces.length > 0) {
					Component placedPiece = placedPieces[0];
					if (placedPiece instanceof JLabel) {
						pieces.add(i);
					}
				}
			}
		}
		
		
		
		if (Math.abs(xDiff) >= 50 && Math.abs(yDiff) == 0) {
			//Right
			if (xDiff > 0) {
				int originalColumn = xOriginal / 50;
				int newColumn = destination.x / 50;
				int row = yOriginal / 52;
				for (int i = originalColumn + 1; i <= newColumn; i++) {
					int index = (row * 11) + i;
					for (int j = 0; j < pieces.size(); j++) {
						if(pieces.get(j) == index) {
							return false;
						}
					}
				}
			//Left
			} else {
				int originalColumn = xOriginal / 50;
				int newColumn = destination.x / 50;
				int row = yOriginal / 52;
				for (int i = originalColumn - 1; i >= newColumn; i--) {
					int index = (row * 11) + i;
					for (int j = 0; j < pieces.size(); j++) {
						if(pieces.get(j) == index) {
							return false;
						}
					}
				}
			}
			return true;
		} else if (Math.abs(xDiff) == 0 && Math.abs(yDiff) >= 52) {
		//Down
		if (yDiff > 0) {
			int originalRow = yOriginal / 52;
			int newRow = destination.y / 52;
			int column = xOriginal / 50;
			for (int i = originalRow + 1; i <= newRow; i++) {
				int index = (i * 11) + column;
				for (int j = 0; j < pieces.size(); j++) {
					if(pieces.get(j) == index) {
						return false;
					}
				}
			}
		//Up
		} else {
			int originalRow = yOriginal / 52;
			int newRow = destination.y / 52;
			int column = xOriginal / 50;
			for (int i = originalRow - 1; i >= newRow; i--) {
				int index = (i * 11) + column;
				for (int j = 0; j < pieces.size(); j++) {
					if(pieces.get(j) == index) {
						return false;
					}
				}
			}
		}
		return true;
		} else {
			return false;
		}
	}


	/**
	 *  method to see if pieces besides the king have been captured
	 *  @param JPanel gameBoard : the game board
	 *  @param Component comp : the component of the board
	 *  @return Component : return the captured piece
	 */
	public Component checkCapture(Component comp, JPanel gameBoard) {
		String black = "./src/main/resources/blackbutton.png";
		String white = "./src/main/resources/whitebutton.png";
		String king = "./src/main/resources/king.png";
		boolean whitePlayer = model.getPlayer().equals(white);
		boolean kingPlayer = model.getPlayer().equals(king);
		boolean blackPlayer = model.getPlayer().equals(black);
		int newLocationX = comp.getX();
		int newLocationY = comp.getY();
		int index = (newLocationY / 52 * 11) + (newLocationX / 50);
		Component [] board = gameBoard.getComponents();
		for (int i = 0; i < board.length; i++) {
			if (i == index) {
				if (newLocationX <= 400) {
					JLabel middlePiece, rightPiece;
					Component middle = (JPanel) board[i + 1];
					Component right = (JPanel) board[i + 2];
					if (middle != null && right != null) {
						Component [] middleComp = ((Container)middle).getComponents();
						Component [] rightComp = ((Container)right).getComponents();
						if (middleComp.length > 0 && rightComp.length > 0) {
							Component mPiece = middleComp[0];
							Component rPiece = rightComp[0];
							if (mPiece instanceof JLabel && rPiece instanceof JLabel) {
								middlePiece = (JLabel)mPiece;
								rightPiece = (JLabel)rPiece;
								if (whitePlayer || kingPlayer) {
									if (middlePiece.getIcon().toString().equals(black) &&
										(rightPiece.getIcon().toString().equals(white) || rightPiece.getIcon().toString().equals(king))) {
										return middlePiece;
									}
								} else if (blackPlayer) {
									if (middlePiece.getIcon().toString().equals(white) && rightPiece.getIcon().toString().equals(black)) {
										return middlePiece;
									}
								}
							}
						} else if (middleComp.length > 0) {
							Component mPiece = middleComp[0];
							if (mPiece instanceof JLabel) {
								middlePiece = (JLabel)mPiece;
								boolean restricted = right.getBackground().equals(Color.decode("#004d00"));
								if (whitePlayer || kingPlayer) {
									if (middlePiece.getIcon().toString().equals(black) && restricted) {
										return middlePiece;
									}
								} else if (blackPlayer) {
									if (middlePiece.getIcon().toString().equals(white) && restricted) {
										return middlePiece;
									}
								}
							}
						}
					}
				}
				if (newLocationX >= 100) {
					JLabel middlePiece, leftPiece;
					Component middle = (JPanel) board[i - 1];
					Component left = (JPanel) board[i - 2];
					if (middle != null && left != null) {
						Component [] middleComp = ((Container)middle).getComponents();
						Component [] leftComp = ((Container)left).getComponents();
						if (middleComp.length > 0 && leftComp.length > 0) {
							Component mPiece = middleComp[0];
							Component rPiece = leftComp[0];
							if (mPiece instanceof JLabel && rPiece instanceof JLabel) {
								middlePiece = (JLabel)mPiece;
								leftPiece = (JLabel)rPiece;
								if (whitePlayer || kingPlayer) {
									if (middlePiece.getIcon().toString().equals(black) &&
										(leftPiece.getIcon().toString().equals(white) || leftPiece.getIcon().toString().equals(king))) {
										return middlePiece;
									}
								} else if (blackPlayer) {
									if (middlePiece.getIcon().toString().equals(white) && leftPiece.getIcon().toString().equals(black)) {
										return middlePiece;
									}
								}
							}
						} else if (middleComp.length > 0) {
							Component mPiece = middleComp[0];
							if (mPiece instanceof JLabel) {
								middlePiece = (JLabel)mPiece;
								boolean restricted = left.getBackground().equals(Color.decode("#004d00"));
								if (whitePlayer || kingPlayer) {
									if (middlePiece.getIcon().toString().equals(black) && restricted) {
										return middlePiece;
									}
								} else if (blackPlayer) {
									if (middlePiece.getIcon().toString().equals(white) && restricted) {
										return middlePiece;
									}
								}
							}
						}
					}
				}
				if (newLocationY >= 104) {
					JLabel middlePiece, topPiece;
					Component middle = (JPanel) board[i - 11];
					Component top = (JPanel) board[i - 22];
					if (middle != null && top != null) {
						Component [] middleComp = ((Container)middle).getComponents();
						Component [] topComp = ((Container)top).getComponents();
						if (middleComp.length > 0 && topComp.length > 0) {
							Component mPiece = middleComp[0];
							Component rPiece = topComp[0];
							if (mPiece instanceof JLabel && rPiece instanceof JLabel) {
								middlePiece = (JLabel)mPiece;
								topPiece = (JLabel)rPiece;
								if (whitePlayer || kingPlayer) {
									if (middlePiece.getIcon().toString().equals(black) &&
										(topPiece.getIcon().toString().equals(white) || topPiece.getIcon().toString().equals(king))) {
										return middlePiece;
									}
								} else if (blackPlayer) {
									if (middlePiece.getIcon().toString().equals(white) && topPiece.getIcon().toString().equals(black)) {
										return middlePiece;
									}
								}
							}
						} else if (middleComp.length > 0) {
							Component mPiece = middleComp[0];
							if (mPiece instanceof JLabel) {
								middlePiece = (JLabel)mPiece;
								boolean restricted = top.getBackground().equals(Color.decode("#004d00"));
								if (whitePlayer || kingPlayer) {
									if (middlePiece.getIcon().toString().equals(black) && restricted) {
										return middlePiece;
									}
								} else if (blackPlayer) {
									if (middlePiece.getIcon().toString().equals(white) && restricted) {
										return middlePiece;
									}
								}
							}
						}
					}
				}
				if (newLocationY <= 416) {
					JLabel middlePiece, bottomPiece;
					Component middle = (JPanel) board[i + 11];
					Component bottom = (JPanel) board[i + 22];
					if (middle != null && bottom != null) {
						Component [] middleComp = ((Container)middle).getComponents();
						Component [] bottomComp = ((Container)bottom).getComponents();
						if (middleComp.length > 0 && bottomComp.length > 0) {
							Component mPiece = middleComp[0];
							Component rPiece = bottomComp[0];
							if (mPiece instanceof JLabel && rPiece instanceof JLabel) {
								middlePiece = (JLabel)mPiece;
								bottomPiece = (JLabel)rPiece;
								if (whitePlayer || kingPlayer) {
									if (middlePiece.getIcon().toString().equals(black) &&
										(bottomPiece.getIcon().toString().equals(white) || bottomPiece.getIcon().toString().equals(king))) {
										return middlePiece;
									}
								} else if (blackPlayer) {
									if (middlePiece.getIcon().toString().equals(white) && bottomPiece.getIcon().toString().equals(black)) {
										return middlePiece;
									}
								}
							}
						} else if (middleComp.length > 0) {
							Component mPiece = middleComp[0];
							if (mPiece instanceof JLabel) {
								middlePiece = (JLabel)mPiece;
								boolean restricted = bottom.getBackground().equals(Color.decode("#004d00"));
								if (whitePlayer || kingPlayer) {
									if (middlePiece.getIcon().toString().equals(black) && restricted) {
										return middlePiece;
									}
								} else if (blackPlayer) {
									if (middlePiece.getIcon().toString().equals(white) && restricted) {
										return middlePiece;
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 *  method to see if pieces are captured via shieldwall
	 *  @param Component comp : piece being moved
	 *  @param JPanel gameBoard : the game board itself
	 *	@return Component [] : array of pieces to be deleted; null if none
	 */
	public ArrayList<JLabel> checkShieldwall(Component comp, JPanel gameBoard) {
		String black = "./src/main/resources/blackbutton.png";
		String white = "./src/main/resources/whitebutton.png";
		String king = "./src/main/resources/king.png";
		boolean whitePlayer = model.getPlayer().equals(white);
		boolean kingPlayer = model.getPlayer().equals(king);
		boolean blackPlayer = model.getPlayer().equals(black);
		int newLocationX = comp.getX();
		int newLocationY = comp.getY();
		boolean startCount = false;
		boolean done = false;
		ArrayList<JLabel> tbd = new ArrayList();
		//Check to see if the moving piece is at most one square away from the edge
		if ((newLocationX >= 100 && newLocationX <= 400) && (newLocationY >= 104 && newLocationY <= 416)) {
			return null;
		}
		//LEFT EDGE
		if (newLocationX < 100) {
			Component [] board = gameBoard.getComponents();
			for (int i = 0; i <= 520; i = i + 52) {
				int index = (i / 52) * 11;
				Component capturedComp = board[index];
				Component rightComp = board[index + 1];
				JLabel rightPiece = null;
				boolean restricted = capturedComp.getBackground().equals(Color.decode("#004d00"));
				if (restricted) {
					if (tbd.size() == 0) {
						startCount = true;
					} else if (tbd.size() == 1) {
						startCount = false;
						tbd.clear();
					} else {
						done = true;
					}
				} else if (capturedComp instanceof JPanel && rightComp instanceof JPanel) {
					JPanel capturedPanel = (JPanel)capturedComp;
					JPanel rightPanel = (JPanel)rightComp;
					Component [] capturedComps = capturedPanel.getComponents();
					Component [] rightComps = rightPanel.getComponents();
					if (capturedComps.length == 1) {
						Component capturedInnerComp = capturedComps[0];
						if (rightComps.length == 1) {
							Component rightInnerComp = rightComps[0];
							if (rightInnerComp instanceof JLabel) {
								rightPiece = (JLabel)rightInnerComp;
							}
						}
						//IF THERE IS A PIECE
						if (capturedInnerComp instanceof JLabel) {
							JLabel edgePiece = (JLabel)capturedInnerComp;
							//If defender's turn and edge piece is an ally
							if ((whitePlayer || kingPlayer) && (edgePiece.getIcon().toString().equals(white) || edgePiece.getIcon().toString().equals(king))) {
								if (tbd.size() == 0) {
									startCount = true;
								} else if (tbd.size() == 1) {
									startCount = false;
									tbd.clear();
								} else {
									done = true;
								}
							//If attacker's turn and edge piece is an ally
							} else if (blackPlayer && edgePiece.getIcon().toString().equals(black)) {
								if (tbd.size() == 0) {
									startCount = true;
								} else if (tbd.size() == 1) {
									startCount = false;
									tbd.clear();
								} else {
									done = true;
								}
							} else if ((whitePlayer || kingPlayer) && edgePiece.getIcon().toString().equals(black) && startCount) {
								if (rightPiece == null) {
									startCount = false;
									tbd.clear();
								} else if (rightPiece.getIcon().toString().equals(white) || rightPiece.getIcon().toString().equals(king)) {
									tbd.add(edgePiece);
								}
							} else if (blackPlayer && edgePiece.getIcon().toString().equals(white) && startCount) {
								if (rightPiece == null) {
									startCount = false;
									tbd.clear();
								} else if (rightPiece.getIcon().toString().equals(black)) {
									tbd.add(edgePiece);
								}
							}
							if (done) {
								break;
							}
						}
					} else if (tbd.size() >= 2) {
						tbd.clear();
						startCount = false;
					} else if (tbd.size() == 0) {
						startCount = false;
					}
				}
			}
			//If the king is within the shieldwall it isn't captured
			for (int i = 0; i < tbd.size(); i++) {
				if (tbd.get(i).getIcon().toString().equals(king)) {
					tbd.remove(i);
				}
			}
			return tbd;
		}
		//RIGHT EDGE
		if (newLocationX > 400) {
			Component [] board = gameBoard.getComponents();
			for (int i = 0; i <= 520; i = i + 52) {
				int index = ((i / 52) * 11) + 10;
				Component capturedComp = board[index];
				Component leftComp = board[index - 1];
				JLabel leftPiece = null;
				boolean restricted = capturedComp.getBackground().equals(Color.decode("#004d00"));
				if (restricted) {
					if (tbd.size() == 0) {
						startCount = true;
					} else if (tbd.size() == 1) {
						startCount = false;
						tbd.clear();
					} else {
						done = true;
					}
				} else if (capturedComp instanceof JPanel && leftComp instanceof JPanel) {
					JPanel capturedPanel = (JPanel)capturedComp;
					JPanel leftPanel = (JPanel)leftComp;
					Component [] capturedComps = capturedPanel.getComponents();
					Component [] leftComps = leftPanel.getComponents();
					if (capturedComps.length == 1) {
						Component capturedInnerComp = capturedComps[0];
						if (leftComps.length == 1) {
							Component leftInnerComp = leftComps[0];
							if (leftInnerComp instanceof JLabel) {
								leftPiece = (JLabel)leftInnerComp;
							}
						}
						//IF THERE IS A PIECE
						if (capturedInnerComp instanceof JLabel) {
							JLabel edgePiece = (JLabel)capturedInnerComp;
							//If defender's turn and edge piece is an ally
							if ((whitePlayer || kingPlayer) && (edgePiece.getIcon().toString().equals(white) || edgePiece.getIcon().toString().equals(king))) {
								if (tbd.size() == 0) {
									startCount = true;
								} else if (tbd.size() == 1) {
									startCount = false;
									tbd.clear();
								} else {
									done = true;
								}
							//If attacker's turn and edge piece is an ally
							} else if (blackPlayer && edgePiece.getIcon().toString().equals(black)) {
								if (tbd.size() == 0) {
									startCount = true;
								} else if (tbd.size() == 1) {
									startCount = false;
									tbd.clear();
								} else {
									done = true;
								}
							} else if ((whitePlayer || kingPlayer) && edgePiece.getIcon().toString().equals(black) && startCount) {
								if (leftPiece == null) {
									startCount = false;
									tbd.clear();
								} else if (leftPiece.getIcon().toString().equals(white) || leftPiece.getIcon().toString().equals(king)) {
									tbd.add(edgePiece);
								}
							} else if (blackPlayer && edgePiece.getIcon().toString().equals(white) && startCount) {
								if (leftPiece == null) {
									startCount = false;
									tbd.clear();
								} else if (leftPiece.getIcon().toString().equals(black)) {
									tbd.add(edgePiece);
								}
							}
							if (done) {
								break;
							}
						}
					} else if (tbd.size() >= 2) {
						tbd.clear();
						startCount = false;
					} else if (tbd.size() == 0) {
						startCount = false;
					}
				}
			}
			//If the king is within the shieldwall it isn't captured
			for (int i = 0; i < tbd.size(); i++) {
				if (tbd.get(i).getIcon().toString().equals(king)) {
					tbd.remove(i);
				}
			}
			return tbd;
		}
		//TOP EDGE
		if (newLocationY < 104) {
			Component [] board = gameBoard.getComponents();
			for (int i = 0; i <= 500; i = i + 50) {
				int index = i / 50;
				Component capturedComp = board[index];
				Component bottomComp = board[index + 11];
				JLabel bottomPiece = null;
				boolean restricted = capturedComp.getBackground().equals(Color.decode("#004d00"));
				if (restricted) {
					if (tbd.size() == 0) {
						startCount = true;
					} else if (tbd.size() == 1) {
						startCount = false;
						tbd.clear();
					} else {
						done = true;
					}
				} else if (capturedComp instanceof JPanel && bottomComp instanceof JPanel) {
					JPanel capturedPanel = (JPanel)capturedComp;
					JPanel bottomPanel = (JPanel)bottomComp;
					Component [] capturedComps = capturedPanel.getComponents();
					Component [] bottomComps = bottomPanel.getComponents();
					if (capturedComps.length == 1) {
						Component capturedInnerComp = capturedComps[0];
						if (bottomComps.length == 1) {
							Component bottomInnerComp = bottomComps[0];
							if (bottomInnerComp instanceof JLabel) {
								bottomPiece = (JLabel)bottomInnerComp;
							}
						}
						//IF THERE IS A PIECE
						if (capturedInnerComp instanceof JLabel) {
							JLabel edgePiece = (JLabel)capturedInnerComp;
							//If defender's turn and edge piece is an ally
							if ((whitePlayer || kingPlayer) && (edgePiece.getIcon().toString().equals(white) || edgePiece.getIcon().toString().equals(king))) {
								if (tbd.size() == 0) {
									startCount = true;
								} else if (tbd.size() == 1) {
									startCount = false;
									tbd.clear();
								} else {
									done = true;
								}
							//If attacker's turn and edge piece is an ally
							} else if (blackPlayer && edgePiece.getIcon().toString().equals(black)) {
								if (tbd.size() == 0) {
									startCount = true;
								} else if (tbd.size() == 1) {
									startCount = false;
									tbd.clear();
								} else {
									done = true;
								}
							} else if ((whitePlayer || kingPlayer) && edgePiece.getIcon().toString().equals(black) && startCount) {
								if (bottomPiece == null) {
									startCount = false;
									tbd.clear();
								} else if (bottomPiece.getIcon().toString().equals(white) || bottomPiece.getIcon().toString().equals(king)) {
									tbd.add(edgePiece);
								}
							} else if (blackPlayer && edgePiece.getIcon().toString().equals(white) && startCount) {
								if (bottomPiece == null) {
									startCount = false;
									tbd.clear();
								} else if (bottomPiece.getIcon().toString().equals(black)) {
									tbd.add(edgePiece);
								}
							}
							if (done) {
								break;
							}
						}
					} else if (tbd.size() >= 2) {
						tbd.clear();
						startCount = false;
					} else if (tbd.size() == 0) {
						startCount = false;
					}
				}
			}
			//If the king is within the shieldwall it isn't captured
			for (int i = 0; i < tbd.size(); i++) {
				if (tbd.get(i).getIcon().toString().equals(king)) {
					tbd.remove(i);
				}
			}
			return tbd;
		}
		//BOTTOM EDGE
		if (newLocationY > 416) {
			Component [] board = gameBoard.getComponents();
			for (int i = 0; i <= 500; i = i + 50) {
				int index = (i / 50) + 110;
				Component capturedComp = board[index];
				Component topComp = board[index - 11];
				JLabel topPiece = null;
				boolean restricted = capturedComp.getBackground().equals(Color.decode("#004d00"));
				if (restricted) {
					if (tbd.size() == 0) {
						startCount = true;
					} else if (tbd.size() == 1) {
						startCount = false;
						tbd.clear();
					} else {
						done = true;
					}
				} else if (capturedComp instanceof JPanel && topComp instanceof JPanel) {
					JPanel capturedPanel = (JPanel)capturedComp;
					JPanel topPanel = (JPanel)topComp;
					Component [] capturedComps = capturedPanel.getComponents();
					Component [] topComps = topPanel.getComponents();
					if (capturedComps.length == 1) {
						Component capturedInnerComp = capturedComps[0];
						if (topComps.length == 1) {
							Component topInnerComp = topComps[0];
							if (topInnerComp instanceof JLabel) {
								topPiece = (JLabel)topInnerComp;
							}
						}
						//IF THERE IS A PIECE
						if (capturedInnerComp instanceof JLabel) {
							JLabel edgePiece = (JLabel)capturedInnerComp;
							//If defender's turn and edge piece is an ally
							if ((whitePlayer || kingPlayer) && (edgePiece.getIcon().toString().equals(white) || edgePiece.getIcon().toString().equals(king))) {
								if (tbd.size() == 0) {
									startCount = true;
								} else if (tbd.size() == 1) {
									startCount = false;
									tbd.clear();
								} else {
									done = true;
								}
							//If attacker's turn and edge piece is an ally
							} else if (blackPlayer && edgePiece.getIcon().toString().equals(black)) {
								if (tbd.size() == 0) {
									startCount = true;
								} else if (tbd.size() == 1) {
									startCount = false;
									tbd.clear();
								} else {
									done = true;
								}
							} else if ((whitePlayer || kingPlayer) && edgePiece.getIcon().toString().equals(black) && startCount) {
								if (topPiece == null) {
									startCount = false;
									tbd.clear();
								} else if (topPiece.getIcon().toString().equals(white) || topPiece.getIcon().toString().equals(king)) {
									tbd.add(edgePiece);
								}
							} else if (blackPlayer && edgePiece.getIcon().toString().equals(white) && startCount) {
								if (topPiece == null) {
									startCount = false;
									tbd.clear();
								} else if (topPiece.getIcon().toString().equals(black)) {
									tbd.add(edgePiece);
								}
							}
							if (done) {
								break;
							}
						}
					} else if (tbd.size() >= 2) {
						tbd.clear();
						startCount = false;
					} else if (tbd.size() == 0) {
						startCount = false;
					}
				}
			}
			//If the king is within the shieldwall it isn't captured
			for (int i = 0; i < tbd.size(); i++) {
				if (tbd.get(i).getIcon().toString().equals(king)) {
					tbd.remove(i);
				}
			}
			return tbd;
		}
		return null;
	}


	/**
	 *	method to advance the turn of the game by one
	 */
	public void incrementTurn() {
		model.incrTurn();
	}

	/**
	 *	method to set the player on the model
	 *	@param String player : the player {}
	 */
	public void setPlayer(String player) {
		model.setPlayer(player);
	}

	
	/**
	 *	method to check if the game is over
	 *	@return boolean : true if the game is over, false otherwise
	 */
	public boolean gameOver() {
		return model.getGameOver();
	}
	
	
	/**
	 *	implements Serializable interface's writeObject method
	 *	@param ObjectOutputStream out : the output stream to write to
	 *	@throws IOException
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		out.writeObject(model);
		out.writeBoolean(defendersSurrounded);
		out.writeObject(pieces);
		out.writeObject(seen);
	}
	
	/**
	 *	implements Serializable interface's readObejct method
	 *	@param ObjectInputStream in : the input stream to read from
	 *	@throws IOException
	 *	@throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		model = (GameMove) in.readObject();
		defendersSurrounded = in.readBoolean();
		pieces = (String[][]) in.readObject();
		seen = (String[][]) in.readObject();
	}
	
}
