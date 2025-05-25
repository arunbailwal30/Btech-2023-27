import java.util.Scanner;
class Bank {
    private String name;
    private int accNo;
    private String address;
    private double balance;
    Bank(){

    }
    public void set(String name, int accNo, String address, double balance){
        this.name = name;
        this.accNo = accNo;
        this.address = address;
        this.balance = balance;
    }
    public int get(){
        return this.accNo;
    }
    public void display(){
        System.out.println("Name of depositor: "+ name);
        System.out.println("Account number: "+accNo);
        System.out.println("Balance : "+balance+"cr");
        System.out.println("Address: "+address);
    }

    public void deposit(double amt){
        this.balance = this.balance+amt;
        System.out.println("Amount deposited");
        System.out.println("Current balance is "+this.balance+"cr");
    }
    
    public void withdraw(double amt){
        if(amt>this.balance){
            System.out.println("Not enough balance");
            return;
        }
        this.balance = this.balance - amt;
        System.out.println(amt+" deducted");
        System.out.println("Current balance is "+this.balance+"cr");
    }
    public void changeAddress(String address){
        this.address = address;
        System.out.println("Current address is: "+ this.address);
    }

}
public class Program9{
public static void main(String args[]){
    Scanner sc = new Scanner(System.in);
    int n;
    System.out.print("Enter number of depsoitors: ");
    n = sc.nextInt();
    Bank depositors[] = new Bank[n];
    for(int i=0;i<n;i++){
        depositors[i] = new Bank();
    }
    int i = 0, acc = 10001;
    int choice =1;
    while(choice != 6){
        System.out.println("""
                           Press:
                           1 to add depositor
                           2 to display info.
                           3 to deposit
                           4 to withdraw
                           5 to Change address
                           6 to exit""");
        choice = sc.nextInt();
        String name, address;
        double amt;     
        int flag = 0;       
        switch (choice) {
            case 1:
                if(i>=n) return;
                System.out.print("Enter name: ");
                sc.nextLine();
                name = sc.nextLine();
                System.out.print("Enter initial balance: ");
                amt = sc.nextDouble();
                System.out.println("Enter address:");
                sc.nextLine();
                address = sc.nextLine();
                depositors[i].set(name, acc++, address, amt);
                System.out.println("Account created");
                depositors[i].display();
                i++;
                break;
            case 2:
                System.out.print("Enter account no. for display: ");
                acc = sc.nextInt();
                for(int j=0 ; j< i; j++){
                    if(depositors[j].get()==acc){
                        depositors[j].display();
                        flag =1;
                        break;
                    }
                }
                if(flag ==0)
                System.out.println("Account not found");
                break;
            case 3:
                System.out.print("Enter account no. for deposit: ");
                acc = sc.nextInt();
                for(int j=0 ; j< i; j++){
                    if(depositors[j].get()==acc){
                        System.out.print("Enter amount: ");
                        amt = sc.nextDouble();
                        depositors[j].deposit(amt);
                        flag = 1;
                        break;
                    }
                }
                if(flag == 0)
                System.out.println("Account not found");
                break;
            case 4:
                System.out.print("Enter account no. for withdraw: ");
                acc = sc.nextInt();
                for(int j=0 ; j< i; j++){
                    if(depositors[j].get()==acc){
                        System.out.print("Enter amount: ");
                        amt = sc.nextDouble();
                        depositors[j].withdraw(amt);
                        flag = 1;
                        break;
                    }
                }
                if(flag == 0)
                System.out.println("Account not found");
                break;
            case 5:
                System.out.print("Enter account no. to change address: ");
                acc = sc.nextInt();
                for(int j=0 ; j< i; j++){
                    if(depositors[j].get()==acc){
                        System.out.print("Enter new address: ");
                        sc.nextLine();
                        address = sc.nextLine();
                        depositors[j].changeAddress(address);
                        flag =1;
                        break;
                    }
                }
                if(flag ==0)
                System.out.println("Account not found");
                break;
            case 6:
                return;
            default:
                break;
        }
    }
    sc.close();
}
}



