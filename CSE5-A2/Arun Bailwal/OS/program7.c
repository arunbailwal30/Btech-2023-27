#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>

#define MAX_LINE_LENGTH 256
ssize_t read_line(int fd, char *buffer, size_t max_len) {
    ssize_t n, i = 0;
    char c;
    while (i < max_len - 1 && (n = read(fd, &c, 1)) > 0) {
        buffer[i++] = c;
        if (c == '\n') {
            break;
        }
    }
    buffer[i] = '\0';
    return i;
}

int main() {
    int fd1, fd2;
    char line1[MAX_LINE_LENGTH];
    char line2[MAX_LINE_LENGTH];

    // --- Create and write to mydetails.txt ---
    fd1 = open("mydetails.txt", O_CREAT | O_WRONLY | O_TRUNC, 0644);
    if (fd1 < 0) {
        perror("Error opening mydetails.txt");
        return 1;
    }
    write(fd1, "Name: Arun Bailwal\n", strlen("Name: Arun Bailwal\n"));
    write(fd1, "Roll: 16\n", strlen("Roll: 16\n"));
    write(fd1, "City: Delhi\n", strlen("City: Delhi\n"));
    close(fd1);

    // --- Create and write to friendsdetails.txt ---
    fd2 = open("friendsdetails.txt", O_CREAT | O_WRONLY | O_TRUNC, 0644);
    if (fd2 < 0) {
        perror("Error opening friendsdetails.txt");
        return 1;
    }
    write(fd2, "Name: Rahul\n", strlen("Name: Rahul\n"));
    write(fd2, "Roll: 101\n", strlen("Roll: 101\n"));
    write(fd2, "City: Delhi\n", strlen("City: Delhi\n"));
    close(fd2);

    // --- Compare files for matching lines ---
    printf("Matching lines:\n");

    fd1 = open("mydetails.txt", O_RDONLY);
    if (fd1 < 0) {
        perror("Error opening mydetails.txt for reading");
        return 1;
    }

    while (read_line(fd1, line1, MAX_LINE_LENGTH) > 0) {
        fd2 = open("friendsdetails.txt", O_RDONLY);
        if (fd2 < 0) {
            perror("Error opening friendsdetails.txt for reading");
            close(fd1);
            return 1;
        }

        while (read_line(fd2, line2, MAX_LINE_LENGTH) > 0) {
            if (strcmp(line1, line2) == 0) {
                write(1, line1, strlen(line1)); 
            }
        }
        close(fd2);
    }
    close(fd1);

    return 0;
}
