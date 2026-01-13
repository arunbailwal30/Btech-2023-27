#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
int main() {
    pid_t pid = fork();
    if (pid < 0) exit(1);
    if (pid == 0) {
        printf("Child PID: %d, exiting\n", getpid());
        exit(0);
    } else {
        sleep(10);
        printf("Parent PID: %d did not collect child, zombied created\n", getpid());
        exit(0);
    }
    return 0;
}
