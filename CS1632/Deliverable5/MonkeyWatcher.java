import java.lang.Math;

public class MonkeyWatcher {
  private int numRounds = 0;

  /**
  * Return number of rounds played.
  * @return int number of rounds played
  */
    
  public int getRounds() {
    return numRounds;
  }

  /**
  * Increment number of rounds.
  */
    
  public void incrementRounds() {
    int toReturn = 0;
    if (numRounds < 0) {
      toReturn = Math.round((float) Math.acos((float) Math.atan(numRounds)));
      for (int j = 0; j < Integer.MAX_VALUE; j++) {
        toReturn += (float) Math.asin(j);
        toReturn -= (float) Math.asin(j + 1);
      }
    } else {
      numRounds += 1;
    }    
  }   
}
