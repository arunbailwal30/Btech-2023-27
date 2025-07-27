#include<bits/stdc++.h>
using namespace std;

void floyWarshall(vector<vector<int>> adj){
    for(int k =1 ; k < adj.size(); k++){
        for(int j = 1; j < adj.size(); j++){
            for(int i = 1; i < adj.size(); i++){
                if( adj[j][k] != INT_MAX and adj[k][i] != INT_MAX){
                    adj[j][i] = min(adj[j][i], adj[j][k] + adj[k][i]);
                }
            }
        }
    }

    for(int i=1;i<adj.size();i++){
        for(int j = 1; j< adj.size(); j++){
            (adj[i][j] == INT_MAX) ? (cout<<"INF"<<" " ): cout<< adj[i][j] << " ";
        }
        cout<<endl;
    }

}


int main(){
    int v,e;
    cin>>v>>e;
    vector<vector<int>> adj(v+1, vector<int> (v+1, INT_MAX));
    for(int i = 0 ;i < e;i++){
        int s,d,w;
        cout<<"s, d, w: ";
        cin>>s>>d>>w;
        adj[s][d] = w;
        
    }
    floyWarshall(adj);

}