#include<bits/stdc++.h>
using namespace std;

void RadixSort(vector<int> &arr) {

    for(auto itr = arr.begin(); itr != arr.end(); ++itr) {

        reverse(itr, min_element(itr, arr.end())+1);
    }
}

int main(){
    int n;
    cin>>n;
    vector<int> arr(n);
    for(int i=0;i<n;i++){
        cin>>arr[i];
    }

    RadixSort(arr);
    for(int i=0;i<n;i++){
        cout<<arr[i]<<" ";
    }
    return 0;
}