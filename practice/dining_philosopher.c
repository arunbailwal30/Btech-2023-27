#include<stdio.h>
#include<pthread.h>
#include<semaphore.h>
#include<unistd.h>

#define N 3

sem_t chopsticks[N];


void *philosopher(void *num){
    int id = *(int*)num;

    while(1){
        printf("Philosopher %d is Thinking\n",id);
        sleep(3);

        int left = id;
        int right = (id +1)%N;
        if(sem_trywait(&chopsticks[left])==0){ //try left
            if(sem_trywait(&chopsticks[right])==0){
                printf("Philosopher %d is eating\n ", id);
                sleep(2);

                sem_post(&chopsticks[left]);
                sem_post(&chopsticks[right]);
                printf("Philosopher %d done eating\n", id);
            }else{
                sem_post(&chopsticks[left]);
            }
        }

        usleep(100);
    }
}


int main(){
    pthread_t tid[N];

    int ids[N];

    //initialize semaphores
    for(int i=0;i<N;i++){
        sem_init(&chopsticks[i],0,1);
    }

    //create threads
    for(int i=0;i<N;i++){
        ids[i]=i;
        pthread_create(&tid[i],NULL, philosopher,&ids[i]);
    }

    //join
    for(int i=0;i<N;i++){
        pthread_join(tid[i],NULL);
    }
}