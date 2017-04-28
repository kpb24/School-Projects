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
import java.util.concurrent.atomic.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import java.util.ArrayList;


/**
 *	GameBoardView
 *	renders and displays the board game and new game button
 *	currently operates as model, view, and controller
 */
public class GameBoardView extends JFrame implements MouseListener, MouseMotionListener {

	/*
	 *	constants
	 */
	private final int BOARD_LEN = 11;
	private final int BUTTON_LEN = 50;
	private final int FRAME_LEN = BUTTON_LEN * BOARD_LEN;
	private final int WINDOW_BAR_HEIGHT = 22;
	private final int TOTAL_HEIGHT = WINDOW_BAR_HEIGHT + FRAME_LEN + 50;

	/*
	 *	instance variables
	 */
	JLayeredPane layeredPane;
	JPanel gameBoard;
	JButton newGame;
	JButton loadGame;
	JButton saveGame;
	ActionListener buttonListener;

	JLabel blackTimer;
	JLabel whiteTimer;
	GameTimer blackGameTimer;
	GameTimer whiteGameTimer;
	AtomicBoolean timerShouldPause;
	AtomicBoolean timerShouldDie;

	Dimension boardSize;
	GamePlayControls controls;
	JLabel gamePiece;
	Controller controller;
	Container initialLocation;
	int xAdjust;
	int yAdjust;
	int xOriginal;
	int yOriginal;
	Point parentLocation;
	Thread timerThread;
	boolean gameStarted;
	ArrayList<Point> defenderPos;
	ArrayList<Point> attackerPos;
	String aggressive = "none";
	
	// TODO: replace hard-coded numbers here with…
	//	…constants computed from board dimensions at compile time
	int kingLocation[] = {60};
	int whitePieceLocations[] = {38,48,49,50,58,59,61,62,70,71,72,82};
	int blackPieceLocations[] = {3,4,5,6,7,16,33,43,44,54,55,56,64,65,
								66,76,77,87,104,113,114,115,116,117};

	/**
	 *	constructor
	 */
	public GameBoardView() {
		controller = new Controller();
		controls = new GamePlayControls();
		boardSize = new Dimension(FRAME_LEN, TOTAL_HEIGHT);
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(boardSize);
		getContentPane().add(layeredPane);
		gameBoard = new JPanel();
		gameBoard.setLayout(new GridLayout(BOARD_LEN, BOARD_LEN));
		gameBoard.setPreferredSize(boardSize);
		gameBoard.setBounds(0, 0, boardSize.width, WINDOW_BAR_HEIGHT + FRAME_LEN);
		layeredPane.add(gameBoard, BorderLayout.NORTH);
		layeredPane.addMouseListener(this);
		layeredPane.addMouseMotionListener(this);

		buttonListener = new ButtonListener(this);
		gameStarted = false;
		timerShouldPause = new AtomicBoolean(false);
		timerShouldDie = new AtomicBoolean(false);
		controller.setUpConfig("nnnnnnndnndddnnnddkddnnndddnndnnnnnnn");
		controller.setUpConfigA("aaaaaaanaannnnaaannnnaaannnaanaaaaaaa");
		defenderPos = new ArrayList<Point>();
		attackerPos = new ArrayList<Point>();
		
		//Add timer buttons to game board- has to be done separately from rest of game board setup
		setupTimerButtons();
	
		//Set up the rest of the game board
		setupBoard();
	
		//Create a new Thread for the timer
		createTimerThread();
	}
	
	/**
	* Sets up two timers, one for each player
	* Initializes JLabel for both teams and places JLabels on board
	* Initializes GameTimer Object for each team
	*/
	public void setupTimerButtons() {
		//Add the attacker's timer to the gameboard
		blackTimer = controls.setupBlackTimer();
		layeredPane.add(blackTimer);
		blackGameTimer = new GameTimer(blackTimer);
		
		//Add the defender's timer to the gameboard
		whiteTimer = controls.setupWhiteTimer();
		layeredPane.add(whiteTimer);
		whiteGameTimer = new GameTimer(whiteTimer);
	}
	
	/**
	 *	sets up the 11x11 game board
	 *	@see placePiece()
	*/
	public void setupBoard() {
		//iterate through each square on grid
		for (int j = 0; j < (BOARD_LEN * BOARD_LEN); j++) {
			JPanel square = new JPanel(new BorderLayout());
			square.setBackground(Color.decode("#008000"));
			square.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
			square.setLayout(new BorderLayout(1, 1));
			gameBoard.add(square);
			//determine colors
			//corners and center should be dark green
			if ((j == 0) || (j == BOARD_LEN - 1) ||
			(j == (BOARD_LEN * (BOARD_LEN - 1))) ||
			(j == ((BOARD_LEN * BOARD_LEN) - 1)) || (j == 60)) {
				square.setBackground(Color.decode("#004d00"));
			} //end if
			//placePiece(j); //place piece in correct spot on board -- do this once new game starts it
		} //end for

		setupControlButtons();

		//Reset whose turn it is
		controller.model.setTurn(1);
		controller.model.setGameOver(false);
	}
	
	/**
	*	sets the pieces on the board
	*/
	public void setupPieces() {
		for (int k=0; k<(BOARD_LEN*BOARD_LEN); k++) {
			placePiece(k);
		}	
	}
	
	/**
	 *	sets up the control buttons (new/save/load)
	*/
	public void setupControlButtons() {
		// set up game buttons
		newGame = controls.setupNewGame();
		newGame.addActionListener(buttonListener);
		layeredPane.add(newGame);
		
		loadGame = controls.setupLoadGame();
		loadGame.addActionListener(buttonListener);
		layeredPane.add(loadGame);
		
		saveGame = controls.setupSaveGame();
		saveGame.addActionListener(buttonListener);
		layeredPane.add(saveGame);
	}
	
	/**
	 *	Determines what piece, if any to place in a square on the board, using a search
	 *	helper function
	 *	@see searchLocation()
	 *	@param int j : linear index of the board position
	 *		(starting from 0 in top left and counting rightward then down)
	 *	@return String : the type of piece placed at the given position
	 *		{"white" | "black" | "king" | null}
	 */
	public String placePiece(int j) {
		if (searchLocation(whitePieceLocations, j) == true) {
			ImageIcon icon = new ImageIcon("./src/main/resources/whitebutton.png"); // add an image here
			JLabel piece = new JLabel(icon);
			JPanel panel = (JPanel)gameBoard.getComponent(j);
			panel.add(piece);
			return "white";
		} else if (searchLocation(blackPieceLocations, j) == true) {
			ImageIcon icon = new ImageIcon("./src/main/resources/blackbutton.png"); // add an image here
			JLabel piece = new JLabel(icon);
			JPanel panel = (JPanel)gameBoard.getComponent(j);
			panel.add(piece);
			return "black";
		} else if (searchLocation(kingLocation, j) == true) {
			ImageIcon icon = new ImageIcon("./src/main/resources/king.png"); // add an image here
			JLabel piece = new JLabel(icon);
			JPanel panel = (JPanel)gameBoard.getComponent(j);
			panel.add(piece);
			return "king";
		} else {
			return null;
		}
	} //end placepiece

	/**
	 *	Searches an array of piece positions via binary search (arrays are pre-sorted)
	 *	@param int[] pieces : the array of piece positions (linear indices)
	 *	@param int j : the linear index to search for in the piece position array
	 *	@return boolean : true if the value of (j) is found in (pieces)
	 */
	public boolean searchLocation(int[]pieces, int j) {
		int low = 0;
		int high = pieces.length - 1;
		while (high >= low) {
			int middle = (low + high) / 2;
			if (pieces[middle] == j) {
				return true;
			}
			if (pieces[middle] < j) {
				low = middle + 1;
			}
			if (pieces[middle] > j) {
				high = middle - 1;
			}
		}
		return false;
	}


	/**
	 *	handler for a mouse-press event on the board/button
	 *	@param MouseEvent event : the event created by the swing framework
	 */
	public void mousePressed(MouseEvent event) {
		if (controller.gameOver()) {
			return;
		}
		gamePiece = null;
		Component comp =  gameBoard.findComponentAt(event.getX(), event.getY());
		if (comp != null) {
			initialLocation = comp.getParent();
			if (comp instanceof JPanel) {
				return;
			} else if (initialLocation != null) {
				parentLocation = comp.getParent().getLocation();
				xAdjust = parentLocation.x - event.getX();
				yAdjust = parentLocation.y - event.getY();
				xOriginal = event.getX() + xAdjust;
				yOriginal = event.getY() + yAdjust;
				gamePiece = (JLabel) comp;
				gamePiece.setLocation(event.getX() + xAdjust, event.getY() + yAdjust);
				layeredPane.add(gamePiece, JLayeredPane.DRAG_LAYER);
				layeredPane.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			}
		}
	}


	/**
	 *	handler for a mouse-drag event on the board/button - allows pieces to be moved by dragging
	 *	@param MouseEvent event : the event created by the swing framework
	 */
	public void mouseDragged(MouseEvent event) {
		if (gamePiece == null) {
			return;
		}
		if (controller.gameOver()) {
			return;
		}
		//Should not be able to drag outside of board
		int x = event.getX() + xAdjust;
		int xMax = layeredPane.getWidth() - gamePiece.getWidth();
		x = Math.min(x, xMax);
		x = Math.max(x, 0);
		int y = event.getY() + yAdjust;
		int yMax = layeredPane.getHeight() - gamePiece.getHeight();
		y = Math.min(y, yMax);
		y = Math.max(y, 0);
		gamePiece.setLocation(x, y);
	}


	/**
	 *  Method for dropping the game piece back onto the board. It allows the player
	 *		to hover over a location and the piece will drop there if it's that player's piece.
	 *		If it's not the player's piece, it will go back to the original location.
	 *	@param MouseEvent event : the event created by the swing framework
	 */
	public void mouseReleased(MouseEvent event) {
		layeredPane.setCursor(null);
		if (gamePiece == null) {
			return;
		}
		if (controller.gameOver()) {
			return;
		}
		//Make sure the game piece is removed from layered pane
		gamePiece.setVisible(false);
		layeredPane.remove(gamePiece);
		gamePiece.setVisible(true);
		//Shouldn't be able to drop piece outside of board
		int xMax = layeredPane.getWidth() - gamePiece.getWidth();
		int x = Math.min(event.getX(), xMax);
		x = Math.max(x, 0);
		int yMax = layeredPane.getHeight() - gamePiece.getHeight();
		int y = Math.min(event.getY(), yMax);
		y = Math.max(y, 0);
		Component comp =  gameBoard.findComponentAt(x, y);
		Point destination = comp.getLocation();
		boolean valid = controller.isValid(destination, xOriginal, yOriginal, gameBoard);
		//Ensure that this is the appropriate person's turn
		controller.setPlayer(gamePiece.getIcon().toString());
		//Ensure that this move is valid
		
		boolean restrictedValid = controller.checkRestricted(comp);
		if (controller.checkTurn() == false || !restrictedValid || comp instanceof JLabel || !valid) {
			initialLocation.add(gamePiece);
			initialLocation.validate();
		} else {
			if (comp instanceof JLabel) {
				Container parent = comp.getParent();
				parent.remove(0);
				parent.add(gamePiece);
				parent.validate();
				controller.incrementTurn();
			} else {
				Container parent = (Container)comp;
				parent.add(gamePiece);
				parent.validate();
				controller.incrementTurn();
			}
			
			
		//check perpetual repetitions
		
		String lose= controller.checkRepetition(gameBoard);
		if(lose.equals("defenders")){
			String outputString = "Congratulations! The Attackers have won!";
			controller.model.setGameOver(true);
			JOptionPane.showMessageDialog(gameBoard, outputString, "Game Over", JOptionPane.INFORMATION_MESSAGE);
		}

		else if(lose.equals("attackers")){
			String outputString = "Congratulations! The Defenders have won!";
			controller.model.setGameOver(true);
			JOptionPane.showMessageDialog(gameBoard, outputString, "Game Over", JOptionPane.INFORMATION_MESSAGE);
		}

		//check for draw fort
		
		
		
		
		
		
		
		
		
		
			//Check to see if this player has won
			String winningPlayer = controller.checkWinner(comp, gameBoard);
			if (!winningPlayer.equals("false")) {
				String outputString = "Congratulations! The " + winningPlayer + " have won!";
				controller.model.setGameOver(true);
				JOptionPane.showMessageDialog(gameBoard, outputString, "Game Over", JOptionPane.INFORMATION_MESSAGE);
			}
			
			//Check to see if any pieces were captured with rule 5a
			Component captured = controller.checkCapture(comp, gameBoard);
			while (captured != null) {
				Container deletePiece = captured.getParent();
				deletePiece.remove(captured);
				deletePiece.validate();
				deletePiece.repaint();
				captured = controller.checkCapture(comp, gameBoard);
			}
			ArrayList<JLabel> tbd = controller.checkShieldwall(comp, gameBoard);
			if (tbd != null) {
				while (tbd.size() >= 2) {
					for (int i = 0; i < tbd.size(); i++) {
						Container deletePiece = tbd.get(i).getParent();
						deletePiece.remove(tbd.get(i));
						deletePiece.validate();
						deletePiece.repaint();
					}
					tbd = controller.checkShieldwall(comp, gameBoard);
				}
			}

			// Add 3 seconds to that player's timer for making a move
			if (gamePiece.getIcon().toString().equals("./src/main/resources/blackbutton.png")) {
				//add 3 seconds to black
				blackGameTimer.addTime();			
			} else {
				//add 3 seconds to white
				whiteGameTimer.addTime();
			}
		}
	}
	
	
	/**
	 *	Reset the game to brand-new state
	 */
	public void newGame() {		
		//Remove "old" pieces
		gameBoard.removeAll();
		gameBoard.revalidate();
		gameBoard.repaint();
		setupBoard();
		gameStarted = false;
		if (!gameStarted) {
			setupPieces();
		}
		
		//Reset black timer label
		blackTimer.setText("5:01");
		blackGameTimer.label = blackTimer;
		blackGameTimer.minutesRemaining = 5;
		blackGameTimer.secondsRemaining = 1;
		
		//Reset white timer label
		whiteTimer.setText("5:00");
		whiteGameTimer.label = whiteTimer;
		whiteGameTimer.minutesRemaining = 5;
		whiteGameTimer.secondsRemaining = 0;
		
		//Display changes
		gameBoard.revalidate();
		gameBoard.repaint();
		
		//re-start the timer thread
		killAndResurrectTimer();
		gameStarted = true;
	}
	
	
	/**
	 *	Store a GameBoardView to a saved state in serialized data
	 *	@param File file : the file to save to
	 *	@return boolean : true if save is successful, false otherwise
	 */
	public boolean saveToFile(File file) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			Component[] comps = layeredPane.getComponentsInLayer(JLayeredPane.DEFAULT_LAYER);
			oos.writeObject(comps[0]);
			oos.writeObject(gameBoard);
			
			//oos.writeObject(blackGameTimer.label);
			oos.writeInt(blackGameTimer.minutesRemaining);
			oos.writeInt(blackGameTimer.secondsRemaining);
			oos.writeInt(whiteGameTimer.minutesRemaining);
			oos.writeInt(whiteGameTimer.secondsRemaining);
			
			oos.writeObject(controller);
			oos.writeObject(initialLocation);			
			oos.writeInt(xAdjust);
			oos.writeInt(xOriginal);
			oos.writeInt(yAdjust);
			oos.writeInt(yOriginal);
			
			oos.close();
			fos.close();
			return true;
		} catch (Exception e) {
			System.out.println("ERROR: " + e);
			return false;
		}
	}
	
	
	/**
	 *	Restore a GameBoardView to a saved state using serialized data
	 *	@param File file : the saved game file to restore to
	 *	@return boolean : true if load successful, false otherwise
	 */
	public boolean loadFromFile(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			// kill the timer
			timerShouldDie.set(true);
			timerThread.interrupt();
			try {
				timerThread.join();
			} catch (Exception e) {
				System.out.println(e);
			}
			
			gameBoard.removeAll();
			Component comp = (Component) ois.readObject();
			layeredPane.remove(0);
			layeredPane.add(comp, 0);
			
			gameBoard = (JPanel) ois.readObject();
			
			blackGameTimer.minutesRemaining = ois.readInt();
			blackGameTimer.secondsRemaining = ois.readInt();
			whiteGameTimer.minutesRemaining = ois.readInt();
			whiteGameTimer.secondsRemaining = ois.readInt();
			
			controller = (Controller) ois.readObject();
			initialLocation = (Container) ois.readObject();
			xAdjust = ois.readInt();
			xOriginal = ois.readInt();
			yAdjust = ois.readInt();
			yOriginal = ois.readInt();
			
			gameStarted = true;
			
			ois.close();
			fis.close();
			
			layeredPane.revalidate();
			layeredPane.repaint();
			gameBoard.revalidate();
			gameBoard.repaint();
			
			blackGameTimer.label.setText(
				String.valueOf(blackGameTimer.minutesRemaining) + " : " + 
				String.format("%02d", blackGameTimer.secondsRemaining)
			);
			whiteGameTimer.label.setText(
				String.valueOf(whiteGameTimer.minutesRemaining) + " : " + 
				String.format("%02d", whiteGameTimer.secondsRemaining)
			);
			
			killAndResurrectTimer();
			
			return true;
		} catch (Exception e) {
			System.out.println("ERROR: " + e);
			return false;
		}
	}
	
	
	/** not yet implemented */
	public void mouseClicked(MouseEvent e) {}
	/** not yet implemented */
	public void mouseMoved(MouseEvent e) {}
	/** not yet implemented */
	public void mouseEntered(MouseEvent e) {}
	/** not yet implemented */
	public void mouseExited(MouseEvent e) {}

	
	/**
	* Method that creates a new thread for the clock to run on
	* Will increment appropriate timer every second while the game is running
	*/
	public void createTimerThread() {
		timerThread = new Thread(new Runnable() {
			public void run() {	
				//While the game has not ended, decrement the appropriate timer every second
				while(!Thread.interrupted() && !controller.model.getGameOver() && !timerShouldDie.get()) { 
					boolean pause = timerShouldPause.get();
					try {
						if (!pause) {
							decrementTimer();
						}
						Thread.sleep(1000);
					} catch (InterruptedException iex) {
						break;
					} //end try-catch
				} //end while loop	
			} //end run()
		});
		timerThread.setDaemon(true);
	}
	
	
	/**
	* Method that terminates an existing timer thread and creates a new one
	* @see createTimerThread()
	*/
	public void killAndResurrectTimer() {
		// kill the timer thread if it exists
		timerShouldDie.set(true);
		timerThread.interrupt();
		try {
			timerThread.join();
		} catch (Exception e) {
			System.out.println(e);
		}
		timerThread = null;
		timerShouldDie.set(false);
		createTimerThread();
		timerThread.start();
	}
	
	
	/**
	* Method that decrements the timer of the appropriate player
	* Checks to see whose turn it is, subtracts one second from that timer
	* Ends game if timer has reached 0
	*/
	public void decrementTimer() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				boolean timeRemaining;
				
				// Determine whose turn it is and decrement that timer
				int currentTurn = controller.model.getTurn();

				if(currentTurn % 2 == 1){
					//Decrement black
					timeRemaining = blackGameTimer.decrement();			
				}
				else{
					//Decrement white
					timeRemaining = whiteGameTimer.decrement();
				}
				
				//If time is up, the game is over and the current player has lost
				if (!timeRemaining){
					//Determine the winning player
					String winningTeam = "";
					
					if (currentTurn % 2 == 1){
						winningTeam = "Defenders";
					}
					
					else {
						winningTeam = "Attackers";
					}
					
					String outputString = "Times up! The " + winningTeam + " have won!";
					controller.model.setGameOver(true);
					JOptionPane.showMessageDialog(gameBoard, outputString, "Game Over", JOptionPane.INFORMATION_MESSAGE);
				} //end if
			} //end run()
		});		
	}
	
	
	/**
	 *	private internal class to act as controller for the game control buttons
	 */
	class ButtonListener implements ActionListener {
		GameBoardView containingView;
		transient JFileChooser fileChooser;
		FileNameExtensionFilter filter;
		
		ButtonListener(GameBoardView _container) {
			containingView = _container;
			fileChooser = new JFileChooser();
			filter = new FileNameExtensionFilter("Hnefatafl saved game files", "tafl");
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.addChoosableFileFilter(filter);
		}

		/**
		 *	resets the current game
		 */
		public void newGameTriggered() {
			containingView.newGame();
		}


		/**
		 *	opens a file-chooser to set a save-file
		 */
		public void saveToFileTriggered() {
			fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
			fileChooser.setSelectedFile(new File("MyGame.tafl"));
			fileChooser.setFileFilter(filter);
			timerShouldPause.set(true);
			int fint = fileChooser.showSaveDialog(containingView);
			if (fint == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				containingView.saveToFile(file);
				String filename = fileChooser.getSelectedFile().toString();
				if (!filename.endsWith(".tafl")) {
					filename += ".tafl";
				}
				File saveFile = new File(filename);
				file.renameTo(saveFile);
			} else {
				System.out.println("ERROR: could not save to file");
			}			
			fileChooser.setSelectedFile(null);
			timerShouldPause.set(false);
		}
		
		
		/**
		 *	opens a file-chooser to select a file from which to load the game
		 */
		public void loadFromFileTriggered() {
			timerShouldPause.set(true);
			int fint = fileChooser.showOpenDialog(containingView);
			if (fint == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				containingView.loadFromFile(file);
			} else {
				System.out.println("ERROR: could not load from file");
			}
			fileChooser.setSelectedFile(null);
			timerShouldPause.set(false);
		}
		
		
		/**
		 *	method invoked by swing framework when button is clicked
		 *	@param ActionEvent event : the event object
		 */
		public void actionPerformed(ActionEvent event) {
			JButton source = (JButton) event.getSource();
			if (source == null) {
				return;
			}
			String currentText = source.getText();
			if (currentText.equals("New Game")) {
				// new
				newGameTriggered();
				layeredPane.revalidate();
				layeredPane.repaint();
			} else if (currentText == "Save Game") {
				// save
				saveToFileTriggered();
			} else if (currentText == "Load Game") {
				// load
				loadFromFileTriggered();
				layeredPane.revalidate();
				layeredPane.repaint();
			}
		}
	}
}