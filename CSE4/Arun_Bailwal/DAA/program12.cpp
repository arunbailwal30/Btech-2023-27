#include<bits/stdc++.h>
using namespace std;

int partition(vector<int> &arr, int low, int high){
    int i = low-1;
    int pivot = arr[high];
    for(int j = low; j< high;j++){
        if(arr[j] < pivot){
            i++;
            swap(arr[i], arr[j]);
        }
    }
    swap(arr[i+1], arr[high]);
    return i+1;
}

int quickSelect(vector<int> &arr, int low, int high, int k){
    if(low == high) return arr[low];
    if(k>0 and k<= high-low+1 and low<=high){
        int p = partition(arr,low, high);
        if(k-1+low == p){
            return arr[p];
        }
        if(k-1+low < p)
            return quickSelect(arr, low , p-1,k);
        else
            return quickSelect(arr, p+1, high,k - (p-low+1));
    }
    return INT_MIN;    
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
        int k;
        cout<<"Enter the key: ";
        cin>>k;
        int ele = quickSelect(arr, 0, n-1, k);
        if(ele != INT_MIN){
            cout<<"The smallest kth element: "<<ele<<endl;
        }else{
            cout<<"Not present"<<endl;
        }
    }
}