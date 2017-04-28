public class City{
	public Location[] locations; //Array of locations in the city.
	public int numLocs = 0;
	
	public City(int i){
		locations = new Location[i];
	}
	
	public boolean addLocation(Location loc){
		if (numLocs < (locations.length)){
			locations[numLocs] = loc;
			numLocs++;
        	return true; //Only add the location to the array if it isn't full.
		}
		return false; //Not able to add location.
	}
	
	public Location getLocation(int num){
		return locations[num]; //Get a specific location.
	}
	
	//Returns the next location to go to.
	public Location nextLocation(Location currentLoc, int i){
		Location nextLoc = currentLoc.getPlaceToGo(i);	
		return nextLoc;
	}
	
	//Returns the next road to go on.
	public String nextRoad(Location currentLoc, int i){
		return currentLoc.getRoad(i); //Chooses one of two road options.
	}
}