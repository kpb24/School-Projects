//Kerilee Bookleiner
public class Lab10{
	
	public static void main(String[] args){
		long startTime = System.nanoTime();
			//2873
		factorialRecursive(10);
		long estimatedTime = System.nanoTime() - startTime;
		System.out.println("Factorial Recursive: " + estimatedTime);
		
		
		long startTimeFaI = System.nanoTime();
			//2873
		factorialIterative(10);
		long estimatedTimeFaI = System.nanoTime() - startTimeFaI;
		System.out.println("Factorial Iterative: " + estimatedTimeFaI);
		
		long startTimeFiR = System.nanoTime();
			//2873
		factorialRecursive(10);
		long estimatedTimeFiR = System.nanoTime() - startTimeFiR;
		System.out.println("Fibonacci Recursive: " + estimatedTimeFiR);
		
		long startTimeFiI = System.nanoTime();
			//2873
		factorialRecursive(10);
		long estimatedTimeFiI = System.nanoTime() - startTimeFiI;
		System.out.println("Fibonacci Iterative: " + estimatedTimeFiI);
		
		
		System.out.println("\nThe Fibonacci are almost always the same or off by 1 for both individually");
		System.out.println("The factorial are always different, but recursive is always much larger");
		System.out.println("I stopped waiting for the fibonacci recursive at 50 and just made it 10");
	}
	
	
	public static long factorialRecursive(long x){
		
		if(x == 1){
			return 1;
		}
		
		long s = x-1;
		long f = factorialRecursive(s);
		return f * x;
	}
	
	
	public static long factorialIterative(long x){
		
		int number = 1;
		for(int i = 1; i < x; i++){
			number *=i;
		}
		
		return number;
		
	}
	
	
	public static long fibRecursive(long x){
		
		
		if (x < 2){
			return 1;
		}
		return fibRecursive(x-2) + fibRecursive(x-1);
		
	}
	
	public static long fibIterative(long x){
		int fibNum1= 1;
		int fib = 1;
		int temp = 0;
		for(int i = 2;i <= x; i++){
			temp = fib + fibNum1;
			fibNum1 = fib;
			fib = temp;
					return temp;
		}
		return 0;
	}
	
	
	
	
	
	
	
	public static sort (int[] arr, int start, int end, int key){
		
		if( start < end){
			
			int middle = (high+low)/2;
			
			if(arr[middle] < key){
				return sort(arr, start, mid, key);
				
			}
			if (arr[middle] > key){
				
				return sort(arr, mid+1, end, key);
				
			}
			
			
			else{
				return middle;
			}
			
			
		}
		
		
		return -(start +1);
		
		
		
	}
	
	
	
	
}