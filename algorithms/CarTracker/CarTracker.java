import java.util.Scanner;
//Kerilee Bookleiner 

public class CarTracker{
	public static void main(String[] args){
	//using two priority queues; one for price and one for mileage
	PriorityQueue minimumPricePQ = new PriorityQueue();
	PriorityQueue minimumMileagePQ = new PriorityQueue();
	
	int choice = 1; //what the user chooses
	
	System.out.println("\nWelcome to Car Tracker \nPlease choose an option below");
	while(choice >= 1 && choice <= 7){
	System.out.println("\n1. Add a car");
	System.out.println("2. Update a car");
	System.out.println("3. Remove a specific car");
	System.out.println("4. Get lowest price car");
	System.out.println("5. Get lowest mileage car");
	System.out.println("6. Get lowest price car by make and model");
	System.out.println("7. Get lowest mileage car by make and model");
	
	Scanner input = new Scanner(System.in);
	choice = input.nextInt();
	
		//adding a car
		if(choice == 1){
			Car addCar = new Car(); //create new car
			boolean correct = false;
			
			while(correct == false){
				System.out.println("Enter the VIN: ");
				String inputVIN = input.next();
				boolean correctVIN = addCar.setVIN(inputVIN);
			
			 
			 	//the VIN must be 17 in length and can't contain any specified chars
				if(inputVIN.length() != 17 || correctVIN == false){
					if(correctVIN == false){
						System.out.println("VIN contains incorrect letters. Please Re-enter:");
					}
					if(inputVIN.length() != 17){
						System.out.println("The VIN needs to be 17 characters");
					}
					correct = false;
				}
				else{
					correct = true;
				}
			}
			
			System.out.println("Enter the make: ");
			String inputMake = input.next();
			addCar.setMake(inputMake); 
			
			System.out.println("Enter the model: ");
			String inputModel = input.next();
			addCar.setModel(inputModel); 
			
			System.out.println("Enter the price: ");
			float inputPrice = input.nextFloat();
			addCar.setPrice(inputPrice);
			
			System.out.println("Enter the mileage"); 
			float inputMileage = input.nextFloat();
			addCar.setMileage(inputMileage);
			
			System.out.println("Enter the color: ");
			String inputColor = input.next();
			addCar.setColor(inputColor); 
			
			//add car to the PriorityQueues and make sure it's not already in there
			boolean insertP = minimumMileagePQ.insertMileage(addCar);
			boolean insertM = minimumPricePQ.insertPrice(addCar);			
			
			if(insertP == false || insertM ==false){
				System.out.println("This car already exists");
			}
		}
	
	
		//update car by VIN
		if(choice == 2){
			System.out.println("Enter the VIN number of the car you want to update");
			String VINUpdateCar = input.next();
			System.out.println("Choose an option from the menu");
			System.out.println("1. Update price");
			System.out.println("2. Update mileage");
			System.out.println("3. Update color");
			int updateCarChoice = input.nextInt();
			
			//update price
			if(updateCarChoice == 1){
				System.out.println("Enter the new price");
				float newPrice = input.nextFloat();
				minimumPricePQ.updatePrice(VINUpdateCar, newPrice); //change the price for this VIN in the PricePQ
				minimumMileagePQ.updatePrice(VINUpdateCar, newPrice);
			}
			
			//update mileage
			else if(updateCarChoice == 2){
				System.out.println("Enter the new mileage");
				float newMileage = input.nextFloat();
				minimumPricePQ.updateMileage(VINUpdateCar, newMileage); //Change the mileage for this VIN in the MileagePQ
				minimumMileagePQ.updateMileage(VINUpdateCar, newMileage);
			} 
			
			//update color
			else if(updateCarChoice == 3){
				System.out.println("Enter the new color");
				String newColor = input.next();
				minimumPricePQ.updateColor(VINUpdateCar, newColor);
				minimumMileagePQ.updateColor(VINUpdateCar, newColor);
			}		
		}
		
		
		
		//remove specific car by VIN
		if(choice == 3){
			System.out.println("Enter the VIN number of the car you want to remove");
			String VINRemoveCar = input.next();
			minimumPricePQ.remove(VINRemoveCar);
			minimumMileagePQ.remove(VINRemoveCar);
		}
		
		
		
		//retrieve lowest price car
		if(choice == 4){
			System.out.println("\nThe lowest price car is:");
			System.out.println(minimumPricePQ.getLowestPriceCar());
		}
		
		
		
		//retrieve lowest mileage car
		if(choice == 5){
			System.out.println("\nThe lowest mileage car is:");
			System.out.println(minimumMileagePQ.getLowestMileageCar());
		}
		
		
				
		//retrieve lowest price car by make and model
		if(choice == 6){
			System.out.println("Enter the make of the car");
			String makePrice = input.next();
		
			System.out.println("Enter the model of the car");
			String modelPrice = input.next();
			
			System.out.println(minimumPricePQ.getLowestPriceCar(makePrice, modelPrice));
		}
	
	
	
		//retrieve lowest mileage car by make and model
		if(choice == 7){
			System.out.println("Enter the make of the car");
			String makeMileage = input.next();
		
			System.out.println("Enter the model of the car");
			String modelMileage = input.next();
			
			System.out.println(minimumMileagePQ.getLowestMileageCar(makeMileage, modelMileage));
		}
	}
	}
}