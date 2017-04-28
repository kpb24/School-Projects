import java.util.Random;

public class CitySim9003 {
	private static Random rand;
	private static Location[] locations;
	private static City city;
	
	public static void main(String[] args) {
		if(checkInput(args)){
			rand = new Random(Integer.parseInt(args[0])); //use the seed
			
			//Create the five locations that will be a part of the city.
			Location hotel = new Location("Hotel");
			Location diner = new Location("Diner");
			Location library = new Location("Library");
			Location coffee = new Location("Coffee");
			Location outsideCity = new Location("Outside City");
		
			//Set places a driver can go to from a location.
			//The driver should never start from outside the city so the places to go
			//from there don't matter.
			hotel.setPlacesToGo(diner, library);
			diner.setPlacesToGo(outsideCity, coffee);
			library.setPlacesToGo(outsideCity, hotel);
			coffee.setPlacesToGo(library, diner);
		
			//Set the roads that connect the locations.
			hotel.setRoads("Fourth Ave", "Bill St");
			diner.setRoads("Fourth Ave", "Phil St");
			library.setRoads("Fifth Ave", "Bill St");
			coffee.setRoads("Fifth Ave", "Phil St");
			outsideCity.setRoads("Fourth Ave", "Fifth Ave");

			//Create the city - has 5 locations.
			city = new City(5);
			city.addLocation(hotel);
			city.addLocation(diner);
			city.addLocation(coffee);
			city.addLocation(library);
			city.addLocation(outsideCity);

			//One driver at a time, print their paths and where they end up.
			for(int i = 0 ; i < 5 ; i++){
				Driver currentDriver = new Driver(i);
				while(true){
					if(!currentDriver.path(city, rand).equals("Outside City")){
						System.out.println(currentDriver.printPath());
					}
					else{
						System.out.println(currentDriver.printPath()); // Print the path they took to leave the city.
						System.out.println(currentDriver.printEnd()); // Print which city they're going to.
						System.out.println("-----");
						break;
			
					}	
				}
			}
		}		
	}
	
	//Checks the input to make sure it's a single integer argument.
	public static boolean checkInput(String[] args){
		if(args.length != 1){
			return false;
		}
		else{
			try{
				Integer.parseInt(args[0]);
			}
			catch(NumberFormatException e){
				return false;
			}
			return true; //Correct input.
		}
	}
}
	