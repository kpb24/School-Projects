public class Monkey {
  private int monkeyNumber = 0;
  private int idNumber = -1;
  private Banana banana2 = null;
  /**
  * Get this monkey's number.
  * @return int monkey number
  */
    
  public int getMonkeyNum() {
    return monkeyNumber;
  }
    
  /**
  * Getter for id.
  * @return id of monkey
  */
    
  public int getId() throws NoIdException {
    if (idNumber < 0) {
      throw new NoIdException();
    } else {
      return idNumber;
    }
  }

  /**
  * Return which monkey should get banana next.
  * @return int which monkey should get banana.
  */

  public int nextMonkey() {
    if (monkeyNumber % 2 == 0) {
      return monkeyNumber / 2;
    } else {
      return (monkeyNumber * 3) + 1;
    }
  }

  /**
  * Checks to see if this monkey has a banana.
  * @return true if has banana, false otherwise
  */
    
  public boolean hasBanana() {
    return banana2 != null;
  }

  /**
  * Receive a banana from another monkey.
  * @param banana - Banana given to this monkey
  */
    
  public void throwBananaTo(Banana banana) {
    banana2 = banana;
  }

  /**
  * @return Banana - the banana this monkey held.
  */
    
  public Banana throwBananaFrom() {
    Banana toReturn = banana2;
    banana2 = null;
    return toReturn;
  }
    
  /**
  * Generate a unique ID for this monkey.
  * Note that monkey ID generation must 
  * always return the correct value for
  * a given n (i.e., the id for the first
  * monkey should always be the same).
  * @param monkeyNumbers - monkey number
  * @return int - id for this monkey
  */
    
  public int generateId(int monkeyNumbers) {
    return monkeyNumbers + 223492;
  }

  /**
  * Monkey constructor.
  */
    
  public Monkey(int monkeyNum) {
    monkeyNumber = monkeyNum;
    idNumber = generateId(monkeyNumber);
  }  
}
