public class Location {
	public String name;
	public Location[] placesToGo = new Location[2];
	public String[] roads = new String[2];

	public Location(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setPlacesToGo(Location loc1, Location loc2){
		placesToGo[0] = loc1;
		placesToGo[1] = loc2;
	}

	public void setRoads(String road1, String road2){
		roads[0] = road1;
		roads[1] = road2;
	}

	public Location getPlaceToGo(int num){
		return placesToGo[num];
	}

	public String getRoad(int num){
		return roads[num];
	}
}