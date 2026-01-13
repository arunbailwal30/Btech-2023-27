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


void checkMax(Job* jobs[],int n, int *iMax, int *Max, int currTime){
    for(int i = 0; i < n; i++){
        if(jobs[i]->remaining > 0 && jobs[i]->arrival <= currTime && jobs[i]->remaining > *Max){
            *Max = jobs[i]->remaining;
            *iMax = i;
        }
    }
}

int allDone(Job* jobs[], int n){
    for(int i = 0; i < n; i++){
        if(jobs[i]->remaining > 0) return 0;
    }
    return 1;
}

void jobScheduling(Job* jobs[], int n){
    int iMax, Max;
    int currTime = 0;
    while(!allDone(jobs,n)){
        iMax = 0, Max=-1;
        checkMax(jobs, n, &iMax, &Max, currTime);
        if(jobs[iMax]->remaining > 0 && jobs[iMax]->arrival <= currTime){
            // printf("PID: %d\n",jobs[iMax]->pid);
            jobs[iMax]->remaining = 0;
            currTime+= jobs[iMax]->burst;
            if(jobs[iMax]->remaining <=0 ){
                 jobs[iMax]->complete = currTime-jobs[iMax]->arrival;
                jobs[iMax]->waiting = jobs[iMax]->complete - jobs[iMax]->burst;

            }
            

        }


    }

    printf("PID\t Arrival Time\t Burst Time\t Completing Time\t Waiting Time\t\n");
    for(int i=0;i<n;i++){
        printf("%d\t %d\t\t %d\t\t %d\t\t\t %d\n",jobs[i]->pid,jobs[i]->arrival,jobs[i]->burst,jobs[i]->complete, jobs[i]->waiting);

    }


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
    jobScheduling(jobs, n);
    return 0;
}