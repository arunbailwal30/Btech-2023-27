#include <iostream>
#include <vector>
#include <limits>
#include <iomanip>
#include <sstream>

using namespace std;

const int INF = numeric_limits<int>::max();

struct Edge {
    int source, destination, weight;
};

void printPath(vector<int> &parent, int j) {

    if (parent[j] == -1) {
        cout << j + 1; 
        return;
    }
    cout << j + 1<<" ";
    printPath(parent, parent[j]);
}

void bellmanFord(const vector<vector<int>>& graph, int V, int source) {
    vector<int> distance(V, INF);
    vector<int> parent(V, -1);
    
    distance[source] = 0;

    for (int i = 0; i < V - 1; i++) {
        for (int u = 0; u < V; u++) {
            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && distance[u] != INF && distance[u] + graph[u][v] < distance[v]) {
                    distance[v] = distance[u] + graph[u][v];
                    parent[v] = u;
                }
            }
        }
    }
    for (int i = 0; i < V; i++) {
        if (i != source) {
            if (distance[i] == INF) {
                cout << "No path" << endl;
            } else {
                printPath(parent, i);
                cout << " : " << distance[i] << endl;
            }
        }
    }
}

int main() {
    int V;
    cout << "Enter number of vertices: ";
    cin >> V;

    vector<vector<int>> graph(V, vector<int>(V, 0));

    cout << "Enter the adjacency matrix:" << endl;
    for (int i = 0; i < V; i++) {
        for (int j = 0; j < V; j++) {
            cin >> graph[i][j];
        }
    }

    int source;
    cout << "Enter the source vertex: ";
    cin >> source;

    bellmanFord(graph, V, source);

    return 0;
}
