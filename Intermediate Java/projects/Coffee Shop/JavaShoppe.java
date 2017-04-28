//Kerilee Bookleiner
public class Shoppe{
	public static void main(String[] args){
		int menuChoice = 0, coffeeSize = 0, monCoffeeAmount = 0, muffinAmount = 0;
		double priceOne1 = 1.00, priceOne2 = 1.75;
		double priceOne3 = 2.50, price2 = 1.75, price3 = 2.00; 
		double price4 = 2.50, price5 = 2.00, price6 = 4.00;
		int smCoffeeQuantity = 0, medCoffeeQuantity = 0; 
		int monCoffeeQuantity = 0, muffinQuantity = 0, teaQuantity = 0;
		int hotChocQuantity = 0, waterQuantity = 0, orgoQuantity = 0;
		double newPriceOne1 = 0, newPriceOne2 = 0;
		double newPriceOne3 = 0, newPrice2 = 0, newPrice3 = 0; 
		double newPrice4 = 0, newPrice5 = 0, newPrice6 = 0;
		//prompt customer until they enter 7
		while (menuChoice != 7){
		//present menu to customer and prompt for item and re-prompt if invalid
			System.out.println("The Java Shoppe Menu:");
			System.out.println("\t 1.) Brewed Coffee");
			System.out.println("\t 2.) Chocolate Chip Muffin");
			System.out.println("\t 3.) Pot of Tea");
			System.out.println("\t 4.) Hot Chocolate");
			System.out.println("\t 5.) Water");
			System.out.println("\t 6.) Organic Water");
			System.out.println("\t 7.) Check out/Print receipt");
			System.out.print("Which item is being purchased? ");
			java.util.Scanner inputChoice = new java.util.Scanner(System.in);
			menuChoice = inputChoice.nextInt();
			while(menuChoice > 7 || menuChoice < 1){
				System.out.println("The Java Shoppe Menu:");
				System.out.println("\t 1.) Brewed Coffee");
				System.out.println("\t 2.) Chocolate Chip Muffin");
				System.out.println("\t 3.) Pot of Tea");
				System.out.println("\t 4.) Hot Chocolate");
				System.out.println("\t 5.) Water");
				System.out.println("\t 6.) Organic Water");
				System.out.println("\t 7.) Check out/Print receipt");
				System.out.print("Which item is being purchased? ");
				menuChoice = inputChoice.nextInt();
				}
		//set menu prices and re-prompt coffee menu if invalid
		if (menuChoice == 1){
			System.out.println("Coffee sizes: ");
			System.out.println("\t 1.) Small");
			System.out.println("\t 2.) Medium");
			System.out.println("\t 3.) Mondo");
			System.out.print("What size? ");
			java.util.Scanner inputSize = new java.util.Scanner(System.in);
			coffeeSize = inputSize.nextInt();
			while(coffeeSize > 3 || coffeeSize < 1){
				System.out.println("Coffee sizes: ");
				System.out.println("\t 1.) Small");
				System.out.println("\t 2.) Medium");
				System.out.println("\t 3.) Mondo");
				System.out.print("What size? ");
				coffeeSize = inputSize.nextInt();
				}
			if (coffeeSize == 1){
				System.out.print("How many? ");
				java.util.Scanner InputQuantity = new java.util.Scanner(System.in);
				smCoffeeQuantity = InputQuantity.nextInt();
				newPriceOne1 = priceOne1 * smCoffeeQuantity;
				}
			else if (coffeeSize == 2){
				System.out.print("How many? ");
				java.util.Scanner InputQuantity = new java.util.Scanner(System.in);
				medCoffeeQuantity = InputQuantity.nextInt();
				newPriceOne2 = priceOne2 * medCoffeeQuantity;
				}
			else if (coffeeSize == 3){
				System.out.print("How many? ");
				java.util.Scanner InputQuantity = new java.util.Scanner(System.in);
				monCoffeeQuantity = InputQuantity.nextInt();
				newPriceOne3 = priceOne3 * monCoffeeQuantity;
				}
		}
		else if (menuChoice == 2){
			System.out.print("How many? ");
			java.util.Scanner InputQuantity = new java.util.Scanner(System.in);
			muffinQuantity = InputQuantity.nextInt();
			newPrice2 = price2 * muffinQuantity;
			}
		else if (menuChoice == 3){
			System.out.print("How many? ");
			java.util.Scanner InputQuantity = new java.util.Scanner(System.in);
			teaQuantity = InputQuantity.nextInt();
			newPrice3 = price3 * teaQuantity;
			}
		else if (menuChoice == 4){
			System.out.print("How many? ");
			java.util.Scanner InputQuantity = new java.util.Scanner(System.in);
			hotChocQuantity = InputQuantity.nextInt();
			newPrice4 = price4 * hotChocQuantity;
			}
		else if (menuChoice == 5){
			System.out.print("How many? ");
			java.util.Scanner InputQuantity = new java.util.Scanner(System.in);
			waterQuantity = InputQuantity.nextInt();
			newPrice5 = price5 * waterQuantity;
			}
		else if (menuChoice == 6){
			System.out.print("How many? ");
			java.util.Scanner InputQuantity = new java.util.Scanner(System.in);
			orgoQuantity = InputQuantity.nextInt();
			newPrice6 = price6 * orgoQuantity;
			}
		}
		//print out items with quantity and new price on receipt
		if (menuChoice == 7){
			if (smCoffeeQuantity != 0){
				System.out.printf(smCoffeeQuantity + "\t" + "Small Coffee\t\t"
								+"\t" + "$%,.2f%n", newPriceOne1);
				}
			if (medCoffeeQuantity != 0){
				System.out.printf(medCoffeeQuantity + "\tMedium Coffee\t\t"
								+"\t" + "$%,.2f%n", newPriceOne2);
				}
			if (monCoffeeQuantity != 0){
				System.out.printf(monCoffeeQuantity + "\tMondo Coffee\t\t"
								+"\t" + "$%,.2f%n", newPriceOne3);
				}
			if (muffinQuantity != 0){
				System.out.printf(muffinQuantity + "\tChocolate Chip Muffin\t"
								+"\t" + "$%,.2f%n", newPrice2);
				}
			if (teaQuantity != 0){
				System.out.printf(teaQuantity + "\tPot of Tea\t\t"
								+"\t" + "$%,.2f%n", newPrice3);
				}
			if (hotChocQuantity != 0){
				System.out.printf(hotChocQuantity + "\tHot Chocolate\t\t"
								+"\t" + "$%,.2f%n", newPrice4);
				}
			if (waterQuantity != 0){
				System.out.printf(waterQuantity + "\tWater\t\t\t"
								+"\t" + "$%,.2f%n", newPrice5);
				}
			if (orgoQuantity != 0){
				System.out.printf(orgoQuantity + "\tOrganic Water\t\t"
								+"\t" + "$%,.2f%n", newPrice6);
			}
		}
		System.out.println("-------------------------"
							+"-------------------------");
		double subtotal = newPriceOne1 + newPriceOne2 + newPriceOne3 + newPrice2 
						  + newPrice3 + newPrice4 + newPrice5 + newPrice6;
		//determine discounts
		if (monCoffeeQuantity != 0 && muffinQuantity != 0){
			while(monCoffeeAmount < monCoffeeQuantity && muffinAmount < muffinQuantity){
				double muffinDiscount = 0.75;
				subtotal -= muffinDiscount;
				++monCoffeeAmount;
				++muffinAmount;
				System.out.println("\tMondo Muffin Discount\t\t" + "$-" + muffinDiscount);
			}
		}
		if (orgoQuantity != 0){
			double freeBook = 0.00;
			System.out.printf("\tFree chemistry textbook\t\t" + "$%,.2f%n", freeBook);
		}
		if((monCoffeeQuantity != 0 && muffinQuantity != 0) || orgoQuantity != 0){
			System.out.println("-------------------------"
								+"-------------------------");
		}
		//print tax, totals, and change on receipt
		System.out.printf("\tSubtotal:\t\t\t" + "$%,.2f%n", subtotal);
		double javaTax = subtotal * 0.05;
		System.out.printf("\t5%% Java Tax:\t\t\t" + "$%,.2f%n", javaTax);
		System.out.println("-------------------------"
							+"-------------------------");
		double total = subtotal + javaTax;
		System.out.printf("\tTotal:\t\t\t\t" + "$%,.2f%n", total);
		System.out.print("Customer paid? $");
		java.util.Scanner inputSize = new java.util.Scanner(System.in);
		double customerPaid = inputSize.nextDouble();
		double change = customerPaid - total;
		System.out.printf("Their change is " + "$%,.2f", change);
		
	}
}
