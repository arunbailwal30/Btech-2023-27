#include<stdio.h>
#include<unistd.h>

int main(){
    int x =fork();
    if(x <0){
        printf("Fork failed!\n");
        return 1;
    }
    if(x ==0)
        printf("This is child process. PID: %d\n", getpid());
    else if(x > 1) 
        printf("This is parent process. PID %d\n", getpid());


    return 0;
}