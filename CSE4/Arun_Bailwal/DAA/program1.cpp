#include<iostream>
#include<vector>
using namespace std;

int linearSearch(vector<int> arr, int key){
    for(int i=0;i<arr.size();i++){
        if(arr[i] == key){
            return i+1;
        }
    }
    return -1;
}

int main(){
    int t,n, key;
    cin>>t;
    for(int i =0 ;i<t;i++){
        cout<<"Enter size of array: ";
        cin>>n;
        vector<int> arr(n);
        for(int j= 0;j<n;j++){
            cin>>arr[j];
        }
        cout<<"Enter key: ";
        cin>>key;
        int pos = linearSearch(arr ,key);
        if(pos>=0){
            cout<<"Present at: "<<pos<<endl;
        }else{
            cout<<"Not Present"<<endl;
        }

    }
}