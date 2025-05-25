#include <iostream>
#include <vector>
#include <limits.h> // For INT_MAX

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

    // Initialize the graph with INT_MAX
    vector<vector<int>> graph(vertices, vector<int>(vertices, INT_MAX));

    // Set the distance from each vertex to itself to 0
    for (int i = 0; i < vertices; i++) {
        graph[i][i] = 0;
    }

    // Input edges
    for (int i = 0; i < edges; i++) {
        int u, v, w;
        cout << "Enter source, destination, weight: ";
        cin >> u >> v >> w;
        graph[u][v] = w; // Assuming 0-based indexing for vertices
    }

    // Run Floyd-Warshall algorithm
    floydWarshall(vertices, graph);

    // Output the shortest path matrix
    cout << "Shortest path matrix:" << endl;
    for (int i = 0; i < vertices; i++) {
        for (int j = 0; j < vertices; j++) {
            if (graph[i][j] == INT_MAX) {
                cout << "INF "; // Print INF for unreachable paths
            } else {
                cout << graph[i][j] << " ";
            }
        }
        cout << endl;
    }

    return 0;
}