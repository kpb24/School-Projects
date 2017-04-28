
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

/**
 *	Models a single move on the board
 */
public class GameMove implements Serializable {
	public int turn;
	public boolean gameOver;
	public String player;
	public int attackerXOriginal;
	public int attackerYOriginal;
	public int attackerXDest;
	public int attackerYDest;
	
	public int DefenderXOriginal;
	public int DefenderYOriginal;
	public int DefenderXDest;
	public int DefenderYDest;

	/**
	 *	constructor
	 */
	public GameMove() {
		this.turn = 1;
		this.player = "Attacker";
		this.gameOver = false;
	}

	/**
	 *	increment the turn of the game - advance
	 */
	public void incrTurn() {
		this.turn++;
	}

	/**
	 *	query the turn
	 *	@return int : the turn number of the current game
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 *	set the turn number of the game
	 *	@param int currentTurn : the turn number to set the game to
	 */
	public void setTurn(int currentTurn){
		this.turn = currentTurn;
	}

	/**
	 *	set the current player
	 *	@param String player : the current player { "Attacker" | "Defender" }
	 */
	public void setPlayer(String player) {
		this.player = player;
	}

	/**
	 *	get the current player
	 *	@return String : the current player { "Attacker" | "Defender" }
	 */
	public String getPlayer() {
		return player;
	}

	/**
	 *	get the complete/over/done state of the game
	 *	@return boolean : true if the game is over, false otherwise
	 */
	public boolean getGameOver() {
		return gameOver;
	}

	/**
	 *	set the complete/over/done state of the game
	 *	@param boolean b : the complete/over/done state value
	 */
	public void setGameOver(boolean b) {
		this.gameOver = b;
	}
	
	
	/**
	 *	implements Serializable interface's writeObject method
	 *	@param ObjectOutputStream out : the output stream to write to
	 *	@throws IOException
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		out.writeInt(turn);
		out.writeBoolean(gameOver);
		out.writeUTF(player);
	}
	
	/**
	 *	implements Serializable interface's readObejct method
	 *	@param ObjectInputStream in : the input stream to read from
	 *	@throws IOException
	 *	@throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		turn = in.readInt();
		gameOver = in.readBoolean();
		player = in.readUTF();
	}
	
}
