#include<iostream>
#include<cmath>
#include<vector>
#include<bits/stdc++.h>
using namespace std;
int binarySearch(vector<int> &arr, int x, int l, int r) {
    while (l <= r) {
        int m = l + (r - l) / 2;
        if (arr[m] == x)
        return m;
        if (arr[m] < x)
        l = m + 1;
        else
        r = m - 1;
    }
        return -1;
}
    
int exponentialSearch(vector<int> &arr, int x) {
    int pow = 2;
    int prev = 0, next = 2;
    while(next< arr.size()){
        if(arr[next] == x){
            return next;
        }
        if(arr[next] > x){
            return binarySearch(arr, x, prev, next);
        }
        prev = next+1;
        next = next*pow;
    }
    if(prev<arr.size()) return binarySearch(arr, x, prev, arr.size()-1);
    return -1;
}

int main(){
    int n,x;
    std::cin >> n >> x;
    std::vector<int> a(n);
    for(int i = 0; i < n; i++){
        std::cin >> a[i];
    }
    cout<<exponentialSearch(a, x)<<endl;
}