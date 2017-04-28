import java.lang.Math;
import java.util.ArrayList;
//Kerilee Bookleiner
public class PriorityQueue{
	private int N = 0;
	public Car[] minHeap;
	public ArrayList<String> positions;  //indirection


	public PriorityQueue(){
		minHeap = new Car[200];
		positions = new ArrayList<>(); 
	}



////////////////////////////////////////////// choice 1

	//inserts the newly added car to the arraylist -- Mileage PQ
	public boolean insertMileage(Car v){
		//make sure this car isn't already in here
		for(int a = 0; a < N;a++){
			if(v.getVIN().equals(positions.get(a))){
				return false;
			}
		}
		positions.add(v.getVIN());
		minHeap[N] = v;
		swim(N, "mileage");
		N++;
		return true; //successful
	}

	//inserts the newly added car to the arraylist -- Price PQ
	public boolean insertPrice(Car v){
		//make sure this car isn't already in here
		for(int a = 1; a < N;a++){
			if(v.getVIN().equals(positions.get(a))){
				return false;
			}
		}
		positions.add(v.getVIN());
		minHeap[N] = v;
		swim(N, "price");
		N++;
		return true; //successful
	}




//////////////////////////////////////////////choice 2

	public boolean updatePrice(String VIN, float price){
		int indexVIN = positions.indexOf(VIN);  //get index of the car by VIN, returns -1 if not in it
		if(indexVIN == -1){
			return false; //not in the arraylist
		}
		else{
			minHeap[indexVIN].setPrice(price);
			//reheapify by sink then swim
			sink(indexVIN, "price");
			indexVIN = positions.indexOf(VIN); //make sure index is updated
			swim(indexVIN, "price");
		}
		return true; //successfully updated
	}


	public boolean updateMileage(String VIN, float mileage){
		int indexVIN = positions.indexOf(VIN);  //get index of the car by VIN, returns -1 if not in it
		if(indexVIN == -1){
			return false; //not in the arraylist
		}
		else{
			minHeap[indexVIN].setMileage(mileage);
			//reheapify by sink then swim
			sink(indexVIN, "mileage");
			indexVIN = positions.indexOf(VIN); //make sure index is updated
			swim(indexVIN, "mileage");
		}
		return true; //successfully updated
	}
	
	
	public boolean updateColor(String VIN, String color){
		int indexVIN = positions.indexOf(VIN);  //get index of the car by VIN, returns -1 if not in it
		if(indexVIN == -1){
			return false; //not in the arraylist
		}
		else{
			minHeap[indexVIN].setColor(color); //don't need to keep track of "min" for colors
		}
		return true; //successfully updated
	}



///////////////////////////////////////////choice 3

	public Car remove(String VIN){
		int k = positions.indexOf(VIN);
		Car t = minHeap[k]; //temporary
		
		//swap, don't use exch because it changes positions for sink/swim
		minHeap[k] = minHeap[N-1];
		minHeap[N-1] = t; 
		minHeap[N-1] = null;
		
		positions.remove(VIN); //remove it by VIN
		N--; //decrement size
		sink(k, "mileage");
		sink(k, "price");
		
		return t;
	}
	

////////////////////////////////////////choices 4 and 5

	public String getLowestPriceCar(){
		if(isEmpty() == true){
			return "No cars have been added";
		}
		return "VIN: " + minHeap[0].getVIN() + "\n" + minHeap[0].getColor() + " " + minHeap[0].getMake() + " " 
				+ minHeap[0].getModel() + "\nPriced at $" + minHeap[0].getPrice() + " with " + minHeap[0].getMileage() +" miles";
	}


	public String getLowestMileageCar(){
		if(isEmpty() == true){
			return "No cars have been added";
		}
		return "VIN: " + minHeap[0].getVIN() + "\n" + minHeap[0].getColor() + " " + minHeap[0].getMake() + " " 
				+ minHeap[0].getModel() + "\nPriced at $" + minHeap[0].getPrice() + " with " + minHeap[0].getMileage() +" miles";
	}




	/////////////////////////////////////////////choices 6 and 7 
	public String getLowestPriceCar(String make, String model){
		int k = 0;
		int t = 0;
		Car[] array = new Car[200]; //use this to go through 
		
		if(isEmpty() == true){
			return "No cars have been added";
		}
		
		while(minHeap[k] != null){
			String getMakeCar = minHeap[k].getMake();
			String getModelCar = minHeap[k].getModel();
			if(getMakeCar.equalsIgnoreCase(make)){
				if(getModelCar.equalsIgnoreCase(model)){
					array[t] = minHeap[k]; //adds them in order that they are in the heap so they will be in order in array
					t++;
				}
			}
			k++;
		}
		
		//the array will be empty if the make and model don't exist
		if(array[0] == null){
			return "Make and model don't exist";
		
		}
		
		Car car = array[0]; //holds lowest price car for make and model
			
		return "VIN: " + car.getVIN() + "\n" + car.getColor() + " " + car.getMake() + " " 
				+ car.getModel() + "\nPriced at $" + car.getPrice() + " with " + car.getMileage() + " miles";
	}
	
	
	public String getLowestMileageCar(String make, String model){
		int k = 0;
		int t = 0;
		Car[] array = new Car[200]; //use this to go through 
		
		if(isEmpty() == true){
			return "No cars have been added";
		}
		
		while(minHeap[k] != null){
			String getMakeCar = minHeap[k].getMake();
			String getModelCar = minHeap[k].getModel();
		
			if(getMakeCar.equalsIgnoreCase(make)){
				if(getModelCar.equalsIgnoreCase(model)){
					array[t] = minHeap[k];
					t++;
				}
			}
			k++;
		}
		
		if(array[0] == null){
			return "Make and model don't exist";
		}
		
		Car car = array[0]; //lowest mileage will be here since adds in order of priority queue
			
		return "VIN: " + car.getVIN() + "\n" + car.getColor() + " " + car.getMake() + " " 
				+ car.getModel() + "\nPriced at $" + car.getPrice() + " with " + car.getMileage() +" miles";
	}
	
	

	//parent(i) = ⌊(i - 1) / 2⌋
	//left_child(i) = 2i + 1
	//right_child(i) = 2i + 2

	//reheapify (swim)
	public void swim(int k, String swimType){
		
		if(swimType.equals("mileage")){
			//must use floor for parent value (from slides)
			while(k > 0 && greater((int)Math.floor((k-1)/2), k, "mileage")){
				exch((k-1)/2, k);
				k = (k-1)/2;
			}
		}
		else if(swimType.equals("price")){
			while(k > 0 && greater((int)Math.floor((k-1)/2), k, "price")){
				exch((k-1)/2, k);
				k = (k-1)/2;
			}
		}
	}



	//reheapify (sink)
	public void sink(int k, String sinkType){
		while((2 * k) + 1 < N){
			int j = (2 * k) +1; //left child
			
			if(sinkType.equals("mileage")){
				if(minHeap[j+1] != null){
					if( j < N && greater(k, j+1, "mileage")){
						j++;
					}
				}
				if(!greater(k, j, "mileage")){
					break;
				}
			}
			else if(sinkType.equals("price")){
				if(minHeap[j+1] != null){
					if( j < N && greater(k, j+1, "price")){
						j++;
					}
				}
				if(!greater(k, j, "price")){
					break;
				}
			}
			exch(k,j);
			k = j;
		}
	}




	//helper for determining if greater than
	private boolean greater(int i, int j, String type){
		if(type.equals("price")){
			return (minHeap[i].getPrice() > minHeap[j].getPrice());
		}
		else if(type.equals("mileage")){
			return (minHeap[i].getMileage() > minHeap[j].getMileage());
		}
		else{
			return false;
		}
	}
	
	//helper for swaps
	private void exch(int i, int j){
		Car t = minHeap[i];
		minHeap[i] = minHeap[j];
		//set to the VIN in indirection arraylist
		positions.set(i, minHeap[j].getVIN());
		minHeap[j] = t;
		//set j
		positions.set(j, t.getVIN());
	}

	public boolean isEmpty(){
		return N == 0;
	}

	public int size(){
		return N;
	}
}