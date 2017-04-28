public class MastermindModel{
	private final int[] solution = generateSolution();
	private int rightPlace;
	private int wrongPlace;
	
	public int[] generateSolution(){
		int[] computerSolution = new int[4];
		for(int i = 0; i < 4; i++){
			int compSolution = (int)(Math.random() * 6) + 1;
			computerSolution[i] = compSolution;
		}
		return computerSolution;
	}
	
	public int checkColorRightPlace(int[] guess){
		int rightPlace = 0;
		for(int j = 0; j < 4; j++){
		if (solution[j] == guess[j] ){
			rightPlace++;
		}
	}
	return rightPlace;
	}
	
	
			
	public int checkColorWrongPlace(int[] guess){
		int wrongPlace= 0;
		for(int h = 0; h < 4; h++){
			if (guess[0] != solution[0]){
				if (guess[0] == solution[h]){
					wrongPlace++;
				
				}
			}
			else if (guess[1] != solution[1]){
				if (guess[1] == solution[h]){
					wrongPlace++;
				}
			}
			else if (guess[2] != solution[2]){
				if (guess[2] == solution[h]){
					wrongPlace++;
				}
			}
			else if (guess[3] != solution[3]){
				if (guess[3] == solution[h]){
					wrongPlace++;
				}
		}
		
	}
	return wrongPlace;
}
}