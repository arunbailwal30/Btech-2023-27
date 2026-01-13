//SJF with preemption
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
        if(jobs[i]->remaining > 0 && jobs[i]->arrival <= currTime && jobs[i]->remaining < *Min){
            *Min = jobs[i]->remaining;
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

void sjf_p(Job* jobs[], int n){
    int iMin, Min;
    int currTime = 0;
    float avgW=0, avgTat=0,cpu=0, tp;
    printf("Gantt's chart: ");
    while(!allDone(jobs,n)){
        iMin = -1, Min=9999;
        checkMin(jobs, n, &iMin, &Min, currTime);
        if(iMin == -1){
            currTime++;
            continue;
        }
        if(jobs[iMin]->remaining > 0 && jobs[iMin]->arrival <= currTime){
            printf("P%d ",jobs[iMin]->pid);
            currTime++;
            cpu++;
            jobs[iMin]->remaining--;
            jobs[iMin]->complete = currTime;
            jobs[iMin]->turnaround = currTime-jobs[iMin]->arrival;
            jobs[iMin]->waiting = jobs[iMin]->complete - jobs[iMin]->burst;
        }

    }

    printf("\nPID\t Arrival Time\t Burst Time\t Completing Time\t Waiting Time\tTurn around time\n");
    
    for(int i=0;i<n;i++){
        printf("%d\t %d\t\t %d\t\t %d\t\t\t %d\t\t\t %d\n",jobs[i]->pid,jobs[i]->arrival,jobs[i]->burst,jobs[i]->complete, jobs[i]->waiting,
        jobs[i]->turnaround);
        avgW += jobs[i]->waiting;
        avgTat += jobs[i]->turnaround;

    }
    avgW = avgW/n;
    avgTat = avgTat/n;
    cpu = cpu/(currTime)*100;
    tp = ((float)n)/currTime;
    printf("Avg Waiting Time: %.2fs\nAvg Turnaround time: %.2fs\nCPU Utilization: %.2f percent\nThroughput: %.2f\n",
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
    sjf_p(jobs, n);
    return 0;
}