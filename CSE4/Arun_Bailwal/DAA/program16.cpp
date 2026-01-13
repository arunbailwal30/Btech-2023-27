#include<bits/stdc++.h>
using namespace std;
#include <vector>

bool dfs(const std::vector<std::vector<int>>& matrix, int s, int d, std::vector<int>& visited) {
    visited[s] = 1; 
    if (s == d) {
        return true; 
    }
    for (int i = 0; i < matrix.size(); i++) {
        if (matrix[s][i] && visited[i] == 0) {
            if (dfs(matrix, i, d, visited)) {
                return true; 
            }
        }
    }
    return false;
}

bool checkPath(const vector<vector<int>> matrix, int source, int dest){
    vector<int> visited(matrix.size(),0);
    return dfs(matrix, source , dest, visited);   
}

int main(){
    int vertices;
    cout<<"Enter no. of vertices: ";
    cin>>vertices;

    vector<vector<int>> matrix(vertices+1, vector<int> (vertices+1));

    for(int i=1;i<matrix.size();i++){
        for(int j = 1;j<matrix.size();j++){
            cin>>matrix[i][j];
        }
    }

    cout<<"Enter Source and destination: ";
    int s,d;
    cin>>s>>d;
    if(checkPath(matrix, s, d))
        cout<<"Yes path exits"<<endl;
    else
        cout<<"No such path exits"<<endl;

}