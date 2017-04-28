import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class MastermindInputPanel extends JPanel implements ActionListener{
	
	JButton button;
	JTextField textField;
	JPanel southPanel;


	
	public MastermindInputPanel(){
	button = new JButton("Enter");
	southPanel = new JPanel();
	textField = new JTextField(20);
	
	
	southPanel.add(textField);
	southPanel.add(button);
	add(southPanel);
	button.addActionListener(this);
	textField.addActionListener(this);
	
	
	
	}
	
	public String getInput() {
      return textField.getText();
   }
	
	
	
	
	public void actionPerformed(ActionEvent event) {
			String guess = getInput();
			System.out.println(guess);
		
		
		
		
}
	
	
	

}