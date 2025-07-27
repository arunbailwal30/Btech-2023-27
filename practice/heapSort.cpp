#include<iostream>
#include<vector>
using namespace std;

void heapify(vector<int> &arr, int i, int n){
    int left = i*2 +1, right = i*2+2;
    int greatest = i;
    if(left < n and arr[i] < arr[left] ){
        greatest = left;
    }if(right < n and arr[greatest] < arr[right]){
        greatest = right;
    }
    if(greatest != i){
        swap(arr[i], arr[greatest]);
        heapify(arr, greatest, n);
    }
}

void heapSort(vector<int> &arr){
    int n = arr.size();
    for(int i = n/2-1;i>=0;i--){
        heapify(arr, i, n);
    }

    for(int i= n-1;i>=0;i--){
        swap(arr[i], arr[0]);
        heapify(arr, 0, i);
    }
}


int main(){
    int n;
    cin>>n;
    vector<int> arr(n);
    for(int i=0;i<n;i++){
        cin>>arr[i];
    }

    heapSort(arr);
    cout<<endl;
    for(auto i: arr){
        cout<<i<<" ";
    }
}