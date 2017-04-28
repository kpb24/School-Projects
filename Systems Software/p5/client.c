//Kerilee Bookleiner
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>

#define true 1;
#define false 0;
#define BLOCK_SIZE 1024 //size of the chunks

//struct for servers
typedef struct Server
{
  char *ip;
  int port;
} Server;

pthread_mutex_t threadNumMutex = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t numFilesMutex = PTHREAD_MUTEX_INITIALIZER;
int serverNum = 0; //number servers
int threadNum = 0; //number of threads
int numFiles = 0; //number of files (temps)
int eof = false;

void *doSomething(void *serverList){
        pthread_mutex_lock(&threadNumMutex);
        int myThreadNum = threadNum;
        threadNum++;
        pthread_mutex_unlock(&threadNumMutex);
        int count = 1;

       while(!eof){
                Server *servers = (Server *)serverList;
                struct sockaddr_in serv_addr;

                memset(&serv_addr, 0, sizeof(struct sockaddr));
                serv_addr.sin_family = AF_INET;
                serv_addr.sin_port = htons(servers[myThreadNum].port);
                serv_addr.sin_addr.s_addr = inet_addr(servers[myThreadNum].ip);

                int sfd = socket(PF_INET, SOCK_STREAM, 0);
                int ret = connect(sfd, (struct sockaddr *)&serv_addr, sizeof(st$

                if (ret < 0){
                        return (void*)0;
                }

                //send chunk number
                int chunkNum = myThreadNum + count * serverNum - serverNum;
                 char buf[256];
                sprintf( buf, "%d", chunkNum );
                ret = write(sfd, buf, strlen(buf));

                //don't continue if we couldn't write to the socket
                if(ret < 0){
                        return (void*)0;
                }
                void * buffer = calloc(BLOCK_SIZE, 1);
                int bytesRead = read(sfd, buffer, BLOCK_SIZE);

                //if can't read from socket then stop
                if(bytesRead < 0){
                        return (void*)0;
                }
                //exit if run of of bytes to read
                if(bytesRead == 0){
                        free(buffer);
                        break;
 }
                //make a file for each chunk to put back together later
                char fileName[256];
                sprintf(fileName, "%d.tmp", chunkNum);
                FILE *outputFile = fopen(fileName,"wb");
                //write to file if we can
                if(outputFile){
                        fwrite(buffer, 1, bytesRead, outputFile);
                        fclose(outputFile);
                }
                pthread_mutex_lock(&numFilesMutex);
                numFiles++; //counts the number of temp files
                pthread_mutex_unlock(&numFilesMutex);

                free(buffer);
                count++;

                if(strlen(buffer) < 1024){//end while loop
                        eof = true;
                }
                close(sfd);
        }
        return (void*)1;
}


int main(int argc, char ** argv){
        int i = 1;
        if (argc < 3 || !(argc % 2)){
                printf("%s <ipaddr1> <port1>\n",argv[0]);
                exit(0);
        }

        serverNum = (argc - 1)/2;
        pthread_t threads[serverNum];
        void *threadJoin[serverNum];
        Server servers[serverNum];

        //make the temp servers
        for(i = 1; i < argc; i+=2){
                struct Server temp = {argv[i], atoi(argv[i + 1])}; //string to int
                servers[i/2] = temp;
        }

        //create and join threads
        for(i = 0; i < serverNum; i++){
                pthread_create(&threads[i], NULL, doSomething, &servers);
        }
        for(i = 0; i < serverNum; i++){
                pthread_join(threads[i], &threadJoin[i]);
        }

        remove("output.txt"); //delete if already exists because want to create new one
        FILE *outputFile = fopen("output.txt","ab");
        if(outputFile == NULL){
                printf("\nUnable to open file\n");
                return -1; //error
        }

        //take all the chunks (temps) and put together into output.txt
        for(i = 0; i < numFiles; i++){
                char fileName[256];
                sprintf(fileName, "%d.tmp", i);
                FILE *temp = fopen(fileName,"rb");
                void *buffer = calloc(BLOCK_SIZE,1);
				int bytesRead = fread(buffer, 1, BLOCK_SIZE, temp);
                if(!bytesRead){
                        continue;
                }
                fwrite(buffer, bytesRead, 1, outputFile);
                free(buffer);
                fclose(temp);

                //remove the .tmp file
                remove(fileName);
        }

        fclose(outputFile); //close the file
        //kill them
        pthread_mutex_destroy(&threadNumMutex);
        pthread_mutex_destroy(&numFilesMutex);

    return 0;
}
