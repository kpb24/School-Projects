
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
import java.util.*;
import javax.swing.*;

public class GamePlayControls {

	private final int BOARD_LEN = 11;
	private final int BUTTON_LEN = 50;
	private final int FRAME_WIDTH = BUTTON_LEN * BOARD_LEN;
	private final int WINDOW_BAR_HEIGHT = 22;  // I think this is specific to macOS
	private final int TOTAL_HEIGHT = WINDOW_BAR_HEIGHT + FRAME_WIDTH + 50;
	private final int BUTTON_WIDTH = 100;
	private final int BUTTON_HEIGHT = 40;
	private final int BUTTON_Y = WINDOW_BAR_HEIGHT + FRAME_WIDTH + 5;
	private final int SAVE_X0 = (FRAME_WIDTH/3) - ((3*BUTTON_WIDTH)/4);
	private final int LOAD_X0 = FRAME_WIDTH - BUTTON_WIDTH - SAVE_X0;

	/**
	 *	constructor (empty)
	 */
	public GamePlayControls() {
		
	}
	
	/**
	 *	Method that adds a button to the game board
	 * @param String label : the JLabel to be added to the board
	 */
	public JButton setupGameButton(String label) {
		JButton button = new JButton(label);
		button.setBorder(null);
		button.setBackground(Color.decode("#004d00"));
		button.setFont(new Font("Helvetica", Font.PLAIN, 14));
		button.setForeground(Color.WHITE);
		button.setOpaque(true);
		return button;
	}

	/**
	* Creates "New Game" JButton
	* @return JButton : the "New Game" button 
	*/
	public JButton setupNewGame() {
		//Set up "New game" button
		JButton newGame = setupGameButton("New Game");
		newGame.setBounds((FRAME_WIDTH / 2) - (BUTTON_WIDTH/2), BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		return newGame;
	}
	
	/**
	* Creates "Save Game" JButton
	* @return JButton : the "Save Game" button 
	*/
	public JButton setupSaveGame() {
		JButton saveGame = setupGameButton("Save Game");
		saveGame.setBounds(SAVE_X0, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		return saveGame;
	}
	
	/**
	* Creates "Load Game" JButton
	* @return JButton : the "Load Game" button 
	*/
	public JButton setupLoadGame() {
		JButton saveGame = setupGameButton("Load Game");
		saveGame.setBounds(LOAD_X0, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		return saveGame;
	}
	
	
	/**
	* Creates attacking team's timer
	* @return JLabel : the attacker's timer  
	*/
	public JLabel setupBlackTimer() {
		//Set up black timer
		JLabel blackTimer = new JLabel("5 : 00", JLabel.CENTER);
		blackTimer.setBorder(null);
		blackTimer.setBackground(Color.BLACK);
		blackTimer.setFont(new Font("Helvetica", Font.PLAIN, 14));
		blackTimer.setForeground(Color.WHITE);
		blackTimer.setOpaque(true);
		blackTimer.setBounds(0, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		
		return blackTimer;
	}
	
	/**
	* Creates defending team's timer
	* @return JLabel : the defender's timer  
	*/
	public JLabel setupWhiteTimer() {
		//Set up white timer
		JLabel whiteTimer = new JLabel("5 : 00", JLabel.CENTER);
		whiteTimer.setBorder(null);
		whiteTimer.setBackground(Color.WHITE);
		whiteTimer.setFont(new Font("Helvetica", Font.PLAIN, 14));
		whiteTimer.setForeground(Color.BLACK);
		whiteTimer.setOpaque(true);
		whiteTimer.setBounds(FRAME_WIDTH - BUTTON_WIDTH, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		
		return whiteTimer;
	}
}
