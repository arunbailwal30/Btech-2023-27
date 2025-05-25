#include<iostream>
#include<vector>
#include<queue>
using namespace std;

bool Bfs(vector<vector<int>> matrix){
    queue<int> q;
    int n = matrix.size();
    vector<int> visited(n, 0);
    q.push(0);
    vector<int> color(n,0);
    color[0] = 1;
    while(!q.empty()){
        int node = q.front();
        q.pop();
        visited[node]=1;
        for(int i=0;i<=n;i++){
            if(matrix[node][i] and visited[i] == 0){
                q.push(i);
                if(color[node] == 0){
                    color[i]=1;
                }else{
                    color[i] = 0;
                }
                visited[i] = 1;
            }else if(matrix[node][i]){
                if(color[node] == color[i]){
                    return false;
                }
            }
        }
    }
    return true;
}

int main(){
    int vertices;
    cout<<"Enter no. of vertices: ";
    cin>>vertices;

    vector<vector<int>> matrix(vertices, vector<int> (vertices));

    for(int i=0;i<vertices;i++){
        for(int j = 0;j<vertices;j++){
            cin>>matrix[i][j];
        }
    }
    if(Bfs(matrix)){
        cout<<"Bipartite"<<endl;
    }else cout<<"Not Bipartite"<<endl;
}