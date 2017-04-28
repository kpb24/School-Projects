import static org.junit.Assert.*;
import org.junit.Test;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class HnefataflTest {
	/*
	 *  Test that the turn becomes two after being incremented once.
	 */
	@Test
	public void testGameMoveTurn0() {
		GameMove g = new GameMove();
		g.incrTurn();
		int turn = g.getTurn();
		assertEquals(turn, 2);
	}
  
	/*
	 *  Test that getPlayer returns the correct player.
	 */
	@Test
	public void testGameMovePlayer0() {
		GameMove g = new GameMove();
		g.setPlayer("./src/main/resources/whitebutton.png");
		String player = g.getPlayer();
		assertEquals(player, "./src/main/resources/whitebutton.png");
	}

	/*
	 *  Test that the game isn't over when starting a game.
	 */
	@Test
	public void testGameMoveGameOver0() {
		GameMove g = new GameMove();
		boolean gameOver = g.getGameOver();
		assertFalse(gameOver);
	}

	/*
	 *  Test that once a player has won, the game is set to be over.
	 */
	@Test
	public void testGameMoveGameOver1() {
		GameMove g = new GameMove();
		g.setGameOver(true);
		boolean gameOver = g.getGameOver();
		assertTrue(gameOver);
	}

	/*
	 *  Test that the white player goes when it's an even number turn.
	 */
	@Test
	public void testControllerCheckTurn0() {
		Controller c = new Controller();
		c.model.setPlayer("./src/main/resources/whitebutton.png");
		c.model.incrTurn();
		boolean t = c.checkTurn();
		assertTrue(t);
	}

	/*
	 *  Test that the white player can move a king.
	 */
	@Test
	public void testControllerCheckTurn1() {
		Controller c = new Controller();
		c.model.setPlayer("./src/main/resources/king.png");

		//turn is incremented to 4
		for(int i = 1; i <= 3; i++) {
			c.model.incrTurn();
		}

		boolean t = c.checkTurn();
		assertTrue(t);
	}

	/*
	 *  Test that the black player can move when the turn is odd.
	 */
	@Test
	public void testControllerCheckTurn2() {
		Controller c = new Controller();
		c.model.setPlayer("./src/main/resources/blackbutton.png");

		//turn is incremented to 3
		for(int i = 1; i <= 2; i++) {
			c.model.incrTurn();
		}

		boolean t = c.checkTurn();
		assertTrue(t);
	}

	/*
	 *  Test that the black player goes first.
	 */
	@Test
	public void testControllerCheckTurn3() {
		Controller c = new Controller();
		c.model.setPlayer("./src/main/resources/blackbutton.png");
		boolean t = c.checkTurn();
		assertTrue(t);
	}

	/*
	 *  If the king reaches the top left corner, the defenders have won
	 *  Create a new JLabel with the king icon, and JPanel with the restricted
	 *  square background color and top left location.
	 *  Move the king to the JPanel. The returned String will be "Defenders"
	 */
	@Test
	public void testControllerKingTopLeftCorner() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		c.setPlayer("./src/main/resources/king.png");
		JPanel oldSquare = new JPanel();
		c.model.setPlayer("./src/main/resources/king.png");
		oldSquare.setLayout(null);
		oldSquare.setBackground(Color.decode("#004d00"));
		oldSquare.setLocation(0,0);

		String winner = c.checkWinner(oldSquare, v.gameBoard);
		assertEquals("Defenders", winner);
	}

	/*
	 *  If the king reaches the top right corner, the defenders have won
	 *  Create a new JLabel with the king icon, and JPanel with the restricted
	 *  square background color and top right location.
	 *  Move the king to the JPanel. The returned String will be "Defenders"
	 */
	@Test
	public void testControllerKingTopRightCorner() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		c.setPlayer("./src/main/resources/king.png");
		JPanel oldSquare = new JPanel();
		oldSquare.setLayout(null);
		oldSquare.setBackground(Color.decode("#004d00"));
		oldSquare.setLocation(500,0);

		String winner = c.checkWinner(oldSquare, v.gameBoard);
		assertEquals("Defenders", winner);
	}

	/*
	 *  If the king reaches the bottom left corner, the defenders have won
	 *  Create a new JLabel with the king icon, and JPanel with the restricted
	 *  square background color and bottom left location.
	 *  Move the king to the JPanel. The returned String will be "Defenders"
	 */
	@Test
	public void testControllerKingBottomLeftCorner() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		c.setPlayer("./src/main/resources/king.png");
		JLabel movingPiece = new JLabel(new ImageIcon("./src/main/resources/king.png"));
		JPanel oldSquare = new JPanel();
		oldSquare.setLayout(null);
		oldSquare.setBackground(Color.decode("#004d00"));
		oldSquare.setLocation(0,520);

		String winner = c.checkWinner(oldSquare, v.gameBoard);
		assertEquals("Defenders", winner);
	}

	/*
	 *  If the king reaches the bottom right corner, the defenders have won
	 *  Create a new JLabel with the king icon, and JPanel with the restricted
	 *  square background color and bottom right location.
	 *  Move the king to the JPanel. The returned String will be "Defenders"
	 */
	@Test
	public void testControllerKingBottomRightCorner() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		c.setPlayer("./src/main/resources/king.png");
		JPanel oldSquare = new JPanel();
		oldSquare.setLayout(null);
		oldSquare.setBackground(Color.decode("#004d00"));
		oldSquare.setLocation(500,520);

		String winner = c.checkWinner(oldSquare, v.gameBoard);
		assertEquals("Defenders", winner);
	}

	/*
	 *  If the King's position is on the top of the board, it cannot be captured
	 *  Pass the leftmost square of the top edge of the board into checkEdge, it will return true.
	 */
	@Test
	public void testControllerTopLeftEdge() {
		Controller c = new Controller();
		int position = 0;
		assertTrue(c.checkEdge(position));
	}

	/*
	 *  If the King's position is on the top of the board, it cannot be captured
	 *  Pass a middle square of the top edge of the board into checkEdge, it will return true.
	 */
	@Test
	public void testControllerTopMiddleEdge() {
		Controller c = new Controller();
		int position = 5;
		assertTrue(c.checkEdge(position));
	}

	/*
	 *  If the King's position is on the top of the board, it cannot be captured
	 *  Pass the rightmost square of the top edge of the board into checkEdge, it will return true.
	 */
	@Test
	public void testControllerTopRightEdge() {
		Controller c = new Controller();
		int position = 10;
		assertTrue(c.checkEdge(position));
	}

	/*
	 *  If the King's position is on the left edge of the board, it cannot be captured
	 *  Pass the a middle square of the left edge of the board into checkEdge, it will return true.
	 */
	@Test
	public void testControllerLeftEdge() {
		Controller c = new Controller();
		int position = 55;
		assertTrue(c.checkEdge(position));
	}

	/*
	 *  If the King's position is on the right edge of the board, it cannot be captured
	 *  Pass the a middle square of the right edge of the board into checkEdge, it will return true.
	 */
	@Test
	public void testControllerRightEdge() {
		Controller c = new Controller();
		int position = 65;
		assertTrue(c.checkEdge(position));
	}

	/*
	 *  If the King's position is on the bottom of the board, it cannot be captured
	 *  Pass the leftmost square of the bottom edge of the board into checkEdge, it will return true.
	 */
	@Test
	public void testControllerBottomLeftEdge() {
		Controller c = new Controller();
		int position = 110;
		assertTrue(c.checkEdge(position));
	}

	/*
	 *  If the King's position is on the bottom of the board, it cannot be captured
	 *  Pass the middle square of the bottom edge of the board into checkEdge, it will return true.
	 */
	@Test
	public void testControllerBottomMiddleEdge() {
		Controller c = new Controller();
		int position = 115;
		assertTrue(c.checkEdge(position));
	}

	/*
	 *  If the King's position is on the bottom of the board, it cannot be captured
	 *  Pass the rightmost square of the bottom edge of the board into checkEdge, it will return true.
	 */
	@Test
	public void testControllerBottomRightEdge() {
		Controller c = new Controller();
		int position = 120;
		assertTrue(c.checkEdge(position));
	}

	//NEW ONES

	/*
	 *  If the king is surrounded by 4 attackers, the attackers have won
	 *  Create 4 components, all of which hold a black game piece, and put into an array.
	 *  Calling checkNeighbors will return true
	 */
	@Test
	public void testSurroundedAllBlack() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		ImageIcon icon = new ImageIcon("./src/main/resources/blackbutton.png");
		JLabel piece = new JLabel(icon);

		ImageIcon icon2 = new ImageIcon("./src/main/resources/blackbutton.png");
		JLabel piece2 = new JLabel(icon2);

		ImageIcon icon3 = new ImageIcon("./src/main/resources/blackbutton.png");
		JLabel piece3 = new JLabel(icon3);

		ImageIcon icon4 = new ImageIcon("./src/main/resources/blackbutton.png");
		JLabel piece4 = new JLabel(icon4);

		JPanel attacker1 = new JPanel();
		JPanel attacker2 = new JPanel();
		JPanel attacker3 = new JPanel();
		JPanel attacker4 = new JPanel();

		attacker1.add(piece);
		attacker2.add(piece2);
		attacker3.add(piece3);
		attacker4.add(piece4);

		JPanel [] attackers = new JPanel [] {attacker1, attacker2, attacker3, attacker4};

		assertTrue(c.checkNeighbors(attackers, v.gameBoard));
	}

	/*
	 *  If the king is surrounded by 3 attackers, the attackers have won
	 *  Create 3 components, all of which hold a black game piece, plus a restricted square panel.
	 *  Put them all into an array. Calling checkNeighbors will return true
	 */
	@Test
	public void testSurrounded3Black() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		ImageIcon icon = new ImageIcon("./src/main/resources/blackbutton.png");
		JLabel piece = new JLabel(icon);

		ImageIcon icon2 = new ImageIcon("./src/main/resources/blackbutton.png");
		JLabel piece2 = new JLabel(icon2);

		ImageIcon icon3 = new ImageIcon("./src/main/resources/blackbutton.png");
		JLabel piece3 = new JLabel(icon3);

		JPanel attacker1 = new JPanel();
		JPanel attacker2 = new JPanel();
		JPanel attacker3 = new JPanel();
		JPanel throne = new JPanel();
		throne.setBackground(Color.decode("#004d00"));

		attacker1.add(piece);
		attacker2.add(piece2);
		attacker3.add(piece3);

		Component [] attackers = new Component [] {attacker1, attacker2, attacker3, throne};

		assertTrue(c.checkNeighbors(attackers, v.gameBoard));
	}

	/*
	 *  If the king is surrounded by 3 attackers, and an empty square (not throne)
	 *  the attackers have NOT won. Create 3 components, all of which hold a black game piece
	 *  And one unrestricted square, then put into an array.
	 *  Calling checkNeighbors will return false
	 */
	@Test
	public void testNotSurroundedEmpty() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		ImageIcon icon = new ImageIcon("./src/main/resources/blackbutton.png");
		JLabel piece = new JLabel(icon);

		ImageIcon icon2 = new ImageIcon("./src/main/resources/blackbutton.png");
		JLabel piece2 = new JLabel(icon2);

		ImageIcon icon3 = new ImageIcon("./src/main/resources/blackbutton.png");
		JLabel piece3 = new JLabel(icon3);

		JPanel attacker1 = new JPanel();
		JPanel attacker2 = new JPanel();
		JPanel attacker3 = new JPanel();
		JPanel throne = new JPanel();
		throne.setBackground(Color.decode("#008000"));

		attacker1.add(piece);
		attacker2.add(piece2);
		attacker3.add(piece3);

		Component [] attackers = new Component [] {attacker1, attacker2, attacker3, throne};

		assertFalse(c.checkNeighbors(attackers, v.gameBoard));
	}

	/*
	 *  If the king is surrounded by 3 attackers and one defender, the attackers have NOT won
	 *  Create 3 components, all of which hold a black game piece, and 1 white piece.
	 *  Put into an array. Calling checkNeighbors will return false
	 */
	@Test
	public void testNotSurroundedWhite() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		ImageIcon icon = new ImageIcon("./src/main/resources/blackbutton.png");
		JLabel piece = new JLabel(icon);

		ImageIcon icon2 = new ImageIcon("./src/main/resources/blackbutton.png");
		JLabel piece2 = new JLabel(icon2);

		ImageIcon icon3 = new ImageIcon("./src/main/resources/blackbutton.png");
		JLabel piece3 = new JLabel(icon3);

		ImageIcon icon4 = new ImageIcon("./src/main/resources/whitebutton.png");
		JLabel piece4 = new JLabel(icon4);

		JPanel attacker1 = new JPanel();
		JPanel attacker2 = new JPanel();
		JPanel attacker3 = new JPanel();
		JPanel attacker4 = new JPanel();

		attacker1.add(piece);
		attacker2.add(piece2);
		attacker3.add(piece3);
		attacker4.add(piece4);

		JPanel [] attackers = new JPanel [] {attacker1, attacker2, attacker3, attacker4};

		assertFalse(c.checkNeighbors(attackers, v.gameBoard));
	}

	/*
	 *  If the king re-enters the throne, the defenders have not won
	 *  Set player as the king and move to the throne.
	 *  The returned String will be "false"
	 */
	@Test
	public void testKingThrone() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		c.setPlayer("./src/main/resources/king.png");
		JPanel oldSquare = new JPanel();
		oldSquare.setLayout(null);
		oldSquare.setBackground(Color.decode("#004d00"));
		oldSquare.setLocation(250,260);

		String winner = c.checkWinner(oldSquare, v.gameBoard);
		assertEquals("false", winner);
	}

	/*
	 *  No piece except the king can enter a restricted square.
	 *  Set player as black and set desired location.
	 *  The restrictedSquare method will return false.
	 */
	@Test
	public void testBlackRestrictedSquare(){
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		c.setPlayer("./src/main/resources/blackbutton.png");
		JPanel desiredLocation = new JPanel();
		desiredLocation.setLayout(null);
		desiredLocation.setBackground(Color.decode("#004d00"));

		assertFalse(c.checkRestricted(desiredLocation));
	}

	/*
	 *  No piece except the king can enter a restricted square.
	 *  Set player as white and set desired location.
	 *  The restrictedSquare method will return false.
	 */
	@Test
	public void testWhiteRestrictedSquare() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		c.setPlayer("./src/main/resources/whitebutton.png");
		JPanel desiredLocation = new JPanel();
		desiredLocation.setLayout(null);
		desiredLocation.setBackground(Color.decode("#004d00"));

		assertFalse(c.checkRestricted(desiredLocation));
	}

	/*
	 *  Tests to make sure that a valid move will return true
	 *  This is testing that moving down in the same column is valid
	 */
	@Test
	public void testValid1() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		Point point = new Point();
		point.x = 250;
		point.y = 104;

		assertTrue(c.isValid(point, 250, 52, v.gameBoard));
	}

	/*
	 *  Tests to make sure that a valid move will return true
	 *  This is testing that moving up in the same column is valid
	 */
	@Test
	public void testValid2() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		Point point = new Point();
		point.x = 250;
		point.y = 416;

		assertTrue(c.isValid(point, 250, 468, v.gameBoard));
	}

	/*
	 *  Tests to make sure that a valid move will return true
	 *  This is testing that moving right in the same column is valid
	 */
	@Test
	public void testValid3() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		Point point = new Point();
		point.x = 100;
		point.y = 260;

		assertTrue(c.isValid(point, 50, 260, v.gameBoard));
	}

	/*
	 *  Tests to make sure that a valid move will return true
	 *  This is testing that moving left in the same column is valid
	 */
	@Test
	public void testValid4() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		Point point = new Point();
		point.x = 400;
		point.y = 260;

		assertTrue(c.isValid(point, 450, 260, v.gameBoard));
	}

	/*
	 *  Tests to make sure that an invalid move will return false
	 *  This is testing that moving in both the X and Y direction is invalid
	 */
	@Test
	public void testValid5() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		Point point = new Point();
		point.x = 150;
		point.y = 52;

		assertFalse(c.isValid(point, 100, 104, v.gameBoard));
    }

	/*
	 *  Tests to make sure that an invalid move will return false
	 *  This is testing that moving down when there is a piece in the way will return false
	 */
	@Test
	public void testValid6() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		Point point = new Point();
		point.x = 250;
		point.y = 416;

		assertFalse(c.isValid(point, 250, 52, v.gameBoard));
	}

	/*
	 *  Tests to make sure that an invalid move will return false
	 *  This is testing that moving up when there is a piece in the way will return false
	 */
	@Test
	public void testValid7() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		Point point = new Point();
		point.x = 250;
		point.y = 104;

		assertFalse(c.isValid(point, 250, 468, v.gameBoard));
	}

	/*
	 *  Tests to make sure that an invalid move will return false
	 *  This is testing that moving right when there is a piece in the way will return false
	 */
	@Test
	public void testValid8() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		Point point = new Point();
		point.x = 350;
		point.y = 260;

		assertFalse(c.isValid(point, 50, 260, v.gameBoard));
	}

	/*
	 *  Tests to make sure that an invalid move will return false
	 *  This is testing that moving left when there is a piece in the way will return false
	 */
	@Test
	public void testValid9() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		Point point = new Point();
		point.x = 100;
		point.y = 260;

		assertFalse(c.isValid(point, 450, 260, v.gameBoard));
	}
	
	/*
	 * Tests that GameTimer.decrement() returns true when
	 * minutesRemaining != 0, secondsRemaining != 0
	 */
	@Test
	public void testDecrementNeither0() {
		JLabel testLabel = new JLabel("5 : 00", JLabel.CENTER);
		testLabel.setBorder(null);
		testLabel.setBackground(Color.decode("#004d00"));
		testLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
		testLabel.setForeground(Color.WHITE);
		testLabel.setOpaque(true);
		
		GameTimer testGameTimer = new GameTimer(testLabel);
		testGameTimer.secondsRemaining = 44;
		
		assertTrue(testGameTimer.decrement());		
	}
	
	/*
	 * Tests that GameTimer.decrement() returns true when
	 * minutesRemaining == 0, secondsRemaining != 0
	 */
	@Test
	public void testDecrementMinutes0() {
		JLabel testLabel = new JLabel("5 : 00", JLabel.CENTER);
		testLabel.setBorder(null);
		testLabel.setBackground(Color.decode("#004d00"));
		testLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
		testLabel.setForeground(Color.WHITE);
		testLabel.setOpaque(true);
		
		GameTimer testGameTimer = new GameTimer(testLabel);
		testGameTimer.minutesRemaining = 0;
		testGameTimer.secondsRemaining = 44;
		
		assertTrue(testGameTimer.decrement());		
	}
	
	/*
	 * Tests that GameTimer.decrement() returns true when
	 * minutesRemaining != 0, secondsRemaining == 0
	 */
	@Test
	public void testDecrementSeconds0() {
		JLabel testLabel = new JLabel("5 : 00", JLabel.CENTER);
		testLabel.setBorder(null);
		testLabel.setBackground(Color.decode("#004d00"));
		testLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
		testLabel.setForeground(Color.WHITE);
		testLabel.setOpaque(true);
		
		GameTimer testGameTimer = new GameTimer(testLabel);
		testGameTimer.minutesRemaining = 4;
		testGameTimer.secondsRemaining = 0;
		
		assertTrue(testGameTimer.decrement());		
	}
	
	/*
	 * Tests that GameTimer.decrement() returns false when
	 * minutesRemaining == 0, secondsRemaining == 0
	 */
	@Test
	public void testDecrementBoth0() {
		JLabel testLabel = new JLabel("5 : 00", JLabel.CENTER);
		testLabel.setBorder(null);
		testLabel.setBackground(Color.decode("#004d00"));
		testLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
		testLabel.setForeground(Color.WHITE);
		testLabel.setOpaque(true);
		
		GameTimer testGameTimer = new GameTimer(testLabel);
		testGameTimer.minutesRemaining = 0;
		testGameTimer.secondsRemaining = 0;
		
		assertFalse(testGameTimer.decrement());		
	}
	
	/*
	 * Tests that when GameTimer.decrement() returns true
	 * the Timer label reflects the new time
	 */
	@Test
	public void testDecrementGameNotOver() {
		GameBoardView v = new GameBoardView();
		v.newGame();
		
		v.whiteTimer.setText("3:33");
		v.whiteGameTimer.label = v.whiteTimer;
		v.whiteGameTimer.minutesRemaining = 3;
		v.whiteGameTimer.secondsRemaining = 33;
		v.whiteGameTimer.decrement();
		
		String currentTime = v.whiteGameTimer.label.getText();
		assertEquals(currentTime, "3 : 32");		
	}
	
	/*
	 * Tests that when GameTimer.decrement() returns false
	 * the Timer label changes to "Time's up!"
	 */
	@Test
	public void testDecrementGameOver() {
		GameBoardView v = new GameBoardView();
		v.newGame();
		
		v.whiteTimer.setText("0:00");
		v.whiteGameTimer.label = v.whiteTimer;
		v.whiteGameTimer.minutesRemaining = 0;
		v.whiteGameTimer.secondsRemaining = 0;
		v.whiteGameTimer.decrement();
		
		String currentTime = v.whiteGameTimer.label.getText();
		assertEquals(currentTime, "Time's up!");				
	}
	
	/*
	 * Tests that when the game first begins
	 * the white timer is 5 : 00 (cannot test black timer precisely, since it begins decrementing immediately)
	 */
	@Test
	public void testGameBeginWhiteTimer() {
		GameBoardView v = new GameBoardView();
		v.newGame();
	
		String currentTime = v.whiteGameTimer.label.getText();
		assertEquals(currentTime, "5:00");				
	}
	
	/*
	 * Tests that when adjustTime() is called when seconds != 0
	 * the secondsRemaining variable is simply decremented
	 */
	@Test
	public void testAdjustTimeNoOverflow() {
		JLabel testLabel = new JLabel("5 : 27", JLabel.CENTER);
		testLabel.setBorder(null);
		testLabel.setBackground(Color.decode("#004d00"));
		testLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
		testLabel.setForeground(Color.WHITE);
		testLabel.setOpaque(true);
		
		GameTimer testGameTimer = new GameTimer(testLabel);
		testGameTimer.minutesRemaining = 5;
		testGameTimer.secondsRemaining = 27;
		
		ArrayList<Integer> expectedResults = new ArrayList<Integer>();
		expectedResults.add(5);
		expectedResults.add(26);
		
		assertArrayEquals(expectedResults.toArray(), testGameTimer.adjustTime(testGameTimer.minutesRemaining, testGameTimer.secondsRemaining).toArray());	
	}
	
	/*
	 * Tests that when adjustTime() is called when seconds == 0
	 * the minutesRemaining variable is decremented and secondsRemaining == 59
	 */
	@Test
	public void testAdjustTimeWithOverflow() {
		JLabel testLabel = new JLabel("5 : 00", JLabel.CENTER);
		testLabel.setBorder(null);
		testLabel.setBackground(Color.decode("#004d00"));
		testLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
		testLabel.setForeground(Color.WHITE);
		testLabel.setOpaque(true);
		
		GameTimer testGameTimer = new GameTimer(testLabel);
		testGameTimer.minutesRemaining = 5;
		testGameTimer.secondsRemaining = 0;
		
		ArrayList<Integer> expectedResults = new ArrayList<Integer>();
		expectedResults.add(4);
		expectedResults.add(59);
		
		assertArrayEquals(expectedResults.toArray(), testGameTimer.adjustTime(testGameTimer.minutesRemaining, testGameTimer.secondsRemaining).toArray());	
	}

	/*
	 *  Tests to make sure a piece can be captured with rule 5a
	 *  This is testing that black pieces can capture white pieces
	 *  and is also testing that it will capture when the moving piece
	 *  is moved to the left of an intended captured piece
	 */
	@Test
	public void testCapture1() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		ImageIcon icon = new ImageIcon("./src/main/resources/blackbutton.png");
		JLabel piece1 = new JLabel(icon);

		ImageIcon icon2 = new ImageIcon("./src/main/resources/whitebutton.png");
		JLabel piece2 = new JLabel(icon2);

		ImageIcon icon3 = new ImageIcon("./src/main/resources/blackbutton.png");
		JLabel piece3 = new JLabel(icon3);

		JPanel leftPiece = (JPanel)v.gameBoard.getComponent(26);
		JPanel middlePiece = (JPanel)v.gameBoard.getComponent(27);
		JPanel rightPiece = (JPanel)v.gameBoard.getComponent(28);

		leftPiece.setLocation(200, 104);

		leftPiece.add(piece1);
		middlePiece.add(piece2);
		rightPiece.add(piece3);

		c.setPlayer("./src/main/resources/blackbutton.png");

		assertEquals(piece2, c.checkCapture(leftPiece, v.gameBoard));
	}

	/*
	 *  Tests to make sure a piece can be captured with rule 5a
	 *  This is testing that white pieces can capture black pieces
	 *  and is also testing that it will capture when the moving piece
	 *  is moved to the right of an intended captured piece
	 */
	@Test
	public void testCapture2() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		ImageIcon icon = new ImageIcon("./src/main/resources/whitebutton.png");
		JLabel piece1 = new JLabel(icon);

		ImageIcon icon2 = new ImageIcon("./src/main/resources/blackbutton.png");
		JLabel piece2 = new JLabel(icon2);

		ImageIcon icon3 = new ImageIcon("./src/main/resources/whitebutton.png");
		JLabel piece3 = new JLabel(icon3);

		JPanel leftPiece = (JPanel)v.gameBoard.getComponent(26);
		JPanel middlePiece = (JPanel)v.gameBoard.getComponent(27);
		JPanel rightPiece = (JPanel)v.gameBoard.getComponent(28);

		rightPiece.setLocation(300, 104);

		leftPiece.add(piece1);
		middlePiece.add(piece2);
		rightPiece.add(piece3);

		c.setPlayer("./src/main/resources/whitebutton.png");

		assertEquals(piece2, c.checkCapture(rightPiece, v.gameBoard));
	}

	/*
	 *  Tests to make sure a piece can be captured with rule 5a
	 *  This is testing that a king can assist in capturing black pieces
	 *  and is also testing that it will capture when the moving piece
	 *  is moved to the above space of an intended captured piece
	 */
	@Test
	public void testCapture3() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		ImageIcon icon = new ImageIcon("./src/main/resources/king.png");
		JLabel piece1 = new JLabel(icon);

		ImageIcon icon2 = new ImageIcon("./src/main/resources/blackbutton.png");
		JLabel piece2 = new JLabel(icon2);

		ImageIcon icon3 = new ImageIcon("./src/main/resources/whitebutton.png");
		JLabel piece3 = new JLabel(icon3);

		JPanel topPiece = (JPanel)v.gameBoard.getComponent(13);
		JPanel middlePiece = (JPanel)v.gameBoard.getComponent(24);
		JPanel bottomPiece = (JPanel)v.gameBoard.getComponent(35);

		topPiece.setLocation(100, 52);

		topPiece.add(piece1);
		middlePiece.add(piece2);
		bottomPiece.add(piece3);

		c.setPlayer("./src/main/resources/king.png");

		assertEquals(piece2, c.checkCapture(topPiece, v.gameBoard));
	}

	/*
	 *  Tests to make sure a piece can be captured with rule 5a
	 *  This is testing to see that a piece will capture another when the moving piece
	 *  is moved to the below space of an intended captured piece
	 */
	@Test
	public void testCapture4() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		ImageIcon icon = new ImageIcon("./src/main/resources/whitebutton.png");
		JLabel piece1 = new JLabel(icon);

		ImageIcon icon2 = new ImageIcon("./src/main/resources/blackbutton.png");
		JLabel piece2 = new JLabel(icon2);

		ImageIcon icon3 = new ImageIcon("./src/main/resources/whitebutton.png");
		JLabel piece3 = new JLabel(icon3);

		JPanel topPiece = (JPanel)v.gameBoard.getComponent(13);
		JPanel middlePiece = (JPanel)v.gameBoard.getComponent(24);
		JPanel bottomPiece = (JPanel)v.gameBoard.getComponent(35);

		bottomPiece.setLocation(100, 156);

		topPiece.add(piece1);
		middlePiece.add(piece2);
		bottomPiece.add(piece3);

		c.setPlayer("./src/main/resources/whitebutton.png");

		assertEquals(piece2, c.checkCapture(bottomPiece, v.gameBoard));
	}

	/*
	 *  Tests to make sure a piece can be captured with rule 5a
	 *  This is testing to see that a piece can be captured using
	 *  a restricted square from below
	 */
	@Test
	public void testCapture5() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		ImageIcon icon2 = new ImageIcon("./src/main/resources/blackbutton.png");
		JLabel piece2 = new JLabel(icon2);

		ImageIcon icon3 = new ImageIcon("./src/main/resources/whitebutton.png");
		JLabel piece3 = new JLabel(icon3);

		JPanel topPiece = (JPanel)v.gameBoard.getComponent(0);
		JPanel middlePiece = (JPanel)v.gameBoard.getComponent(11);
		JPanel bottomPiece = (JPanel)v.gameBoard.getComponent(22);

		bottomPiece.setLocation(0, 104);

		middlePiece.add(piece2);
		bottomPiece.add(piece3);

		c.setPlayer("./src/main/resources/whitebutton.png");

		assertEquals(piece2, c.checkCapture(bottomPiece, v.gameBoard));
	}

	/*
	 *  Tests to make sure a piece can be captured with rule 5a
	 *  This is testing to see that a white piece can't be captured
	 *  using the middle restricted square if the king is on the throne
	 */
	@Test
	public void testCapture6() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		ImageIcon icon = new ImageIcon("./src/main/resources/king.png");
		JLabel piece1 = new JLabel(icon);

		ImageIcon icon2 = new ImageIcon("./src/main/resources/whitebutton.png");
		JLabel piece2 = new JLabel(icon2);

		ImageIcon icon3 = new ImageIcon("./src/main/resources/blackbutton.png");
		JLabel piece3 = new JLabel(icon3);

		JPanel topPiece = (JPanel)v.gameBoard.getComponent(13);
		JPanel middlePiece = (JPanel)v.gameBoard.getComponent(24);
		JPanel bottomPiece = (JPanel)v.gameBoard.getComponent(35);

		bottomPiece.setLocation(250, 364);

		topPiece.add(piece1);
		middlePiece.add(piece2);
		bottomPiece.add(piece3);

		c.setPlayer("./src/main/resources/blackbutton.png");

		assertEquals(null, c.checkCapture(bottomPiece, v.gameBoard));
	}

	/*
	 *  Tests to make sure a piece can be captured with rule 5b
	 *  This is testing to see that black pieces on the left border
	 *  will be captured if surrounded by white pieces
	 */
	@Test
	public void testShieldwall1() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		//Deletes JLabel in this square to change it later
		Component [] board = v.gameBoard.getComponents();
		Component piece = (JPanel) board[56];
		if (piece != null) {
			Component [] placedPieces = ((Container)piece).getComponents();
			if (placedPieces.length > 0) {
				Component placedPiece = placedPieces[0];
				if (placedPiece instanceof JLabel) {
					JLabel clear = (JLabel)placedPiece;
					Container clearContainer = clear.getParent();
					clearContainer.remove(clear);
					clearContainer.validate();
					clearContainer.repaint();
				}
			}
		}

		//Makes a list of JLabels that should be deleted
		ArrayList<JLabel> captured = new ArrayList();
		for (int i = 0; i < board.length; i++) {
			Component capturedComp = (JPanel) board[i];
			if (capturedComp != null && (i == 33 || i == 44 || i == 55 || i == 66 || i == 77)) {
				Component [] placedPieces = ((Container)capturedComp).getComponents();
				if (placedPieces.length > 0) {
					Component placedPiece = placedPieces[0];
					if (placedPiece instanceof JLabel) {
						JLabel singlePiece = (JLabel)placedPiece;
						captured.add(singlePiece);
					}
				}
			}
		}

		ImageIcon icon1 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon icon2 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon icon3 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon icon4 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon icon5 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon icon6 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon icon7 = new ImageIcon("./src/main/resources/whitebutton.png");

		JLabel piece1 = new JLabel(icon1);
		JLabel piece2 = new JLabel(icon2);
		JLabel piece3 = new JLabel(icon3);
		JLabel piece4 = new JLabel(icon4);
		JLabel piece5 = new JLabel(icon5);
		JLabel piece6 = new JLabel(icon6);
		JLabel piece7 = new JLabel(icon7);

		JPanel topPiece = (JPanel)v.gameBoard.getComponent(22);
		JPanel middle1 = (JPanel)v.gameBoard.getComponent(34);
		JPanel middle2 = (JPanel)v.gameBoard.getComponent(45);
		JPanel middle3 = (JPanel)v.gameBoard.getComponent(56);
		JPanel middle4 = (JPanel)v.gameBoard.getComponent(67);
		JPanel middle5 = (JPanel)v.gameBoard.getComponent(78);
		JPanel bottomPiece = (JPanel)v.gameBoard.getComponent(88);

		topPiece.setLocation(0, 104);

		topPiece.add(piece1);
		bottomPiece.add(piece2);
		middle1.add(piece3);
		middle2.add(piece4);
		middle3.add(piece5);
		middle4.add(piece6);
		middle5.add(piece7);

		c.setPlayer("./src/main/resources/whitebutton.png");

		assertEquals(captured, c.checkShieldwall(topPiece, v.gameBoard));
	}

	/*
	 *  Tests to make sure a piece can be captured with rule 5b
	 *  This is testing to see that white pieces on the top border
	 *  will be captured if surrounded by black pieces
	 *  Also this is testing that if the king is also on the edge
	 *  with other to be captured defenders it will not be captured
	 *  while the other pieces will be
	 */
	@Test
	public void testShieldwall2() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		//Deletes JLabel in these squares to change it later
		Component [] board = v.gameBoard.getComponents();
		for (int i = 0; i < board.length; i++) {
			Component capturedComp = (JPanel) board[i];
			if (capturedComp != null && (i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 16)) {
				Component [] placedPieces = ((Container)capturedComp).getComponents();
				if (placedPieces.length > 0) {
					Component placedPiece = placedPieces[0];
					if (placedPiece instanceof JLabel) {
					JLabel clear = (JLabel)placedPiece;
					Container clearContainer = clear.getParent();
					clearContainer.remove(clear);
					clearContainer.validate();
					clearContainer.repaint();
				}
				}
			}
		}

		//The attackers that are going to surround the defenders
		ImageIcon icon1 = new ImageIcon("./src/main/resources/blackbutton.png");
		ImageIcon icon2 = new ImageIcon("./src/main/resources/blackbutton.png");
		ImageIcon icon3 = new ImageIcon("./src/main/resources/blackbutton.png");
		ImageIcon icon4 = new ImageIcon("./src/main/resources/blackbutton.png");
		ImageIcon icon5 = new ImageIcon("./src/main/resources/blackbutton.png");
		ImageIcon icon6 = new ImageIcon("./src/main/resources/blackbutton.png");
		ImageIcon icon7 = new ImageIcon("./src/main/resources/blackbutton.png");

		//The defenders that are going to be surrounded by the attackers
		ImageIcon white1 = new ImageIcon("./src/main/resources/king.png");
		ImageIcon white2 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon white3 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon white4 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon white5 = new ImageIcon("./src/main/resources/whitebutton.png");

		JLabel piece1 = new JLabel(icon1);
		JLabel piece2 = new JLabel(icon2);
		JLabel piece3 = new JLabel(icon3);
		JLabel piece4 = new JLabel(icon4);
		JLabel piece5 = new JLabel(icon5);
		JLabel piece6 = new JLabel(icon6);
		JLabel piece7 = new JLabel(icon7);

		JLabel defender1 = new JLabel(white1);
		JLabel defender2 = new JLabel(white2);
		JLabel defender3 = new JLabel(white3);
		JLabel defender4 = new JLabel(white4);
		JLabel defender5 = new JLabel(white5);

		JPanel leftPiece = (JPanel)v.gameBoard.getComponent(2);
		JPanel middle1 = (JPanel)v.gameBoard.getComponent(14);
		JPanel middle2 = (JPanel)v.gameBoard.getComponent(15);
		JPanel middle3 = (JPanel)v.gameBoard.getComponent(16);
		JPanel middle4 = (JPanel)v.gameBoard.getComponent(17);
		JPanel middle5 = (JPanel)v.gameBoard.getComponent(18);
		JPanel rightPiece = (JPanel)v.gameBoard.getComponent(8);

		JPanel replace1 = (JPanel)v.gameBoard.getComponent(3);
		JPanel replace2 = (JPanel)v.gameBoard.getComponent(4);
		JPanel replace3 = (JPanel)v.gameBoard.getComponent(5);
		JPanel replace4 = (JPanel)v.gameBoard.getComponent(6);
		JPanel replace5 = (JPanel)v.gameBoard.getComponent(7);

		leftPiece.setLocation(100, 0);

		leftPiece.add(piece1);
		rightPiece.add(piece2);
		middle1.add(piece3);
		middle2.add(piece4);
		middle3.add(piece5);
		middle4.add(piece6);
		middle5.add(piece7);

		replace1.add(defender1);
		replace2.add(defender2);
		replace3.add(defender3);
		replace4.add(defender4);
		replace5.add(defender5);

		//Makes a list of JLabels that should be deleted
		Component [] board1 = v.gameBoard.getComponents();
		ArrayList<JLabel> captured = new ArrayList();
		for (int i = 0; i < board1.length; i++) {
			Component capturedComp = (JPanel) board1[i];
			if (capturedComp != null && (i == 4 || i == 5 || i == 6 || i == 7)) {
				Component [] placedPieces = ((Container)capturedComp).getComponents();
				if (placedPieces.length > 0) {
					Component placedPiece = placedPieces[0];
					if (placedPiece instanceof JLabel) {
						JLabel singlePiece = (JLabel)placedPiece;
						captured.add(singlePiece);
					}
				}
			}
		}

		c.setPlayer("./src/main/resources/blackbutton.png");

		assertEquals(captured, c.checkShieldwall(leftPiece, v.gameBoard));
	}

	/*
	 *  Tests to make sure a piece can be captured with rule 5b
	 *  This is testing to see that black pieces on the right border
	 *  will be captured if surrounded by white pieces including a king
	 *  and the king is the piece that is being moved
	 */
	@Test
	public void testShieldwall3() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		//Deletes JLabel in this square to change it later
		Component [] board = v.gameBoard.getComponents();
		Component piece = (JPanel) board[64];
		if (piece != null) {
			Component [] placedPieces = ((Container)piece).getComponents();
			if (placedPieces.length > 0) {
				Component placedPiece = placedPieces[0];
				if (placedPiece instanceof JLabel) {
					JLabel clear = (JLabel)placedPiece;
					Container clearContainer = clear.getParent();
					clearContainer.remove(clear);
					clearContainer.validate();
					clearContainer.repaint();
				}
			}
		}

		//Makes a list of JLabels that should be deleted
		ArrayList<JLabel> captured = new ArrayList();
		for (int i = 0; i < board.length; i++) {
			Component capturedComp = (JPanel) board[i];
			if (capturedComp != null && (i == 43 || i == 54 || i == 65 || i == 76 || i == 87)) {
				Component [] placedPieces = ((Container)capturedComp).getComponents();
				if (placedPieces.length > 0) {
					Component placedPiece = placedPieces[0];
					if (placedPiece instanceof JLabel) {
						JLabel singlePiece = (JLabel)placedPiece;
						captured.add(singlePiece);
					}
				}
			}
		}

		ImageIcon icon1 = new ImageIcon("./src/main/resources/king.png");
		ImageIcon icon2 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon icon3 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon icon4 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon icon5 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon icon6 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon icon7 = new ImageIcon("./src/main/resources/whitebutton.png");

		JLabel piece1 = new JLabel(icon1);
		JLabel piece2 = new JLabel(icon2);
		JLabel piece3 = new JLabel(icon3);
		JLabel piece4 = new JLabel(icon4);
		JLabel piece5 = new JLabel(icon5);
		JLabel piece6 = new JLabel(icon6);
		JLabel piece7 = new JLabel(icon7);

		JPanel topPiece = (JPanel)v.gameBoard.getComponent(32);
		JPanel middle1 = (JPanel)v.gameBoard.getComponent(42);
		JPanel middle2 = (JPanel)v.gameBoard.getComponent(53);
		JPanel middle3 = (JPanel)v.gameBoard.getComponent(64);
		JPanel middle4 = (JPanel)v.gameBoard.getComponent(75);
		JPanel middle5 = (JPanel)v.gameBoard.getComponent(86);
		JPanel bottomPiece = (JPanel)v.gameBoard.getComponent(98);

		topPiece.setLocation(500, 104);

		topPiece.add(piece1);
		bottomPiece.add(piece2);
		middle1.add(piece3);
		middle2.add(piece4);
		middle3.add(piece5);
		middle4.add(piece6);
		middle5.add(piece7);

		c.setPlayer("./src/main/resources/king.png");

		assertEquals(captured, c.checkShieldwall(topPiece, v.gameBoard));
	}

	/*
	 *  Tests to make sure a piece can be captured with rule 5b
	 *  This is testing to see that black pieces on the bottom border
	 *  will be captured if surrounded by white pieces including a king
	 *  and a white piece that's not the king is being moved
	 */
	@Test
	public void testShieldwall4() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		//Deletes JLabel in this square to change it later
		Component [] board = v.gameBoard.getComponents();
		Component piece = (JPanel) board[104];
		if (piece != null) {
			Component [] placedPieces = ((Container)piece).getComponents();
			if (placedPieces.length > 0) {
				Component placedPiece = placedPieces[0];
				if (placedPiece instanceof JLabel) {
					JLabel clear = (JLabel)placedPiece;
					Container clearContainer = clear.getParent();
					clearContainer.remove(clear);
					clearContainer.validate();
					clearContainer.repaint();
				}
			}
		}

		//Makes a list of JLabels that should be deleted
		ArrayList<JLabel> captured = new ArrayList();
		for (int i = 0; i < board.length; i++) {
			Component capturedComp = (JPanel) board[i];
			if (capturedComp != null && (i == 113 || i == 114 || i == 115 || i == 116 || i == 117)) {
				Component [] placedPieces = ((Container)capturedComp).getComponents();
				if (placedPieces.length > 0) {
					Component placedPiece = placedPieces[0];
					if (placedPiece instanceof JLabel) {
						JLabel singlePiece = (JLabel)placedPiece;
						captured.add(singlePiece);
					}
				}
			}
		}

		ImageIcon icon1 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon icon2 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon icon3 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon icon4 = new ImageIcon("./src/main/resources/king.png");
		ImageIcon icon5 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon icon6 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon icon7 = new ImageIcon("./src/main/resources/whitebutton.png");

		JLabel piece1 = new JLabel(icon1);
		JLabel piece2 = new JLabel(icon2);
		JLabel piece3 = new JLabel(icon3);
		JLabel piece4 = new JLabel(icon4);
		JLabel piece5 = new JLabel(icon5);
		JLabel piece6 = new JLabel(icon6);
		JLabel piece7 = new JLabel(icon7);

		JPanel leftPiece = (JPanel)v.gameBoard.getComponent(112);
		JPanel middle1 = (JPanel)v.gameBoard.getComponent(102);
		JPanel middle2 = (JPanel)v.gameBoard.getComponent(103);
		JPanel middle3 = (JPanel)v.gameBoard.getComponent(104);
		JPanel middle4 = (JPanel)v.gameBoard.getComponent(105);
		JPanel middle5 = (JPanel)v.gameBoard.getComponent(106);
		JPanel rightPiece = (JPanel)v.gameBoard.getComponent(118);

		leftPiece.setLocation(100, 520);

		leftPiece.add(piece1);
		rightPiece.add(piece2);
		middle1.add(piece3);
		middle2.add(piece4);
		middle3.add(piece5);
		middle4.add(piece6);
		middle5.add(piece7);

		c.setPlayer("./src/main/resources/whitebutton.png");

		assertEquals(captured, c.checkShieldwall(leftPiece, v.gameBoard));
	}

	/*
	 *  Tests to make sure a piece can be captured with rule 5b
	 *  This is testing to see that black pieces on the left border
	 *  will be captured if surrounded by white pieces and a restricted
	 *  sqaure on the top left square
	 */
	@Test
	public void testShieldwall5() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();
		

		//Deletes JLabel in this square to change it later
		Component [] board = v.gameBoard.getComponents();
		Component piece = (JPanel) board[33];
		if (piece != null) {
			Component [] placedPieces = ((Container)piece).getComponents();
			if (placedPieces.length > 0) {
				Component placedPiece = placedPieces[0];
				if (placedPiece instanceof JLabel) {
					JLabel clear = (JLabel)placedPiece;
					Container clearContainer = clear.getParent();
					clearContainer.remove(clear);
					clearContainer.validate();
					clearContainer.repaint();
				}
			}
		}

		ImageIcon icon1 = new ImageIcon("./src/main/resources/blackbutton.png");
		ImageIcon icon2 = new ImageIcon("./src/main/resources/blackbutton.png");

		ImageIcon icon3 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon icon4 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon icon5 = new ImageIcon("./src/main/resources/whitebutton.png");

		JLabel piece1 = new JLabel(icon1);
		JLabel piece2 = new JLabel(icon2);

		JLabel defender1 = new JLabel(icon3);
		JLabel defender2 = new JLabel(icon4);
		JLabel defender3 = new JLabel(icon5);

		JPanel black1 = (JPanel)v.gameBoard.getComponent(11);
		JPanel black2 = (JPanel)v.gameBoard.getComponent(22);

		JPanel white1 = (JPanel)v.gameBoard.getComponent(12);
		JPanel white2 = (JPanel)v.gameBoard.getComponent(23);
		JPanel white3 = (JPanel)v.gameBoard.getComponent(33);

		white1.setLocation(50, 52);

		black1.add(piece1);
		black2.add(piece2);
		white1.add(defender1);
		white2.add(defender2);
		white3.add(defender3);

		//Makes a list of JLabels that should be deleted
		Component [] board1 = v.gameBoard.getComponents();
		ArrayList<JLabel> captured = new ArrayList();
		for (int i = 0; i < board1.length; i++) {
			Component capturedComp = (JPanel) board1[i];
			if (capturedComp != null && (i == 11 || i == 22)) {
				Component [] placedPieces = ((Container)capturedComp).getComponents();
				if (placedPieces.length > 0) {
					Component placedPiece = placedPieces[0];
					if (placedPiece instanceof JLabel) {
						JLabel singlePiece = (JLabel)placedPiece;
						captured.add(singlePiece);
					}
				}
			}
		}

		c.setPlayer("./src/main/resources/whitebutton.png");

		assertEquals(captured, c.checkShieldwall(white1, v.gameBoard));
	}

	/*
	 *  Tests to make sure a piece can be captured with rule 5b
	 *  This is testing to see that white pieces on the top border
	 *  will be captured if surrounded by black pieces and a restricted
	 *  sqaure on the top right square
	 */
	@Test
	public void testShieldwall6() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();

		//Deletes JLabel in this square to change it later
		Component [] board = v.gameBoard.getComponents();
		Component piece = (JPanel) board[7];
		if (piece != null) {
			Component [] placedPieces = ((Container)piece).getComponents();
			if (placedPieces.length > 0) {
				Component placedPiece = placedPieces[0];
				if (placedPiece instanceof JLabel) {
					JLabel clear = (JLabel)placedPiece;
					Container clearContainer = clear.getParent();
					clearContainer.remove(clear);
					clearContainer.validate();
					clearContainer.repaint();
				}
			}
		}

		ImageIcon icon1 = new ImageIcon("./src/main/resources/whitebutton.png");
		ImageIcon icon2 = new ImageIcon("./src/main/resources/whitebutton.png");

		ImageIcon icon3 = new ImageIcon("./src/main/resources/blackbutton.png");
		ImageIcon icon4 = new ImageIcon("./src/main/resources/blackbutton.png");
		ImageIcon icon5 = new ImageIcon("./src/main/resources/blackbutton.png");

		JLabel piece1 = new JLabel(icon1);
		JLabel piece2 = new JLabel(icon2);

		JLabel defender1 = new JLabel(icon3);
		JLabel defender2 = new JLabel(icon4);
		JLabel defender3 = new JLabel(icon5);

		JPanel black1 = (JPanel)v.gameBoard.getComponent(8);
		JPanel black2 = (JPanel)v.gameBoard.getComponent(9);

		JPanel white1 = (JPanel)v.gameBoard.getComponent(7);
		JPanel white2 = (JPanel)v.gameBoard.getComponent(19);
		JPanel white3 = (JPanel)v.gameBoard.getComponent(20);

		white1.setLocation(350, 0);

		black1.add(piece1);
		black2.add(piece2);
		white1.add(defender1);
		white2.add(defender2);
		white3.add(defender3);

		//Makes a list of JLabels that should be deleted
		Component [] board1 = v.gameBoard.getComponents();
		ArrayList<JLabel> captured = new ArrayList();
		for (int i = 0; i < board1.length; i++) {
			Component capturedComp = (JPanel) board1[i];
			if (capturedComp != null && (i == 8 || i == 9)) {
				Component [] placedPieces = ((Container)capturedComp).getComponents();
				if (placedPieces.length > 0) {
					Component placedPiece = placedPieces[0];
					if (placedPiece instanceof JLabel) {
						JLabel singlePiece = (JLabel)placedPiece;
						captured.add(singlePiece);
					}
				}
			}
		}

		c.setPlayer("./src/main/resources/blackbutton.png");

		assertEquals(captured, c.checkShieldwall(white1, v.gameBoard));
	}

	
	/*
	 *  Tests that this method returns false when defenders aren't completely surrounded.
	 *  This test creates a game board that does not have defenders completely surrounded.
	 */
	@Test
	public void testDefendersSurrounded0() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();
		String[][] pieces = new String[11][11];
		//add defenders to positions - 13 defenders including king
		pieces[3][4] = "defender";
		pieces[4][4] = "defender";
		pieces[1][5] = "defender";
		pieces[4][5] = "defender";
		pieces[5][4] = "defender";
		pieces[5][5] = "defender"; 
		pieces[5][6] = "defender";
		pieces[5][7] = "defender";
		pieces[6][3] = "defender";
		pieces[6][4] = "defender";
		pieces[6][5] = "defender";
		pieces[6][6] = "defender";
		pieces[7][4] = "defender";
		//add attackers to positions - 24 attackers
		pieces[2][2] = "attacker";
		pieces[2][3] = "attacker";
		pieces[2][4] = "attacker";
		pieces[2][5] = "attacker";
		pieces[2][6] = "attacker";
		pieces[2][7] = "attacker";
		pieces[2][8] = "attacker";
		pieces[3][2] = "attacker";
		pieces[3][8] = "attacker";
		pieces[4][2] = "attacker";
		pieces[4][8] = "attacker";
		pieces[6][2] = "attacker";
		pieces[6][8] = "attacker";
		pieces[7][2] = "attacker";
		pieces[7][8] = "attacker";
		pieces[7][3] = "attacker";
		pieces[7][4] = "attacker";
		pieces[7][5] = "attacker";
		pieces[7][6] = "attacker";
		pieces[7][7] = "attacker";
		pieces[7][8] = "attacker";
		pieces[9][1] = "attacker";
		c.pieces = pieces;
		boolean surrounded = c.defendersSurrounded(v.gameBoard); //returns true if completely surrounded
		assertFalse(surrounded);
	}
	
	/*
	 *  Tests that this method returns false when defenders aren't in starting position and aren't completely surrounded.
	 *  This test creates a game board that does not have defenders completely surrounded.
	 */
	@Test
	public void testDefendersSurrounded1() {
		Controller c = new Controller();
		GameBoardView v = new GameBoardView();
		v.newGame();
		String[][] pieces = new String[11][11];
		//add defenders to positions - 13 defenders including king
		pieces[3][4] = "defender";
		pieces[4][4] = "defender";
		pieces[1][5] = "defender";
		pieces[4][5] = "defender";
		pieces[5][4] = "defender";
		pieces[5][5] = "defender"; 
		pieces[5][6] = "defender";
		pieces[5][7] = "defender";
		pieces[2][3] = "defender";
		pieces[6][4] = "defender";
		pieces[3][5] = "defender";
		pieces[6][6] = "defender";
		pieces[7][1] = "defender";
		//add attackers to positions - 24 attackers
		pieces[2][2] = "attacker";
		pieces[2][3] = "attacker";
		pieces[2][4] = "attacker";
		pieces[2][5] = "attacker";
		pieces[2][6] = "attacker";
		pieces[2][7] = "attacker";
		pieces[2][8] = "attacker";
		pieces[3][2] = "attacker";
		pieces[3][8] = "attacker";
		pieces[4][2] = "attacker";
		pieces[4][8] = "attacker";
		pieces[6][2] = "attacker";
		pieces[6][8] = "attacker";
		pieces[7][2] = "attacker";
		pieces[7][8] = "attacker";
		pieces[7][3] = "attacker";
		pieces[7][4] = "attacker";
		pieces[7][5] = "attacker";
		pieces[7][6] = "attacker";
		pieces[7][7] = "attacker";
		pieces[7][8] = "attacker";
		pieces[9][1] = "attacker";
		c.pieces = pieces;
		boolean surrounded = c.defendersSurrounded(v.gameBoard); //returns true if completely surrounded
		assertFalse(surrounded);
	}
} //end class

