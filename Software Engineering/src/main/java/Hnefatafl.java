
/**
 *	Hnefatafl
 *	CS 1530 Spring 2017
 *	Group Cool People
 *	@author Keri Bookleiner <kpb24@pitt.edu>
 *	@author Vivien Chang <vsc5@pitt.edu>
 *	@author Maxwell Garber <mbg21@pitt.edu>
 *	@author Jennifer Zysk <jez20@pitt.edu>
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.ArrayList;
import javax.swing.*;


/**
 *	Hnefatafl
 *	point of entry for compilation and execution
 *	no command line arguments, no defined error codes
 */
public class Hnefatafl extends JFrame {

	/**
	 *	main: starting point of execution
	 *	@param String[] args : disused command line argument
	 */
	public static void main(String[] args) {
		JFrame frame = new GameBoardView();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
  
}
