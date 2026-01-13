#include<iostream>
#include<vector>
#include<algorithm>
using namespace std;

bool twoSubsetSum(vector<int> arr, int sum ,int n){
    if(sum == 0) return true;
    if(n==0) return false;
    if(arr[n-1]>sum) return twoSubsetSum(arr,sum,n-1);
    else return twoSubsetSum(arr,sum-arr[n-1],n-1)|| twoSubsetSum(arr, sum, n - 1);
}

int main(){
    int n;
    cin>>n;
    vector<int> arr(n);
    for(int i=0;i<n;i++){
        cin>>arr[i];
    }

    int total = 0;
    for(int i: arr) total += i;
    twoSubsetSum(arr, total/2,n)? cout<<"yes": cout<<"no";

}