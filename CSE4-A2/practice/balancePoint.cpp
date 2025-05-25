#include<iostream>
#include<vector>
using namespace std;

int balance(vector<int> arr){
    int n = arr.size();
    vector<int> min(n);
     min[0] = arr[0];
    for(int i=1;i<n;i++){
        min[i] = max(arr[i], min[i-1]);
    }

}

int main(){
    int n;
    cin>>n;
    vector<int> arr(n);
    for(int i=0;i<n;i++){
        cin>>arr[i];
    }
    cout<<balance(arr);


    return 0;
}