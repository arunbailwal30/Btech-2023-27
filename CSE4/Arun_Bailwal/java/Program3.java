//Program to find if the given numbers are Friendly pair or not (Amicable or not)

import java.util.Scanner;
//
public class Program3 {
    
    static int sumOfDivisors(int n) {
        int sum = 1;
        for (int i = 2; i <= n / 2; i++) {
            if (n % i == 0) {
            sum += i;
            }
        }
        return sum;
    }
    static boolean isFriendlyPair(int n1, int n2) {
        int sum1 = sumOfDivisors(n1);
        int sum2 = sumOfDivisors(n2);
        return ((sum1==n1) &&  (sum2==n2));
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter two numbers to check whether they are friendly numbers:");
                
        int n1 = sc.nextInt();
        int n2 = sc.nextInt();
        if (isFriendlyPair(n1, n2)) {
            System.out.println(n1 + " and " + n2 + " are friendly");
        }
        else {
            System.out.println(n1 + " and " + n2 + " are not friendly");
        }
        sc.close(); 
    }
        
        
}
