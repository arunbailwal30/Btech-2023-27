#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
int main() {
    int fd1, fd2;
    char buf1[256], buf2[256];
    ssize_t n1, n2;

    fd1 = open("mydetails.txt", O_CREAT | O_WRONLY | O_TRUNC, 0644);
    write(fd1, "Name: Arun Bailwal\nRoll: 16\nCity: Delhi\n", 35);
    close(fd1);

    fd2 = open("friendsdetails.txt", O_CREAT | O_WRONLY | O_TRUNC, 0644);
    write(fd2, "Name: Rahul\nRoll: 101\nCity: Delhi\n", 35);
    close(fd2);

    fd1 = open("mydetails.txt", O_RDONLY);
    fd2 = open("friendsdetails.txt", O_RDONLY);

    char line1[256], line2[256];
    int i = 0, j = 0;

    while ((n1 = read(fd1, buf1, sizeof(buf1))) > 0) {
        i = 0;
        while (i < n1) {
            int k = 0;
            while (i < n1 && buf1[i] != '\n') line1[k++] = buf1[i++];
            line1[k] = '\n';
            line1[k + 1] = '\0';
            i++;

            lseek(fd2, 0, SEEK_SET);
            while ((n2 = read(fd2, buf2, sizeof(buf2))) > 0) {
                j = 0;
                while (j < n2) {
                    int l = 0;
                    while (j < n2 && buf2[j] != '\n') line2[l++] = buf2[j++];
                    line2[l] = '\n';
                    line2[l + 1] = '\0';
                    j++;
                    if (strcmp(line1, line2) == 0) write(1, line1, strlen(line1));
                }
            }
        }
    }

    close(fd1);
    close(fd2);
    return 0;
}

