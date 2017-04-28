//This class represents the space where shapes are drawn.
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;

class ShapesPanel extends JPanel {
	private ArrayList<AllShapes> shapes = new ArrayList<AllShapes>();
	private ButtonPanel bp;

	public ShapesPanel(ButtonPanel panel) {
		bp = panel;
		addMouseListener(new DrawingShapesMouseListener());
		setOpaque(true);
		setBackground(Color.lightGray);
	}
	private class DrawingShapesMouseListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent source) {
			//add new shapes
			if(bp.getMode() == ButtonPanel.CIRCLE_MODE) {
			if(bp.getMode() == ButtonPanel.CIRCLE_MODE) {
				shapes.add(new Circle(source.getX(), source.getY(), bp.getShapeSize()));
			}
			else if(bp.getMode() == ButtonPanel.SQUARE_MODE) {
				shapes.add(new Square(source.getX(), source.getY(), bp.getShapeSize()));
			}
			else if(bp.getMode() == ButtonPanel.TRIANGLE_MODE) {
				shapes.add(new Triangle(source.getX(), source.getY(), bp.getShapeSize()));
			}
			else if(bp.getMode() == ButtonPanel.SMILEY_MODE) {
				shapes.add(new Smiley(source.getX(), source.getY(), bp.getShapeSize()));
			}
			else if(bp.getMode() == ButtonPanel.HOURGLASS_MODE) {
				shapes.add(new Hourglass(source.getX(), source.getY(), bp.getShapeSize()));
			}
			//Redraw the window
			repaint();
		}
	}

	public void paintComponent(Graphics g) {
		//super class paint component
		super.paintComponent(g);
		//Draw our shapes
		for(AllShapes shape: shapes) {
			shape.draw(g);
		}
	}
	//We return the size we want so that the window starts off properly-sized.
	public Dimension getPreferredSize()
	{
		return new Dimension(300, 300);
	}
}
//Draw a circle
class Circle implements AllShapes{
	private int x;
	private int y;
	private int size;
	public Circle(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	public void draw(Graphics g) {
		g.drawOval(x-size/2,y-size/2,size,size);
	}
}
//Draw a square
class Square implements AllShapes{
	private int x;
	private int y;
	private int size;
	public Square(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	public void draw(Graphics g) {
		g.drawRect(x-size,y-size,size,size);
	}
}
//Draw a triangle
class Triangle implements AllShapes{
	private int x;
	private int y;
	private int size;
	public Triangle(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	public void draw(Graphics g) {
		int[] xPoints = {x, x, x + size};
		int[] yPoints = {y, y + size, y};
		g.drawPolygon(xPoints,yPoints, 3) ;
	}
}
//Draw a smiley face
class Smiley implements AllShapes{
	private int x;
	private int y;
	private int size;
	public Smiley(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	public void draw(Graphics g) {
		g.drawOval(x-size/2,y-size/2,size,size);
		g.drawOval(x-size/4,y-size/4,size/6,size/6);
		g.drawOval(x+size/10,y-size/4,size/6,size/6);
		g.drawArc(x - 1 * size/4, y - 1 * size/10, size/2, size/3, 230, 90);
	}
}
//Draw hourglass
class Hourglass implements AllShapes{
	private int x;
	private int y;
	private int size;
	public Hourglass(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	public void draw(Graphics g) {
		g.drawLine(x, y, x-size, y);
		g.drawLine(x-size, y, x, y-size);
		g.drawLine(x, y-size, x-size, y-size);
		g.drawLine(x-size, y-size, x, y);
	}
}