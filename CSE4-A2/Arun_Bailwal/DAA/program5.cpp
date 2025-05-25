#include<bits/stdc++.h>
using namespace std;

void threeIndices(vector<int> &arr, int n) {
    int i=n-1,j,k;

    while (i>1)
    {
        j = 0;
        k = i-1;
        while(j<i and k >j and j!=k){
            if(arr[k] + arr[j] == arr[i]){
                cout<<"Sequence - "<<j+1<<" "<<k+1<<" "<<i+1<<endl;
                return ;
            }
            if(arr[k] + arr[j] < arr[i]){
                j++;
            }else if(arr[k] + arr[j] > arr[i]){
                k--;
            }
        }
        i--;
    }
    cout<<"No sequence found\n";
    
}

int main(){
    int t;
    cin>>t;
    for(int j= 0;j<t;j++){
        int n;
        cout<<"Enter size of array: ";
        cin >> n;
        vector<int> a(n);
        for(int i = 0; i < n; i++){
            cin >> a[i];
        }
        threeIndices(a, n);
    }
    return 0;
    
}