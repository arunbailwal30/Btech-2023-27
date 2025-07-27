#include<iostream>
#include<queue>
using namespace std;

struct Node { 
    int data; 
    struct Node *left, *right; 
}; 

void printLevelOrder(Node* root){
    if(root == NULL) return;
    queue<Node*> q;
    q.push(root);
    priority_queue<int , vector<int>, greater<int>> current;
    priority_queue<int, vector<int> , greater<int>> next;
    q.push(NULL);
    current.push(root->data);
    while(!q.empty()){
        int data = current.top();
        Node* node = q.front();
        if(node == NULL){
            q.pop();
            if(q.empty()) break;
            q.push(NULL);
            cout<<endl;
            current.swap(next);
            continue;
        }
        cout<<data<<" ";
        q.pop();
        current.pop();
        if(node->left != NULL){
            q.push(node->left);
            next.push(node->left->data);
        }
        if(node->right != NULL){
            q.push(node->right);
            next.push(node->right->data);
        }
    }
}


Node* newNode(int data) 
{ 
    Node* temp = new Node; 
    temp->data = data; 
    temp->left = temp->right = NULL; 
    return temp; 
} 

int main() 
{ 
    Node* root = newNode(7); 
    root->left = newNode(6); 
    root->right = newNode(5); 
    root->left->left = newNode(4); 
    root->left->right = newNode(3); 
    root->right->left = newNode(2); 
    root->right->right = newNode(1); 
  
    cout << "Level Order traversal of binary tree is \n"; 
    printLevelOrder(root); 
    return 0; 
} 