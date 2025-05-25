#include<iostream>
#include<vector>
#include<queue>
using namespace std;

vector<int> dijkstra(int V, int start, vector<vector<pair<int,int>>> &graph){
    priority_queue<pair<int,int>, vector<pair<int,int>>, greater<pair<int,int>>> pq;
    vector<int> dist(V, INT8_MAX);
    dist[start] =0;
    pq.push({0,start});

    while (!pq.empty())
    {
        int currentDist = pq.top().first;
        int u = pq.top().second;
        pq.pop();
        for(auto it:graph[u]){
            int v = it.first;
            int w = it.second;
            if(dist[v] > currentDist +w){
                dist[v] = currentDist +w;
                pq.push({dist[v],v});
            }

        }    
    }
    return dist;
    
}

int main(){
    int vertices, edges;
    cout<<"Enter no. of vertices and edges: ";
    cin>>vertices>>edges;
    vector<vector<pair<int,int>>> graph(vertices);
    for(int i=0;i<edges;i++){
        int u,v,w;
        cout<<"Enter source, destination, weight: ";
        cin>>u>>v>>w;
        graph[u].push_back({v,w});
        // graph[v].push_back({u,w});
    }
    int source,destination;
    cout<<"Enter source: ";
    cin>>source;
    cout<<"Enter destination";
    cin>>destination;
    vector<int> dist = dijkstra(vertices, source,graph );

    cout<<"Shortest distance: "<<dist[destination];
    return 0;
}