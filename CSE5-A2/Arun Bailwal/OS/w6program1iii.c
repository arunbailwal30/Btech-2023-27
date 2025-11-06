#include <stdio.h>
#include <string.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/types.h>

int main() {
    key_t key = ftok("shmfile",'A');
    int shmid = shmget(key, 1024, 0666 | IPC_CREAT);
    char *ptr = (char*) shmat(shmid, NULL, 0);
    printf("Input Data : ");
    fgets(ptr, 1024, stdin);
    shmdt(ptr);
    return 0;
}
