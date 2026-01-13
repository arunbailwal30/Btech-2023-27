#include <stdio.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/types.h>

int main() {
    key_t key = ftok("shmfile", 'A');
    int shmid = shmget(key, 1024, 0666 | IPC_CREAT);
    char *ptr = (char*) shmat(shmid, NULL, 0);
    printf("The Data stored : %s", ptr);
    shmdt(ptr);
    shmctl(shmid, IPC_RMID, NULL);
    return 0;
}
