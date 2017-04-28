public class Circle{
	private double xCoord;
	private double yCoord;
	private double radius;
	
	public Circle(double x, double y, double r){
		xCoord = x;
		yCoord = y;
		radius = r;
	}
	public Circle(){
	this(0,0,1);
	}
	//set values of radius and coordinates
	public void setXCoord(double x){
		xCoord = x;
	}
	public void setYCoord(double y){
		yCoord = y;
	}
	public void setRadius(double r){
		radius = r;
	}
	public double getXCoord(){
		return xCoord;
	}
	public double getYCoord(){
		return yCoord;
	}
	public double getRadius(){
		return radius;
	}
	public double getArea(){
		double radiusSquared = radius * radius;
		return radiusSquared * Math.PI;
	}
	public double getCircumference(){
		double diameter = radius * 2;
		return Math.PI * diameter;
	}
	public boolean isInsideCircle(double xCoord, double yCoord){
		double distance = Math.sqrt(Math.pow((xCoord - 0), 2) + Math.pow((yCoord - 0), 2));
		if (distance < radius){
			return true;
		}
	return false;
	}
}