
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Mastermind extends JFrame implements ActionListener{
	
	MastermindInputPanel inputPanel;
	MastermindInputGuessPanel guessPanel;
	// MastermindGuessPanel guessPanel2;
	
	
	public static void main(String[] args) {
	Mastermind app = new Mastermind();
	// int numberOfGuesses = 0;

	}
	public Mastermind() {
		super("Mastermind");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(200,300);
		inputPanel = new MastermindInputPanel();

		guessPanel = new MastermindInputGuessPanel();
		// guessPanel2 = new MastermindGuessPanel();
		
		inputPanel.button.addActionListener(this);
		
		setLayout(new BorderLayout());
		add(inputPanel, BorderLayout.SOUTH);
		add(guessPanel, BorderLayout.CENTER);
		
				pack();
		setVisible(true);
		



		
		

	}
	
	public void actionPerformed(ActionEvent event){
		if(event.getSource().equals(inputPanel.button)){
			add(guessPanel, BorderLayout.CENTER);
			String guess = inputPanel.getInput();
			
			int[] newGuess = new int[4];
			newGuess = changeGuess(guess);
			System.out.println(newGuess);
		MastermindInputGuessPanel guessPanels = new MastermindInputGuessPanel(newGuess);
		

			// int numberOfGuesses + numOfGuesses();
			
		}
	}
	
	
	
	public int[] changeGuess(String playerGuess){
		int[] playerGuesses = new int[4];
		
	for(int i = 1; i <= 10; i++){
			
			
			char playerColor1 = playerGuess.charAt(0);
			int playerColorNum1 = generateColor(playerColor1);
			playerGuesses[0] = playerColorNum1;
			
			char playerColor2 = playerGuess.charAt(1);
			int playerColorNum2 = generateColor(playerColor2);
			playerGuesses[1] = playerColorNum2;
			
			char playerColor3 = playerGuess.charAt(2);
			int playerColorNum3 = generateColor(playerColor3);
			playerGuesses[2] = playerColorNum3;
			
			char playerColor4 = playerGuess.charAt(3);
			int playerColorNum4 = generateColor(playerColor4);
			playerGuesses[3] = playerColorNum4;
			
			// numberOfGuesses = numOfGuesses();
	}
		return playerGuesses;
	}
	
	public int generateColor(char playerGuess){
		int g = 0;
		switch(playerGuess){
			case 'r': 
				g = 1;
				break;
			case 'o':
				g = 2;
				break;
			case 'y':
				g = 3;
				break;
			case 'g':
				g = 4;
				break;
			case 'b':
				g = 5;
				break;
			case 'p':
				g = 6;
				break;
		}
		return g;
	}
	
	// public int numOfGuesses(){
			// int numOfGuesses = 0;
		// numOfGuesses++;
	// }
	
}