#include <iostream>
#include <vector>

using namespace std;

int coinChange(vector<int> arr, int n, int sum) {
    
    if (sum == 0) return 1;
    if (n == 0) return 0;
    if (arr[n - 1] > sum) {
        return coinChange(arr, n - 1, sum);
    } else {
        return coinChange(arr, n, sum - arr[n - 1]) + coinChange(arr, n - 1, sum);
    }
}

int main() {
    int n;
    cout << "Enter size of array: ";
    cin >> n;
    vector<int> arr(n);
    for (int i = 0; i < n; i++) {
        cin >> arr[i];
    }
    int sum;
    cout << "Enter sum: ";
    cin >> sum;
    cout << "No. of ways: " << coinChange(arr, n, sum) << endl;

    return 0;
}
