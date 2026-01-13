#include<iostream>
using namespace std;
char max(char arr[], int n){
    char maxE = arr[0];
    for(int i =1;i<n;i++){
        if(maxE< arr[i])
            maxE = arr[i];
    }
    return maxE;
}
void frequency(char arr[],int n){
    char max_Element = max(arr, n);
    int freq[max_Element-'a'+1] = {0};
    for(int i=0;i<n;i++){
        freq[arr[i]-'a']++;
    }

    int maxFreq = -1;
    char ch;
    for (int i = 0; i < n; i++) {
        if (freq[arr[i] - 'a'] > maxFreq) {
            maxFreq = freq[arr[i] - 'a'];
            ch = arr[i];
        }
    }

    if (maxFreq == 1) {
        cout << "No duplicate elements" << endl;
    } else {
        cout<<ch<<" - "<<maxFreq;
    }
      
}

int main(){
    int t;
    cin>>t;
    for(int j=0;j<t;j++){
        int n;
        cout<<"Enter size of array: ";
        cin>>n;
        char arr[n];
        for(int i=0;i<n;i++){
            cin>>arr[i];
        }
        frequency(arr, n);
    }
}