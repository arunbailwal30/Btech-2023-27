#include <iostream>
#include <vector>
#include <algorithm>
#include <climits>
#include<unordered_map>
using namespace std;
vector<vector<int>> dp;
int solve(unordered_map<int,vector<vector<int>>> &adj, int src, int dst, int k) {
    if(k == 0 && src == dst) return 0;
    if(k == 0) return INT_MAX;
    if(dp[src][k] != -1) return dp[src][k];
    int ans = INT_MAX;
    for(auto &adjPair : adj[src]) {
        int next = adjPair[0];
        int wt = adjPair[1];
        int temp = solve(adj, next, dst, k-1);
        if(temp != INT_MAX) {
            ans = min(ans, wt + temp);
        }
    }
    dp[src][k] = ans;
    return ans;
}
int main() {
    int V, E, k;
    cout << "Enter the number of vertices : " << endl;
    cin >> V;
    cout << "Enter the number of edges : " << endl;
    cin >> E;
    unordered_map<int,vector<vector<int>>> adj;
    for(int i = 0; i < E; i++) {
        int u, v, wt;
        cout << "Enter node1 , node 2 and weight : " << endl;
        cin >> u >> v >> wt;
        adj[u].push_back({v, wt});
    }
    int src, dst;
    cout << "Enter the source node : " << endl;
    cin >> src;
    cout << "Enter the destination node : " << endl;
    cin >> dst;
    cout << "Enter number of edges (k) between source and destination : " << endl;
    cin >> k;
    dp.assign(V, vector<int>(k+1, -1));
    int result = solve(adj, src, dst, k);
    if (result == -1) {
        cout << "No path found with the given number of edges." << endl;
    } else {
        cout << "Minimum path length: " << result << endl;
    }

    return 0;
}
