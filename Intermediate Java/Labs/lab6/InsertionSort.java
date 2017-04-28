//inner loop looks backwards to compare against already sorted values

public class InsertionSort{
	static void insertionSort(int[] arr){
		for(int i = 1; i <= (arr.length-1); i++){
			int temp = arr[i];
			int j = i;

			while(j > 0 && arr[j - 1] > temp){
				arr[j] = arr[j-1];
				j--;
			}
			arr[j] = temp;

		}
					for(int h = 0; h <= (arr.length-1); h++){
		System.out.println(arr[h]);
	}

}
}