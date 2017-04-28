/**
 * University of Pittsburgh
 * Department of Computer Science
 * CS1645: Introduction to HPC Systems
 * Instructor Bryan Mills, PhD
 * Student: Kerilee Bookleiner
 * Implement Pthreads version of Strassen algorithm for matrix multiplication.
 */

#include "timer.h"
#include "io.h"
#include <pthread.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <math.h>
// Make these globals so threads can operate on them. You will need to
// add additional matrixes for all the M and C values in the Strassen
// algorithms.
int **A;
int **B;
int **C;
// Reference matrix, call simpleMM to populate.
int **R;

// Stupid simple Matrix Multiplication, meant as example.
void simpleMM(int N) {
  for (int i=0; i<N; i++) {
    for (int j=0; j<N; j++) {
      for (int k=0; k<N; k++) {
	R[i][j] += A[i][k] * B[k][j];
      }
    }
  }
}

int **M1,**M2,**M3,**M4,**M5,**M6,**M7;










// WRITE YOUR CODE HERE, you will need to also add functions for each
// of the sub-matrixes you will need to calculate but you can create your
// threads in this fucntion.
int **allocMatrix(int size);

//M1 = (A1,1 + A2,2)(B1,1 + B2,2)
void *calcM1(void *m){
	int n = *((int*)m);
	int **sum_a = allocMatrix(n);
	int **sum_b = allocMatrix(n);
	for(int i = 0; i < n; i++){
		for(int j = 0; j < n; j++){
			sum_a[i][j] = A[i][j] + A[i + n][j + n];
			sum_b[i][j] = B[i][j] + B[i + n][j + n];
			M1[i][j] = 0;
		}
	}	
	for(int i = 0; i < n; i++){
		for(int j = 0;j < n; j++){
			for(int k = 0; k < n; k++){
				M1[i][j] += sum_a[i][k] * sum_b[k][j];
			}
		}
	}
	free(sum_a);
	free(sum_b);
	return NULL;
}

//M2 = (A2,1 + A2,2)B1,1
void *calcM2(void *m){
	int n = *((int*)m);
	int **sum_a = allocMatrix(n);
	int **sum_b = allocMatrix(n);
	for(int i = 0; i < n; i++){
		for(int j = 0; j < n; j++){
			sum_a[i][j] = A[i + n][j] + A[i + n][j + n];
			sum_b[i][j] = B[i][j];
			M2[i][j] = 0;
		}
	}	
	for(int i = 0; i < n; i++){
		for(int j = 0; j < n; j++){
			for(int k = 0;k < n; k++){
				M2[i][j] += sum_a[i][k] * sum_b[k][j];
			}
		}
	}
	free(sum_a);
	free(sum_b);
	return NULL;
}

//M3 = A1,1(B1,2 - B2,2)
void *calcM3(void *m){
	int n = *((int*)m);
	int **sum_a = allocMatrix(n);
	int **sum_b = allocMatrix(n);
	for(int i=0;i<n;i++){
		for(int j = 0; j < n; j++){
			sum_a[i][j] = A[i][j];
			sum_b[i][j] = B[i][j + n] - B[i + n][j + n];
			M3[i][j] = 0;
		}
	}	
	for(int i = 0; i < n; i++){
		for(int j = 0; j < n; j++){
			for(int k = 0;k < n; k++){
				M3[i][j] += sum_a[i][k] * sum_b[k][j];
			}
		}
	}
	free(sum_a);
	free(sum_b);
	return NULL;
}

//M4 = A2,2(B2,1 - B1,1)
void *calcM4(void *m){
	int n = *((int*)m);
	int **sum_a = allocMatrix(n);
	int **sum_b = allocMatrix(n);
	for(int i = 0; i < n; i++){
		for(int j = 0;j < n; j++){
			sum_a[i][j] = A[i + n][j + n];
			sum_b[i][j] = B[i + n][j] - B[i][j];
			M4[i][j] = 0;
		}
	}	
	for(int i = 0; i < n; i++){
		for(int j = 0; j < n; j++){
			for(int k = 0; k < n; k++){
				M4[i][j] += sum_a[i][k] * sum_b[k][j];
			}
		}
	}
	free(sum_a);
	free(sum_b);
	return NULL;
}

//M5 = (A1,1 + A1,2)B2,2
void *calcM5(void *m){
	int n = *((int*)m);
	int **sum_a = allocMatrix(n);
	int **sum_b = allocMatrix(n);
	for(int i = 0; i < n; i++){
		for(int j = 0;j < n; j++){
			sum_a[i][j] = A[i][j] + A[i][j+n];
			sum_b[i][j] = B[i + n][j + n];
			M5[i][j] = 0;
		}
	}	
	for(int i = 0; i < n; i++){
		for(int j = 0; j < n; j++){
			for(int k = 0; k < n; k++){
				M5[i][j] += sum_a[i][k] * sum_b[k][j];
			}
		}
	}
	free(sum_a);
	free(sum_b);
	return NULL;
}

//M6 = (A2,1 - A1,1)(B1,1 + B1,2)
void *calcM6(void *m){
	int n = *((int*)m);
	int **sum_a = allocMatrix(n);
	int **sum_b = allocMatrix(n);
	for(int i = 0;i < n; i++){
		for(int j = 0; j < n; j++){
			sum_a[i][j] = A[i + n][j] - A[i][j];
			sum_b[i][j] = B[i][j] + B[i][j+n];
			M6[i][j] = 0;
		}
	}	
	for(int i = 0;i < n; i++){
		for(int j = 0;j < n; j++){
			for(int k = 0; k < n; k++){
			M6[i][j] += sum_a[i][k] * sum_b[k][j];
			}
		}
	}
	free(sum_a);
	free(sum_b);
	return NULL;
}

//M7 = (A1,2 - A2,2)(B2,1 + B2,2)
void *calcM7(void *m){
	int n = *((int*)m);
	int **sum_a = allocMatrix(n);
	int **sum_b = allocMatrix(n);
	for(int i = 0; i < n; i++){
		for(int j = 0; j < n; j++){
			sum_a[i][j] = A[i][j + n]-A[i + n][j + n];
			sum_b[i][j] = B[i + n][j] + B[i + n][j + n];
			M7[i][j] = 0;
		}
	}	
	for(int i = 0; i < n; i++){
		for(int j = 0; j < n; j++){
			for(int k = 0; k < n; k++){
				M7[i][j] += sum_a[i][k] * sum_b[k][j];
			}
		}
	}
	free(sum_a);
	free(sum_b);
	return NULL;
}

//C1,1 = M1 + M4 - M5 +M7
void *calcC11(void *m){
	int n = *((int*)m);
	for(int i = 0; i < n; i++){
		for(int j = 0; j < n; j++){
			C[i][j] = M1[i][j] + M4[i][j] - M5[i][j] + M7[i][j];
		}
	}
	return NULL;
}

//C1,2 = M3 + M5
void *calcC12(void *m){
	int n = *((int*)m);
	for(int i=0;i<n;i++){
		for(int j=0;j<n;j++){
			C[i][j + n] = M3[i][j] + M5[i][j];
		}
	}
	return NULL;
}

//C2,1 = M2 + M4
void *calcC21(void *m){
	int n = *((int*)m);
	for(int i = 0; i < n; i++){
		for(int j = 0; j < n; j++){
			C[i + n][j] = M2[i][j] + M4[i][j];
		}
	}
	return NULL;
}

//C2,2 = M1 - M2 + M3 + M6
void *calcC22(void *m){
	int n = *((int*)m);
	for(int i = 0; i < n; i++){
		for(int j = 0; j < n; j++){
			C[i + n][j + n] = M1[i][j] - M2[i][j] + M3[i][j] + M6[i][j];
		}
	}
	return NULL;
}


//multiply matrix//////////////////////////////////////////////////////////////
void mMul(int **X, int **Y, int **Z, int n){
		int new_n = n / 2;
		int *a11; 
		int *a12;
		int *a21; 
		int *a22; 
		int *b11; 
		int *b12; 
		int *b21; 
		int *b22;
		a11 = X[0]; 
		a12 = X[0] + new_n;
		a21 = X[new_n];
		a22 = X[new_n] + new_n;
		b11 = Y[0];
		b12 = Y[0] + new_n;
		b21 = Y[new_n];
		b22 = Y[new_n] + new_n;
		
		M1 = allocMatrix(new_n);
		M2 = allocMatrix(new_n);
		M3 = allocMatrix(new_n);
		M4 = allocMatrix(new_n);
		M5 = allocMatrix(new_n);
		M6 = allocMatrix(new_n);
		M7 = allocMatrix(new_n);
		
		pthread_t m_threads[7];
		pthread_create(&m_threads[0], NULL, calcM1, &new_n);
		pthread_create(&m_threads[1], NULL, calcM2, &new_n);
		pthread_create(&m_threads[2], NULL, calcM3, &new_n);
		pthread_create(&m_threads[3], NULL, calcM4, &new_n);
		pthread_create(&m_threads[4], NULL, calcM5, &new_n);
		pthread_create(&m_threads[5], NULL, calcM6, &new_n);
		pthread_create(&m_threads[6], NULL, calcM7, &new_n);
		for(int i = 0; i < 7; i++){
			pthread_join(m_threads[i],NULL);
		}
		calcC11(&new_n);
		calcC12(&new_n);
		calcC21(&new_n);
		calcC22(&new_n);
		
		free(M1);
		free(M2);
		free(M3);
		free(M4);
		free(M5);
		free(M6);
		free(M7);
}

void cpy(int **target, int **source, int n){
	for(int i = 0; i < n; i++){
		for(int j=0; j < n; j++){
			target[i][j] = source[i][j];
		}	
	}
}

void strassenMM(int N) {
	int n = ceil((double)N/2);
	int pad = 2 * n;
	
	if(N != pad){
		int **newA; 
		int **newB;
		newA = allocMatrix(pad);
		newB = allocMatrix(pad);
		cpy(newA, A, N);
		cpy(newB, B, N);
		free(A);
		free(B);
		free(C);
		
		A = newA;
		B = newB;
		C = allocMatrix(pad);
	}
	
	mMul(A, B, C, pad);
}






















// Allocate square matrix.
int **allocMatrix(int size) {
  int **matrix;
  matrix = (int **)malloc(size * sizeof(int *));
  for (int row = 0; row < size; row++) {
    matrix[row] = (int *)malloc(size * sizeof(int));
  }
  for (int i = 0; i < size; i++) {
    for (int j = 0; j < size; j++) {
      matrix[i][j] = 0;
    }
  }
  return matrix;
}

// Allocate memory for all the matrixes, you will need to add code
// here to initialize any matrixes that you need.
void initMatrixes(int N) {
  A = allocMatrix(N); B = allocMatrix(N); C = allocMatrix(N); R = allocMatrix(N);
}

// Free up matrixes.
void cleanup() {
  free(A);
  free(B);
  free(C);
  free(R);
}

// Main method
int main(int argc, char* argv[]) {
  int N;
  double elapsedTime;

  // checking parameters
  if (argc != 2 && argc != 4) {
    printf("Parameters: <N> [<fileA> <fileB>]\n");
    return 1;
  }
  N = atoi(argv[1]);
  initMatrixes(N);

  // reading files (optional)
  if(argc == 4){
    readMatrixFile(A,N,argv[2]);
    readMatrixFile(B,N,argv[3]);
  } else {
    // Otherwise, generate two random matrix.
    for (int i=0; i<N; i++) {
      for (int j=0; j<N; j++) {
	A[i][j] = rand() % 5;
	B[i][j] = rand() % 5;
      }
    }
  }

  // Do simple multiplication and time it.
  timerStart();
  simpleMM(N);
  printf("Simple MM took %ld ms\n", timerStop());

  // Do strassen multiplication and time it.
  timerStart();
  strassenMM(N);
  printf("Strassen MM took %ld ms\n", timerStop());

  if (compareMatrix(C, R, N) != 0) {
    if (N < 20) {
      printf("\n\n------- MATRIX C\n");
      printMatrix(C,N);
      printf("\n------- MATRIX R\n");
      printMatrix(R,N);
    }
    printf("Matrix C doesn't match Matrix R, if N < 20 they will be printed above.\n");
  }
  // stopping timer
  
  cleanup();
  return 0;
}
