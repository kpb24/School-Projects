//This class represents the set of buttons at the bottom
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
class ButtonPanel extends JPanel implements ActionListener {
	private JRadioButton circleButton = new JRadioButton("Circle");
	private JRadioButton squareButton = new JRadioButton("Square");
	private JRadioButton triangleButton = new JRadioButton("Triangle");
	private JRadioButton smileyButton = new JRadioButton("Smiley Face");
	private JRadioButton hourglassButton = new JRadioButton("Hourglass");

	private JSpinner sizeSpinner = new JSpinner(new SpinnerNumberModel(100, 1, 200, 10));

	//These are the 5 types of things we can draw. We will represent the current shape
	//as an integer called mode, that takes one of these values.
	public static final int CIRCLE_MODE = 0;
	public static final int SQUARE_MODE = 1;
	public static final int TRIANGLE_MODE = 2;
	public static final int SMILEY_MODE = 3;
	public static final int HOURGLASS_MODE = 4;

	private int mode = CIRCLE_MODE;

	public ButtonPanel() {
		//The buttons will be laid out in a 4 row by 2 column grid
		setLayout(new GridLayout(4, 2));

		//Group the radio buttons. This allows only one to be selected at a time
		ButtonGroup group = new ButtonGroup();
		circleButton.setSelected(true);
		group.add(circleButton);
		group.add(squareButton);
		group.add(triangleButton);
		group.add(smileyButton);
		group.add(hourglassButton);

		//Register a listener for the radio buttons.
		circleButton.addActionListener(this);
		squareButton.addActionListener(this);
		triangleButton.addActionListener(this);
		smileyButton.addActionListener(this);
		hourglassButton.addActionListener(this);

		//Add the radio buttons
		add(circleButton);
		add(squareButton);
		add(triangleButton);
		add(smileyButton);
		add(hourglassButton);

		//Make a panel that will hold a label and a spinner that allows you to
		//select a size
		JPanel sizePanel = new JPanel();
		JLabel sizeLabel = new JLabel("Size");
        sizePanel.add(sizeLabel);
        sizeLabel.setLabelFor(sizeSpinner);
        sizePanel.add(sizeSpinner);

		//Add the size panel to our grid
		add(sizePanel);
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == circleButton) {
			mode = CIRCLE_MODE;
		}
		else if(event.getSource() == squareButton) {
			mode = SQUARE_MODE;
		}
		else if(event.getSource() == triangleButton) {
			mode = TRIANGLE_MODE;
		}
		else if(event.getSource() == smileyButton) {
			mode = SMILEY_MODE;
		}
		else if(event.getSource() == hourglassButton) {
			mode = HOURGLASS_MODE;
		}
	}

	//This returns the current size from the Spinner
	public int getShapeSize() {
		return (int)((SpinnerNumberModel)sizeSpinner.getModel()).getNumber();
	}

	//This returns the mode selected
	public int getMode() {
		return mode;
	}
}


