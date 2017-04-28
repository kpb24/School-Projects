public class RollDiceGame{
	static int rollDice(number){
		return (int) (Math.random() * number) + 1;
	}
	
	public static void main(String[] args){
		System.out.println("How many dies? ");
		java.util.Scanner input = new java.util.Scanner(System.in);
		int dieNumber = input.nextInt();
		System.out.println("How many sides?");
		int sideNumber = input.nextInt();
		
		int i = 0;
		int result = 0;
		while (i <= dieNumber){
			result = rollDice(sideNumber);
			i++;
		}
		
		System.out.println("Rolls: ");
		System.out.println("\t" + result);
	}
}