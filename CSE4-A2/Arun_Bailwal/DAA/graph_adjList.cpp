#include<bits/stdc++.h>
using namespace std;

void Bfs(vector<vector<int>> a){
    int s;
    cin>>s;
    queue<int> q;
    vector<int> visit(a.size(),0);
    q.push(s);
    visit[s] = 1;
    while(!q.empty()){
        int node = q.front();
        q.pop();
        cout<<node<<" --> ";
        for(auto j: a[node]){
            if(visit[j]==0){
                visit[j] = 1;
                q.push(j);
            }
        }
    }
}


int main(){
    int v,e;
    cin>>v>>e;
    vector<vector<int>> adj(v+1);
    for(int i=0;i<e;i++){
        int s,d;
        cin>>s>>d;
        adj[s].push_back(d);
        adj[d].push_back(s);
    }

    for(int i=0;i<v+1;i++){
        cout<<i<<" --> ";
        for(auto itr: adj[i]){
            cout<<itr<<" ";
        }
        cout<<endl;
    }
    Bfs(adj);

}