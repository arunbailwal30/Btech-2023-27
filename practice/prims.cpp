#include<bits/stdc++.h>
using namespace std;


void prims2(vector<vector<int>> graph){
    int n  = graph.size();
    vector<int> parent(n);
    vector<int> vis(n, false);
    vector<int> weight(n, INT_MAX);
    parent[0] = -1;
    weight[0] = 0;
    

}

void prims(vector<vector<int>> graph){
    int n = graph.size();
    vector<int> parent(n);
    vector<int> visit(n, false);
    vector<int> key(n, INT_MAX);
    // for(int i = 0;i<n;i++) parent[i] = -1;

    key[0] = 0;
    parent[0] = -1;
    
    priority_queue<pair<int,int>, vector<pair<int,int>> , greater<pair<int,int>>> pq;
    pq.push({0,0});
    while(!pq.empty()){
        int node = pq.top().second;
        pq.pop();
        visit[node] = true;
        for(int i=0;i<n;i++){
            if(!visit[i] and graph[node][i] !=0 and graph[node][i] < key[i]){
                pq.push({graph[node][i], i});
                key[i] = graph[node][i];
                parent[i] = node;
            }
        }
    }

    cout<<"Edge \t Weight\n";
    for(int i=1;i<n;i++){
        cout<<parent[i]<<" - "<<i<<"\t"<<graph[i][parent[i]] <<endl;
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
        adj[d][s] = w;
    }
    prims(adj);
    


}