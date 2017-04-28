import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;
//Kerilee Bookleiner

class GradeCalculator{
	public static void main(String[] args){
		double labsAndQuizzes = 0, finalGrade = 0, weightedLab = 0, finalClassGrade = 0, finalContribution = 0;
		double labAndQuizGrade = 0.0, lowestLabGrade = 50.0, labTotalGrade = 0.0;
		int choice = 0, secondChoice = 0, labTotalNumber = 12;
		//file for output
		File overallGrade = new File("grades-2157.txt");
		CS401 myGrades = new CS401();
		//menu
		System.out.println("1) Enter in your grades");
		System.out.println("2) Compute your overall grade");
		System.out.println("3) Calculate the final exam score necessary");
		java.util.Scanner input = new java.util.Scanner(System.in);
		int firstChoice = input.nextInt();
		
			//repeat the menu if they didn't enter grades first
		while(firstChoice != 1){
			System.out.println("\nYou must enter your grades first!");
			System.out.println("1) Enter in your grades");
			System.out.println("2) Compute your overall grade");
			System.out.println("3) Calculate the final exam score necessary");
			firstChoice = input.nextInt();
			}
			
		if (firstChoice == 1){
		//get file name from command line
		try {
		Scanner inputGrades = new Scanner(new File(args[0]));
		myGrades.midterm1Grade = inputGrades.nextDouble();
		myGrades.midterm2Grade = inputGrades.nextDouble();
		myGrades.finalExamGrade = inputGrades.nextDouble();
		myGrades.project1Grade = inputGrades.nextDouble();
		myGrades.project2Grade = inputGrades.nextDouble();
		myGrades.project3Grade = inputGrades.nextDouble();
		myGrades.project4Grade = inputGrades.nextDouble();
		myGrades.project5Grade = inputGrades.nextDouble();
		for(int i = 0; i < labTotalNumber; i++){
			labAndQuizGrade = myGrades.labGrade = inputGrades.nextDouble();
			labTotalGrade += labAndQuizGrade;
			if (labAndQuizGrade < lowestLabGrade){
				lowestLabGrade = labAndQuizGrade;
				}
		}
		labsAndQuizzes = ((labTotalGrade * 2) - (lowestLabGrade * 2))/(labTotalNumber - 1);
		weightedLab = labsAndQuizzes * 0.10;
		myGrades.participationGrade = inputGrades.nextDouble();
		
		inputGrades.close();
		}
		catch(FileNotFoundException e){
			
		}
		//re prompt for choice 1 or 2
		System.out.println("\n2) Compute your overall grade");
		System.out.println("3) Calculate the final exam score necessary");
		secondChoice = input.nextInt();
		
		if (secondChoice == 2 && firstChoice == 1){
			// call finalScore to get the final grade
			finalGrade = finalScore(myGrades, secondChoice);
			System.out.printf("\nThe final grade is " + "%.2f%n", finalGrade + weightedLab);
			}
		
		else if(secondChoice == 3 && firstChoice == 1){
			System.out.println("\nWhat score would you like to receive in the class?");
			double finalGradeChoice = input.nextDouble();
			double calcFinalExam = finalScore(myGrades, secondChoice);
			finalContribution = finalGradeChoice - (calcFinalExam + weightedLab);
			finalClassGrade = finalContribution / .15;
			System.out.printf("\nYou need " + "%.2f", finalClassGrade);
			System.out.print(" on the final exam.");
			}
		 }
		 
		 //save output to file
		 try {
			PrintWriter newFile = new PrintWriter(overallGrade);
			if(secondChoice == 2){
			newFile.print(finalGrade + weightedLab); 
			newFile.print(" Final grade");
			}
			if(secondChoice == 3){
			newFile.print(finalClassGrade); 
			newFile.print(" Final exam grade needed");
			}
			newFile.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static double finalScore(CS401 p, int choice){
		double weightedMidterm1 = p.midterm1Grade * 0.15;
		double weightedMidterm2 = p.midterm2Grade * 0.15;
		double weightedFinalExam = p.finalExamGrade * 0.15;
		double weightedProject1 = p.project1Grade * 0.08;
		double weightedProject2 = p.project2Grade * 0.08;
		double weightedProject3 = p.project3Grade * 0.08;
		double weightedProject4 = p.project4Grade * 0.08;
		double weightedProject5 = p.project5Grade * 0.08;
		double weightedParticipation = p.participationGrade * 0.05;
		
		double totalProjects = weightedProject1 + weightedProject2 + weightedProject3 
								+ weightedProject4 + weightedProject5;
		double totalExamsAndPar = weightedMidterm1 + weightedMidterm2 + weightedParticipation;
		
		double total = 0;
		if (choice == 2){
			total = totalProjects + totalExamsAndPar + weightedFinalExam;
		}
		else if(choice == 3){
			total = totalProjects + totalExamsAndPar;
		}
		return total;
	}
}