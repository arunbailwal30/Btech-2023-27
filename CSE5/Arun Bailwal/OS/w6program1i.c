#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>

int main() {
    int fd[2];
    pipe(fd);
    pid_t pid = fork();
    if(pid == 0) {
        close(fd[0]);
        char msg[] = "Hello from Child";
        write(fd[1], msg, strlen(msg) + 1);
        close(fd[1]);
    } else {
        close(fd[1]);
        char buffer[50];
        read(fd[0], buffer, sizeof(buffer));
        printf("Parent read: %s\n", buffer);
        close(fd[0]);

    }
    return 0;
}
