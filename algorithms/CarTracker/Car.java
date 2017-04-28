public class Car{
//Kerilee Bookleiner
private String VIN;
private String make;
private String model;
private float price;
private float mileage;
private String color;


public Car(){



}

//No I, i, O, o, Q, q
public boolean setVIN(String VINCar){
	VIN = VINCar;
	for(int i = 0; i < VIN.length(); i++){
		//VIN shouldn't have these characters
		if(VINCar.charAt(i) == 'I' || VINCar.charAt(i) == 'O' || VINCar.charAt(i) == 'Q'){
			return false;
		}
	
	}
	return true;
}

public String getVIN(){

	return VIN;
}

///////////////////////////////

public void setMake(String makeCar){
	make = makeCar;
}

public String getMake(){
	return make;
}

///////////////////////////////

public void setModel(String modelCar){
	model = modelCar;
}

public String getModel(){
	return model;
}

//////////////////////////////

public void setPrice(float priceCar){
	price = priceCar;
}

public float getPrice(){
	return price;
}

///////////////////////////////

public void setMileage(float mileageCar){
	mileage = mileageCar;
}


public float getMileage(){
	return mileage;
}

//////////////////////////////

public void setColor(String colorCar){
	color = colorCar;
}

public String getColor(){
	return color;
}
}