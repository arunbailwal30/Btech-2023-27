#include<iostream>
#include<vector>
using namespace std;

void selectionSort(vector<int> &arr, int &swaps, int &comparisons){
    for(int i=0;i<arr.size()-1;i++){
        int minIdx = i;
        for(int j = i+1;j<arr.size();j++){
            comparisons++;
            if(arr[j]<arr[minIdx]){
                minIdx = j;
                
            }
        }
        swap(arr[minIdx], arr[i]);
        swaps++;
    }
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
        selectionSort(arr, swaps, comparisons);
        for(int i=0;i<arr.size();i++){
            cout<<arr[i]<<" ";
        }
        cout<<"\nNo. comparisons: "<<comparisons<<"\nNo. of swaps: "<<swaps<<endl;
    }
}