#include<iostream>
#include<math.h>
using namespace std;
int linearSearch(int arr[], int start, int end, int key){
    for(start; start<=end ;start++){
        if(arr[start] == key) return start;
    }
    return -1;
}
int jumpSearch(int arr[], int key, int n){
    int power = 2;
    int prev = 0, next = 2;
    while(next<n){
        if(arr[next] == key){
            return next+1;
        }
        if(arr[next] > key){
            return linearSearch(arr, prev, min(next ,n-1), key)+1;
        }
        prev = next+1;
        next = pow(next , power);
    }
    if(prev<n) return linearSearch(arr, prev, n-1, key)+1;
    return -1;
}
int main(){
    int t,n, key;
    cin>>t;
    for(int i =0 ;i<t;i++){
        cout<<"Enter size of array: ";
        cin>>n;
        int arr[n];
        for(int j= 0;j<n;j++){
            cin>>arr[j];
        }
        cout<<"Enter key: ";
        cin>>key;
        int pos = jumpSearch(arr ,key,n);
        if(pos>0){
            cout<<"Present at: "<<pos<<endl;
        }else{
            cout<<"Not Present"<<endl;
        }
    }
}