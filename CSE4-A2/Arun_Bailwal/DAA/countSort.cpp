#include<bits/stdc++.h>
using namespace std;

void countSort(vector<int> &v){
    int max_val = *max_element(v.begin(), v.end());
    vector<int> freq(max_val + 1, 0);
    
    for(int i = 0; i < v.size(); i++){
        freq[v[i]]++;
    }

    for(int i = 1; i <= max_val; i++){
        freq[i] += freq[i - 1];
    }

    vector<int> b(v.size());
    for(int i = v.size() - 1; i >= 0; i--){
        int idx = --freq[v[i]];
        b[idx] = v[i];
    }

    for(int i = 0; i < v.size(); i++){
        v[i] = b[i];
    }
}

int main(){
    int n;
    cin >> n;
    vector<int> v;
    for(int i = 0; i < n; i++){
        int x;
        cin >> x;
        v.push_back(x);
    }
    countSort(v);
    for(int i: v){
        cout << i << " ";
    }
}