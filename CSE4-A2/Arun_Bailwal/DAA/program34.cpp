#include <iostream>
#include <vector>
#include<algorithm>
#include<string>
using namespace std;

// int longestSubsequence(string str1, string str2, int n, int m) {
//     if (n == 0 || m == 0) return 0;
//     if (str1[n - 1] == str2[m - 1]) {
//         return 1 + longestSubsequence(str1, str2, n - 1, m - 1);
//     } else {
//         return max(longestSubsequence(str1, str2, n - 1, m), longestSubsequence(str1, str2, n, m - 1));
//     }
// }

void longestSubsequenceTab(string str1, string str2, int n, int m, vector<vector<int>> &t) {
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) { 
            if (str1[i - 1] == str2[j - 1]) {
                t[i][j] = 1 + t[i - 1][j - 1];
            } else {
                t[i][j] = max(t[i - 1][j], t[i][j - 1]);
            }
        }
    }
    string ans = "";
    int i = n, j = m;
    while (i > 0 && j > 0) {
        if (str1[i - 1] == str2[j - 1]) {
            ans += str1[i - 1]; 
            i--;
            j--;
        } else if (t[i - 1][j] > t[i][j - 1]) {
            i--; 
        } else {
            j--;
        }
    }

    reverse(ans.begin(), ans.end());
    cout << "Length : " << t[n][m] << endl;
    cout << "Longest Common Subsequence: " << ans << endl;
}

int main() {
    string str1, str2;
    cout << "Enter first string: ";
    cin >> str1;
    cout << "Enter second string: ";
    cin >> str2;
    int n = str1.length();
    int m = str2.length();
    vector<vector<int>> t(n + 1, vector<int>(m + 1, 0));
    longestSubsequenceTab(str1,str2,n,m,t);

    return 0;
}
