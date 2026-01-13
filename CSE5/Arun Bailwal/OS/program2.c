#include<stdio.h>
#include<unistd.h>
int main(){
    int pid = fork();
    if(pid< 0){
        printf("Fork failed\n");
        return 1;
    }else if( pid == 0){
        printf("Child process\n");
        printf("PID: %d, Parent PID: %d\n", getpid(), getppid());
    }else{
        printf("Parent process\n PID: %d, Child PID: %d \n",getpid(),pid);
    }
    return 0;
}