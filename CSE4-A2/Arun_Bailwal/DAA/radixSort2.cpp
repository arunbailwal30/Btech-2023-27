#include<iostream>
#include<vector>
#include<algorithm>
using namespace std;


void radixSort(vector<int> &v){
    for(int i=0;i<v.size();i++){
        auto min = min_element(v.begin()+i, v.end());
        reverse(v.begin()+i, min+1);
    }
}

void radixSort2(vector<int> &v){
    int max = *max_element(v.begin(), v.end());
    
    vector<int> b(v.size());
    for(int exp = 1; max/exp > 0; exp*=10){
        vector<int> freq(10, 0);
        for(int i = 0; i < v.size(); i++)
            freq[(v[i]/exp)%10]++;
        
        for(int i = 1; i < 10; i++)
            freq[i] += freq[i-1];
        
        for(int i = v.size()-1; i >= 0; i--){
            int idx = --freq[(v[i]/exp)%10];
            b[idx] = v[i];
        }
        v = b;
    }  
}

int main(){
    vector<int> v;
    int n;
    cin>>n;
    for(int i= 0;i<n;i++){
        int x;
        cin>>x;
        v.push_back(x);
    }
    radixSort2(v);
    for(int i: v){
        cout<<i<<" ";
    }
}