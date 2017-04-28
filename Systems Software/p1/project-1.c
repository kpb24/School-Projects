
/*
  project-1.c

  MY NAME: Kerilee Bookleiner
  MY PITT EMAIL: kpb24@pitt.edu
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main( int argc, char *argv[])
{
  FILE * inFile= NULL;
        short int current;
        short int previous = 0;
        short int num = 1;  //sample number
        short int full_wave_counter = 0;
        short int wave_sample = 0;
  short int max_value = 0;
        short int min_value = 0;
        short int count = 0;

  if (argc < 2)
    {
      fprintf(stderr,"\nMust specify a binary file on the command line. Please $
      exit(EXIT_FAILURE);
    }
  if  ((inFile = fopen(argv[1],"rb")) == NULL)
    {
      fprintf(stderr,"Can't open %s for input\n", argv[1] );
      exit(EXIT_FAILURE);
    }

  // YOUR CODE HERE - READ EACH VALUE FROM THE BINARY FILE ONE AT A TIME AND LO$
        fread(&previous, sizeof(previous), 1, inFile);
        max_value = previous;
 min_value = previous;
        while(1){

                fread(&current, sizeof(current), 1, inFile);
                if(feof(inFile)){
                        break;
                }

                //sin wave starts at zero or first number above it
                if((previous == 0 && current > previous) || (current > 0 && pre$
                        count = num;
                        if(full_wave_counter != 0){

                                printf("%hi\t%hi\t%hi\t%hi\t%hi\n", num, previo$
                                max_value = 0;
                                min_value = 0;
                        }
                        wave_sample = count;
 full_wave_counter++;
                }
                num++;  //end of wave
                previous = current;
                if(previous > max_value){
                        max_value = previous;
                }
                else if(previous < min_value){
                        min_value = previous;
                }
}



  fclose(inFile); /* after the read loop is done. close file */

  return EXIT_SUCCESS;  // this is just a zero value. In C a zero return often $
}
