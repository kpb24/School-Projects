public class RockPaperScissors{
	public static void main(String[] args)
	{
	System.out.println("Welcome to Rock, Paper, Scissors");
	System.out.println("What is your choice?");
	System.out.println("1.) Rock");
	System.out.println("2.) Paper");
	System.out.println("3.) Scissors");
	//human choice
	System.out.print("What is your choice? ");
	java.util.Scanner input = new java.util.Scanner(System.in);
	int choice = input.nextInt();
	//computer choice
	int computerChoice = (int)(Math.random() * 3) + 1;
	//determine winner
	if (choice == computerChoice)
	{
	System.out.println("It is a tie");
	}
	else if (choice == 1)
	{
		if (computerChoice == 2)
		{
		System.out.println("The computer chooses Paper. You lose!");
		}
		else if (computerChoice == 3)
		{
		System.out.println("The computer chooses Scissors. You win!");
		}
	}
	else if (choice == 2)
	{
		if (computerChoice == 1)
		{
		System.out.println("The computer chooses Rock. You win!");
		}
		else if (computerChoice == 3)
		{
		System.out.println("The computer chooses Scissors. You lose!");
		}
	}
	else if (choice == 3)
	{
		if (computerChoice == 1)
		{
		System.out.println("The computer chooses Rock. You lose!");
		}
		else if (computerChoice == 2)
		{
		System.out.println("The computer chooses Paper. You win!");
		}
	}

}
}
	
