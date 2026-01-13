#include<iostream>
#include<vector>
#include<algorithm>
using namespace std;

void activitySelection(vector<int> start, vector<int> end ,int n){

    vector<pair<int,pair<int,int>>> activity;
    for(int i=0;i<n;i++){
        activity.push_back({end[i],{start[i],i+1}});
    }
    sort(activity.begin(),activity.end());
    vector<pair<int,pair<int,int>>> ans;
    ans.push_back({activity[0].second.second,{activity[0].second.first, activity[0].first}});
    int count =0;
    for(int i=1;i<n;i++){
         int currentEnd =   ans[count].second.second;
        if(activity[i].second.first > currentEnd){
            ans.push_back({activity[i].second.second,{activity[i].second.first,activity[i].first}});
            count++;
        }
        
        
    }
    cout<<"Total activities: "<<ans.size()<<endl;
    cout<<"List of selected activities: ";
    for(auto i: ans){
            cout<<i.first<<" ";
    }
    cout<<endl;


}


int main(){

    int n;
    cin>>n;
    vector<int> start(n);
    vector<int> end(n);
    for(int i=0;i<n;i++){
        cin>>start[i];
    }
    for(int i=0;i<n;i++){
        cin>>end[i];
    }
    activitySelection(start,end,n);

}