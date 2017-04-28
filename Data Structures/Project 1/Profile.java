import java.io.Serializable;
import java.util.Arrays;
public class Profile implements ProfileInterface, Serializable{
	private String name;
	private String about;
	SimpleStack myStack;
	
	public Profile(){
		myStack = new SimpleStack();
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setAbout(String about){
		this.about = about;
	}
	
	public String getAbout(){
		return about;
	}
	
	public boolean follow(ProfileInterface other){
		myStack.add(other);
		return true;
	}
	
	public ProfileInterface unfollow(){
		System.out.println("You unfollowed " + myStack.remove());
		return new Profile();
	}
	
	public ProfileInterface[] following(int howMany){
		Object[] newE = new Object[howMany];
		Profile[] newP = new Profile[howMany];
		newE = myStack.topItems(howMany);
		for(int i = 0; i < howMany; i++){
			newP[i] = (Profile) newE[i];
		}
		return newP;
	}
	
	public ProfileInterface recommend(){
		Object[] newM = new Object[10];
		Profile[] newProfile = new Profile[10];
		newM = myStack.topItems(10);
		for(int i = 0; i < 10; i++){
			newProfile[i] = (Profile) newM[i];
		}
		if(newProfile.length == 0){
			return null;
		}
		return newProfile[0];
	}
	
	
	public String toString() {
		return name;
	}
	
}