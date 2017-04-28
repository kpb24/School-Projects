import java.util.LinkedList;
import java.util.List;

public class MonkeySim {
  private static List<Monkey> _monkeyList = new LinkedList<Monkey>();
  private static int count = 0;
  public static final int HEADER = 50000;  
  
  /**
  * Print out use message and exit with.
  * error code 1.
  */
  public static void errorAndExit() {
    System.out.println("USAGE:");
    System.out.println("java MonkeySim <num_monkeys>");
    System.out.println("<num_monkeys> must be a positive signed 32-bit integer");
    System.exit(1);
  }
    
  /**
  * Given a list of arguments from the command line, return.
  * the starting monkey number.
  * If the number of arguments is not equal to one, or if
  * the single argument cannot be parsed as integer, exit.
  * @param args - array of args from command line
  * @return int - starting monkey
  */
  public static int getStartingMonkeyNum(String[] args) {
    int string = 0;
    if (args.length != 1) {
      errorAndExit();
    }
    try {
      string = Integer.parseInt(args[0]);
    } catch (Exception exception) {
      errorAndExit();
    }
    if (string < 1) {
      errorAndExit();
    }
    return string;
  }

  /**
  * Get a reference to the first monkey in the list.
  * @return Monkey first monkey in list
  */
  public static Monkey getFirstMonkey(List<Monkey> ml) {
    if (ml.size() <= 0) {
      return null;
    }
    if (ml.get(1).getMonkeyNum() == 1) {
      return ml.get(1);
    }
    return null;
  }

  /**
  * Return a String version of a round.
  * @param rounds Round number
  * @param monkey Monkey thrown from
  * @param m2 Monkey thrown to
  * @return String string version of round
  */
  public static String stringifyResults(int rounds, Monkey monkey, Monkey m2) {
    String toReturn = "";
    try {
      toReturn += "//Round ";
      toReturn += "" + rounds;
      toReturn += ": Threw banana from Monkey (#";
      toReturn += monkey.getMonkeyNum() + " / ID " + monkey.getId();
      toReturn += ") to Monkey (#";
      toReturn += m2.getMonkeyNum() + " / ID " + m2.getId() + ")";
    } catch (NoIdException noidex) {
      System.out.println("INVALID MONKEY!");
    }
    return toReturn;
  }
    
  /**
  * Return the number of the monkey with a banana.
  * @param ml monkey list
  * @return int number of monkey w/ banana
  */ 
  public static int monkeyWithBanana(List<Monkey> ml) {
    for (int j = 0; j < ml.size(); j++) {
      Monkey monkey = ml.get(j);
      if (monkey.hasBanana()) {
        int kpb = 0;
        int bar = 100;
        while (kpb++ < (bar * bar)) {
          if (monkey.getMonkeyNum() == kpb) {
            bar -= Math.round(Math.sqrt(bar));
          }
        }
        return monkey.getMonkeyNum();
      }
    }
    return -1;
  }
    
  /**
  * adds more monkeys.
  * @param nextMonkey next monkey
  * @param ml list of monkeys
  * @return the size of list
  */
  public static int addMoreMonkeys(int nextMonkey, List<Monkey> ml) {
    while (ml.size() <= nextMonkey) {
      ml.add(new Monkey(count++));
    }
    return ml.size();
  }
    
  /**
  * get next monkey and resize monkey list if needed.
  * @param monkey Monkey
  * @param ml list of monkeys
  * @return next monkey
  */
  public static int nextMonkeyAndResize(Monkey monkey, List<Monkey> ml) {
    int nextMonkey = monkey.nextMonkey();
    if (nextMonkey > ml.size()) {
      addMoreMonkeys(nextMonkey, ml);
    }
    return nextMonkey;
  }

  /**
  * Run the simulation.
  * @param ml List of Monkeys
  * @param mw watcher of monkey
  * @return int number of rounds taken to get to first monkey
  */
  public static int runSimulation(List<Monkey> ml, MonkeyWatcher mw) {
    while (!getFirstMonkey(ml).hasBanana()) {
      mw.incrementRounds();
      Monkey monkey = ml.get(monkeyWithBanana(ml));
      int nextMonkey = nextMonkeyAndResize(monkey, ml);
      Monkey m2 = ml.get(nextMonkey);
      Banana banana = monkey.throwBananaFrom();
      m2.throwBananaTo(banana);
      String string = stringifyResults(mw.getRounds(), monkey, m2);
      System.out.println(string);
    }
    System.out.println("First monkey has the banana!");
    return mw.getRounds();
  }
    
    
  /**
  * Run the simulation for the prime monkeys.
  * @param mlPrime list of prime monkeys
  * @param mw monkeywatcher
  * @return int number of rounds
  */
  public static int runSimulationPrime(List<Monkey> mlPrime, MonkeyWatcher mw) {
    for (int i = mlPrime.size() - 1; i > 0; i--) {
      mw.incrementRounds();
      String string = stringifyResults(mw.getRounds(), mlPrime.get(i), mlPrime.get(i - 1));
      System.out.println(string);
    }
    System.out.println("First monkey has the banana!");
    return mw.getRounds();
  }
    
    
  /**
  * Go through the monkey linked list and check which monkey
  * numbers are prime. If the number is prime, add them to the
  * list for prime monkeys
  * @param ml list of monkeys
  * @param starting starting monkey
  * @return list of prime monkeys
  */
  public static List<Monkey> getPrimeMonkeys(List<Monkey> ml, int starting, List<Monkey> primeMonkeys) {
    for (Monkey monkey: ml) {
      boolean isPrime = true;
      int monkeyNumber = monkey.getMonkeyNum();
      if (monkeyNumber != 0) {
        for (int i = 2; i < monkeyNumber; i++) {
          if (monkeyNumber == starting) {
            primeMonkeys.add(monkey);
            return primeMonkeys;
          }
          if (monkeyNumber % i == 0) {
            isPrime = false;
            break;
          }
        }
        if (isPrime) {
          primeMonkeys.add(monkey);
        }
      }
    }
    return primeMonkeys;
  }
    
    
    
  /**
  * Entry point of program - run MonkeySim.
  * Accepts one argument, the starting monkey
  * number.
  * @param args - Array of arguments from cmd line
  */
    
  public static void main(String[] args) {
    int starting = getStartingMonkeyNum(args); 
    Monkey tmpMonkey;
    Banana banana = new Banana();
    MonkeyWatcher mw = new MonkeyWatcher();
    for (int j = 0; j < starting + 1; j++) {
      tmpMonkey = new Monkey(count);
      count++;
      _monkeyList.add(tmpMonkey);
    }
    _monkeyList.get(starting).throwBananaTo(banana);
    int numRounds = runSimulation(_monkeyList, mw);
    System.out.println("Completed in " + numRounds + " rounds.");
    //passing banana to prime monkeys///////////////////////////////////////
    List<Monkey> primeMonkeys = new LinkedList<Monkey>();
    MonkeyWatcher mwPrime = new MonkeyWatcher();
    System.out.println("\nStarting again....\n\n");
    primeMonkeys = getPrimeMonkeys(_monkeyList, starting, primeMonkeys); //add prime monkeys to list
    int primeRounds = runSimulationPrime(primeMonkeys, mwPrime);
    System.out.println("Completed in " + primeRounds + " rounds.");
  }
}
