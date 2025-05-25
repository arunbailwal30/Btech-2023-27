#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

void permutations(string &str, int i, vector<string> &v) {
    if (i >= str.length()) {
        v.push_back(str); 
        return;
    }
    for (int j = i; j < str.length(); j++) {
        // Skip duplicates
        if (j != i && str[i] == str[j]) continue;
        swap(str[i], str[j]); 
        permutations(str, i + 1, v); 
        swap(str[i], str[j]); 
    }
}

int main() {
    string str;
    cout << "Enter the string: ";
    cin >> str;
    vector<string> v;
    permutations(str, 0, v);
    sort(v.begin(), v.end());
    cout << "All unique permutations: " << endl;
    for (const auto &perm : v) {
        cout << perm << endl;
    }
    return 0;
}
