import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
//Kerilee Bookleiner

//The frame represents the whole window
class DrawingShapes extends JFrame {
	public static void main(String[] args) {
		DrawingShapes app = new DrawingShapes();
	}
	public DrawingShapes() {
		super("Drawing Shapes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Make our top and bottom parts
		ButtonPanel buttons = new ButtonPanel();
		ShapesPanel pane = new ShapesPanel(buttons);
		setLayout(new BorderLayout());
		add(buttons, BorderLayout.SOUTH);
		add(pane, BorderLayout.CENTER);
		pack();
		setVisible(true);
	}
}

