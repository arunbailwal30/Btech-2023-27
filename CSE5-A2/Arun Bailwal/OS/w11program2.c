#include <stdio.h>
#include <stdlib.h>
#define LOW 0
#define HIGH 199

int main(){
    int n;
    printf("Enter number of disk requests: ");
    scanf("%d", &n);
    int req[n];
    printf("Enter disk requests: ");
    for(int i = 0; i < n; i++){
        scanf("%d", &req[i]);
    }

    int head;
    printf("Enter head location: ");
    scanf("%d", &head);

    // Sort the requests
    for(int i = 0; i < n; i++)
        for(int j = i + 1; j < n; j++)
            if(req[i] > req[j]){
                int temp = req[i];
                req[i] = req[j];
                req[j] = temp;
            }

    // Find the division point
    int index = 0;
    while(index < n && req[index] < head) index++;

    int movement = 0;
    int curr = head;

    // Move right (towards HIGH)
    for(int i = index; i < n; i++){
        movement += abs(req[i] - curr);
        curr = req[i];
    }
    // Go to HIGH if not already there
    if(curr != HIGH) {
        movement += abs(HIGH - curr);
        curr = HIGH;
    }
    // Move left (towards LOW)
    for(int i = index - 1; i >= 0; i--){
        movement += abs(req[i] - curr);
        curr = req[i];
    }

    printf("Total seek movement: %d\n", movement);
    printf("Average seek movement: %.2f\n", (float)movement / n);
    return 0;
}
