import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
public class MastermindGuessPanel extends JPanel{
	
	public MastermindGuessPanel(){
		JPanel northPanel = new JPanel();
		JLabel lable = new JLabel("Score");
		
		setLayout(new BorderLayout());
		add(lable, BorderLayout.EAST);
		
	}
	
	public MastermindGuessPanel(ArrayList<String> guesses){
	
	}
	// repaint();
	
	public void paintComponent(Graphics g) {
	//super class paint component
	super.paintComponent(g);
	//Draw our shapes
	for(String guess: guesses) {
		guess.draw(g);
	}
}
	public void draw(Graphics g) {
		g.fillOval(20/2,20/2,20,20);
	}
}






