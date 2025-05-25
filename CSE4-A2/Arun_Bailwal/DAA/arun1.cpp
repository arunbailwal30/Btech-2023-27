#include<bits/stdc++.h>
using namespace std;

void duets(vector<int> &arr, int n) {
    int i=0,j,k;

    while (i<n)
    {
        j = i+1;
        k = j+1;
        while(j<n-1 and k < n and j!=k){
            if(arr[i] + arr[j] == arr[k]){
                cout<<i+1<<" "<<j+1<<" "<<k+1<<endl;
                break;
            }
            if(arr[i] + arr[j] < arr[k]){
                j++;
                if(j == k){
                    k++;
                }
            }else if(arr[i] + arr[j] > arr[k]){
                k++;
            }

        }
        i++;
    }
    
}

int main(){
    int n;
    cin >> n;
    vector<int> a(n);
    for(int i = 0; i < n; i++){
        cin >> a[i];
    }
    sort(a.begin(), a.end());
    duets(a, n);
    return 0;
    
}