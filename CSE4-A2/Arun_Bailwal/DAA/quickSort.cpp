#include<bits/stdc++.h>
using namespace std;

int partition(vector<int> &v, int low, int high){
    int i = low-1;
    int pivot = v[high];
    for(int j = low; j< high;j++){
        if(v[j] < pivot){
            i++;
            swap(v[i], v[j]);
        }
    }
    swap(v[i+1], v[high]);
    return i+1;
}

void quickSort(vector<int> &v, int low, int high){
    if(low>=high || high-low == 1){
        return;
    }

    int p = partition(v,low, high);
    quickSort(v, low , p-1);
    quickSort(v, p+1, high);
}

int main(){
    int n;
    cin>>n;
    vector<int> v(n);
    for(int i =0;i<n;i++){
        cin>>v[i];
    }
    quickSort(v, 0 , v.size()-1);
    for(int i : v){
        cout<<i<<" ";
    }

}