//Kerilee Bookleiner
import java.util.Arrays;
public class FriendCoupon{
	private int userNumPrint;
	private int numberOfCoupons;
	private int numberOfUsers;
	boolean[][] friendGrid;
	int size; 
	int printIncrement;
	
	public FriendCoupon(int coupon, int userNum, boolean [][] grid){
		userNumPrint = 1;
		numberOfCoupons = coupon;
		numberOfUsers = userNum;
		friendGrid = grid;
		printIncrement = 1;
	} 
	
	public FriendCoupon(){}
	
	
// //recursive method that calls helper methods
	public void recurse(char[] answer){
		// //if isFullSolution returns true, the solution is complete and will be printed
		if(isFullSolution(answer) == true){
			for(int i = 0; i < numberOfUsers; i++){
				System.out.println("User " + printIncrement + ":  "+ answer[i]);
				printIncrement++;
			}
			// exit because we only need one solution
			System.exit(0);
		}
		//check if the solution is rejected before continuing
		if(reject(answer) == true){
			//give control back to method
			return;
		}
		//set partial to be the result of extend
		char[] pSoln = extend(answer);
		//System.out.println(pSoln); 
		//as long as extend doesn't return null we can keep going
		//once it's null there aren't anymore steps to add
		while(pSoln != null){
			recurse(pSoln);
			pSoln = next(pSoln);
		}
	}
	
	
	//accepts a partial solution and 
	//returns true if it is a valid solution.
	public boolean isFullSolution(char[] answer){
		int size = answer.length-1;
		//solution is invalid if the last element is 0 or solution rejected
		for(int i = 0; i < answer.length; i++){
			//invalid
			if(answer[i] ==0){
			return false;
			}
		}
		//if we get here that means the solution is valid
		return true;
	}
		
			//Tests isFullSolution
			public void testIsFullSolutionUnit(char[] test) {
				if (isFullSolution(test)) {
					System.err.println("Full Solution:\t" + Arrays.toString(test));
				} else {
					System.err.println("Not full:\t" + Arrays.toString(test));
				}
			}
			public void testIsFullSolution() {
				System.err.println("\nTesting isFullSolution()");
				// Full solutions:
					testIsFullSolutionUnit(new char[] {'A', 'B', 'C', 'A'});
				// Not full solutions:
				testIsFullSolutionUnit(new char[] {'A', 'C', 0, 0});
				testIsFullSolutionUnit(new char[] {'A', 0, 0, 0});
				testIsFullSolutionUnit(new char[] {'A', 'C', 'D', 0});
			}
	
	
	
	
	
	//accepts a partial solution
	//returns true if it should be rejected since can't extend
	public boolean reject(char[] answer){
		//if it's equal to zero it must be less than the length to go back
		for(int i = 0; answer[i] > 0; i++){
			if(i < answer.length-1){
				if(answer[i] == 0){
					i--;
				}
				for(int j = 0; j < i; j++){
				//we need to reject the solution if j and i are the same
				//since can't be friends with themselves AND it's true in grid
					if(answer[j] == answer[i]){
						if(friendGrid[i][j] == true){
							return true;
					}
				}
			}
		}
		}
		//if we get here then it isn't rejected
		return false;
	}
	
			//Test reject
			public void testRejectUnit(char[] test){
				if (reject(test)){
					System.err.println("Rejected:\t" + Arrays.toString(test));
				} 
				else{
					System.err.println("Not rejected:\t" + Arrays.toString(test));
				}
			}
			public void testReject(){
				System.err.println("\nTesting reject()");
				// Should not be rejected:
				testRejectUnit(new char[] {'A', 'C', 'B', 0});
				// Should be rejected:
				testRejectUnit(new char[] {'A', 'A', 'A', 'A'});
			}
	
	
	
	
	
	
	//accepts a partial solution 
	//returns another partial solution w/ another step added
	//return null if there are no more steps to add
	public char[] extend(char[] answer){
		//fill each space in the temp array with the letter a
		//since number of coupons is being inputted we don't know how many
		//so we will use ascii code to increment later
		char[] temp = new char[numberOfUsers];
		for (int i = 0; i < numberOfUsers; i++) {
			if (answer[i] != 0) {
				//ran out of space
				temp[i] = answer[i];
			} 
			else {
				//copy a into each space
				temp[i] = 'a';
				return temp;
			}
		}
		//return null if we run out of space in array
		return null;
	}
	
			//test extend
			public void testExtendUnit(char[] test) {
				System.err.println("Extended " + Arrays.toString(test) + " to " + Arrays.toString(extend(test)));
			}
			public void testExtend() {
				System.err.println("\nTesting extend()");
				//Can't extend
				testExtendUnit(new char[] {'A', 'B', 'B', 'C'});
				// Can extend
				//testExtendUnit(new char[] {0, 0, 0, });
			}
	
	
	
	
	
	//a method that accepts a partial solution 
	//returns another partial solution where most recent has been incremented
	//return null if most recent part had no more options
	public char[] next(char[] answer){
		//Since we don't know the coupon number and get them from input,
		//Use ascii code to get to the next character by adding one as int
		//then turning it back to char to have next char
		//temporary 
		int temp = 0;
		for(int i = 0; answer[i] != 0; i++){
			if(i < answer.length-1){
				if(answer[i] == 0){
					i--;
				}
				else if (i == numberOfCoupons){
					return null;
				}
				else {
					temp = (int)answer[i]+1;
					answer[i] = (char) temp;
				}
		
			}
		}
		return answer;
	}
		
			public void testNextUnit(char[] test) {
				System.err.println("Nexted " + Arrays.toString(test) + " to " + Arrays.toString(next(test)));
				}
			public void testNext() {
				System.err.println("\nTesting next()");
				// Can't next
				// Can next
				testNextUnit(new char[] {'A', 0, 0, 0});
				testNextUnit(new char[] {'A', 'B', 'B', 'A'});
				testNextUnit(new char[] {'A', 0, 0, 0});
			}
	
}