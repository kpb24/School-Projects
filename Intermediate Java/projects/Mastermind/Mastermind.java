import javax.swing.JFrame;


public class Mastermind extends JFrame{
	public static void main(String[] args){
		
		JFrame = frame new JFrame("Mastermind");
		frame.setSize(200,300);
		frame.setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//add panels to JFrame
		MastermindInputPanel inputPanel= new MastermindInputPanel();
		frame.add(inputPanel, BorderLayout.SOUTH);
		
		MastermindGuessPanel guessPanel = new MastermindGuessPanel();
		frame.add(guessPanel, BorderLayout.CENTER);
		
		MastermindInputGuessPanel inputGuessPanel = new MastermindInputGuessPanel();
		frame.add(inputGuessPanel, BorderLayout.EAST);
		
		
		
		java.util.Scanner input = new java.util.Scanner(System.in);
		System.out.println("Welcome to Mastermind!");
		System.out.println("Would you like to play?");
		String playAnswer = input.next();
		String answer = playAnswer.toLowerCase();
			
			
		//player guess
		int numberofGuesses = 0;
		for(int i = 1; i <= 10; i++){
			
			int[] playerGuesses = new int[4];
			
			System.out.println("\nEnter guess number " + i);
			String playerGuess = input.next();
			
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
				
			numberofGuesses++;
			
			MastermindController controller = new MastermindController();
			
			int rightColorRightPlace = controller.getRightColorRightPlace(playerGuesses);
			int rightColorWrongPlace = controller.getRightColorWrongPlace(playerGuesses);
			
			boolean correctAnswer = controller.isCorrect(rightColorRightPlace);
			if (correctAnswer == true){
				if(numberofGuesses <= 7){
				System.out.println("You won and played a perfect game!");
				}
				else{
					System.out.println("You won!");
				}
				break;
			}
			else if (correctAnswer == false){
				System.out.println("\nColors in the correct place: " + rightColorRightPlace);
				System.out.println("Colors correct but in wrong place: " + rightColorWrongPlace);
			}
		}
			
		System.out.println("\nYou didn't have a correct guess after 10 tries. You lose!");
	}

			
			
	public static int generateColor(char playerGuess){
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
	
}