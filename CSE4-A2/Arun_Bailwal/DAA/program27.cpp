#include<iostream>
#include<vector>
#include<queue>
#include<climits>
#include<algorithm>
using namespace std;
int main()
{
    int n;
    cout<<"Enter the number of files : "<<endl;
    cin>>n;
    vector<int> arr(n);
    priority_queue<int,vector<int>,greater<int>> pq;
    for(int i=0;i<n;i++){
        cin>>arr[i];
        pq.push(arr[i]);
    }
    int minCost=0;
    while(pq.size()>1)
    {
        int a=pq.top();
        pq.pop();
        int b=pq.top();
        pq.pop();
        int newSize=a+b;
        minCost+=newSize;
        pq.push(newSize);
    }

    cout<<"Minimum computation : "<<pq.top()<<endl;
    return 0;
}
