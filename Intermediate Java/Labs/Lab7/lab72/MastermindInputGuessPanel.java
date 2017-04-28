import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
public class MastermindInputGuessPanel extends JPanel{

	private ArrayList<String> guesses = new ArrayList<String>();
	
	public MastermindInputGuessPanel(){
		
	JPanel centerPanel = new JPanel();
	setLayout(new GridLayout(10, 1));
	add(centerPanel);
	}
		
}