#include<stdio.h>
#include<stdlib.h>

typedef struct Job{
    int arrival,burst,remaining,turnaround, waiting,priority,completion,start;
    int pid;


}Job;

Job* createJob(int at, int bt, int prior, int pid){
    Job* temp = (Job*)malloc(sizeof(Job));
    temp->pid = pid;
    temp->arrival =at;
    temp->burst = bt;
    temp->remaining = bt;
    temp->waiting =0;
    temp->priority = prior;
    temp->turnaround = 0;
    temp->completion = 0;
    temp->start = 0;
    return temp;
}

int allDone(Job* jobs[], int n){
    for(int i=0;i<n;i++){
        if(jobs[i]->remaining > 0) return 0;
    }
    return 1;
}

int find(Job* jobs[], int n, int currTime){
    int maxPriority = -1;
    int ind=-1;
    for(int i =0;i<n;i++){
        if(maxPriority < jobs[i]->priority && currTime >= jobs[i]->arrival && jobs[i]->remaining>0){
            maxPriority = jobs[i]->priority;
            ind = i;
        }
    }
    return ind;

}


void ps(Job* jobs[], int n){
    int currTime = 0;
    float avgW=0,avgTat=0,cpu=0l,tp;
    printf("Gantt's chart: ");
    while(!allDone(jobs, n)){
        int i = find(jobs, n,currTime);
        if(i == -1){
            currTime++;
            continue;
        }
        printf("P%d ",jobs[i]->pid);
        jobs[i]->start = currTime;
        cpu += jobs[i]->burst; 
        currTime += jobs[i]->burst;
        jobs[i]->remaining = 0;
        jobs[i]->completion = currTime;
        jobs[i]->turnaround = currTime - jobs[i]->arrival;
        jobs[i]->waiting = jobs[i]->turnaround - jobs[i]->burst;
        jobs[i]->priority = -1;

    }
    
    printf("\nProcess \tArrival Time\tBurst Time\tResponse Time\tStart Time\tCompletion Time \t Turnaround Time \t Waiting Time\n");
    for(int i =0;i<n;i++){
        printf("P%d\t\t%d\t\t%d\t\t%d\t\t%d\t\t%d\t\t\t%d\t\t\t%d\n",jobs[i]->pid, jobs[i]->arrival, jobs[i]->burst,
        jobs[i]->start-jobs[i]->arrival,jobs[i]->start,jobs[i]->completion,jobs[i]->turnaround, jobs[i]->waiting);
        avgW += jobs[i]->waiting;
        avgTat += jobs[i]->turnaround;
    }
    avgW = avgW/n;
    avgTat = avgTat/n;
    cpu = cpu/(currTime)*100;
    tp = ((float)n)/currTime;
    printf("Avg Waiting Time: %.2f\nAvg Turnaround time: %.2f\nCPU Utilization: %.2f\nThroughput: %.2f\n",
    avgW, avgTat, cpu, tp);
}


int main(){
    int n;
    scanf("%d", &n);
    Job* jobs[n];
    for(int i=0;i<n;i++){
        int at,bt,pt;
        scanf("%d %d %d", &at,&bt, &pt);
        jobs[i] = createJob(at,bt,pt ,i+1);

    }

    ps(jobs,n);
    return 0;
}