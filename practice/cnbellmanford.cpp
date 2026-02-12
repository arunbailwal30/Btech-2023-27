#include<iostream>
#include<vector>

using namespace std;

void bellmanfor(vector<vector<int>> cost, int n){
    vector<vector<int>> dist(n, vector<int>(n, 999));
    vector<pair<int,int>> nextHop(n, {0,-1});
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            dist[i][j] = cost[i][j];
        }
    }
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            for(int k=0;k<n;k++){
                if(dist[j][k] > dist[j][i] + dist[i][k]){
                    dist[j][k] = dist[j][i] + dist[i][k];
                    nextHop[j] = {i, dist[j][i]};
                }
            }
        }
    }

    for(int i =0;i<n;i++){
        cout<<"Routing table for router "<<i<<" : "<<endl;
        cout<<"Destination\tCost\tNext Hop"<<endl;
        for(int j=0;j<n;j++){
            cout<<j<<"\t\t"<<dist[i][j]<<"\t\t"<<j<<"\t\t"<<endl;
        }
        cout<<endl;
    }
}


int main(){
    int n;
    cout<<"Enter number of routers: ";
    cin>>n;
    vector<vector<int>> cost(n, vector<int>(n,999));
    cout<<"Enter cost matrix\n";
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            cin>>cost[i][j];
        }
    }

    bellmanfor(cost, n);
}