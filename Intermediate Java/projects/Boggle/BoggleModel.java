import java.util.ArrayList;
import java.util.Arrays;
public class BoggleModel{
	private char[][] board;
	
	//constructor creates board
	public BoggleModel() {
		this.board = new char[4][4];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				board[i][j] = (char)(Math.random() * 26 + 'A');
			}
		}
	}
	
	//calls a recursive search(backtracking)
	public boolean findConnections(String word) {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				//returns a boolean that will be checked here
				if (findConnections(r, c, word) == true) {
					//if we found the word path
					return true;
				}
			}
		}
		//if not found
		return false;
	}
	
	/*recursive search through the board
	*change the position of x and y relative to each
	*char to make sure there is a path. 
	*/
	private boolean findConnections(int r, int c, String word) {
	//base case
		//when hits the end
		if (word.equals("")) {
			return true;
		}
		//if coordinates fall off board
		else if (r < 0 || r >= board.length || 
				 c < 0 || c >= board.length || 
				 board[r][c] != word.charAt(0)) {
			return false; 
		}
		//don't use letter more than once so replace with temp letter
			char temp = board[r][c];
			board[r][c] = '!';
			//gives the word without the first letter
			String check = word.substring(1, word.length());
			//store whether true or false in connection
			boolean connection = findConnections(r, c-1, check) ||
								 findConnections(r, c+1, check) ||
								 findConnections(r-1, c+1, check) ||
								 findConnections(r-1, c-1, check) ||
								 findConnections(r-1, c, check) ||
								 findConnections(r+1, c-1, check) ||
								 findConnections(r+1, c, check) ||
								 findConnections(r+1, c+1, check);
			//add letters back to board for use with next word guessed
			board[r][c] = temp;
		return connection;
	}
	
	//prints the board
	public String toString() {
		//make new board by creating new string and adding to it
		String guess = "";
		for (int i = 0; i < board.length; i++) {
			for (char letter: board[i]) {
				guess += letter + " ";
			}
			if (i < board.length-1) {
				//making new rows
				guess += "\n"; 
			}
		}

		return guess;
	}
}