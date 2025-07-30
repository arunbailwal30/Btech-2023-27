#include<dirent.h>
#include<stdio.h>
#include<stdlib.h>


int main(){
    DIR *dir;struct dirent *entry;
    dir = opendir(".");
    if(dir == NULL){
        printf("Error");
        return 1;
    }

    while((entry = readdir(dir)) != NULL){
        printf("%s\n", entry->d_name);
    }
    closedir(dir);

}