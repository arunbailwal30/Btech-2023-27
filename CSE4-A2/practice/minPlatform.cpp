#include<vector>
#include<iostream>
#include<algorithm>
using namespace std;
int platform(vector<int>& arr, vector<int>& dep){
    int ans=0;
    for(int i=0; i<arr.size();i++){
        int cnt = 1;
        for(int j=0;j<arr.size();j++){
            if(i!=j){
                if(arr[i]>=arr[j] and dep[j]>= arr[i]){
                    cnt++;
                }
            }
        }
        ans = max(ans, cnt);
    }
    return ans;
}

int platform2(vector<int> &arr, vector<int> &dep){
    sort(arr.begin(), arr.end());
    sort(dep.begin(), dep.end());

    int j=0;
    int cnt = 0;
    int n = arr.size();
    int res = 0;
    for(int i =0;i<n;i++){

        while(j<n and dep[j] < arr[i]){
            cnt--;
            j++;
        }
        cnt++;
        res = max(cnt, res);
    }
    return res;
}
int platform3(vector<int> &arr, vector<int> &dep){
    int max_dep = dep[0];
    int n = arr.size();
    for(int i = 0;i<n;i++){
        max_dep = max(max_dep, dep[i]);
    }
    int res = 0;

    vector<int> v(max_dep+2, 0);
     for(int i=0;i<n;i++){
        v[arr[i]]++;
        v[dep[i]+1]--;
     }
     int count = 0;
    for (int i = 0; i <= max_dep + 2; i++) {
        count += v[i];
        res = max(res, count);
    }
    
    return res;
}

int main(){
    int n;
    cin>>n;
    vector<int> arr(n);
    vector<int> dep(n);
    for(int i=0;i<n;i++){
        cin>>arr[i];
    }
    for(int i=0;i<n;i++){
        cin>>dep[i];
    }
    cout<<platform2(arr, dep);
}