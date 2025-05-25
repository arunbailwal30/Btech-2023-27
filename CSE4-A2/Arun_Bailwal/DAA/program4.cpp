#include<iostream>

using namespace std;

int binarySearchCount(int arr[], int n, int key) {
    int left = 0, right = n - 1;
    int first = -1, last = -1;
    //apply binary search to find first occurence
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (arr[mid] == key) {
            first = mid;
            right = mid - 1;
        } else if (arr[mid] < key) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    if (first == -1) return 0;
    left = 0;
    right = n - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (arr[mid] == key) {
            last = mid;
            left = mid + 1;
        } else if (arr[mid] < key) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }

    return last - first + 1;
}

int main() {
    int t, n, key;
    cin >> t;
    for (int i = 0; i < t; i++) {
        cout << "Enter size of array: ";
        cin >> n;
        int arr[n];
        for (int j = 0; j < n; j++) {
            cin >> arr[j];
        }
        cout << "Enter key: ";
        cin >> key;
        int count = binarySearchCount(arr, n, key);
        if (count > 0) {
            cout << key << ": " << count << endl;
        } else {
            cout << "Not Present" << endl;
        }
    }
    return 0;
}