#include<bits/stdc++.h>

using namespace std;
class lowBalance: public exception{
    private:
    double balance;
    public:
    lowBalance(double balance){
        this->balance = balance;
    }
    const char* what() const noexcept {

        return "\nLow balance";
    }
};


class Bank{
private:
    string name;
    string acc_no;
    string userId;
    string type;
    double balance ;
    string password;
public:
    Bank(string name, string acc_no, string type, double balance, string userId, string password){
        this->name = name;
        this->acc_no = acc_no;
        this->type = type;
        this->balance = balance;
        this->userId = userId;
        this->password = password;
        cout<<"*******************************"<<endl;
        cout<<type<<" Account created for "<<name<<endl<<"*******************************"<<endl;
    }

    void checkBalance(){
        cout<<"*******************************"<<endl;
        cout<<"Current balance: "<<balance<<endl;
        cout<<"*******************************"<<endl;
    }

    bool checkUser(string userId){
        if(userId == this->userId){
            return true;
        }
        else{
            return false;
        }
    }

    void deposit(double amt){
		balance = amt + balance;
		cout<<endl<<"*******************************"<<endl;
		cout<<amt<<" is credited to your account your current balance is "<<balance<<endl<<"*******************************"<<endl;
	}

	void withdraw(double amt){
        try
        {
            if(amt <= balance){
                balance = balance - amt;
                cout<<endl<<"*******************************"<<endl;
                cout<<amt<<" is debited from your account. Now your current balance is "<<balance<<endl<<"*******************************"<<endl;
	        }else {
                lowBalance e(amt);
                throw e;
            }   
        }

        catch(const lowBalance &e)
        {
            std::cerr << e.what() << '\n';
        }
        
	}

    void display(){
        cout<<setw(5)<<"Name of acc. holder"<<setw(20)<<name<<endl;
        cout<<setw(5)<<"Current Balance"<<setw(24)<<balance<<endl<<"*******************************"<<endl;
    }

    bool checkPassword(string password){
        if(password == this->password){
            return true;
        }
        else{
            cout<<"Password does not match"<<endl;
            return false;
        }
    }

};

class App{
private:
    vector<Bank> accounts;
public:
    App(){}
    void run(){
        int choice;
        do{
            cout<<"1. Create Account"<<endl;
            cout<<"2. Login"<<endl;
            cout<<"3. Exit"<<endl;
            cout<<"Enter your choice: ";
            cin>>choice;
            switch(choice){
                case 1:
                    createAccount();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    cout<<"Exiting..."<<endl;
                    break;
                default:
                    cout<<"Invalid choice"<<endl;
            }
        }while(choice != 3);
    }


    void createAccount(){
        string userId;
        cout<<"Enter the user id: ";
        cin.ignore();
        getline(cin,userId);
        string password;
        cout<<"Enter the password: ";
        cin.ignore();
        getline(cin,password);
        string pass2;
        cout<<"confirm password: ";
        cin.ignore();
        getline(cin,pass2);
        if(pass2 != password){
            cout<<"Password does not match"<<endl;
            return;
        }
        string name;
        cout<<"Enter the name of the account holder: ";
        cin.ignore();
        getline(cin,name);
        string acc_no;
        cout<<"Enter the account number: ";
        cin.ignore();
        getline(cin,acc_no);
        string type;
        cout<<"Enter the type of account: ";
        cin.ignore();
        getline(cin,type);
        double balance;
        cout<<"Enter the initial balance: ";
        cin>>balance;

        Bank b(name,acc_no,type,balance, userId, password);
        accounts.push_back(b);
    }

    void login(){
        string user;
        cout<<"Enter the user id: ";
        cin.ignore();
        getline(cin,user);
        string pass;
        cout<<"Enter the password: ";
        cin.ignore();
        getline(cin,pass);
        for(int i = 0; i < accounts.size(); i++){
            if(accounts[i].checkUser(user) && accounts[i].checkPassword(pass)){
                cout<<"Login successful"<<endl;
                showOption(i);
                break;
            }else{
                cout<<"Login failed\nUser Id or passwordd is incorrect "<<endl;
            }
        }
    }

    void showOption(double n){
        double amt;
        int choice;
        do{
            cout<<"1. Check Balance"<<endl;
            cout<<"2. Deposit"<<endl;
            cout<<"3. Withdraw"<<endl;
            cout<<"4. Logout"<<endl;
            cout<<"Enter your choice: ";
            cin>>choice;
            switch(choice){
                case 1:
                    accounts[n].checkBalance();
                    break;
                case 2:
                    cout<<"Enter the amount to deposit: ";
                    cin>>amt;
                    accounts[n].deposit(amt);
                    break;
                case 3:
                    cout<<"Enter the amount to withdraw: ";
                    cin>>amt;
                    accounts[n].withdraw(amt);
                    break;
                case 4:
                    cout<<"Logged Out..."<<endl;
                    break;
                default:
                    cout<<"Invalid choice"<<endl;
            }
        }while(choice != 4);
    }

};

int main(){
    App a;
    a.run();
    return 0;
}