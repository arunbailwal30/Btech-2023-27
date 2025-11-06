#include <stdio.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#define MAX 100

struct msg {
    long msg_type;
    char msg_text[MAX];
} message;

int main() {
    key_t key = ftok("progfile", 65);
    int msgid = msgget(key, 0666 | IPC_CREAT);
    message.msg_type = 1;
    printf("Write Data : ");
    fgets(message.msg_text, MAX, stdin);
    msgsnd(msgid, &message, sizeof(message), 0);
    return 0;
}
