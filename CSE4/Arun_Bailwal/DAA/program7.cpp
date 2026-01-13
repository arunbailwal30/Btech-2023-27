/*Given an unsorted array of integers, design an algorithm and a program to sort the array using
insertion sort. Your program should be able to find number of comparisons and shifts ( shifts -
total number of times the array elements are shifted from their place) required for sorting the
array.*/
#include<bits/stdc++.h>
using namespace std;

void insertionSort(vector<int> &arr){
    int comparisons = 0, shifts=0;

    for(int i =1;i<arr.size();i++){
        int j=i-1;
        int key = arr[i];
        while(j>=0 and key< arr[j]){
            comparisons++;
            arr[j+1] = arr[j];
            shifts++;
            j--;
        }
        shifts++;
        arr[j + 1] = key;

        
    }
    for(int i=0;i<arr.size();i++){
        cout<<arr[i]<<" ";
    }
    cout<<"\ncomparisons = "<<comparisons<<"\nshifts = "<<shifts<<endl;
}

int main(){
    int t;
    cin>>t;
    for(int j=0;j<t;j++){
        int n;
        cout<<"Enter size of array: ";
        cin>>n;
        vector<int> arr(n);
        for(int i =0;i<n;i++)
            cin>>arr[i];
        
        insertionSort(arr);
        
    }
}