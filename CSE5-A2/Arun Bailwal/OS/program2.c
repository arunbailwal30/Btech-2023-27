// parent should print even element sum child should print odd element sum . parent should finish first


#include<stdio.h>
#include<stdlib.h>
#include<sys/wait.h>
#include<unistd.h>

int main(){
    pid_t pid;
    pid = fork();
    if(pid< 0){
        printf("Fork failed\n");
        return 1;
    }
    if(pid==0){
        printf("This is child process \n");
        printf("Child process id: %d \n", getpid());
        printf("Parent process id: %d\n\n", getppid());

    }else{
        printf("This is parent process\n");
        printf("Parent process id: %d\n", getpid());
        printf("Child process id: %d\n\n", pid);
    }

    return 0;
}