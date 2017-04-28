
public class MastermindController{

private int[] playerGuesses;
private int rightColor;
private int wrongColor;
	public MastermindController(){

	}

public MastermindController(MastermindModel model){
		this.model = model;

	}
			
	public boolean isCorrect(int rightColors){
		boolean correct = false;
	if (rightColors == 4){
		correct = true;
	}
		return correct;
	}
	
	public int getRightColorRightPlace(int[] guess){
		rightColor = model.checkColorRightPlace(playerGuesses);
		return rightColor;
	}
	
	public int getRightColorWrongPlace(int[] guess){
		wrongColor = model.checkColorRightPlace(playerGuesses);
		return wrongColor;
	}
}