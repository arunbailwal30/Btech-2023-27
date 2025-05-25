#include<bits/stdc++.h>
using namespace std;

void merge(vector<int> &v, int low ,int mid, int high){
    vector<int> temp;
    int i = low, j = mid+1;
    while(i<=mid and j<=high){
        if(v[i] <= v[j]){
            temp.push_back(v[i]);
            i++;
        }else{
            temp.push_back(v[j]);
            j++;
        }
    }
    while(i<=mid){
        temp.push_back(v[i]);
        i++;
    }
    while(j<=high){
        temp.push_back(v[j]);
        j++;
    }

    for(int i = 0 ;i< temp.size();i++){
        v[i+low] = temp[i];
    }
}

void mergeSort(vector<int> &v, int low, int high){

    if(low<high){
        int mid = (low+high)/2;
        mergeSort(v, low, mid);
        mergeSort(v, mid+1, high);
        merge(v, low, mid, high);
    }
}

int main(){
    vector<int>v;

    int n;
    cin>>n;
    for(int i = 0;i<n;i++){
        int x;
        cin>>x;
        v.push_back(x);
    }

    mergeSort(v, 0, v.size()-1);
    for(int i: v){
        cout<<i<<" ";
    }


}