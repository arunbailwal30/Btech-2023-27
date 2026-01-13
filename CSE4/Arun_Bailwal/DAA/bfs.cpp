#include<bits/stdc++.h>
using namespace;


void Bfs(vector<vector<int>> matrix){
    int s;
    cin>>s;
    queue<int> q;
    vector<int> visited(matrix.size(), 0);
    q.push(s);
    while(!q.empty()){
        int node = q.front();
        q.pop();
        visited[node]=1;
        cout<<node<<" ";
        for(int i=0;i<=matrix.size();i++){
            if(matrix[node][i] and visited[i] == 0){
                q.push(i);
                visited[i] = 1;
            }
        }
    }
}
