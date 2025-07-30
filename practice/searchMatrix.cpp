#include <iostream>
#include <vector>
#include <stdio.h>

using namespace std;

void search(int n, vector<vector<int>> arr, int k) {
    int start = 0, end = n*n-1;
    while (start <= end) {
        int mid = start + (end - start) / 2;
        int row = mid / n;
        int col = mid % n;
        if (arr[row][col] == k) {
            cout << "Found" << endl;
            return ;
        }
        if (arr[row][col] < k) {
            start = mid + 1;
        } else {
            end = mid - 1;
        }
    }
    cout << "Not Found" << endl;
}

int main() {
    int n;
    cin >> n;
    vector<vector<int>> arr(n, vector<int> (n));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            cin >> arr[i][j];
        }
    }

    int k;
    cin >> k;
    search(n, arr, k);
}