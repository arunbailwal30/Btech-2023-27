#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

#define MAX_FILES 100
#define MAX_BLOCKS 1000

typedef struct {
    char name;             
    int start_block;
    int noBlock;
    int flag;                  
} File;

typedef struct {
    int next;               
    bool used;        
} Block;

int main() {
    int n;
    printf("Enter number of files: ");
    scanf("%d", &n);
    if(n > MAX_FILES || n <= 0){
        printf("Invalid number of files!\n");
        return 1;
    }

    File files[MAX_FILES];
    Block blocks[MAX_BLOCKS];

    for(int i = 0; i < MAX_BLOCKS; i++) {
        blocks[i].next = -1;
        blocks[i].used = false;
    }

    for(int i = 0; i < n; i++) {
        printf("Enter file %d name: ", i+1);
        scanf(" %c", &files[i].name);

        printf("Enter starting block of file %d: ", i+1);
        scanf("%d", &files[i].start_block);

        printf("Enter number of blocks for file %d: ", i+1);
        scanf("%d", &files[i].noBlock);

        files[i].flag = 0; // Assume fail

        if(files[i].start_block < 0 || files[i].start_block >= MAX_BLOCKS) {
            printf("Invalid starting block!\n");
            continue;
        }
        if(blocks[files[i].start_block].used) {
            printf("Starting block %d already occupied! Try again.\n", files[i].start_block);
            continue;
        }

        blocks[files[i].start_block].used = true;
        int allocated = 1;
        int last = files[i].start_block;

        printf("Enter blocks for file %c (except starting block %d):\n",files[i].name, files[i].start_block);
        for(int j = 1; j < files[i].noBlock; j++) {
            int found = -1;
            scanf("%d", &found);

            if(found < 0 || found >= MAX_BLOCKS) {
                printf("Block %d is out of range. Enter a valid block number.\n", found);
                j--; continue;
            }
            if(blocks[found].used) {
                printf("Block %d already used. Enter another block.\n", found);
                j--; continue;
            }
            blocks[last].next = found;
            blocks[found].used = true;
            last = found;
            allocated++;
        }
        if(allocated == files[i].noBlock)
            files[i].flag = 1;
    }
    printf("\nFile Allocation Table:\n");
    printf("File\tStart\tBlocks Linked\n");
    printf("----------------------------------------\n");
    for(int i = 0; i < n; i++) {
        if(!files[i].flag) continue;
        printf("%c\t%d\t", files[i].name, files[i].start_block);
        int j = files[i].start_block;
        while(j != -1) {
            printf("%d ", j);
            j = blocks[j].next;
        }
        printf("\n");
    }
    return 0;
}
