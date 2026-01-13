#include<stdio.h>
#include<stdlib.h>
#include<sys/ipc.h>
#include<sys/types.h>
#include<sys/shm.h>

//writer
int main(){
    key_t key = ftok("pragfile", 'A');
    int shmid = shmget(key,1024, 0666 | IPC_CREAT);
    char* p = (char*) shmat(shmid, NULL, 0);
    printf("Writing: ");
    fgets(p, 1024, stdin);
    shmdt(p);
    return 0;

}