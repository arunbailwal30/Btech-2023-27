#include<bits/stdc++.h>
using namespace std;

void merge(vector<int> &arr, int low ,int mid, int high){
    vector<int> temp;
    int i = low, j = mid+1;
    while(i<=mid and j<=high){
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
    while(j<=high){
        temp.push_back(arr[j]);
        j++;
    }
    for(int i = 0 ;i< temp.size();i++){
        arr[i+low] = temp[i];
    }
}

void mergeSort(vector<int> &arr, int low, int high){
    if(low<high){
        int mid = (low+high)/2;
        mergeSort(arr, low, mid);
        mergeSort(arr, mid+1, high);
        merge(arr, low, mid, high);
    }
}

void givenSum(vector<int> arr, int key){
    for(int i=0;i<arr.size()-1;i++){
        int start= i+1, end= arr.size()-1;
        while(start<=end){
            int mid = start + (end-start)/2;
            if(arr[mid] + arr[i] == key){
                cout<<arr[mid]<<" "<<arr[i]<<endl;
                return;
            }
            if(arr[mid]+arr[i] < key){
                start = mid+1;
            }else{
                end = mid-1;
            }
        }
    }
    cout<<"No such elements exits"<<endl;
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
        cout<<"Ente the key: ";
        cin>>k;
        mergeSort(arr, 0, n-1);
        givenSum(arr, k);

    }
}