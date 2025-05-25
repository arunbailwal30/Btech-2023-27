#include<bits/stdc++.h>
using namespace std;

int partition(vector<int> &v, int low, int high, int &comparisons, int &swaps){
    int i = low-1;
    int pivot = v[high];
    for(int j = low; j< high;j++){
        if(v[j] < pivot){
            i++;
            swap(v[i], v[j]);
            swaps++;
        }
        comparisons++;
    }
    swap(v[i+1], v[high]);
    swaps++;
    return i+1;
}

void quickSort(vector<int> &v, int low, int high, int &comparisons, int &swaps){
    if(low>=high){
        return;
    }

    int p = partition(v,low, high, comparisons, swaps);
    quickSort(v, low , p-1, comparisons,swaps);
    quickSort(v, p+1, high, comparisons,swaps);
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
        int swaps=0, comparisons=0;
        quickSort(arr, 0, n-1, comparisons, swaps);
        for(int i=0;i<arr.size();i++){
            cout<<arr[i]<<" ";
        }
        cout<<"\nNo. comparisons: "<<comparisons<<"\nNo. of swaps: "<<swaps<<endl;
    }
}