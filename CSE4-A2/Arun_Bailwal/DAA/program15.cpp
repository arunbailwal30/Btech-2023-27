#include<iostream>
#include<vector>
#include<algorithm>
using namespace std;

void findCommon(vector<int> arr1, vector<int> arr2){
    int i=0, j=0;
    while(i<arr1.size() and j < arr2.size()){
        if(arr1[i] == arr2[j]){
            cout<<arr1[i]<<" ";
            i++;
            j++;
        }else if(arr1[i] < arr2[j])
            i++;
        else  j++;
    }
}

int main(){
    int m,n;
    cout<<"Enter size of first array: ";
    cin>>m;
    vector<int> arr1(m);
    for(int i=0;i<m;i++)
        cin>>arr1[i];
    cout<<"Enter size of second array: ";
    cin>>n;
    vector<int> arr2(n);
    for(int i=0;i<n;i++)
        cin>>arr2[i];
    sort(arr1.begin(), arr1.end());
    sort(arr2.begin(), arr2.end());
    findCommon(arr1, arr2);

}