#include<stdio.h>
#include<unistd.h>

int main(){
    printf("Hello World\n");
    fork(); // Create a new process
    fork(); // Create another new process
    printf("Hello World\n");
    return 0;   
}