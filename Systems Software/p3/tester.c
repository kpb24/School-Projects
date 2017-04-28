#include <stdio.h>
#include <unistd.h>
#include "mymalloc.h"
//Kerilee Bookleiner

int main(){
        printf("\n\n\nTest Number One:\n");
        int *malloc1 = my_nextfit_malloc(20);
        int *malloc2 = my_nextfit_malloc(50);
        int *malloc3 = my_nextfit_malloc(20);
        printf("malloc1 %d", malloc1);
        printf("\nmalloc2 %d", malloc2);
        printf("\nmalloc3 %d\n", malloc3);
        print_malloc();
        printf("\n\nFreeing middle//////////");
        my_free(malloc2);
        print_malloc();



        printf("\n\n\nTest Number Two:\n");
        int *malloc4 = my_nextfit_malloc(80);
        int *malloc5 = my_nextfit_malloc(40);
        int *malloc6 = my_nextfit_malloc(20);
        printf("malloc4 %d", malloc4);
        printf("\nmalloc5 %d", malloc5);
        printf("\nmalloc6 %d\n", malloc6);
        print_malloc();



        printf("\n\n\nTest Number Three:\n");
        int *malloc7 = my_nextfit_malloc(25);
        int *malloc8 = my_nextfit_malloc(25);
        int *malloc9 = my_nextfit_malloc(25);
        int *malloc10 = my_nextfit_malloc(25);
        int *malloc11 = my_nextfit_malloc(25);
        int *malloc12 = my_nextfit_malloc(25);
        print_malloc();
}