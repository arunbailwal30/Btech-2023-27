import java.text.DecimalFormat;
import java.util.Scanner;
public class Program2 {
    public static void main(String args[]) {    
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the choice:\n 1. for Term Deposit\n 2. for Recurring Deposit");
        int ch = sc.nextInt();
        double p, r, t, amount;
        DecimalFormat d=new DecimalFormat("#.##");
        switch (ch) {
            case 1:
            System.out.println("Please enter principal:");
            p = sc.nextDouble();
            System.out.println("Please enter rate:");
            r = sc.nextDouble();
            System.out.println("Please enter time period in years:");
            t = sc.nextDouble();
            amount = p * Math.pow((1 + (r / 100)), t);
            System.out.println("Total amount after term deposit: " + d.format(amount));
            break;
        case 2:
            System.out.println("Please enter monthly installment amount:");
            p = sc.nextDouble();
            System.out.println("Please enter rate:");
            r = sc.nextDouble();
            System.out.println("Please enter time period in months:");
            t = sc.nextDouble();
            amount = (p * t) + (p * (t * (t + 1)) / 2 * (r / 100) * (1.0 / 12));
            System.out.println("Total amount after recurring deposit: " + d.format(amount));
            break;
            default:
            System.out.println("Enter a correct option.");
            break;
        }
        sc.close();
    }
}

