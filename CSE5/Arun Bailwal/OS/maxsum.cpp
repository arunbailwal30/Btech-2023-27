#include <bits/stdc++.h>

using namespace std;

string ltrim(const string &);
string rtrim(const string &);
vector<string> split(const string &);

/*
 * Complete the 'solve' function below.
 *
 * The function is expected to return an INTEGER.
 * The function accepts following parameters:
 *  1. INTEGER n
 *  2. 2D_INTEGER_ARRAY operations
 */

int solve(int n, vector<vector<int>> operations) {
    int ans = 0;
    vector<int> jar(n);
    double total = 0;
    int opert = operations.size();
    for(int i=0;i<opert;i++)
    {
        total += (double)(operations[i][2]*(1+operations[i][1]-operations[i][0]))/n;
        
    }
    return (int)total;
    
}

int main()
{
    int n;

    int m;
    cin>>n>>m;
    vector<vector<int>> operations;

    for (int i = 0; i < m; i++) {
        int a,b,k;
        cin>>a>>b>>k;
        operations.push_back({a,b,k});
    }

    int result = solve(n, operations);

    cout<< result << "\n";


    return 0;
}

