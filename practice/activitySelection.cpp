// given start and finish time, compute a schedule whre the largest number of acitivies take place
#include<bits/stdc++.h>
using namespace std;

void activitySelection(vector<int> start, vector<int> end, int n){
    vector<pair<int,pair<int,int>>> act;
    for(int i = 0;i< n ;i++){
        act.push_back({end[i],{start[i], i+1}});

    }
    sort(act.begin(), act.end());

    int lastE = -1;
    for(int i =0;i<n;i++){
        int currS = act[i].second.first;
        int currE = act[i].first;
        if(currS >= lastE ){
            cout<<"Activity - "<<act[i].second.second<<" Selected"<<endl;
            lastE = currE;
        }
    }
}


void recursiveAS(vector<int> start, vector<int> end, int n, int k){
    int m = k + 1;
    while (m < n) {
        if (start[m] >= end[k]) {
            cout << m + 1 << ",  ";
            recursiveAS(start, end, n, m);
            break; // Only select the next compatible activity
        }
        m++;
    }
}

int main(){
    int n;
    cin>>n;

    vector<int> start(n);
    vector<int> finish(n);
    for(int i = 0;i<n;i++ ) cin>>start[i];
    for(int i = 0;i<n;i++ )cin>>finish[i];
    // activitySelection(start, finish, n);
    recursiveAS(start, finish   , n ,0);
    return 0;
}