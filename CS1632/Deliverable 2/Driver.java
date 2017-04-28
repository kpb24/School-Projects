import java.util.Random;

public class Driver {
	public int driverNumber;
	public Location currentLoc;
	public Location nextLoc;
	public String road;
	public boolean justStarted;
	public int coffeeCount;

	public Driver(int driverNumber){
		this.driverNumber = driverNumber;
		justStarted = true; //When this variable is true, the driver just started driving (use for checking if outside city).
		coffeeCount = 0;
	}
	
	//Determines the path the driver is driving.
	public String path(City city, Random rand){
		currentLoc = nextLoc;
		currentLoc = isOutsideCity(city, rand, currentLoc); //Makes sure they aren't starting from outside the city.
		coffee(currentLoc); //Check if the driver got coffee and if so, then increment the coffee count.
		int next = rand.nextInt(2); //Gives a random number to use for road and placesToGo arrays since only two items in each.
		nextLoc = city.nextLocation(currentLoc, next); //Get the next location to go to using generated random number.
		road = city.nextRoad(currentLoc, next); //Get the next road to go to using generated random number.
		return nextLoc.getName();
	}

	//Check if the driver is going outside the city when they just start driving.
	//If they are starting from outside the city, change to a different starting location.
	public Location isOutsideCity(City city, Random rand, Location currentLoc){
		if(justStarted){
			boolean notOutsideCity = false;
			while(notOutsideCity == false){
				currentLoc = city.getLocation(rand.nextInt(5));
				if(!currentLoc.getName().equals("Outside City")){
					notOutsideCity = true; //We can use this current location since it's not outside city.
				}
			}
			justStarted = false; 
		}
		return currentLoc; 
	}
	
	//Checks if the driver went to the coffee shop and adds to the number of cups they got.
	public boolean coffee(Location currLocation){
		if(currLocation.getName().equals("Coffee")){
			coffeeCount++;
			return true; 
		}
		return false; //Driver didn't go to coffee shop.
	}
	
	//Print the path the driver took.
	public String printPath(){
		return("Driver " + (driverNumber + 1) + " is heading from " + currentLoc.getName() + " to " + nextLoc.getName() + " via " + road + ".");
	}
	
	//Once the driver goes outside city print where they went and how many cups of coffee they got.
	public String printEnd(){
		if (road.equals("Fourth Ave")){
			return("Driver " + (driverNumber + 1) + " has gone to Philadelphia!\nDriver " + (driverNumber + 1) + " got " + coffeeCount + " cup(s) of coffee.");
		}
		else if(road.equals("Fifth Ave")){
			return("Driver " + (driverNumber + 1) + " has gone to Cleveland!\nDriver " + (driverNumber + 1) + " got " + coffeeCount + " cup(s) of coffee.");
		}
		return null; //This should NOT happen.	
	}
}