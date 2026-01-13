#include <iostream>
#include <vector>
#include <limits.h> 

using namespace std;

void floydWarshall(int V, vector<vector<int>> &graph) {
    for (int k = 0; k < V; k++) {
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (graph[i][k] != INT_MAX && graph[k][j] != INT_MAX) {
                    graph[i][j] = min(graph[i][j], graph[i][k] + graph[k][j]);
                }
            }
        }
    }
}

int main() {
    int vertices, edges;
    cout << "Enter vertices and edges: ";
    cin >> vertices >> edges;
    vector<vector<int>> graph(vertices, vector<int>(vertices, INT_MAX));    
    for (int i = 0; i < vertices; i++) {
        graph[i][i] = 0;
    }

    for (int i = 0; i < edges; i++) {
        int u, v, w;
        cout << "Enter source, destination, weight: ";
        cin >> u >> v >> w;
        graph[u][v] = w; 
    }

    floydWarshall(vertices, graph);
    cout << "Shortest path matrix:" << endl;
    for (int i = 0; i < vertices; i++) {
        for (int j = 0; j < vertices; j++) {
            if (graph[i][j] == INT_MAX) {
                cout << "INF "; 
            } else {
                cout << graph[i][j] << " ";
            }
        }
        cout << endl;
    }

    return 0;
}