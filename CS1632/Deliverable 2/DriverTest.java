import org.junit.Assert;
import org.junit.Test;
import java.util.Random;
import org.mockito.*;
import static org.mockito.Mockito.verify;

public class DriverTest{
	@Mock
	Location mockLoc1 = Mockito.mock(Location.class);
	Location mockLoc2 = Mockito.mock(Location.class);
	Random mockRand = Mockito.mock(Random.class);
	City mockCity = Mockito.mock(City.class);
	
	//Create a driver and have the mock city return mockLoc1 as the location.
	//Since we want to avoid randomness, mockRand will return 2.
	//Have the name of mockLoc1 be Library. The currentLoc is mockLoc1
	//and nextLoc is mockLoc2. The nextLoc determined by path() should be the same as mockLoc2.
	@Test
	public void testPath(){
		Driver driver = new Driver(5);
		Mockito.when(mockCity.getLocation(2)).thenReturn(mockLoc1);
		Mockito.when(mockLoc1.getName()).thenReturn("Library");
		Mockito.when(mockRand.nextInt(Mockito.anyInt())).thenReturn(2);
		Mockito.when(mockCity.nextLocation(mockLoc1, 2)).thenReturn(mockLoc2);
 		driver.currentLoc = mockLoc1;
		driver.path(mockCity, mockRand);
		Assert.assertEquals(driver.nextLoc, mockLoc2);
	}
	
	//Create a driver and have the mock city return mockLoc1 as the location.
	//Since we want to avoid randomness, mockRand will return 0.
	//Have the name of mockLoc1 be Diner. The method isOutsideCity should
	//not change the location since Diner isn't the same as Outside City.
	@Test
	public void testIsOutsideCity(){
		Driver driver = new Driver(0);
		Mockito.when(mockCity.getLocation(0)).thenReturn(mockLoc1);
		Mockito.when(mockLoc1.getName()).thenReturn("Diner");
		Mockito.when(mockRand.nextInt(Mockito.anyInt())).thenReturn(0);
 		driver.currentLoc = mockLoc1;
		driver.nextLoc = mockLoc2;
		Location loc = driver.isOutsideCity(mockCity, mockRand, driver.currentLoc);
		Assert.assertEquals(driver.currentLoc, loc);
	}
		
	//When the driver visits the Coffee location, they should get a coffee.
	//Create a driver and a Coffee location and have the driver visit it.
	//The driver should have picked up a coffee.
	@Test
	public void testCoffee(){
		Driver driver = new Driver(0);
		Location loc = new Location("Coffee");
		boolean gotCoffee = driver.coffee(loc);
		Assert.assertTrue(gotCoffee);
	}
	
	//When the driver visits a location other than coffee, they should not get a coffee.
	//Create a driver and Library location and have the driver visit it.
	//The driver should not have picked up a cup of coffee.
	@Test
	public void testNoCoffee(){
		Driver driver = new Driver(0);
		Location loc = new Location("Library");
		boolean gotCoffee = driver.coffee(loc);
		Assert.assertFalse(gotCoffee);
	}
	
	//Create a Driver and set the road. 
	//Set the current and next locations of the driver.
	//The string from printPath() should equal path.
	@Test
	public void testPrintPath(){
		Driver driver = new Driver(0);
		driver.road = "Forbes Ave";
		driver.currentLoc = mockLoc1;
		driver.nextLoc = mockLoc2;
		Mockito.when(mockLoc1.getName()).thenReturn("Cathedral");
		Mockito.when(mockLoc2.getName()).thenReturn("Sennot");
		String path = "Driver 1 is heading from Cathedral to Sennot via Forbes Ave.";
		Assert.assertEquals(path, driver.printPath());
	}
	
	//Create a driver and set the road. 
	//The string from printEnd() should equal the string path.
	@Test
	public void testPrintEnd(){
		Driver driver = new Driver(0);
		driver.road = "Fourth Ave"; //Fourth Ave leads to Philadelphia.
		String path = "Driver 1 has gone to Philadelphia!\nDriver 1 got 0 cup(s) of coffee."; 
		Assert.assertEquals(path, driver.printEnd());
	}
	
	//Create a driver and set the road to a road that doesn't lead to Philadelphia or Cleveland. 
	//The method printEnd() determines which outside city the driver is going to based on the road.
	//printEnd() should return null because Bill st. doesn't lead to an outside city.
	@Test
	public void testPrintEndNull(){
		Driver driver = new Driver(0);
		driver.road = "Bill St"; //Bill st doesn't lead to an outside city.
		Assert.assertNull(driver.printEnd());
	}
}