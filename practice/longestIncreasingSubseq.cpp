#include<bits/stdc++.h>
using namespace std;

int lisEndingAtIdx(int arr[], int i){
    if(i==0) return 1;
    int maxi = 1;
    for(int prev= 0;prev <i ; prev++){
        if(arr[prev]  < arr[i]){
            maxi = max(maxi, lisEndingAtIdx(arr, prev)+1);
        }
    }
    return maxi;
}

int naive(int arr[], int n){
    int res = 1;
    for(int i=1;i<n;i++){
        res = max(res,lisEndingAtIdx(arr, i));
    }
    return res;
    
}

int memorizationHelper(int arr[], int i, vector<int> &memo){
     if(i ==0 )return 1;
    if(memo[i] != -1) return memo[i];

    int mx = 1;
    for(int prev= 0;prev <i ; prev++){
        if(arr[prev]  < arr[i]){
            mx = max(mx, memorizationHelper(arr, prev, memo)+1);
        }
        
    }
    memo[i] = mx;
    return mx;
}

int memorization(int arr[], int n){
    vector<int> memo(n,-1);
    int res = 1;
    for(int i=1 ;i<n;i++){
        res = max(res, memorizationHelper(arr, i, memo));
    }
    return res;
}


int dynamicProgramming(int arr[], int n){
    vector<int> dp(n,1);
    for(int i=1;i<n;i++){
        for(int j = 0;j<i;j++){
            if(arr[j] < arr[i]){
                dp[i] = max(dp[i], dp[j]+1); 
            }
        }
    }
    return *max_element(dp.begin(), dp.end());
}


int main(){


    int n;
    cin>>n;
    int arr[n];
    for(int i =0;i<n;i++) cin>>arr[i];
    cout<<" Max length = "<<memorization(arr,n)<<endl;

}