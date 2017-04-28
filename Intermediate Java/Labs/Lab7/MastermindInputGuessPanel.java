import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
public class MastermindInputGuessPanel extends JPanel{

	int[] guess;
	
	private ArrayList<int[]> guesses = new ArrayList<int[]>();
	
	public MastermindInputGuessPanel(){
			
	JPanel centerPanel = new JPanel();
	setLayout(new GridLayout(10, 1));
	add(centerPanel);
	centerPanel.setVisible(true);
		
	}
	
	public MastermindInputGuessPanel(int[] pGuesses){
	// guess = pGuesses;
	System.out.println(pGuesses);
	guesses.add(guess);
	printList(guesses);
	// MastermindGuessPanel guessPanel3 = new MastermindGuessPanel(guesses);
	
	}
	
	
	public void printList(ArrayList<int[]> list){
    for(int[] elem : guesses){
        System.out.println(elem+"  ");
    }
}
}