#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<stdbool.h>

typedef struct file{
    char name;
    int start_block;
    int noBlock;
}file;

int main(){
    bool blocks[1000];
    for(int i=0;i<1000;i++) blocks[i] = true;

    int n;
    printf("Enter number of files: ");
    scanf("%d", &n);
    file files[n];
    for(int i=0;i<n;i++){
        getchar();
        printf("Enter file %d name: ",i+1);
        scanf("%c", &files[i].name);
        printf("Enter starting block of file %d: ", i+1);
        scanf("%d",&files[i].start_block);
        printf("Enter no of block in file %d: ",i+1);
        scanf("%d",&files[i].noBlock);
        int st = files[i].start_block;
        for(int j=0;j<files[i].noBlock;j++)
            blocks[st++] = false;
           
    }
    char ch;
    getchar();
    printf("Enter the file name to be searched: ");
    scanf("%c",&ch);
    bool found = false;
    for(int i =0;i<n;i++){
        if(files[i].name == ch){
            printf("File Name : %c\n", files[i].name);
            printf("Start Block : %d\n", files[i].start_block);
            printf("No. of Blocks : %d\n", files[i].noBlock);
            printf("Blocks occupied : ");
            int st = files[i].start_block;
            for(int j=0; j < files[i].noBlock; j++)
                printf("%d ", st++);
            found = true;
            break;
        }
    }
    if(!found ) printf("\nFile not found\n");
    printf("\n");
}

