//Given an array of nonnegative integers, design an algorithm and a program to count the number
// of pairs of integers such that their difference is equal to a given key, K. 
#include<iostream>
#include<vector>
#include<algorithm>
using namespace std;

int pairs(vector<int> arr, int key){
    int count=0;
    for(int i =0;i<arr.size()-1;i++){
        for(int j=i+1; j<arr.size();j++){
            if(abs(arr[i]-arr[j]) == key){
                count++;
            }
        }
    }
    return count;
}

int main(){
    int t;
    cin>>t;
    for(int j=0;j<t;j++){
        int n;
        cout<<"Enter size of array: ";
        cin>>n;
        vector<int> arr(n);
        for(int i=0;i<n;i++)
            cin>>arr[i];
        int key;
        cin>>key;
        cout<<"Enter the key: ";
        cout<<"Total pairs: "<<pairs(arr, key);        
    }
}