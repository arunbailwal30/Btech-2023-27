#include <bits/stdc++.h>

using namespace std;


struct Node {
    char ch;
    int freq;
    Node* left, *right;
};


struct MinHeap {
    Node* heap[1000];
    int size;
    MinHeap() { size = 0; }
    void heapify(int i) {
        int smallest = i, left = 2 * i + 1, right = 2 * i + 2; // Adjusted for 0-based indexing
        if (left < size && heap[left]->freq < heap[smallest]->freq)
            smallest = left;
        if (right < size && heap[right]->freq < heap[smallest]->freq)
            smallest = right;
        if (smallest != i) {
            swap(heap[i], heap[smallest]);
            heapify(smallest);
        }
    }

    void insert(Node* temp) {
        heap[size] = temp; 
        size++;
        int i = size - 1;
        while (i > 0 && heap[i]->freq < heap[(i - 1) / 2]->freq) {
            swap(heap[i], heap[(i - 1) / 2]);
            i = (i - 1) / 2;
        }
    }

    Node* extractMin() {
        if (size == 0) return nullptr;
        Node* minNode = heap[0];
        heap[0] = heap[size - 1]; size--;
        heapify(0);
        return minNode;
    }
};

void encode(Node* root, string str, unordered_map<char, string>& huffmanCode) {
    if (root == nullptr)
        return;
    if (!root->left && !root->right)
        huffmanCode[root->ch] = str;
    encode(root->left, str + "0", huffmanCode);
    encode(root->right, str + "1", huffmanCode);
}

void decode(Node* root, int& index, string str) {
    if (root == nullptr)
        return;
    if (!root->left && !root->right) {
        cout << root->ch;
        return;
    }
    index++;
    if (index < str.length()) { // Ensure index is within bounds
        if (str[index] == '0')
            decode(root->left, index, str);
        else
            decode(root->right, index, str);
    }
}


Node* getNode(char ch, int freq, Node* left, Node* right) {
    Node* temp = new Node();
    temp->ch = ch;
    temp->freq = freq;
    temp->left = left;
    temp->right = right;
    return temp;
}


void buildHuffmanTree(string text){
    unordered_map<char, int> freq;
    for(int i=0;i<text.length();i++){
        freq[text[i]]++;
    }
    MinHeap minheap;
    for(auto pair: freq){
        minheap.insert(getNode(pair.first, pair.second, nullptr, nullptr));
    }

    while(minheap.size > 1){
        Node* left = minheap.extractMin();
        Node* right = minheap.extractMin();
        int sum = left->freq + right->freq;
        minheap.insert(getNode('\0',sum, left, right));
    }

    Node* root = minheap.heap[0];
    unordered_map<char, string> huffmanCode;
    encode(root, "", huffmanCode);
    cout<<"Huffman codes are: \n";
    for(auto pair: huffmanCode){
        cout<<pair.first<<" "<<pair.second<<endl;
    }

    cout<<"\nOriginal string was: \n"<<text<<endl;
    string str = "";
    for(char ch: text){
        str += huffmanCode[ch];
    }
    cout<<"\nEncoded string is:\n"<<str<<'\n';

    //Decode the encoded string
    int index = -1;
    cout<<"\nDecode string is: \n";
    while(index<str.length()){
        decode(root, index, str);
    }
}

int main(){

    string text;
    cout<<"Enter string: ";
    getline(cin, text);
    buildHuffmanTree(text);
    return 0;
}