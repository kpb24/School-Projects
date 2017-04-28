import org.junit.Assert;
import org.junit.Test;
import org.mockito.*;
import java.util.LinkedList;
import java.util.List;

public class MonkeySimTest{
	@Mock
	Monkey mockMonkey1 = Mockito.mock(Monkey.class);
	Monkey mockMonkey2 = Mockito.mock(Monkey.class);
	Monkey mockMonkey3 = Mockito.mock(Monkey.class);
	Monkey mockMonkey4 = Mockito.mock(Monkey.class);
	MonkeyWatcher mockMW = Mockito.mock(MonkeyWatcher.class);
	MonkeySim monkeySim = new MonkeySim();

	//Tests that the method increments the rounds correctly.
	@Test
	public void testSimulationPrime() throws NoIdException{
		List<Monkey> primeMonkeys = new LinkedList<Monkey>();
		MonkeySim monkeySim = new MonkeySim();
		MonkeyWatcher mw = new MonkeyWatcher();
		primeMonkeys.add(mockMonkey1);
		primeMonkeys.add(mockMonkey2);
		Mockito.when(mockMonkey1.getMonkeyNum()).thenReturn(17);
 		Mockito.when(mockMonkey2.getMonkeyNum()).thenReturn(13);
 		Mockito.when(mockMonkey1.getId()).thenReturn(223509);
 		Mockito.when(mockMonkey2.getId()).thenReturn(223505);
		int rounds = monkeySim.runSimulationPrime(primeMonkeys, mw);
		Assert.assertEquals(rounds, 1);	
	}
	
	//Tests that if an empty monkey list is passed to simulationPrime,
	//the program won't increment the rounds since the game can't be played
	//with an empty monkey list.
	@Test
	public void testSimulationPrime2() throws NoIdException{
		List<Monkey> primeMonkeys = new LinkedList<Monkey>();
		MonkeySim monkeySim = new MonkeySim();
		MonkeyWatcher mw = new MonkeyWatcher();
		int rounds = monkeySim.runSimulationPrime(primeMonkeys, mw);
		Assert.assertEquals(rounds, 0);	
	}
	
	//Tests that there are only two rounds when three monkeys are in the 
	//primes list. There should not be one round per monkey in the list.
	@Test
	public void testSimulationPrime3() throws NoIdException{
		List<Monkey> primeMonkeys = new LinkedList<Monkey>();
		MonkeySim monkeySim = new MonkeySim();
		MonkeyWatcher mw = new MonkeyWatcher();
		primeMonkeys.add(mockMonkey1);
		primeMonkeys.add(mockMonkey2);
		primeMonkeys.add(mockMonkey3);
		Mockito.when(mockMonkey1.getMonkeyNum()).thenReturn(11);
 		Mockito.when(mockMonkey2.getMonkeyNum()).thenReturn(13);
 		Mockito.when(mockMonkey3.getMonkeyNum()).thenReturn(17);
 		Mockito.when(mockMonkey1.getId()).thenReturn(223503);
 		Mockito.when(mockMonkey2.getId()).thenReturn(223505);
 		Mockito.when(mockMonkey3.getId()).thenReturn(223509);
		int rounds = monkeySim.runSimulationPrime(primeMonkeys, mw);
		Assert.assertEquals(rounds, 2);	
	}
	
	//Test that stringifyResults still outputs the correct string since all of the 
	//"new String()"'s were removed to help with memory according to FindBugs. Since
	//the simulation of the prime and monkey lists uses this method, it is important 
	//to test due to those changes.
	@Test
	public void testStringifyResultsPrime() throws NoIdException{
		MonkeySim monkeySim = new MonkeySim();
		Mockito.when(mockMonkey1.getMonkeyNum()).thenReturn(45);
		Mockito.when(mockMonkey1.getId()).thenReturn(223535);
		Mockito.when(mockMonkey2.getMonkeyNum()).thenReturn(43);
		Mockito.when(mockMonkey2.getId()).thenReturn(223533);
		String compareString = "//Round 1: Threw banana from Monkey (#45 / ID 223535) to Monkey (#43 / ID 223533)";
		Assert.assertEquals(compareString, monkeySim.stringifyResults(1, mockMonkey1, mockMonkey2));
	}
	
	//Test that the first number added to the primeMonkeys list is 7 since
	//8 is not prime.
	@Test
	public void testPrime(){
		List<Monkey> monkeyList = new LinkedList<Monkey>();
		List<Monkey> primeMonkeys = new LinkedList<Monkey>();
		monkeyList.add(mockMonkey1);
		monkeyList.add(mockMonkey2);
		Mockito.when(mockMonkey1.getMonkeyNum()).thenReturn(8);
		Mockito.when(mockMonkey2.getMonkeyNum()).thenReturn(7);
		primeMonkeys = monkeySim.getPrimeMonkeys(monkeyList, 10, primeMonkeys);
		Assert.assertEquals(primeMonkeys.get(0).getMonkeyNum(), 7);
	}
	
	//Test that the size of the primeMonkey list is zero when none of the
	//monkeys in the monkey list have prime monkey numbers and when the monkey list
	//does not include the starting number(if it's not prime).
	@Test
	public void testPrime2(){
		List<Monkey> ml = new LinkedList<Monkey>();
		List<Monkey> primes = new LinkedList<Monkey>();
		ml.add(mockMonkey3);
 		ml.add(mockMonkey4);
 		Mockito.when(mockMonkey3.getMonkeyNum()).thenReturn(6);
 		Mockito.when(mockMonkey4.getMonkeyNum()).thenReturn(4);
 		primes = monkeySim.getPrimeMonkeys(ml, 10, primes);
 		Assert.assertEquals(primes.size(), 0);
	}
	
	//Test that the starting monkey is included in the primes list 
	//if the starting monkey number is in the monkey list even though
	//it is not considered prime. It is used to start the passing of the
	//banana to its primes lower than it.
	@Test
	public void testPrime3(){
		List<Monkey> ml = new LinkedList<Monkey>();
		List<Monkey> primes = new LinkedList<Monkey>();
		ml.add(mockMonkey1);
 		ml.add(mockMonkey2);
 		ml.add(mockMonkey3);
 		ml.add(mockMonkey4);
 		Mockito.when(mockMonkey1.getMonkeyNum()).thenReturn(20);
 		Mockito.when(mockMonkey2.getMonkeyNum()).thenReturn(17);
 		Mockito.when(mockMonkey3.getMonkeyNum()).thenReturn(13);
 		Mockito.when(mockMonkey4.getMonkeyNum()).thenReturn(9);
 		primes = monkeySim.getPrimeMonkeys(ml, 20, primes);
 		Assert.assertEquals(primes.get(0).getMonkeyNum(), 20);
	}
	
	//Test that the starting monkey is not included in the primes list
	//if it is not included in the monkey list. The starting number should
	//be in the monkey list so this tests if the loop iterates correctly for
	//adding the starting number along with primes to the primes list.
	@Test
	public void testPrime4(){
		List<Monkey> ml = new LinkedList<Monkey>();
		List<Monkey> primes = new LinkedList<Monkey>();
		ml.add(mockMonkey1);
 		ml.add(mockMonkey2);
 		ml.add(mockMonkey3);
 		Mockito.when(mockMonkey2.getMonkeyNum()).thenReturn(17);
 		Mockito.when(mockMonkey3.getMonkeyNum()).thenReturn(13);
 		primes = monkeySim.getPrimeMonkeys(ml, 20, primes);
 		Assert.assertNotEquals(primes.get(0).getMonkeyNum(), 20);
	}
}