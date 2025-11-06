#include<stdio.h>
#include <stdlib.h>

typedef struct process{
    int size;
    int block;

}process;

typedef struct ram{
    int **block;
    int size;

}ram;

ram* createBlock(int size){
    ram *temp = (ram*)malloc(sizeof(ram));
    
    temp->size = size;
    temp->block = (int**)malloc(sizeof(int*)*size);
    for(int i =0;i<size;i++){
        temp->block[i] = (int*)malloc(sizeof(int)*2);
        temp->block[i][1] = 0;
    } 
    return temp;
}

process* createProcess(int size){
    process *temp = (process*)malloc(sizeof(process));
    temp->size = size;
    temp->block = 0;
    return temp;
}

void allocate(ram* r, process* arr[], int p, int n){
    for(int i =0;i<p;i++){
        int j=0;
        for( j = 0;j< n; j++){
            if(r->block[j][0] >= arr[i]->size && r->block[j][1] != 1){
                break;
            }
        }
        if(j>=n) continue;
        arr[i]->block = j;
        r->block[j][1] =  1;
    }

    for(int i =0;i<p;i++){
        printf("P: %d,  Block: %d\n",i+1,arr[i]->block);
    }

}

int main(){
    int n;
    scanf("%d",&n);
    ram* r = createBlock(n);
    for(int i=0;i<n;i++){
        scanf("%d",&(r->block[i][0]));
    }

    int p;
    scanf("%d", &p);
    process* arr[p];
    for(int i=0;i<p;i++){
        int x;
        scanf("%d",&x);
        arr[i] = createProcess(x);
    }
    allocate(r,arr,p,n);
}



