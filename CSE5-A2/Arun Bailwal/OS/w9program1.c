#include<stdio.h>
#include<stdlib.h>
#include<string.h>

typedef struct file{
    char name[100];
    int index;
    int *blocks;
    int noBlock;
}file;

void sequential(file *arr, int n){
    for(int i=0;i<n;i++){
        arr[i].blocks = (int*)malloc(sizeof(int) * arr[i].noBlock);
        for(int j  = 0;j<arr[i].noBlock;j++){
            if(j==0){
                arr[i].blocks[j] = arr[i].index;
                continue;
            } 
            arr[i].blocks[j] = arr[i].blocks[j-1]+1;
        }
    }

    printf("Enter name of file: ");
    char s[100];
    scanf("%s",s);
    printf("File Name  Start block  no. of blocks Blocks occupied\n");
    for(int i =0;i<n;i++){
        if(strcmp(arr[i].name,s)==0){
            printf("%s \t\t %d \t %d \t\t",arr[i].name,arr[i].index ,arr[i].noBlock);
            for(int j = 0; j<arr[i].noBlock;j++){
                printf("%d, ", arr[i].blocks[j]);
            }
            break;
        }
    }
    printf("\n");

}

int main(){
    int n;
    scanf("%d",&n);
    file arr[n];
    for(int i= 0;i<n;i++){
        printf("Enter file%d name: ",i+1);
        scanf("%s",(arr[i].name));
        printf("Enter starting block number in file%d: ",i+1);
        scanf("%d", &(arr[i].index));
        printf("Enter number of blocks in file%d: ",i+1);
        scanf("%d",&(arr[i].noBlock));
    }

    sequential(arr, n);
    return 0;
}