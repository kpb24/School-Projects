
/*
  txt2bin.c

  MY NAME: Kerilee Bookleiner
  MY PITT EMAIL: kpb24@pitt.edu
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>


int main( int argc, char *argv[] )
{
        FILE* txtFile, *binFile;
        short int shortVal;



        if (argc<3)
        {
                printf("usage: %s  <infilename> <outfilename> (i.e. ./txt2bin.e$
                return EXIT_FAILURE;
        }

        txtFile = fopen( argv[1], "rt" );
        if (txtFile == NULL )
        {
			printf("Can't open %s for input. Program halting\n",argv[1] );
                return EXIT_FAILURE;
        }

        binFile = fopen( argv[2], "wb" );
        if (binFile == NULL )
        {
                printf("Can't open %s for output. Program halting\n",argv[2] );
                return EXIT_FAILURE;
 }


        while(1){
                fscanf(txtFile,"%hi", &shortVal);
                if(feof(txtFile)){
                        break;
                }
                fwrite(&shortVal, sizeof(shortVal), 1, binFile);
 }



        fclose( txtFile );
        fclose( binFile );

/*      freopen("binFile", "rb", binFile);

                fscanf(binFile,"%hi", &shortVal);       */


        binFile = fopen(argv[2],"rb");

        while(1){
                fread(&shortVal, sizeof(shortVal), 1,binFile);
                if(feof(binFile)){
                        break;
  }
                printf("%hi\n", shortVal);
        }

        return EXIT_SUCCESS;  // 0 value return means no errors
}
