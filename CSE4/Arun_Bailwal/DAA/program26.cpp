#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

void knapsack(vector<int> wt, vector<int> val, int w, int n) {
    vector<pair<double, pair<int, pair<int, int>>>> v;
    for (int i = 0; i < n; i++) {
        v.push_back({(double)val[i] / wt[i], {i + 1, {wt[i], val[i]}}});
    }
    sort(v.rbegin(), v.rend());

    double ans = 0;
    int i = 0;
    vector<pair<int, double>> items; 

    while (w > 0 && i < n) { 
        if (v[i].second.second.first <= w) {
            ans += v[i].second.second.second;
            w -= v[i].second.second.first;
            items.push_back({v[i].second.first, 1.0}); 
        } else {
            ans += v[i].second.second.second * ((double)w / v[i].second.second.first);
            items.push_back({v[i].second.first, (double)w / v[i].second.second.first}); 
            w = 0; 
        }
        i++;
    }

    cout << "Maximum value: " << ans << endl;
    cout << "Item - Fraction" << endl;
    for (const auto& item : items) {  
        cout << item.first << " - " << item.second << endl;
    }
}

int main() {
    int n;
    cout << "Enter number of elements: ";
    cin >> n;
    vector<int> wt(n);
    vector<int> val(n);
    cout << "Enter weights: ";
    for (int i = 0; i < n; i++) {
        cin >> wt[i];
    }
    cout << "Enter values: ";
    for (int i = 0; i < n; i++) {
        cin >> val[i];
    }
    cout << "Enter capacity of bag: ";
    int w;
    cin >> w;
    knapsack(wt, val, w, n);
    return 0;
}
