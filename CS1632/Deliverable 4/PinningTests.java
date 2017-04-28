import org.junit.Assert;
import org.junit.Test;
import java.util.*;
import java.io.*;
import java.lang.*;
import org.junit.Before;
import org.junit.After;
import org.mockito.*;
import java.util.LinkedList;

public class PinningTests{

 	@SuppressWarnings("unchecked")
	
	@Mock
	Monkey mockMonkey1 = Mockito.mock(Monkey.class);
	Monkey mockMonkey2 = Mockito.mock(Monkey.class);
	MonkeySim mockMonkeySim = Mockito.mock(MonkeySim.class);


	//Test that monkey number one is given the same id as the original
	//method which is 223493.
	@Test
	public void testGenerateID1(){
		Monkey monkey = new Monkey();
		int id = monkey.generateId(1);
		
		Assert.assertEquals(id, 223493);
	}


	//Test that a string passed into generateId() gives the same result as the original
	//method which is 223612.
	@Test
	public void testGenerateID3(){
		Monkey monkey = new Monkey();
		int id = monkey.generateId('x');
		Assert.assertEquals(id, 223612);
	}
	
	
	//Run the generateId() method a large amount of times
	//and check that every id generated is equal to 224678 
	//to make sure the same id is generated each time.
	@Test
	public void testGenerateID4(){
		Monkey monkey = new Monkey();
		int[] array = new int[100];
		for(int i = 1; i < 100; i++){
			array[i] = monkey.generateId(1186);
		}
		boolean same = true;
		for(int i = 1; i < 100; i++){
			if(array[i] != 224678){
				same = false;
			}
		
		}
		Assert.assertTrue(same);
	}


	//Test that getFirstMonkey() returns the first monkey in the list if the monkey
	//number is 1.
	@Test
	public void testGetFirstMonkey1(){
		MonkeySim monkeySim = new MonkeySim();
		LinkedList<Monkey> ml = new LinkedList<Monkey>();
		ml.add(mockMonkey2);
		ml.add(mockMonkey1);
		Mockito.when(mockMonkey1.getMonkeyNum()).thenReturn(1);
		
		Assert.assertEquals(monkeySim.getFirstMonkey(ml), mockMonkey1);
	}	
	
	
	//Test that getFirstMonkey() returns null because none of the monkeys
	//in the list have a monkey number of one.
	@Test
	public void testGetFirstMonkey2(){
		MonkeySim monkeySim = new MonkeySim();
		LinkedList<Monkey> ml = new LinkedList<Monkey>();
		ml.add(mockMonkey2);
		ml.add(mockMonkey1);
		Mockito.when(mockMonkey1.getMonkeyNum()).thenReturn(3);
		Mockito.when(mockMonkey2.getMonkeyNum()).thenReturn(2);
		
		Assert.assertNull(monkeySim.getFirstMonkey(ml));
	}	
	
	
	//Test that getFirstMonkey() returns null if there is nothing in the
	//list of monkeys.
	@Test
	public void testGetFirstMonkey3(){
		MonkeySim monkeySim = new MonkeySim();
		LinkedList<Monkey> ml = new LinkedList<Monkey>();
		Assert.assertEquals(monkeySim.getFirstMonkey(ml), null);
	}
	

///////testing stringifyResults() in MonkeySim class////////////////////////////////////

	//Test that the correct string is printed
	@Test
	public void testStringifyResults1() throws NoIdException{
		MonkeySim monkeySim = new MonkeySim();
		Mockito.when(mockMonkey1.getMonkeyNum()).thenReturn(54);
		Mockito.when(mockMonkey1.getId()).thenReturn(223546);
		Mockito.when(mockMonkey2.getMonkeyNum()).thenReturn(27);
		Mockito.when(mockMonkey2.getId()).thenReturn(223519);
		
		String compareString = "//Round 1: Threw banana from Monkey (#54 / ID 223546) to Monkey (#27 / ID 223519)";
		Assert.assertEquals(compareString, monkeySim.stringifyResults(1, mockMonkey1, mockMonkey2));
	}
	

	//Test that testStringifyResults() throws an exception when no id 
	//is passed into getId().
	@Test
	public void testStringifyResults2() throws NoIdException{
		boolean thrownException = false;
		try{
			MonkeySim monkeySim = new MonkeySim();
			Mockito.when(mockMonkey1.getMonkeyNum()).thenReturn(9232);
			Mockito.when(mockMonkey1.getId());
			Mockito.when(mockMonkey2.getMonkeyNum()).thenReturn(4616);
			Mockito.when(mockMonkey2.getId());
		}
		catch(Exception e){
			thrownException = true; 
		}
		Assert.assertTrue(thrownException);
	}





	@Test
	public void testStringifyResults3() throws NoIdException{
		MonkeySim monkeySim = new MonkeySim();
		Monkey monkey1 = new Monkey();
		Monkey monkey2 = new Monkey();
		monkey1.generateId(0);
		monkey2.generateId(0);
		String compareString1 = monkeySim.stringifyResults(5, monkey1, monkey2);
		String compareString2 = "//Round 5: Threw banana from Monkey (#0 / ID 223492) to Monkey (#1 / ID 223493)";
		Assert.assertEquals(compareString1, compareString2);

	}




}