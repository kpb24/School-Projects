import org.junit.Assert;
import org.junit.Test;
import org.mockito.*;

public class LocationTest{
	@Mock
	Location mockLoc1 = Mockito.mock(Location.class);
	Location mockLoc2 = Mockito.mock(Location.class);
	
	//Create a Location and pass the location name to the constructor.
	//The name returned from getName() should be the same name 
	//that was passed to the constructor.	
	@Test
	public void testGetName(){
		Location loc = new Location("Pitt");
		Assert.assertEquals(loc.getName(), "Pitt");
	}

	//Create a Location and set the mock places to go.
	//The first item of that location's placesToGo array should be equal to mockLoc1.
	//The second item of that location's placesToGo array should be equal to mockLoc2.
	@Test
	public void testSetPlacesToGo(){
		Location loc = new Location("Sennot");
		loc.setPlacesToGo(mockLoc1, mockLoc2);
		Assert.assertEquals(loc.placesToGo[0], mockLoc1);
		Assert.assertEquals(loc.placesToGo[1], mockLoc2);
	}
	
	//Create a location and set the second item of placesToGo to be a mock location.
	//getPlaceToGo(index) should be equal to it's corresponding mock location.
	@Test
	public void testGetPlaceToGo(){
		Location loc = new Location("Sennot");
		loc.placesToGo[1] = mockLoc2;
		Assert.assertEquals(loc.getPlaceToGo(1), mockLoc2);	
	}
	
	//Create a location and use setRoads() to create the roads for that location.
	//The first road in the roads array should be equal to the first road we passed in setRoads().
	//The second road in the roads array should be equal to the second road we passed in setRoads().
	public void testSetRoads() {
		Location loc = new Location("Cathedral");
		loc.setRoads("Forbes Ave", "Fifth Ave");
		Assert.assertEquals(loc.roads[0],"Forbes Ave");
		Assert.assertEquals(loc.roads[1],"Fifth Ave");
	}
	
	//Create a Location and set the first road in the roads array for that locations.
	//getRoad(index) should be equal to it's corresponding road that was set in the roads array.
	@Test
	public void testGetRoads(){
		Location loc = new Location("Cathedral");
		loc.roads[0] = "Forbes Ave";
		Assert.assertEquals(loc.getRoad(0), "Forbes Ave");
	}
}