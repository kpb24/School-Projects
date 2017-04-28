import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
public class Client implements Serializable{
	//Kerilee Bookleiner
	public static void main (String[] args){
		int menuChoice = 0;
		String testFile = "profiles.bin";
		Client test = new Client();
		ArrayList<Profile> masterList = new ArrayList<Profile>();
		Scanner input = new Scanner(System.in);
		
		System.out.println("Welcome to Social Network!");
		while(menuChoice != 8 && menuChoice < 9){
		System.out.println("\n1) List Profiles");
		System.out.println("2) Create Profile");
		System.out.println("3) Show Profile");
		System.out.println("4) Edit Profile");
		System.out.println("5) Follow");
		System.out.println("6) Unfollow");
		System.out.println("7) Suggest a Follow");
		System.out.println("8) Quit");
		menuChoice = input.nextInt();
		
		try{
		@SuppressWarnings("unchecked")
		ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream (testFile));
		masterList = (ArrayList<Profile>)inputStream.readObject();
		inputStream.close();
		}
		catch(FileNotFoundException e) {
			System.err.println(testFile + " does not exist.");
		}
		catch(IOException e) {
			System.err.println("Error resuming from " + testFile);
		}
		catch(ClassNotFoundException e) {
			System.err.println("Error resuming from " + testFile);
		}
		
		
		//This program only lists items that were entered through "Create a Profile"
		if(menuChoice == 1){
			//If profiles do not show up when choice 1 is selected in menu that means
			//that no profiles have been entered and saved yet.
			System.out.println("\nHere are all current profiles: ");
			for (int i = 0 ; i < masterList.size(); i++){
				System.out.println((i+1) + " " + masterList.get(i)) ;
			}
		}
		
		else if(menuChoice == 2){
			//create new profile and add to front of list
			Profile allProfiles =new Profile();
			System.out.println("\nPlease enter your profile name: ");
			allProfiles.setName(input.next());
			String aboutMe = System.console().readLine("Please enter your about me: ");
			allProfiles.setAbout(aboutMe);
			masterList.add(0,allProfiles);
			try {
				ObjectOutputStream saveStream = new ObjectOutputStream(new FileOutputStream(testFile));
				saveStream.writeObject(masterList);
			}
			catch(IOException e) {
				System.err.println("Something went wrong saving to " + testFile);
				e.printStackTrace();
			}
		}
		
		else if(menuChoice == 3){
			//print name, about me, and up to 5 most recently followed profiles
			System.out.println("Please enter the number of the profile: ");
			int profileChoice5 = input.nextInt();
			System.out.println("\nName: " + masterList.get(profileChoice5-1).getName());
			System.out.println("About me: " + masterList.get(profileChoice5-1).getAbout());
			System.out.println("Most Recent Follows: ");
			masterList.get(profileChoice5 - 1).following(5);
		}
		
		else if(menuChoice == 4){
			//set name and about me
			System.out.println("Please enter the number of the profile you want to edit ");
			int profileChoice = input.nextInt() - 1;
			System.out.println("Please enter your profile name: ");
			String newName = input.next();
			String newAboutMe = System.console().readLine("Please enter your about me: ");
			masterList.get(profileChoice).setName(newName);
			masterList.get(profileChoice).setAbout(newAboutMe);
			try {
				ObjectOutputStream saveStream = new ObjectOutputStream(new FileOutputStream(testFile));
				saveStream.writeObject(masterList);
			}
			catch(IOException e) {
				System.err.println("Something went wrong saving to " + testFile);
				e.printStackTrace();
			}
		}
		
		else if(menuChoice == 5){
			//have one profile follow another
			System.out.println("What profile do you want to start with? Enter profile number.");
			int profileChoice2 = input.nextInt();
			System.out.println("Who would you like them to follow? Enter profile number.");
			int profileChoice3 = input.nextInt();
			masterList.get(profileChoice2 - 1).follow(masterList.get(profileChoice3 - 1));
			try {
				ObjectOutputStream saveStream = new ObjectOutputStream(new FileOutputStream(testFile));
				saveStream.writeObject(masterList);
			}
			catch(IOException e) {
				System.err.println("Something went wrong saving to " + testFile);
				e.printStackTrace();
			}
		}
		
		else if(menuChoice == 6){
			//choose profile from list and have it unfollow most recently added profile
			System.out.println("Enter the number of a profile. The most recent profile will be removed.");
			int profileChoice4 = input.nextInt();
			masterList.get(profileChoice4 - 1).unfollow();
			try {
				ObjectOutputStream saveStream = new ObjectOutputStream(new FileOutputStream(testFile));
				saveStream.writeObject(masterList);
			}
			catch(IOException e) {
				System.err.println("Something went wrong saving to " + testFile);
				e.printStackTrace();
			}
		}
		
		else if(menuChoice == 7){
			System.out.println("Enter the number of the profile you want a suggestion for: ");
			int profileChoice6 = input.nextInt();
			
			Object[] newM = new Object[10];
			newM = masterList.get(profileChoice6 - 1).following(10);
			Profile[] newProfiles = new Profile[10];
			//Adds the first ten follows from this profile's stack to an arraylist.
			ArrayList<Profile> newProfiles1 = new ArrayList<Profile>();
			//For each followed profile in the arraylist, look at each of their follows.
			for(int i = 0; i < 10; i++){
				newProfiles[i] = (Profile) newM[i];
				newProfiles1.add(newProfiles[i]);
				if(newProfiles1.get(i) != null){
					//Once a friend is found in the search that follows someone, it will return that profile
					//as a suggestion. Does not check to see if chosen profile already follows suggested.
					System.out.println("Your friend " + newProfiles1.get(i) + " follows "+ newProfiles1.get(i).recommend());
					break;
					}
			}
			
		}
	
		
		else if(menuChoice == 8){
			//quit
			try {
				ObjectOutputStream saveStream = new ObjectOutputStream(new FileOutputStream(testFile));
				saveStream.writeObject(masterList);
			}
			catch(IOException e) {
				System.err.println("Something went wrong saving to " + testFile);
				e.printStackTrace();
			}
			break;
		}
	}
}
}