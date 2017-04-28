//Kerilee Bookleiner
public class CreateArray{
	public static void main(String[] args){
		
		InsertionSort sortNumbers = new InsertionSort();
		int[] newArray = new int[args.length];

		for(int i = 0; i <= (args.length - 1); i++){
			int a = Integer.parseInt(args[i]);
			newArray[i] = a;
		}
		sortNumbers.insertionSort(newArray);
}
}