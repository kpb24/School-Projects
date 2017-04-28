import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class MastermindInputPanel extends JPanel{
	
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
	}
	
	public String getInput() {
      return textField.getText();
   }
}
	
