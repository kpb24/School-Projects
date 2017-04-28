//Kerilee Bookleiner
public class CircleCalculator{
public static void main(String[] args){
	
	boolean done = false;
	while (!done){
	java.util.Scanner input = new java.util.Scanner(System.in);
	System.out.println("Would you like to construct a unit-circle or a circle?");
	String circleInput = input.next();
	String circleType = circleInput.toLowerCase();
	
	//If they choose a unit circle x is 0, y is 0, and radius is 1
	if (circleType.equals("unit-circle")){
		Circle myUnitCircle = new Circle();
		System.out.println("\nWhat would you like to do next?");
		System.out.println("1) Display center, radius, circumference, and area");
		System.out.println("2) Start a new circle");
		System.out.println("3) Exit");
		int menuChoice = input.nextInt();
		if (menuChoice == 1){
			System.out.println("\nThe center of the unit circle is " + myUnitCircle.getXCoord() + "," + myUnitCircle.getYCoord());
			System.out.println("The radius is " + myUnitCircle.getRadius());
			System.out.printf("The area is " + "%.2f", myUnitCircle.getArea());
			System.out.printf("\nThe circumference is " + "%.2f", myUnitCircle.getCircumference());
			done = true;
		}
		else if(menuChoice == 2){
			done = false;
		}
		else if (menuChoice == 3){
			done = true;
		}
	}
	
	//allows the user to enter own values for x, y, radius
	else if(circleType.equals("circle")){
		System.out.println("Please enter the x-coordinate.");
		double xCoord = input.nextDouble();
		System.out.println("Please enter the y-coordinate.");
		double yCoord = input.nextDouble();
		System.out.println("Please enter the radius.");
		double radius = input.nextDouble();
		Circle myCircle = new Circle(xCoord, yCoord, radius);
		
		System.out.println("\nWould you like to update any values? Yes or No");
		String answerOne = input.next();
		String answer1 = answerOne.toLowerCase();
		if (answer1.equals("yes")){
			System.out.println("\nPlease re-enter the x- coordinate");
			myCircle.setXCoord(input.nextDouble());
			System.out.println("\nPlease re-enter the y- coordinate");
			myCircle.setYCoord(input.nextDouble());
			System.out.println("\nPlease re-enter the radius");
			myCircle.setRadius(input.nextDouble());
			}
			
		System.out.println("\nWould you like to determine if a point is in the circle? Yes or No");
		String answerTwo = input.next();
		String answer2 = answerTwo.toLowerCase();
		if(answer2.equals("yes")){
			System.out.println("\nEnter the x-coordinate you want to test");
			double newXCoord = input.nextDouble();
			System.out.println("\nEnter the y-coordinate you want to test");
			double newYCoord = input.nextDouble();
			boolean pointCircle = myCircle.isInsideCircle(newXCoord, newYCoord);
			if(pointCircle == true){
				System.out.println("The point is in the circle\n");
				}
			else{
				System.out.println("The point is not in the circle\n");
				}
		}
		
	System.out.println("\nWhat would you like to do next?");
	System.out.println("1) Display center, radius, circumference, and area");
	System.out.println("2) Start a new circle");
	System.out.println("3) Exit");
	int menuChoice = input.nextInt();
	if (menuChoice == 1){
		System.out.println("\nThe center of the circle is " + myCircle.getXCoord() + "," + myCircle.getYCoord());
		System.out.println("The radius is " + myCircle.getRadius());
		System.out.printf("The area is " + "%.2f", myCircle.getArea());
		System.out.printf("\nThe circumference is " + "%.2f", myCircle.getCircumference());
		done = true;
		}
	else if(menuChoice == 2){
		done = false;
	}
	else if (menuChoice == 3){
		done = true;
	}
	}
}
}
}