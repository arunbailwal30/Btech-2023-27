#include<iostream>
#include<vector>
#include<algorithm>
using namespace std;
int main()
{
    vector<int> prices = {100,180,260,310,40,535,695};
    int profit = 0;
    for(int i=1;i<prices.size();i++){
        if(prices[i] > prices[i-1]){
            profit += prices[i]-prices[i-1];
        }
    }
    cout<<"total profit: "<<profit<<endl;
    
    
}