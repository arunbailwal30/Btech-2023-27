#include<bits/stdc++.h>
using namespace std;

void bellmanford(vector<vector<int>> &edges, int n){
    vector<bool> vis(n, false);
    vector<int> dis(n, INT_MAX);
    dis[0] = 0;
    sort(edges.begin(), edges.end());
    for(int i=0;i<n;i++){
        for(vector<int> edge: edges ){
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            if(dis[u] != INT_MAX and dis[u] + w < dis[v]){
                if(i == n-1) return;
                dis[v] = dis[u] +w;
            }
        }
    }

    for(auto i: dis) cout<<i<<"   ";
    
}


int main(){
    
    int v,e;
    cin>>v>>e;
    vector<vector<int>> edges(e, vector<int> (3));
    for(int i=0;i<e;i++){
        int s,d,w;
        cin>>s>>d>>w;
        edges.push_back({s,d,w});
    }

    bellmanford(edges, v);

    return 0;
}