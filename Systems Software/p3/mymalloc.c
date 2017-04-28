
/*Kerilee Bookleiner*/

#include <stdio.h>
#include <unistd.h>
#include <string.h>

typedef struct node *Node;
struct node{
        int size; //size of chunk
        int free; //1 or 0 for if free or empty
        struct node *nextNode; //pointer to next node
        struct node *previousNode; //pointer to previous node
};


//globals to keep track of first, current, and last node
static Node firstNode = 0;
static Node currentNode = 0;
static Node lastNode = 0;


//if the memory allocation requested is more than the space we have to make new$
static Node new_node(int size){
        Node n; //new node n
        n = sbrk(sizeof(struct node) + size); //new brk value
        n->size = size;
        n->free = 0;
        n->nextNode = NULL;
        n->previousNode = lastNode;
        lastNode->nextNode = n;
        lastNode = n;

        return n;
}


//finds the next fit
static Node next_fit(int size){
        Node n = currentNode->nextNode;
        while(n != NULL){
                if(n->free == 1){
                        if(n->size == (size + sizeof(struct node))){
                                n->free = 0;
                                n->size = size;
                                currentNode = n;
                                return n;
                        }
                        else if( n->size > (size + sizeof(struct node))){
                                currentNode = n;

                                //split node
                                Node n;
                                currentNode->size = currentNode->size - (size + sizeof(struct node));
                                n = (sizeof(struct node) + currentNode) + currentNode->size;
                                n->size = size;
                                n->free = 0;
                                n->nextNode = currentNode->nextNode;
                                n->previousNode = currentNode;
                                currentNode->nextNode->previousNode = n;
                                currentNode->nextNode = n;

                                 return n;
                        }
                }

                 if(n->nextNode != NULL){
                        if(n != currentNode){
                                 n = n->nextNode;
                        }
                        else{
                                n = NULL;
                        }
                }
                else{
                        if(n != firstNode){
                                n = firstNode;
                        }
                        else{
                                n = NULL;
                        }
                }
        }
        return (void*) (new_node(size) + 1);
}





void *my_nextfit_malloc(int size){
        //checks if list  needs created or not
         if(firstNode == 0){
                //create list
                firstNode = sbrk(size + sizeof(struct node));
                firstNode->size = size;
                firstNode->free = 0;
                firstNode->nextNode = NULL;
                firstNode->previousNode = NULL;
                lastNode = firstNode;
                currentNode = firstNode;

         return firstNode + 1;
  }
        //find the next fit
         return (void*)(next_fit(size));
}




void my_free(void *ptr){
        Node n = ((Node)ptr - 1);
        n->free = 1;
        if(n->previousNode != NULL){
                if(n->previousNode->free == 1){
                        n->previousNode->size = n->previousNode->size +  n->size + sizeof(struct node);
                        n->previousNode->nextNode = n->nextNode;
                        if(n == lastNode){
                                lastNode = n->previousNode;
                        }
                        if(n == currentNode){
                                currentNode = n->previousNode;
                        }
                        n = n->previousNode;
                }
        }

        if(n->nextNode != NULL){
                if(n->nextNode->free == 1){
                        n->size = n->size + n->nextNode->size + sizeof(struct node);
 n->nextNode = n->nextNode->nextNode;
                }
        }
}





void print_malloc(){
        Node n = firstNode;
        int count = 0;
        while(n != NULL){
                printf("\n\nNode: %d", count);
                printf("\nAddress: %d", n);
                printf("\nNext Node: %d", n->nextNode);
                printf("\nPrevious Node: %d", n->previousNode);
                printf("\nSize: %d", n->size);
                if(n->free == 1){
                        printf("\nFree: True");
                }
                else{
                        printf("\nFree: False");
                }
                n = n->nextNode;
                count++;
        }
}
