#include <bits/stdc++.h>

using namespace std;

int matrixMultiplication(vector<int> &arr)
{
    int n = arr.size();
    vector<vector<int>> dp(n, vector<int>(n, 0));
    for (int len = 2; len < n; len++)
    {
        for (int i = 0; i < n - len; i++)
        {
            int j = i + len;
            dp[i][j] = INT_MAX;

            for (int k = i + 1; k < j; k++)
            {
                int cost = dp[i][k] + dp[k][j] + arr[i] * arr[k] * arr[j];
                dp[i][j] = min(dp[i][j], cost);
            }
        }
    }
    return dp[0][n - 1];
}

int matrixMultiplication(vector<int> &arr, int i, int j){
    if (i>=j) return 0;
    int Final = INT_MAX;
    for(int k =i; k<j;k++){
        int temp = matrixMultiplication(arr,i,k) + matrixMultiplication(arr,k+1,j) + (arr[i-1]*arr[k]*arr[j]);
        Final = min(temp,Final);
    }
    return Final;
}

int memoriztion(vector<int> &arr){}

int main()
{
    int n;
    cin>>n;
    vector<int> arr(n,0);
    for(int i=0;i<n;i++){
        cin>>arr[i];
    } 
    cout << matrixMultiplication(arr,1,n-1);
    return 0;
}