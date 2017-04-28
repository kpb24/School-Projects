public class Beverage extends MenuItem{
	public static final String LARGE = "Large";
	public static final String SMALL = "Small";
	private String size;
	
	public Beverage(String name, String size, double price){
		super(name, price);
		if(size == LARGE){
			this.size = LARGE;
		}
		else if(size == SMALL){
			this.size = SMALL;
		}
	}
	
	public String toString(){
		return size + " " + super.toString();
	}
}