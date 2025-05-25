#include<iostream>
#include<vector>
using namespace std;
void merge(vector<int> &arr, int start, int mid, int end, int &comparisons, int &inversions){
    vector<int> temp;
    int i = start, j = mid+1;
    while(i<=mid and j<=end){
        if(arr[i] <= arr[j]){
            temp.push_back(arr[i]);
            i++;
        }else{
            temp.push_back(arr[j]);
            j++;
            inversions++;
        }
        comparisons++;
    }
    while(i<=mid){
        temp.push_back(arr[i]);
        i++;
    }
    while(j<=end){
        temp.push_back(arr[j]);
        j++;
    }
    for(int k = 0 ; k< temp.size();k++){
        arr[k+start] = temp[k];
    }
}

void mergeSort(vector<int> &arr,int start, int end, int &comparisons, int &inversions){
    if(start<end){
        int mid = start+ (end-start)/2;
        mergeSort(arr, start, mid, comparisons, inversions);
        mergeSort(arr, mid+1, end, comparisons, inversions);
        merge(arr, start, mid, end, comparisons, inversions);
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
        int inversions=0, comparisons=0;
        mergeSort(arr, 0, n-1, comparisons, inversions);
        for(int i=0;i<arr.size();i++){
            cout<<arr[i]<<" ";
        }
        cout<<"\nNo. comparisons: "<<comparisons<<"\nNo. of inversions: "<<inversions<<endl;
    }
}