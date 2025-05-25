#include<iostream>
#include<vector>
#include<cmath>
using namespace std;

int linearSearch(int arr[], int start, int end, int key){
    for(start; start<=end ;start++){
        if(arr[start] == key) return start;
    }
    return -1;
}

int jumpSearch(int arr[], int key, int n){
    int window = sqrt(n), prev = 0;
    int next = window;

    while (next < n) {
        if (arr[next] == key) {
            return next;
        }
        if (arr[next] > key) {
            return linearSearch(arr, prev, next, key);
        }
        prev = next + 1;
        next = next + window;
    }

    if (prev < n) {
        return linearSearch(arr, prev, n - 1, key);
    }

    return -1; 
}

int binarySearch(int arr[], int key, int start, int end)
{
    if (start > end) return -1;
    int mid = start + (end - start) / 2;
    if (arr[mid] == key) return mid;
    if (arr[mid] < key) return binarySearch(arr, key, mid + 1, end);
    else return binarySearch(arr, key, start, mid - 1 );
}

int exponentialSearch(int arr[], int n , int key){
    int window = sqrt(n) ,prev = 0;
    int next = window;

    while(next < n){
        if(arr[next] == key) return next;
        if(arr[next] > key) return binarySearch(arr,key, prev, next);
        prev = next+1;
        next+= window;
    }
    if(prev<n){
        return binarySearch(arr, key, prev, n-1);
    }
    return -1;
}

int main(){
    int n;
    int key;
    cout<<"enter size and key: ";
    cin>>n>>key;
    int arr[n];
    for(int i = 0;i<n;i++)
        cin>>arr[i];
    
    cout<<binarySearch(arr, key, 0, n-1);


}