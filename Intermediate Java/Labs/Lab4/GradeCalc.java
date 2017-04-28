class GradeCalculator{
public static void main(String[] args){
	double labsAndQuizzes = 0, finalGrade = 0, weightedLab = 0;
	int choice = 0;
	
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
//exams
System.out.println("Please enter your first midterm grade.");
myGrades.midterm1Grade = input.nextDouble();
System.out.println("Please enter your second midterm grade.");
myGrades.midterm2Grade = input.nextDouble();
System.out.println("Please enter your final exam grade.");
myGrades.finalExamGrade = input.nextDouble();

//projects
System.out.println("Please enter your first project grade.");
myGrades.project1Grade = input.nextDouble();
System.out.println("Please enter your second project grade.");
myGrades.project2Grade = input.nextDouble();
System.out.println("Please enter your third project grade.");
myGrades.project3Grade = input.nextDouble();
System.out.println("Please enter your fourth project grade.");
myGrades.project4Grade = input.nextDouble();
System.out.println("Please enter your fifth project grade.");
myGrades.project5Grade = input.nextDouble();

//call lab numbers
labsAndQuizzes = lowestLab();
//labs
weightedLab = labsAndQuizzes * 0.10;
System.out.println("the total lab grade is " + labsAndQuizzes);
System.out.println("the total weighted lab grade is " + weightedLab);
//participation
System.out.println("Please enter your participation grade.");
myGrades.participationGrade = input.nextDouble();
}





//re prompt for choice 1 or 2
System.out.println("\n2) Compute your overall grade");
System.out.println("3) Calculate the final exam score necessary");
int secondChoice = input.nextInt();


if (secondChoice == 2 && firstChoice == 1){
	// call finalScore to get the final grade
	finalGrade = finalScore(myGrades);
	System.out.printf("\nThe final grade is " + "%.2f%n", (finalGrade + weightedLab));
	}




 else if(secondChoice == 3 && firstChoice == 1){
	System.out.println("\nWhat score would you like to receive in the class?");
	double finalGradeChoice = input.nextDouble();
	
	double calcFinalExam = finalScore1(myGrades);
	//System.out.println("grade total " + newGradeTotal);
	//double newGradeTotal = calcFinalExam * .85;
	System.out.println("cal final " + calcFinalExam);
	
	double finalContribution = finalGradeChoice - (calcFinalExam + weightedLab);
	System.out.println("new grade total " + finalContribution);
	double finalClassGrade = finalContribution / .15;
	System.out.printf("\nYou need " + "%.2f", finalClassGrade);
	System.out.print(" on the final exam.");
	}
 }



static double finalScore(CS401 p){
//exams
double weightedMidterm1 = p.midterm1Grade * 0.15;
double weightedMidterm2 = p.midterm2Grade * 0.15;
double weightedFinalExam = p.finalExamGrade * 0.15;
//projects
double weightedProject1 = p.project1Grade * 0.08;
double weightedProject2 = p.project2Grade * 0.08;
double weightedProject3 = p.project3Grade * 0.08;
double weightedProject4 = p.project4Grade * 0.08;
double weightedProject5 = p.project5Grade * 0.08;
//participation
double weightedParticipation = p.participationGrade * 0.05;
//totals 
double totalProjects = weightedProject1 + weightedProject2 + weightedProject3 + weightedProject4 + weightedProject5;
double totalExamsAndPartic = weightedMidterm1 + weightedMidterm2 + weightedParticipation;

return weightedParticipation + weightedMidterm1 + weightedMidterm2 + weightedFinalExam + totalProjects;
}

static double finalScore1(CS401 p){
//exams
double weightedMidterm1 = p.midterm1Grade * 0.15;
double weightedMidterm2 = p.midterm2Grade * 0.15;
double weightedFinalExam = p.finalExamGrade * 0.15;
//projects
double weightedProject1 = p.project1Grade * 0.08;
double weightedProject2 = p.project2Grade * 0.08;
double weightedProject3 = p.project3Grade * 0.08;
double weightedProject4 = p.project4Grade * 0.08;
double weightedProject5 = p.project5Grade * 0.08;
//participation
double weightedParticipation = p.participationGrade * 0.05;
//totals 
double totalProjects = weightedProject1 + weightedProject2 + weightedProject3 + weightedProject4 + weightedProject5;

return weightedParticipation + weightedMidterm1 + weightedMidterm2 + totalProjects;
}

static double lowestLab(){
CS401 myGrades = new CS401();
java.util.Scanner input = new java.util.Scanner(System.in);
double labAndQuizGrade = 0.0;
double lowestLabGrade = 50.0;
double labTotalGrade = 0.0;
System.out.println("How many total lab and quiz grades are there?");
int labTotalNumber = input.nextInt();
// number of labs
int i = 1;
while (i <= labTotalNumber){
	System.out.println("Please enter your " + i + "th lab grade.");
	labAndQuizGrade = myGrades.labGrade = input.nextDouble();
	i += 1;
	labTotalGrade += labAndQuizGrade;
	if (labAndQuizGrade < lowestLabGrade){
		lowestLabGrade = labAndQuizGrade;
		}
}
return ((labTotalGrade * 2) - (lowestLabGrade * 2))/(labTotalNumber - 1);
}
// }

 }