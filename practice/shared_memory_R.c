
#include<stdio.h>
#include<stdlib.h>
#include<sys/ipc.h>
#include<sys/shm.h>
#include<sys/types.h>

int main(){
    key_t key = ftok("pragfile",'A');
    int shmid = shmget(key, 1024, 0666 | IPC_CREAT);
    char* p  = (char*) shmat(shmid, NULL, 0);
    printf("Data: %s \n",p);
    shmdt(p);
    shmctl(shmid, IPC_RMID, NULL);
}