#include<iostream>
#include<vector>
#include<algorithm>
#include<climits>
using namespace std;

void dijkstra(vector<vector<int>> &arr, int n, int s) {
    vector<int> dist(n, INT_MAX);
    vector<bool> visited(n, false);
    dist[s] = 0;

    for(int j = 0; j < n - 1; j++) {
        int u = -1;
        for(int i = 0; i < n; i++) {
            if(!visited[i] && (u == -1 || dist[i] < dist[u])) {
                u = i;
            }
        }

        if (u == -1) break; 

        visited[u] = true;

        for(int v = 0; v < n; v++) {
            if(arr[u][v] != 0 && !visited[v]) {
                dist[v] = min(dist[v], dist[u] + arr[u][v]);
            }
        }
    }

    cout << "Vertex\tDistance from Source\n";
    for(int i = 0; i < n; i++) {
        cout << i << "\t\t" << dist[i] << "\n";
    }
}

int main() {
    int n;
       cout<<"Enter no. of nodes: ";
    cin >> n;
 
    cout<<"Enter matrix:\n";
    vector<vector<int>> arr(n, vector<int>(n, 0));
    for(int i = 0; i < n; i++) {
        for(int j = 0; j < n; j++) {
            cin >> arr[i][j];
        }
    }

    cout << "Enter source node: ";
    int s;
    cin >> s;

    dijkstra(arr, n, s);
    return 0;
}
