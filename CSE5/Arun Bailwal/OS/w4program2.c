// round robin
#include<stdio.h>
#include<stdlib.h>
typedef struct Job
{
    int pid;
    int arrival,burst;
    int waiting,complete,turnaround,remaining; 
}Job;

typedef struct Queue{
    int front,rear;
    Job* arr[1000];
}Queue;

Queue* createQueue(){
    Queue* temp = (Queue*)malloc(sizeof(Queue));
    temp->front = -1;
    temp->rear = -1;
    return temp;
}

void push(Queue* q, Job* j){
    if(q->rear == 999) return;
    if(q->front == -1) q->front = 0;
    q->arr[++(q->rear)] = j;
}
Job* pop(Queue* q){
    if(q->front == -1 || q->front > q->rear) return NULL;
    Job* temp;
    temp = q->arr[q->front];
    q->front++;
    return temp;
}

Job* create(int at, int bt, int id){
    Job*  temp = (Job*) malloc(sizeof(Job));
    temp->pid = id;

    temp->arrival = at;
    temp->burst = bt;
    temp->waiting = 0;
    temp->complete = 0;
    temp->turnaround = 0;
    temp->remaining = bt;
    return temp;
}


void checkMin(Job* jobs[],int n, int *iMin, int *Min, int currTime){
    for(int i = 0; i < n; i++){
        if(jobs[i]->remaining > 0 && jobs[i]->arrival <= currTime && jobs[i]->arrival < *Min){
            *Min = jobs[i]->arrival;
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

void RoundRobin(Job* jobs[], int n, int tb){
    int iMin, Min;
    int currTime = 0;
    printf("Gantt's chart: ");
    Queue* q = createQueue();
    float avgW=0.0, avgTat=0.0,cpu=0.0, tp=0.0;
    while(!allDone(jobs,n)){
        iMin = -1; Min = 1e9;
        checkMin(jobs,n,&iMin,&Min,currTime);
        if(iMin == -1){
            currTime++;
            continue;
        }
        push(q,jobs[iMin]);
        Job* currJob = pop(q);
        if(currJob->remaining > tb){
            currJob->remaining -= tb;
            currTime += tb;
            cpu += tb;
            printf("P%d ", currJob->pid);
            for(int i = 0; i < n; i++){
                if(jobs[i]->arrival <= currTime && jobs[i]->remaining > 0 && jobs[i]->pid != currJob->pid){
                    push(q,jobs[i]);
                }
            }
            push(q,currJob);
        }
        else{
            cpu = cpu + currJob->remaining;
            currTime += currJob->remaining;
            printf("P%d ", currJob->pid);
            currJob->remaining = 0;
            currJob->complete = currTime;
            currJob->turnaround = currJob->complete - currJob->arrival;
            currJob->waiting = currJob->turnaround - currJob->burst;
            for(int i = 0; i < n; i++){
                if(jobs[i]->arrival <= currTime && jobs[i]->remaining > 0 && jobs[i]->pid != currJob->pid){
                    push(q,jobs[i]);
                }
            }
        }
    }

    printf("\nPID\t Arrival Time\t Burst Time\t Completing Time\t Waiting Time\tTurn around time\n");
    
    for(int i=0;i<n;i++){
        printf("%d\t %d\t\t %d\t\t %d\t\t\t %d\t\t\t %d\n",jobs[i]->pid,jobs[i]->arrival,jobs[i]->burst,jobs[i]->complete, jobs[i]->waiting,
        jobs[i]->turnaround);
        avgW += jobs[i]->waiting;
        avgTat += jobs[i]->turnaround;

    }
    avgW = (float)avgW/n;
    avgTat = (float)avgTat/n;
    cpu = (float)cpu/(currTime)*100;
    tp = (float)n/currTime;
    printf("Avg Waiting Time: %.2f\nAvg Turnaround time: %.2f\nCPU Utilization: %.2f\nThroughput: %.2f\n",
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

    RoundRobin(jobs, n, tb);
    return 0;
}