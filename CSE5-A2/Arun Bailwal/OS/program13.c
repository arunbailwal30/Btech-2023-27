#include<stdio.h>

#include<stdlib.h>
typedef struct Job
{
    int pid;
    int arrival,burst;
    int waiting,complete,turnaround,remaining; 
}Job;

Job* create(int at, int bt, int id){
    Job*  temp = (Job*) malloc(sizeof(Job));
    temp->pid = id;
    temp->arrival = at;
    temp->burst = bt;
    temp->waiting = 0;
    temp->complete = 0;
    temp->turnaround = 0;
    temp->remaining = bt;
}


void checkMin(Job* jobs[],int n, int *iMin, int *Min, int currTime){
    for(int i = 0; i < n; i++){
        if(jobs[i]->remaining > 0 && jobs[i]->arrival <= currTime && jobs[i]->burst < *Min){
            *Min = jobs[i]->burst;
            *iMin = i;
        }
    }
}

int allDone(Job* jobs[], int n){
    for(int i = 0; i < n; i++){
        if(jobs[i]->remaining > 0) return 0;
    }
    return 1;
}

void jobScheduling(Job* jobs[], int n, int tb){
    int iMin, Min;
    int currTime = 0;
    printf("Gantt's chart: ");
    while(!allDone(jobs,n)){
        iMin = 0, Min=9999;
        checkMin(jobs, n, &iMin, &Min, currTime);
        if(jobs[iMin]->remaining > 0 && jobs[iMin]->arrival <= currTime){
            printf("P%d ",jobs[iMin]->pid);
            currTime++;
            jobs[iMin]->remaining--;
            jobs[iMin]->complete = currTime;
            jobs[iMin]->turnaround = currTime-jobs[iMin]->arrival;
            jobs[iMin]->waiting = jobs[iMin]->complete - jobs[iMin]->burst;
        }

    }

    printf("\nPID\t Arrival Time\t Burst Time\t Completing Time\t Waiting Time\tTurn around time\n");
    float avgW=0.0, avgTat=0.0,cpu, tp;
    for(int i=0;i<n;i++){
        printf("%d\t %d\t\t %d\t\t %d\t\t\t %d\t\t\t %d\n",jobs[i]->pid,jobs[i]->arrival,jobs[i]->burst,jobs[i]->complete, jobs[i]->waiting,
        jobs[i]->turnaround);
        avgW += jobs[i]->waiting;
        avgTat += jobs[i]->turnaround;

    }
    avgW = avgW/n;
    avgTat = avgTat/n;
    cpu = 100;
    tp = currTime/n;
    printf("Avg Waiting Time: %d\nAvg Turnaround time: %d\nCPU Utilization: %d\nThroughput: %d\n",
    avgW, avgTat, cpu, tp);
}


int main(){
    int n;
    scanf("%d", &n);
    Job* jobs[n];
    for(int i=0;i<n;i++){
        int at,bt;
        scanf("%d %d", &at, &bt);
        jobs[i] = create(at,bt,i);

    }
    int tb;
    printf("Enter time bound: ");
    scanf("%d", &tb);

    jobScheduling(jobs, n, tb);
    return 0;
}