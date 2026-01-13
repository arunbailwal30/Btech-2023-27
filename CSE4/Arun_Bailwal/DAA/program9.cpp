#include<iostream>
#include<vector>
using namespace std;

void merge(vector<int> &arr, int start, int mid, int end){
    vector<int> temp;
    int i = start, j = mid+1;
    while(i<=mid and j<=end){
        if(arr[i] <= arr[j]){
            temp.push_back(arr[i]);
            i++;
        }else{
            temp.push_back(arr[j]);
            j++;
        }
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

void mergeSort(vector<int> &arr,int start, int end){
    if(start<end){
        int mid = start+ (end-start)/2;
        mergeSort(arr, start, mid);
        mergeSort(arr, mid+1, end);
        merge(arr, start, mid, end);
    }
}
bool hasDuplicates(vector<int>arr){
    for(int i =0;i<arr.size()-1;i++){
        if(arr[i]==arr[i+1]) return true;
    }
    return false;
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
        mergeSort(arr,0,n-1);
        if(hasDuplicates(arr)){
            cout<<"YES"<<endl;
        }else cout<<"NO"<<endl;
    }
}