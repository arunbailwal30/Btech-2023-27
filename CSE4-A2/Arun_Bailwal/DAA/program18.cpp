#include<iostream>
#include<vector>

using namespace std;

bool Dfs(vector<vector<int>> graph, vector<int>& vis, vector<int> &dfs, int source){
    vis[source]=1;
    dfs[source]=1;
    
    for(int i = 0;i < graph[source].size();i++){
        if(graph[source][i]==1){
            if(vis[i]==0){
            if(Dfs(graph, vis, dfs, i)){
                return true;
            }}
        else if(dfs[i]==1) return true;
        }
    }
    dfs[source ] = 0;
    return false;
}

bool hasCycle(vector<vector<int>> &graph){
    int n = graph.size();
    vector<int> vis(n,0);
    vector<int> dfs(n,0);
    for(int i= 0;i<n;i++){
        if(!vis[i]){
            if(Dfs(graph, vis, dfs, i)){
               return true; 
            }
        }
    }
    return false;
}

int main(){
    int vertices;
    cout<<"Enter no. of vertices : ";
    cin>>vertices;

    vector<vector<int>> graph(vertices, vector<int> (vertices));

    for(int i=0;i<vertices;i++){
        for(int j = 0;j<vertices;j++){
            cin>>graph[i][j];
        }
    }
    if(hasCycle(graph))
        cout<<"Cycle Exists"<<endl;
    else cout<<"No Cycle Exists"<<endl;
}