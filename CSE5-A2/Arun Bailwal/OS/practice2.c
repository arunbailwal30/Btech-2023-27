// parent should print even element sum child should print odd element sum . parent should finish first


#include<stdio.h>
#include<stdlib.h>
#include<sys/wait.h>
#include<unistd.h>

int main(){
    pid_t pid1, pid2;
    pid1 = fork();

    if(pid1 == 0){
        wait(NULL); 
        int sum = 0;
        for(int i = 1; i <= 10; i += 2) {
            sum += i;
        
        }
        printf("Child: Odd element sum = %d\n", sum);
        exit(0);
    }else{
        int sum = 0;
        for(int i = 0; i <= 10; i += 2) {
            sum += i;
        }
        printf("Parent: Even element sum = %d\n", sum);
        wait(NULL);
        exit(0);
    }


}