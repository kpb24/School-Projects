//Kerilee Bookleiner
public class Lab3{
	public static void main(String[] args){
	int previousFlip = -1;
	//largest run of heads and tails
	int longestConsecHeads = 0;
	int longestConsecTails = 0;
	//current run of heads and tails
	int currentConsecHeads = 1;
	int currentConsecTails = 1;
	//total heads and tails
	int headCount = 0;
	int tailCount = 0;
	
	System.out.println("Coin Flip Simulator");
	System.out.print("How many flips would you like? ");
	java.util.Scanner input = new java.util.Scanner(System.in);
	int choice = input.nextInt();
	
	for (int i= 0; i < choice; i++){
	int currentFlip = flipCoin();
		if (currentFlip == previousFlip){
			if (currentFlip == 1){
				currentConsecHeads+= 1;
				if (currentConsecHeads > longestConsecHeads){
					longestConsecHeads = currentConsecHeads;
				}
			}
			else if (currentFlip == 2){
				currentConsecTails+= 1;
				if(currentConsecTails > longestConsecTails){
					longestConsecTails = currentConsecTails;
				}
			}
		}
		else{
			//set back to initial count of 1 if condition not met
			currentConsecHeads = 1;
			currentConsecTails = 1;
		}
		//update head and tails count
		if (currentFlip == 1){
			headCount += 1;
			}
		else if (currentFlip == 2){
			tailCount += 1;
		}
		previousFlip = currentFlip;
	}
	System.out.println("\nStatistics");
	System.out.println("--------------------------");
	System.out.println("Heads\t\t\t" + headCount);
	System.out.println("Tails\t\t\t" + tailCount);
	System.out.println("Longest Run of Heads\t" + longestConsecHeads);
	System.out.println("Longest Run of Tails\t" + longestConsecTails);
	}
	//flip coin
	public static int flipCoin(){
	return (int)(Math.random() * 2) + 1;
	}
}