#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

// int knapsack(vector<int> wt, vector<int> val, int w, int n, vector<int>& wtSelected, vector<int>& valWeights, vector<vector<int>>& matrix) {
//     if (w == 0 || n == 0) return 0;
//     if (matrix[n][w] != -1) return matrix[n][w];
//     if (wt[n - 1] > w) return knapsack(wt, val, w, n - 1, wtSelected, valWeights, matrix);
    
//     int item1 = val[n - 1] + knapsack(wt, val, w - wt[n - 1], n - 1, wtSelected, valWeights, matrix);
//     int item2 = knapsack(wt, val, w, n - 1, wtSelected, valWeights, matrix);
    
//     if (item1 > item2) {
//         wtSelected.push_back(wt[n - 1]);
//         valWeights.push_back(val[n - 1]);
//         matrix[n][w] = item1; 
//         return item1;
//     } else {
//         matrix[n][w] = item2; 
//         return item2;
//     }
// }

void iteKnasack(vector<int> wt, vector<int> val, vector<vector<int>>& matrix, int n, int w) {
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= w; j++) {
            if (wt[i - 1] <= j) {
                matrix[i][j] = max(val[i - 1] + matrix[i - 1][j - wt[i - 1]], matrix[i - 1][j]);
            } else {
                matrix[i][j] = matrix[i - 1][j];
            }
        }
    }    
    cout << "Maximum value in knapsack: " << matrix[n][w] << endl;
    vector<int> wtSelected;
    vector<int> valWeights;
    int j = w;
    for (int i = n; i > 0; i--) {
        if (matrix[i][j] != matrix[i - 1][j]) { 
            wtSelected.push_back(wt[i - 1]);
            valWeights.push_back(val[i - 1]);
            j -= wt[i - 1]; 
        }
    }

    cout << "Weights selected: ";
    for (int i : wtSelected) cout << i << " ";
    cout << "\nValues of selected weights: ";
    for (int i : valWeights) cout << i << " ";


}

int main() {
    int n;
    cout << "Enter number of items: ";
    cin >> n;

    vector<int> wt(n);
    vector<int> val(n);

    cout << "Enter weights: ";
    for (int i = 0; i < n; i++)    
        cin >> wt[i];
    cout << "Enter values: ";
    for (int i = 0; i < n; i++)
        cin >> val[i];

    cout << "Enter weight of bag: ";
    int w;
    cin >> w;
    vector<vector<int>> matrix(n + 1, vector<int>(w + 1, 0));
    iteKnasack(wt,val,matrix,n,w);
    return 0;
}
