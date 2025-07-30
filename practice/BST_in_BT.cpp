#include<iostream>
#include<vector>
#include<climits>
using namespace std;

typedef struct node{
    int val;
    node* left, *right;
    node(int val){
        this->val = val;
        left = NULL;
        right = NULL;
    }

}node;

typedef struct info{
    int size;
    int max;
    int min;
    int ans;
    bool isBST;
    info(){}
    info(int size,int max, int min, int ans, bool isBST){
        this->size = size;
        this->ans = ans;
        this->isBST = isBST;
        this->max = max;
        this->min = min;
    }
}info;

// int min(int x,int y){
//     return (x>y)? y:x;
// }

info largestBST(node* root){
    if(root == NULL) return {0,INT_MIN, INT_MAX, 0, true};
    if(root->left == NULL and root->right == NULL){
        return {1,root->val,root->val, 1, true};

    }

    info leftInfo = largestBST(root->left);
    info rightInfo = largestBST(root->right);
    info curr;
    curr.size = (1+leftInfo.size +rightInfo.size);
    if(leftInfo.isBST and rightInfo.isBST and leftInfo.max < root->val and rightInfo.min > root->val){
        curr.min = min(leftInfo.min, min(rightInfo.min, root->val));
        curr.max = max(rightInfo.max, max(leftInfo.max, root->val));
        curr.ans = curr.size;
        curr.isBST = true;
        return curr;

    }
    curr.min = min(leftInfo.min, min(rightInfo.min, root->val));
        curr.max = max(rightInfo.max, max(leftInfo.max, root->val));
    curr.ans = max(leftInfo.ans, rightInfo.ans);
    curr.isBST = false;
    return curr;
}




int main(){

    node* root = new node(15);
    root->left = new node(20);
    root->right = new node(30);
    root->left->left  = new node(5);
    cout<<"largest BST in BT: "<<largestBST(root).ans<<endl;
    
    return 0;
}