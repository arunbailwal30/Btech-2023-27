#include <iostream>
#include <vector>
#include <algorithm>
#include <unordered_map>
using namespace std;
int findMajorityElement(const vector<int>& arr) {
    int n = arr.size();
    int candidate = -1;
    int count = 0;
    for (int num : arr) {
        if (count == 0) {
            candidate = num;
            count = 1;
        } else if (num == candidate) {
            count++;
        } else {
            count--;
        }
    }
    count = 0;
    for (int num : arr) {
        if (num == candidate) {
            count++;
        }
    }
    return (count > n / 2) ? candidate : -1;
}
double findMedian(vector<int>& arr) {
    sort(arr.begin(), arr.end());
    int n = arr.size();
    if (n % 2 == 1) {
        return arr[n / 2];
    } else {
        return (arr[n / 2 - 1] + arr[n / 2]) / 2.0;
    }
}

int main() {
    int n;
    cin >> n; 
    vector<int> arr(n);
    for (int i = 0; i < n; i++) {
        cin >> arr[i]; 
    }
    int majorityElement = findMajorityElement(arr);
    if (majorityElement != -1) {
        cout << "yes" << endl;
    } else {
        cout << "no" << endl;
    }
    double median = findMedian(arr);
    cout << median << endl;
    return 0;
}
