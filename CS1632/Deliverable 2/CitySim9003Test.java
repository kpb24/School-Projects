import org.junit.Assert;
import org.junit.Test;

public class CitySim9003Test{
	
	//Checks that testInput() will accept a single integer argument.
	//Should return true.
	@Test
	public void testInputInt(){
		CitySim9003 citySim = new CitySim9003();
		String[] arguments = new String[]{"250"};
		Assert.assertTrue(citySim.checkInput(arguments));		
	}
	
	//Checks that testInput() will not accept more than one integer.
	//Should return false.
	@Test
	public void testInputMultipleInts(){
		CitySim9003 citySim = new CitySim9003();
		String[] arguments = new String[]{"25", "22"};
		Assert.assertFalse(citySim.checkInput(arguments));		
	}
	
	//Checks that testInput() will not accept a single string as an argument.
	//Should return false.
	@Test
	public void testInputString(){
		CitySim9003 citySim = new CitySim9003();
		String[] arguments = new String[]{"hi"};
		Assert.assertFalse(citySim.checkInput(arguments));		
	}
	
	//Checks that testInput() will not accept more than one string.
	//Should return false.
	@Test
	public void testInputMultipleStrings(){
		CitySim9003 citySim = new CitySim9003();
		String[] arguments = new String[]{"hi", "hello"};
		Assert.assertFalse(citySim.checkInput(arguments));		
	}
}