#include<bits/stdc++.h>
using namespace std;

void dijkstra(vector<vector<pair<int,int>>> adj, int V){

    vector<int> dist(V, INT_MAX);
    priority_queue<pair<int, int> , vector<pair<int,int>> , greater<pair<int,int>>> pq;
    dist[0] = 0 ;
    pq.push({0,0});
    while(!pq.empty()){
        int currDist = pq.top().first;
        int u = pq.top().second;
        pq.pop();

        for(auto i: adj[u]){
            if(dist[i.first] > currDist + i.second){
                dist[i.first] = currDist+ i.second;
                pq.push({dist[i.first], i.first});
            }
        }

    }

    for(auto i : dist) cout<< i<< "   ";


}


int main(){
    int v,e;
    cin>>v>>e;
    vector<vector<pair<int, int>>> adj(v);
    for(int i= 0 ;i< e;i++){
        int s,d,w;
        cin>>s>>d>>w;
        adj[s].push_back({d,w});
    }

    dijkstra(adj, v);

    
}