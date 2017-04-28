import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;
//Kerilee Bookleiner

public class Boggle{
	public static void main(String args[]){
		int totalScore = 0;
		int a = 0;
		BoggleModel gameModel = new BoggleModel();

		//print out the board
		System.out.println("$ Java Boggle");
		String gameBoard = gameModel.toString();
		System.out.println(gameBoard + "\n");
		
		//store all words in dictionary in the arraylist
		ArrayList<String> words = new ArrayList<String>();
		try {
		Scanner readWords = new Scanner(new File("dict.txt"));
		while (readWords.hasNext()){
			words.add(readWords.next());
		}
		readWords.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		//read in values from high score list and store in arrays
		int[] highScores = new int[10];
		String[] playerInitials = new String[10];
		try {
		Scanner readScores = new Scanner(new File("scores.txt"));
		while(readScores.hasNext()){
			String s = readScores.next();
			playerInitials[a] = s;
			int x = readScores.nextInt();
			highScores[a] = x;
			a++;
		}
		readScores.close();
		}
		catch(FileNotFoundException f){
			f.printStackTrace();
		}
		
		//make an array of the words in arraylist of dictionary words
		String[] arrayOfWords = words.toArray(new String[words.size()]);
		//create arraylist of words already guessed
		ArrayList<String> wordsGuessed = new ArrayList<String>();
		
		boolean done = false;
		while(!done){
			int wordScore = 0;
			//get the input from player
			Scanner input = new Scanner(System.in);
			String playerWord = input.next();
			String newPlayerWord = playerWord.toUpperCase();
			
			//game ends when player enters 'q'
			if(playerWord.equals("q") || playerWord.equals("Q")){
				int index = guessIndex(totalScore, highScores);
				if(index != -1){
					System.out.print("\n" + totalScore + " points is the new high score! Enter your initials: ");
					String initials = input.next();
					playerInitials[index] = initials;
					highScores[index] = totalScore;
					//if there is a high score, change the file
					try {
						File newHighScores = new File("scores.txt");
						PrintWriter newFile = new PrintWriter(newHighScores);
						for(int k = 0;k <10;k++){
						newFile.print(playerInitials[k]); 
						newFile.print(" "); 
						newFile.print(highScores[k]); 
						newFile.println();
					}
						newFile.close();
					} 
					catch (IOException e) {
						e.printStackTrace();
					}
				}
				else{
					System.out.println("Your score of " + totalScore + " is not in the top 10");
				}
				done = true;
				break;
			}
			
			boolean checkLength = checkLetters(newPlayerWord);
			boolean checkConnection = gameModel.findConnections(newPlayerWord);
			boolean checkDict = wordInDict(arrayOfWords, newPlayerWord);
			boolean checkUsed = usedGuess(wordsGuessed, newPlayerWord);
			
			//add the word to arraylist of guessed words
			wordsGuessed.add(newPlayerWord);
			
			
			//only score it if word is valid
			if(checkLength == true && checkDict == true && checkUsed == false && checkConnection == true){
				//calculate individual word score and add it to total score
				for(int i = 0; i < newPlayerWord.length(); i++){
					if(newPlayerWord.charAt(i) == 'A' || newPlayerWord.charAt(i) == 'E' 
						|| newPlayerWord.charAt(i) == 'L' || newPlayerWord.charAt(i) == 'I' 
						|| newPlayerWord.charAt(i) == 'N' || newPlayerWord.charAt(i) == 'O' 
						|| newPlayerWord.charAt(i) == 'R' || newPlayerWord.charAt(i) == 'S' 
						|| newPlayerWord.charAt(i) == 'T' || newPlayerWord.charAt(i) == 'U'){
						totalScore += 1;
						wordScore += 1;
					}
					if(newPlayerWord.charAt(i) == 'D' || newPlayerWord.charAt(i) == 'G'){
						totalScore+= 2;
						wordScore += 2;
					}
					if(newPlayerWord.charAt(i) == 'B' || newPlayerWord.charAt(i) == 'C' 
						|| newPlayerWord.charAt(i) == 'M' || newPlayerWord.charAt(i) == 'P'){
						totalScore += 3;
						wordScore += 3;
					}
					if(newPlayerWord.charAt(i) == 'F' || newPlayerWord.charAt(i) == 'H' || newPlayerWord.charAt(i) == 'V' 
						|| newPlayerWord.charAt(i) == 'W' || newPlayerWord.charAt(i) == 'Y'){
						totalScore+= 4;
						wordScore += 4;
					}
					if(newPlayerWord.charAt(i) == 'K'){
						totalScore+= 5;
						wordScore += 5;
					}
					if(newPlayerWord.charAt(i) == 'J' || newPlayerWord.charAt(i) == 'X'){
						totalScore+= 8;
						wordScore += 8;
					}
					if(newPlayerWord.charAt(i) == 'Q' || newPlayerWord.charAt(i) == 'Z'){
						totalScore+= 10;
						wordScore += 10;
					}
				}
				//print out the score
				System.out.println(newPlayerWord + " Scored " + wordScore + " points. " + "Your total score is " + totalScore);
			}
			
		//do not score invalid word
		else{
			System.out.println("\nINVALID word. Try again.");
		}
	}
}
	//is it more than 3 letters?
	public static boolean checkLetters(String guess){
			if(guess.length() < 3){
				return false;
			}
			else{
				return true;
			}
		}
		
		//is the word in the dictionary?
	public static boolean wordInDict(String[] dict, String word){
		int index = Arrays.binarySearch(dict, word, String.CASE_INSENSITIVE_ORDER);
		if(index >= 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	//has the player guessed the word before?
	public static boolean usedGuess(ArrayList<String> guesses, String word){
		for (int i = 0; i < guesses.size(); i++) {
			if(guesses.get(i).equals(word)){
				return true;
			}
		}
		return false;
		}
		
	//index for high score
	public static int guessIndex(int totalScore, int[]highScores){
		for(int b = 0;b < 10;b++){
			if(totalScore > highScores[b]){
				return b;
				}
			}
			return -1;
		}
}