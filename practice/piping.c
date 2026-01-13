#include<stdio.h>
#include<unistd.h>
#include<string.h>
// #include <sys/types.h>

int main(){
    int fd[2];
    pipe(fd);

    int pid = fork();

    if(pid ==0){
        close(fd[0]);
        char msg[] = "hello there how are you?";
        write(fd[1], msg, strlen(msg)+ 1);
        close(fd[1]);
    }else{
        close(fd[1]);
        char buffer[50];
        read(fd[0], buffer, sizeof(buffer));
        printf("%s\n",buffer);
        close(fd[0]);
    }
    return 0;
}