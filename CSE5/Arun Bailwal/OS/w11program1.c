#include<stdio.h>
#include<stdlib.h>
int main(){
    int n;
    printf("Enter number of disk requests: ");
    scanf("%d",&n);
    int req[n];
    for(int i=0;i<n;i++){
        scanf("%d", req+i);
    }
    int head= req[0];
    int movement=0;
    for(int i =1;i<n;i++){
        movement += abs(head - req[i]);
        head = req[i];
    }
    printf("Total seek movement: %d\n", movement);
    printf("Average seek movement: %.2f\n", (float)movement/n);
}