#include<stdio.h>
#include<unistd.h>
int main(){
    int pid = fork();
    if(pid< 0){
        printf("Fork failed\n");
        return 1;
    }else if( pid == 0){
        printf("this is child process! PID: %d\n", getpid());
    }else{
        printf("this is parent process! PID: %d, Child PID: %d \n",getpid(),pid);
    }
    return 0;
}