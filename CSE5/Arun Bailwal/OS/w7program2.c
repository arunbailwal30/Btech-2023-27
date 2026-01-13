#include <stdio.h>
#include <stdbool.h>

bool search(int frame[], int n, int num) {
    for (int i = 0; i < n; i++) {
        if (frame[i] == num)
            return true;
    }
    return false;
}

void display(int frame[], int n) {
    for (int i = 0; i < n; i++) {
        if (frame[i] == -1)
            printf("- ");
        else
            printf("%d ", frame[i]);
    }
}

int main() {
    int n, num;
    printf("Enter the number of frames: ");
    scanf("%d", &n);

    int frame[n];
    for (int i = 0; i < n; i++)
        frame[i] = -1;

    printf("Enter the number of requests: ");
    scanf("%d", &num);

    int seq[num];
    printf("Enter the page request sequence: ");
    for (int i = 0; i < num; i++) {
        scanf("%d", &seq[i]);
    }

    int hit = 0, miss = 0, j = 0;

    for (int i = 0; i < num; i++) {
        if (search(frame, n, seq[i])) {
            hit++;
            display(frame, n);
            printf(" HIT\n");
        } else {
            frame[j] = seq[i];
            j = (j + 1) % n; 
            display(frame, n);
            miss++;
            printf(" MISS\n");
        }
    }

    printf("\nPage Hits: %d", hit);
    printf("\nPage Misses: %d\n", miss);

    return 0;
}
