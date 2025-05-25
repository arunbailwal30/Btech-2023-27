#include <bits/stdc++.h>
using namespace std;

int findElement(vector<int> &arr) {
    int n = arr.size();

    // leftMax[i] stores maximum of arr[0..i]
    vector<int> leftMax(n);
    leftMax[0] = arr[0];

    // Fill leftMax[1..n-1]
    for (int i = 1; i < n; i++)
        leftMax[i] = max(leftMax[i-1], arr[i]);

    // rightMin to store the minimum value
    int rightMin = arr[n-1];

    // Check if we found a required element
    for(int i = n - 2; i > 0; i--) {
        if(arr[i] >= leftMax[i] && arr[i] <= rightMin) {
            return arr[i];
        }
        
        // update rightMin
        rightMin = min(rightMin, arr[i]);
    }
    return -1;
}

int main() {
    vector<int> arr = {5, 1, 4, 3, 6, 8, 10, 7, 9};
    cout << findElement(arr);
    return 0;
}