#include <iostream>
#include <vector>
#include <queue>
using namespace std;

vector<int> prims(int V, int start, vector<vector<pair<int, int>>> &graph) {
    priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> pq;
    vector<int> vis(V, 0);
    vector<int> dist(V, INT8_MAX); 
    dist[start] = 0; 
    pq.push({0, start}); 

    while (!pq.empty()) {
        int w = pq.top().first; 
        int u = pq.top().second; 
        pq.pop();

        if (vis[u]) {
            continue; 
        }

        vis[u] = 1; 

        for (auto edge : graph[u]) {
            int v = edge.first; 
            int weight = edge.second; 
            if (!vis[v] && weight < dist[v]) {
                dist[v] = weight; 
                pq.push({weight, v}); 
            }
        }
    }
    return dist; 
}

int main() {
    int vertices, edges;
    cout << "Enter no. of vertices and edges: ";
    cin >> vertices >> edges;
    vector<vector<pair<int, int>>> graph(vertices);
    for (int i = 0; i < edges; i++) {
        int u, v, w;
        cout << "Enter source, destination, weight: ";
        cin >> u >> v >> w;
        graph[u].push_back({v, w});
        graph[v].push_back({u, w}); 
    }
    int source;
    cout << "Enter source: ";
    cin >> source;
    vector<int> dist = prims(vertices, source, graph);
    
    cout << "Minimum Spanning Tree weights from source " << source << ":\n";
    for (int i = 0; i < dist.size(); i++) {
        cout << "Vertex " << i << ": " << dist[i] << "\n";
    }
    return 0;
}