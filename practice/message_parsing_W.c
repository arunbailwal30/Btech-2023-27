#include<stdio.h>
#include<string.h>
#include<unistd.h>
#include<sys/ipc.h>
#include<sys/msg.h>
#define MAX 100

struct msg{
    long msg_type;
    char msg[MAX];
}message;

int main(){
    key_t key = ftok("progfile",65);
    int msgid = msgget(key, 0666 | IPC_CREAT);
    message.msg_type = 1;
    printf("Writing data: ");
    fgets(message.msg, MAX, stdin);
    msgsnd(msgid, &message, sizeof(message),0);
    return 0;
}