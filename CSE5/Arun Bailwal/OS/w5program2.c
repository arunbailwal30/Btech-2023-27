
#include<stdlib.h>
#include<stdio.h>

typedef struct Process{
    int size;
    int *res;
}Process;

Process* createProcess(int size){
    Process* temp = (Process*)malloc(sizeof(Process));
    temp->res = (int*)malloc(sizeof(int)*size);
    return temp;
}

int find( Process *remaining[], int available[], int n , int r){
    for(int i=0;i<n;i++){
        int flag = 1;
        int remained = 0;
        for(int j = 0;j<r;j++){
            if(remaining[i]->res[j] > 0) remained = 1;
            if(remaining[i]->res[j] > available[j] ){
                flag = 0;
                break;
            }
        }
        if(flag && remained) return i;
    }
    return -1;
}

void detection(Process *allocated[], Process *remaining[], Process *maxneed[], int available[], int n , int r){
    for(int i=0;i<n;i++){
        int ind = find(remaining, available, n , r);
        if(ind == -1){
            printf("Deadlock Detected\n");
            return;
        }
        for (int  j = 0; j<r; j++)
        {
            remaining[ind]->res[j] = 0;
            available[j] += allocated[ind]->res[j]; 
        }
    }
    printf("\nDeadlock not detected\n");
}


int main(){
    int n,r ;
    printf("No. of processes: ");
    scanf("%d",&n);
    printf("No. of resources: ");
    scanf("%d",&r);
    Process* maxneed[n], *allocated[n], *remaining[n], *total, *totalAllocated;
    int available[r];
    total  =createProcess(r);
    totalAllocated = createProcess(r);
    for(int i=0;i<n;i++){ 
        maxneed[i] =createProcess(r); 
        allocated[i] = createProcess(r);
        remaining[i] = createProcess(r);
    }
    printf("Enter maximum resources needed: \n");
    for (int i = 0; i < n; i++)
    {
        for(int j=0;j<r;j++) scanf("%d", &(maxneed[i]->res[j]));
    }
    for(int j=0;j<r;j++) totalAllocated->res[j] = 0;
    printf("Enter allocated resources\n");
    for (int i = 0; i < n; i++){
        for(int j=0;j<r;j++){
            scanf("%d", &(allocated[i]->res[j]));
            remaining[i]->res[j] = maxneed[i]->res[j]-allocated[i]->res[j];
            totalAllocated->res[j] += allocated[i]->res[j]; 
        }
    }
    
    printf("Enter total resources: ");
    for(int i=0;i<r;i++){ 
        scanf("%d", &(total->res[i]));
        available[i] = total->res[i] - totalAllocated->res[i]; 
    }
    detection(allocated, remaining, maxneed, available, n, r);

    return 0;
}