#include <stdio.h>

int main() {
    printf("Enter number of free blocks available: ");
    int n;
    scanf("%d", &n);
    int blocks[n];
    for (int i = 0; i < n; i++) {
        scanf("%d", &blocks[i]);
    }

    printf("Enter number of processes: ");
    int p;
    scanf("%d", &p);
    int procs[p];
    for (int i = 0; i < p; i++) {
        scanf("%d", &procs[i]);
    }

    printf("\n--- First Fit Allocation ---\n");
    for (int i = 0; i < p; i++) {
        printf("Process %d (%d KB) -> ", i + 1, procs[i]);
        int ind = -1;

        for (int j = 0; j < n; j++) {
            if (blocks[j] >= procs[i]) {
                ind = j;
                break;  // first suitable block
            }
        }

        if (ind == -1)
            printf("No free block available\n");
        else {
            printf("Block %d (%d KB)\n", ind + 1, blocks[ind]);
            blocks[ind] = 0;
        }
    }
    return 0;
}
