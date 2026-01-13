#include<iostream>
using namespace std;
int maxElement(int arr[],int n){
    int max = arr[0];
    for(int i = 1;i<n;i++){
        if(arr[i]>max){
            max = arr[i];
        }
    }
    return max;
}
int minElement(int arr[],int n){
    int min = arr[0];
    for(int i = 1;i<n;i++){
        if(arr[i]<min){
            min = arr[i];
        }
    }
    return min;
}


void countSort(int arr[], int n){
    int max_val = maxElement(arr, n);
    int min_val = minElement(arr, n);
    int size = max_val-min_val+1;
    int freq[size] = {0};
    

    for (int i = 0; i < n; i++) {
        freq[arr[i] - min_val]++;
    }

    for (int i = 1; i < size; i++) {
        freq[i] += freq[i - 1];
    }

    int b[n];
    for (int i = n - 1; i >= 0; i--) {
        int idx = --freq[arr[i] - min_val];
        b[idx] = arr[i];
    }

    
    for (int i = 0; i < n; i++) {
        arr[i] = b[i];
    }
}

int smallest(int arr[], int n, int k){
    return arr[k+1];
}
int largest(int arr[], int n, int k){
    return arr[n-k];
}

int main(){
    int t;
    cin>>t;
    for(int j =0;j<t;j++){
        int n;
        cout<<"Enter size of array: ";
        cin>>n;
        int arr[n];
        for(int i=0;i<n;i++){
            cin>>arr[i];
        }
        int k;
        cout<<"Enter position: ";
        cin>>k;
        countSort(arr, n);
        cout<<"kth smallest number: "<<smallest(arr, n,k);
        cout<<"kth largest number: "<<largest(arr, n,k);

        for(int i =0;i<n;i++){
            cout<<arr[i]<<" ";
        }

    }
}