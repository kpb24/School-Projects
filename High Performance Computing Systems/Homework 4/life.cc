/**
 * University of Pittsburgh
 * Department of Computer Science
 * CS1645/CS2045: Introduction to HPC Systems
 * Instructor: Xiaolong Cui
 * Students: Keri Bookleiner
 * Implement openmp verions of conway's game of life.
 */
#include "stdlib.h"
#include "timer.h"
#include "io.h"
#ifdef _OPENMP
#include "omp.h"
#endif

int **allocMatrix(int size);

int checkRules(int **World, int r, int c, int N){
	int numberOfNeighbors = 0;
	//count number of neighbors
    for (int i = r - 1; i <= r + 1; i++) {
        for (int j = c - 1; j <= c + 1; j++){
            if (i == r && j == c){
				continue;
			}
            else if(i < 0 || j < 0 || i > N - 1 || j > N - 1){
				continue;
			}
            else if(World[i][j] != 0){
				numberOfNeighbors++;
			}
            else{
				continue;
			}
        }
    }
	
	//Check the rules
	if(numberOfNeighbors < 2 && World[r][c] != 0){
		return 0; //Dies of loneliness
	} 
	else if((numberOfNeighbors == 2 || numberOfNeighbors == 3) && World[r][c] != 0){
		return World[r][c] + 1; //Lives on to next generation
	} 
	else if(numberOfNeighbors > 3 && World[r][c] != 0){
		 return 0; //Dies of crowding
	} 
	else if(numberOfNeighbors == 3 && World[r][c] == 0){
		return 1; //becomes a live cell (reproduction)
	} 
	else{
		return World[r][c];
	}
}

// Function implementing Conway's Game of Life
void conway(int **World, int N, int M){
  // STUDENT: IMPLEMENT THE GAME HERE, make it parallel!
    int **nextWorld = allocMatrix(N);
    int m, i;
    for (m = 0; m < M; ++m) {
        # pragma omp parallel for shared(World, nextWorld, N) private(i)
        for (i = 0; i < N * N; i++) {
            nextWorld[i / N][i % N] = checkRules(World, i / N, i % N, N);
        }
        # pragma omp parallel for shared(World, nextWorld, N) private(i)
        for (i = 0; i < N * N; i++) {
            World[i/ N][i % N] = nextWorld[i / N][i % N];
        }
    }
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

// Main method
int main(int argc, char* argv[]) {
  int N,M;
  int **World;
  double elapsedTime;

  // checking parameters
  if (argc != 3 && argc != 4) {
    printf("Parameters: <N> <M> [<file>]\n");
    return 1;
  }
  N = atoi(argv[1]);
  M = atoi(argv[2]);

  // allocating matrices
  World = allocMatrix(N);

  // reading files (optional)
  if(argc == 4){
    readMatrixFile(World,N,argv[3]);
  } else {
    // Otherwise, generate two random matrix.
    srand (time(NULL));
    for (int i=0; i<N; i++) {
      for (int j=0; j<N; j++) {
        World[i][j] = rand() % 2;
      }
    }
  }

  // starting timer
  timerStart();

  // calling conway's game of life
  conway(World,N,M);

  // stopping timer
  elapsedTime = timerStop();
  
   printMatrix(World,N);

  printf("Took %ld ms\n", timerStop());

  // releasing memory
  for (int i=0; i<N; i++) {
    delete [] World[i];
  }
  delete [] World;

  return 0;
}
