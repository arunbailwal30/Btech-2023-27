#include<stdio.h>
#include<unistd.h>
#include<stdlib.h>

int main(){
    excelp("ls","ls","-l",NULL);
    printf("excelp failed\n");
    return 0;
}