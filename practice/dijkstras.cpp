#include<bits/stdc++.h>
using namespace std;


void dijkstra(vector<vector<int>> &adj, int s){
    int v= adj.size();
    vector<int> distance(v, INT_MAX);
    distance[s] = 0;
    priority_queue<pair<int,int>, vector<pair<int,int>> , greater<pair<int,int>>> q;
    q.push({0,s});
    while(!q.empty()){
        int currdis = q.top().first;
        int source  = q.top().second;
        q.pop();
        for(int i=0;i<v;i++){
            if(adj[source][i] != INT_MAX){
                distance[i] = min(distance[i], currdis + adj[source][i]);
                q.push({distance[i], i});
            }
        }

    }

    for(auto i: distance){
        cout<<i<<"  ";
    }

}

int main(){
    int v,e;
    cin>>v>>e;
    vector<vector<int>> adj(v, vector<int> (v, INT_MAX));
    for(int i=0;i<e;i++){
        int s,d,w;
        cin>>s>>d>>w;
        adj[s][d] = w;
    }
    dijkstra(adj, 0);


}