#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

#define N 3

sem_t chopstick[N];  

void *philosopher(void *num) {
    int id = *(int *)num;

    while (1) {
        printf("Philosopher %d is THINKING\n", id);
        sleep(3);
        
        int left = id;
        int right = (id + 1) % N;

        if (sem_trywait(&chopstick[left]) == 0) {         // try left
            if (sem_trywait(&chopstick[right]) == 0) {    // try right
                printf("Philosopher %d is EATING\n", id);
                sleep(2); // eating

                sem_post(&chopstick[right]);
                sem_post(&chopstick[left]);
                printf("Philosopher %d finished EATING\n", id);
            } else {
                sem_post(&chopstick[left]);
            }
        }

        usleep(100);
    }
}

int main() 
{
    pthread_t tid[N];
    int ids[N];

    // Initialize semaphores
    for (int i = 0; i < N; i++)
        sem_init(&chopstick[i], 0, 1);

    // Create philosopher threads
    for (int i = 0; i < N; i++) {
        ids[i] = i;
        pthread_create(&tid[i], NULL, philosopher, &ids[i]);
    }

    // Join threads (runs forever)
    for (int i = 0; i < N; i++)
        pthread_join(tid[i], NULL);

    return 0;
}
