#include<stdio.h>
#include<stdlib.h>



int main(){
    int m,n;
    printf("Enter no. of processes: ");
    scanf("%d", &m);
    printf("Enter no. of resources: ");
    scanf("%d", &n);
    int maxreq[m][n];
    int all[m][n];
    int totalall[n];
    for(int i=0;i<n;i++) totalall[i] = 0;
    int avail[n];
    int maxres[n];
    printf("Allocated resource \t max needs ");
    for(int i=0;i<m;i++){
        for(int j=0;j<n;j++){
            scanf("%d",&all[i][j]);
            totalall[j]++;
        }
        for(int j=0;j<n;j++) scanf("%d",&maxreq[i][j]);
    }
    printf("Enter max resources: ");
    for(int i=0;i<n;i++) scanf("%d", &maxres[i]);
    
    for(int i=0;i<n;i++){
        avail[i] = maxres[i]- totalall[i];
    }
}