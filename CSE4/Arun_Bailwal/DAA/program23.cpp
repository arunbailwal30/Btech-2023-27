#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;


int find(int node, vector<int> &parent) {
    if (node == parent[node]) {
        return node;
    }
    // Path compression
    parent[node] = find(parent[node], parent);
    return parent[node];
}

void Union(int u, int v, vector<int> &parent, vector<int> &Rank) {
    int x = find(u,parent);
    int y = find(v,parent);
    if (x == y) return;

    // Union by Rank
    if (Rank[x] < Rank[y]) {
        parent[x] = y;
    } else if (Rank[x] > Rank[y]) {
        parent[y] = x;
    } else {
        parent[y] = x;
        Rank[x]++;
    }
}

void kruskal(vector<pair<int, pair<int, int>>> &edges, vector<pair<int, pair<int, int>>> &mst) {
    sort(edges.begin(), edges.end());
    int v = edges.size();

    vector<int> Rank(v,0);
    vector<int> parent(v,0);
    for (int i = 0; i < v; i++) {
        parent[i] = i; 
    }
    for (auto it : edges) {
        int w = it.first;
        int u = it.second.first;
        int v = it.second.second;
        int x = find(u,parent);
        int y = find(v,parent);
        if (x != y) {
            mst.push_back({w, {u, v}});
            Union(u, v,parent,Rank);
        }
    }
    cout << "Minimum spanning weight:  ";
    int total = 0;
    for (auto it : mst) {
        total+=it.first;
    }
    cout<<total<<endl;
    
}

int main() {
    int v, e;
    cout << "Enter number of vertices (v) and edges (e):\n";
    cin >> v >> e;
    vector<pair<int, pair<int, int>>> edges;
    for (int i = 0; i < e; i++) {
        cout << "Enter source, destination, weight: ";
        int s, d, w;
        cin >> s >> d >> w;
        edges.push_back({w, {s, d}});
    }
    vector<pair<int, pair<int, int>>> mst; 
    kruskal(edges, mst);
    return 0;
}1`                                                                     