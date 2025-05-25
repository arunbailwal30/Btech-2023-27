#include<iostream>
using namespace std;

int binarySearch(int arr[], int n,int key, int &comparisons){
    int start =0, end = n-1;

    while(start<=end){
        int mid = start + (end-start)/2;
        if(arr[mid] == key){
            comparisons++;
            return mid;
        }else if(arr[mid] > key){
            end = mid-1;
        }else{
            start = mid+1;
        }
        comparisons++;
    }
    return INT8_MIN;
}

int main(){
    int n;
    cout<<"Enter size of array: ";
    cin>>n;
    int arr[n];
    for(int i =0;i<n;i++){
        cin>>arr[i];
    }
    int key, comparisons=0;
    cout<<"Enter key: ";
    cin>>key;

    int pos = binarySearch(arr, n, key, comparisons);
    if(pos == INT8_MIN){
        cout<<"element not found"<<endl;
        return 0;
    }
    cout<<"Element at position : "<<pos+1<<endl;
    cout<<"No. of comparisons: "<<comparisons<<endl;

    return 0;
}