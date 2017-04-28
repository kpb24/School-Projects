#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>
//Kerilee Bookleiner

int upperScores[6] = {-1,-1,-1,-1,-1,-1};
int lowerScores[7] = {-1,-1,-1,-1,-1,-1,-1}; //initialize all to -1
int rolls[5] = {-1,-1,-1,-1,-1}; //what the player rolls from the 5 dice
int scoreUpper = 0; //score for the upper section
int scoreLower = 0; //score for the lower section
int bonus = 0; //bonus points




//rolls the dice////////////////////////////////////////////
int rollDice(){
        int output;
        int i = open("/dev/dice", O_RDONLY);
        int n = read(i, &output, sizeof(int));
        return output;

}


//rerolls the dice the user chooses///////////////////////////
void rerollDice(){
        char userInput[10] = {0};
        char *token[10];
        int j = 0;
        int rollNum = 0;
        int diceToRoll = 0;

        printf("\nWhich dice do you want to reroll?");
        fgets(userInput, sizeof(userInput), stdin);

        token[0] = strtok(userInput, " ");
 while(token[j] != NULL){
                diceToRoll = atoi(token[j]) -1; //get the position
                if(diceToRoll >= 1 || diceToRoll <= 5){
                        rolls[diceToRoll] = -1;
                        j++;
                        token[j] = strtok(NULL, " ");
                }
        }

        for(j = 0; j < 5; j++){
                if(rolls[j] == -1){
                        rolls[j] = rollDice();
                }
        }
        // display the roll
        printf("\nYour reroll is:\n");
        for(j = 0; j < 5; j++)
        printf("%d ", rolls[j]);
        printf("\n");
}


//compare ints for qsort
int compare (const void * a, const void * b){
        return ( *(int*)a - *(int*)b );
}



int main(){
        int numberOfRolls = 0; //keeps track of how many turns they have used
        int playerRolls[13] = {0,0,0,0,0,0,0,0,0,0,0,0,0}; //player gets 13 rolls max
        int section = 0; //the sections they choose
        int sum = 0; //sum
        int k = 0;

        srand(time(NULL));
 printf("Welcome to Yahtzee\n");

        //user only gets 13 rolls max
        while(numberOfRolls <= 13){
                int i; //keeps track of how many dies are rolled
                printf("\nYour roll:\n");
                for(i = 0; i < 5; i++){
                        rolls[i] = rollDice();
                        printf("%d ", rolls[i]);

                }
                numberOfRolls++;
        printf("\n");
        rerollDice();
        printf("\n");
        rerollDice(); //ask twice to reroll
        qsort(rolls, 5, sizeof(int), compare);


        //assigning sections for scoring///////////////////////////////////
        printf("\nPlace dice into:\n");
        printf("1) Upper Section\n");
        printf("2) Lower Sections");
        printf("\nSelection? ");
        scanf(" %d", &section);

        //if the user chooses upper section(choice 1), they choose 1-6.
        //sum the dice based on the choice
        if(section == 1){
                printf("\nPlace dice into:\n");
                printf("1) Ones\n");
                printf("2) Twos\n");
                printf("3) Threes\n");
                printf("4) Fours\n");
                printf("5) Fives\n");
                printf("6) Sixes\n");
                printf("Selection? ");
                scanf(" %d", &section);
 for (k = 0; k < 5; k++)
                        if (rolls[k] == section)
                                sum += section;

                        upperScores[section - 1] = sum; //place sum in the correct place for section
                        scoreUpper += sum; //add sum to upper score

                        if(scoreUpper > 62){ //if the total upper score is over 62 they get bonus
                                bonus = 35;
                        }
        }
        else if(section == 2){
                printf("\nPlace dice into:\n");
                printf("1) Three of a kind\n");
                printf("2) Four of a kind\n");
                printf("3) Small Straight\n");
                printf("4) Large Straight\n");
                printf("5) Full House\n");
                printf("6) Yahtzee\n");
                printf("7) Chance\n");
                printf("Selection? ");
                scanf(" %d", &section);


                //3 or 4 of a kind... adds the frequency of each number rolled
                if(section == 1 || section == 2){
                        int frequency[8] = {0}; //set all frequencies to 0
                        int sum = 0;

                        for(i = 0; i < 5; i++){
                                int rolled_value = rolls[i];
                                frequency[rolled_value] += 1;
                                sum += rolled_value;
                        }
                        //three of a kind
                        if(section == 1){
                        for(i = 0; i < 8; i++)
                                if(frequency[i] == 3){
                                        lowerScores[0] = sum;
                                        scoreLower += sum; //score is total of all 5 dice
                                        break;
                                }
                        }
                        //Four of a kind
                        if(section == 2){
                        for(i = 0; i < 8; i++)
                                if(frequency[i] == 4){
                                        lowerScores[1] = sum;
                                        scoreLower += sum; //score is total of all 5 dice
                                        break;
                                }
                        }
                }
 //full house
                else if(section == 3){
                        printf("\n\nFull House");
                        if(rolls[0] == rolls[1] && (rolls[2] == rolls[3]) && (rolls[3] == rolls[4])){
                                scoreLower += 25;
                                lowerScores[2] = 25; //score is 25 added
                        }
                        else if((rolls[0] == rolls[1]) && (rolls[1] == rolls[2]) && rolls[3] == rolls[4]);{
                                scoreLower += 25;
                                lowerScores[2] = 25;
                        }
                } //end full house
 //small straight
                else if(section == 4){
                        if(rolls[0] < rolls[1] && rolls[1] < rolls[2] && rolls[2] < rolls[3]){
                                scoreLower += 30; //add 30 to score for lower section
                                lowerScores[3] = 30;
                        }
                }//end small straight

                //large straight
                if(section == 5){
                        if(rolls[1] < rolls[2] && rolls[2] < rolls[3] && rolls[3] < rolls[4]){
                                scoreLower += 40;
                                lowerScores[4] = 40;
                        }
                }//end large straight

                //Yahtzee!
                if(section == 6){
                        if(rolls[0] == rolls[1] && rolls[1] == rolls[2] && rolls[2] == rolls[3] && rolls[3] == rolls[4]){
                                scoreLower += 50;
                                lowerScores[5] = 50;
                        }
                }//end yahtzee

                //Chance
                if(section == 7){
                        sum = 0;
for(i = 0; i < 5; i++){
                                sum += rolls[i];
                        }
                        scoreLower += sum; //score is the sum of the dice
                        lowerScores[6] = sum;
                }//end chance

        }//end lower section
        //end calculating scores////////////////////////////////////////////////////////////////////////////////////////////////////////////


        //Determine score so far
        printf("\nYour score so far is: %d\n", scoreUpper + scoreLower + bonus); //prints the total score


        if(upperScores[0] > 0){
                printf("\n\nOnes: %d", upperScores[0]);
        }
        else{
                printf("\n\nOnes:  ");

        }

        if(upperScores[3] > 0){
                printf("\t\t\t\tFours:  %d", upperScores[3]);
        }
        else{
                printf("\t\t\t\tFours:");
        }

        if(upperScores[1] > 0){
                printf("\nTwos:  %d", upperScores[1]);
        }
        else{
                printf("\nTwos:  ");
        }

                if(upperScores[4] > 0){
                printf("\t\t\t\tFives:  %d", upperScores[4]);
        }
        else{
                printf("\t\t\t\tFives:");
        }

        if(upperScores[2] > 0){
                printf("\nThrees:  %d", upperScores[2]);
        }
        else{
                printf("\nThrees:  ");
        }

        if(upperScores[5] > 0){
                printf("\t\t\tSixes:  %d", upperScores[5]);
        }
        else{
                printf("\t\t\tSixes:");
        }

        printf("\nUpper Section Bonus: %d", bonus);


         if(lowerScores[0] > 0){
                printf("\nThree of a Kind:  %d", lowerScores[0]);
        }
        else{
                printf("\nThree of a Kind:  ");
        }

        if(lowerScores[1] > 0){
                printf("\t\tFour of a Kind:  %d", lowerScores[1]);
        }
        else{
                printf("\t\tFour of a Kind:");
        }

         if(lowerScores[2] > 0){
 if(lowerScores[2] > 0){
                printf("\nSmall Straight:  %d", lowerScores[2]);
        }
        else{
                printf("\nSmall Straight:  ");
        }

        if(lowerScores[3] > 0){
                printf("\t\tLarge Straight:  %d", lowerScores[3]);
        }
        else{
                printf("\t\tLarge Straight:");
        }

         if(lowerScores[4] > 0){
                printf("\nFull House:  %d", lowerScores[4]);
        }
        else{
                printf("\nFull House:  ");
        }

        if(lowerScores[5] > 0){
                printf("\t\t\tYahtzee:  %d", lowerScores[5]);
        }
        else{
                printf("\t\t\tYahtzee:");
        }

        if(lowerScores[6] > 0){
                printf("\nChange:  %d", lowerScores[6]);
        }
        else{
                printf("\nChance:");
        }

}//end while loop
printf("\n\nThank you for playing Yahtzee\n");
return 0;
}
