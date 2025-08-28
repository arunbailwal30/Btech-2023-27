#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
int main() {
    pid_t pid = fork();
    if (pid < 0) exit(1);
    if (pid == 0) {
        sleep(5);
        printf("Child PID: %d, New Parent PID: %d\n", getpid(), getppid());
    } else {
        printf("Parent PID: %d exiting now\n", getpid());
        exit(0);
    }
    return 0;
}
