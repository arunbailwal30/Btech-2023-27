#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<sys/wait.h>


int main(){
  int x = fork();
  pid_t pid1, pid2;
  pid1 = fork();
  if(pid1<0){

    printf("fork failed\n");
    return 1;

  }else if(pid1 ==0){
    printf("child process 1 (PID: %d): Listing files...\n", getpid());
    execlp("ls", "-l", (char*)NULL);
    exit(0);
    

  }else{
  wait(NULL);
     printf("Parent process (PID: %d): First child completed.\n",getpid());
     pid2 = fork();
     if(pid2<0){

    printf("fork failed\n");
    return 1;

  }else if(pid1 ==0){
    printf("child process 2 (PID: %d): Listing files...\n", getpid());
    sleep(5);
    printf("child process 2 (PID: %d): Work done. \n",getpid());
    exit(0);
    

  }else{
  wait(NULL);
     printf("Parent process (PID: %d): Exiting now.\n",getpid());
     exit(0);
     
  }
  }
  return 0;
}

