int main(){
    printf("Hello World\n");
    fork(); // Create a new process
    fork(); // Create another new process // 
    printf("Hello World\n"); // it is responsibility of the parent to clear the resources of the child process
    sleep(1); // Sleep for 1 second to ensure all processes have time to print
    return 0;   
}