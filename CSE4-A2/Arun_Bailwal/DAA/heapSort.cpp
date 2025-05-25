#include <iostream>
#include <vector>
using namespace std;

void heapify(vector<int> &arr, int i, int n) {
    int parent = i, left = 2 * i + 1, right = 2 * i + 2; // Adjusted for 0-based indexing
    if (left < n && arr[left] > arr[parent]) // Changed <= to < for 0-based indexing
        parent = left;
    if (right < n && arr[right] > arr[parent]) // Changed <= to < for 0-based indexing
        parent = right;
    if (parent != i) {
        swap(arr[i], arr[parent]);
        heapify(arr, parent, n);
    }
}

void heapSort(vector<int> &arr) {
    int n = arr.size();
    for (int i = n / 2 - 1; i >= 0; i--) {
        heapify(arr, i, n);
    }
    for (int i = n - 1; i > 0; i--) {
        swap(arr[0], arr[i]);
        heapify(arr, 0, i);
    }
}

int main() {
    int n;
    cin >> n;
    vector<int> arr(n);
    for (int i = 0; i < n; i++) {
        cin >> arr[i];
    }
    heapSort(arr);
    for (int i = 0; i < arr.size(); i++) {
        cout << arr[i] << " ";
    }
    cout << endl;
    return 0;
}