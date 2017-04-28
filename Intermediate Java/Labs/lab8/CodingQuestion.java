import java.util.ArrayList;
//Kerilee Bookleiner
class CodingQuestion{
	public static void main (String[] args){
		ArrayList <MenuItem> items = new ArrayList<>();
		items.add(new Beverage("Hot Coffee", Beverage.LARGE, 2.25));
		items.add(new Food("Muffin", 1.15));
		items.add(new Beverage("Bottled Water", Beverage.SMALL, 2.95));
		items.add(new Food("Black and White Cookie", 1.50));
		
		
		
		for(MenuItem g: items){
			System.out.printf("Item: " + g + ", Cost: $%,.2f%n", + g.getPrice());
		}
		
	}
}