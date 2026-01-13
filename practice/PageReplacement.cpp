#include<iostream>
#include<vector>
#include<algorithm>
#include<queue>
#include<set>
#include<map>
#include<unordered_set>
#include<unordered_map>
using namespace std;

int find(queue<int> frame, int x){
    if(frame.empty()) return -1;
    int temp = frame.front();
    
    if(x == temp) return temp;
    frame.pop();
    return find(frame, x);
    
}

void fcfs(vector<int> arr,queue<int> &frame, int size, int n){
    int hit = 0, fault = 0;
    for(int i=0;i<n;i++){
        if(find(frame, arr[i])>=0){
            hit++;
            printf("hit ");
            continue;
        }else{
            printf("fault ");
            fault++;
            if(frame.size()<size){
                frame.push(arr[i]);
            }else{
                frame.pop();
                frame.push(arr[i]);
            }
        }

    }
    cout<<"\nhit ratio:  "<< (float(hit)/(float)(hit + fault)) *100<<endl;
    
}

void lru(vector<int> &arr, int size, int n) {
    unordered_set<int> s;
    unordered_map<int, int> indexes;
    int hit = 0, fault =0;
    for(int i=0;i<n;i++){
        if(s.size()<size){
            if(s.find(arr[i])==s.end()){
                s.insert(arr[i]);
                fault++;
            }else{
                hit++;
            }
            indexes[arr[i]] = i;
        }else{
            if(s.find(arr[i])==s.end()){
                int lru = 999, val;
                for(auto it=s.begin();it!=s.end();it++){
                    if(indexes[*it]<lru){
                        lru = indexes[*it];
                        val = *it;
                    }
                }
                s.erase(val);
                s.insert(arr[i]);
                fault++;
            }else{
                hit++;
            }
            indexes[arr[i]] = i;
        }
    }
    
}


int main(){
    int n;
    cin>>n;
    int size;
    cin>>size;
    queue<int> frame;
    vector<int> arr(n);
    for(int i=0;i<n;i++) cin>>arr[i];
    fcfs(arr,frame,size,n);


}