
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Mastermind extends JFrame{
	
	MastermindInputPanel inputPanel;
	MastermindInputGuessPanel guessPanel;
	// MastermindGuessPanel guessPanel2;
	
	
	public static void main(String[] args) {
	Mastermind app = new Mastermind();
	


	}
	public Mastermind() {
		super("Mastermind");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(200,300);
		inputPanel = new MastermindInputPanel();
		// MastermindGuessPanel guessPanel2 = new MastermindGuessPanel();
		
		guessPanel = new MastermindInputGuessPanel();
		
		inputPanel.button.addActionListener(this);
		
		setLayout(new BorderLayout());
		add(inputPanel, BorderLayout.SOUTH);
		add(guessPanel, BorderLayout.CENTER);
		pack();
		setVisible(true);
		
		

	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource().equals(inputPanel.button)){
			add(guessPanel, BorderLayout.CENTER);
		}
	}
}