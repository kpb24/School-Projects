import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.Serializable;
import java.io.InputStream.*;
import java.io.OutputStream.*;
import java.util.*;
import java.util.ArrayList;
import javax.swing.*;


/**
 *	GameTimer
 *	timer object that keeps track of how much time a player has remaining to make a move
 *	Decrements by 1 second every second that it is the player's turn
 *	Adds 3 seconds when a player makes a move
 */
 
public class GameTimer implements Serializable {
	public JLabel label;
	int minutesRemaining;
	int secondsRemaining;

	/**
	* Constructor 
	*/
	public GameTimer(JLabel givenLabel) {
		this.label = givenLabel;
		this.minutesRemaining = 5;
		this.secondsRemaining = 0;
	}

	/**
	*	Method that decrements a GameTimer by one second
	*	@return boolean : false if the timer has hit 0 minutes and 0 seconds, true otherwise.
	*/
	public boolean decrement() {
	
		//If the timer has not run out		
		if ((minutesRemaining != 0) || (secondsRemaining != 0)) {
			ArrayList<Integer> adjustedTime = adjustTime(minutesRemaining,secondsRemaining);
			minutesRemaining = adjustedTime.get(0);
			secondsRemaining = adjustedTime.get(1);
			this.label.setText(String.valueOf(minutesRemaining) + " : " + String.format("%02d", secondsRemaining));
			return true;
		}
		//Otherwise
		else {
			this.label.setText("Time's up!");
			return false;
		}
	}

	/**
	* Helper method that handles logic for decrementing timer by 1 second
	*	@param int minutes : the number of minutes currently on the timer 
	*	@param int seconds : the number of seconds currently on the timer
	*	@return ArrayList<Integer> : ArrayList containing adjusted minute and second values 
	*/
	public ArrayList<Integer> adjustTime(int minutes, int seconds) {
		//If the value of seconds is greater than 0, simple decrement it
		if (seconds > 0){
			seconds--;
		}
		
		//Otherwise, the minutes need to be decremented by 1, and the seconds revert to 59
		else{
			minutes--;
			seconds = 59;
		}
		
		
		//Return these new values
		ArrayList<Integer> newTime = new ArrayList<Integer>();
		newTime.add(minutes);
		newTime.add(seconds);
		
		return newTime;
	}
 
 	/**
	* Method that adds 3 seconds to a player's timer
	* Called every time that player makes a move
	*/
	public void addTime() {
		
		secondsRemaining += 3;
		
		//Check for overflow
		if (secondsRemaining > 59){
			int difference = secondsRemaining - 60;
			minutesRemaining++;
			secondsRemaining = difference;
		}
		
		this.label.setText(String.valueOf(minutesRemaining) + " : " + String.format("%02d", secondsRemaining));
	}
	
	
	/**
	 *	implements Serializable interface's writeObject method
	 *	@param ObjectOutputStream out : the output stream to write to
	 *	@throws IOException
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		out.writeObject(label);
		out.writeInt(minutesRemaining);
		out.writeInt(secondsRemaining);
	}
	
	/**
	 *	implements Serializable interface's readObejct method
	 *	@param ObjectInputStream in : the input stream to read from
	 *	@throws IOException
	 *	@throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		label = (JLabel) in.readObject();
		minutesRemaining = in.readInt();
		secondsRemaining = in.readInt();
	}
	
}