import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.io.IOException;
//Kerilee Bookleiner
public class Test{
public static void main(String[] args){
	
	if (args.length >= 1 && args[0].equals("-t")) {
		FriendCoupon testFriend = new FriendCoupon();
		testFriend.testIsFullSolution();
		testFriend.testReject();
		testFriend.testExtend();
		testFriend.testNext();
	}
	
	
	else{
		int checkFriend = 0;
		int userNum = 0;
		//read in filename from command prompt
		String fileName = args[0];
		//read in number of coupons from command prompt
		String numberOfCoupons = args[1];
		int couponNum = Integer.parseInt(numberOfCoupons);
		
		//get length of row/column 2d array
		try {
		Scanner friends = new Scanner(new File(fileName));
		while(friends.hasNextLine()){
			String s = friends.nextLine();
			userNum++;
		}
		}
		catch(FileNotFoundException f){
			f.printStackTrace();
		}
		//userNum is the length of row and column
		//make the array a boolean to allow for 0 to be the end of partial
		boolean[][] friendGrid = new boolean[userNum][userNum];
		try {
			Scanner friends2 = new Scanner(new File(fileName));
			while(friends2.hasNextLine()){
				for (int i = 0; i < userNum; i++) {
				for (int j = 0; j < userNum; j++) {
				if(friends2.hasNextInt()){
					checkFriend = friends2.nextInt();
					if(checkFriend == 0){
						friendGrid[i][j] = false;
					}
					else if(checkFriend == 1){
						friendGrid[i][j] = true;
					}
					else{
						System.out.println("Invalid input");
					}
				}
			}
			}
			}
		}
		catch(FileNotFoundException f){
			f.printStackTrace();
		}
		
		//the solution array capacity is equal to the number of users (rows/columns)
		char[] answer = new char[userNum];

		//pass coupon number and number of users to constructor
		FriendCoupon couponDistribution = new FriendCoupon(couponNum, userNum, friendGrid);
		//call recursive method 
		couponDistribution.recurse(answer);
	}
	}
}