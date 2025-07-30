// given deadline and profit time, compute a schedule whre the largest number of acitivies take place
#include<bits/stdc++.h>
using namespace std;

bool feasible(set<int> &freq, int t){
    for(int i = t ;i > 0 ;i--){
        if(freq.count(i) == 0){freq.insert(i); return true; }
    }
    return false;
}

void jobScheduling(vector<int> profit, vector<int> dead, int n){
    vector<pair<int, pair<int, int >>> job;
    for(int i =0;i<n;i++){
        job.push_back({profit[i], {dead[i], i+1}});
    }
    set<int> freq;
    sort(job.rbegin(), job.rend());

    for(int i=0;i<n;i++){
        if(feasible(freq, job[i].second.first)){
            cout<<"job - "<<job[i].second.second<<endl;
        }
    }
}

int main(){
    int n;
    cin>>n;

    vector<int> deadline(n);
    vector<int> profit(n);
    for(int i = 0;i<n;i++ ) cin>>deadline[i];
    for(int i = 0;i<n;i++ )cin>>profit[i];
    jobScheduling(profit, deadline, n);
    return 0;
}