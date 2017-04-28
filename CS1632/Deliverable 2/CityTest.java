import org.junit.Assert;
import org.junit.Test;
import java.util.Random;
import org.mockito.*;

public class CityTest{
	@Mock
	Location mockLoc1 = Mockito.mock(Location.class);
	Location mockLoc2 = Mockito.mock(Location.class);
	
	//Create a City and add a mock location.
	//It should return true if adding the location is successful.
	@Test
	public void testAddLocation(){
		City c = new City(1);
		Assert.assertTrue(c.addLocation(mockLoc1));
	}
	
	//Create a City and set a mock location.
	//It should return the location that we set.
	@Test
	public void testGetLocation(){
		City c = new City(3);
		c.locations[0] = mockLoc1;
		Assert.assertEquals(c.getLocation(0), mockLoc1);
	}
	
	//Create a City and get the the next location.
	//The location nextLocation() returns should be equal to mockLock2.
	@Test
	public void testNextLocation(){
		City c = new City(5);
		Mockito.when(mockLoc1.getPlaceToGo(5)).thenReturn(mockLoc2);
		Assert.assertEquals(mockLoc2, c.nextLocation(mockLoc1, 5));
	}
	
	//Create a City and get the next road the driver should go on.
	//The road we use for mockLoc1 should be equal to the road nextRoad() returns.
	@Test
	public void testNextRoad(){
		City c = new City(1);
		Mockito.when(mockLoc1.getRoad(Mockito.anyInt())).thenReturn("Fifth Ave");
		Assert.assertEquals("Fifth Ave", c.nextRoad(mockLoc1, 0));
	}
}