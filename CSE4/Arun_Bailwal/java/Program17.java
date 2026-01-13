import java.util.Scanner;

public class Program17 {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter n and p : ");
        double n  =sc.nextDouble();
        double p = sc.nextDouble();
        MyCalculator ob = new MyCalculator();
        ob.power(n, p);
    }    
}

class Power extends RuntimeException{
    public Power(String s){
        super(s);
    }
}


class MyCalculator{
    void power(double n, double p){
        try{
            if(n <0 || p<0){
                throw new Power("n and p should be non-negative numbers\n");
            }
            if(n==0 && p ==0){
                throw new Power("n and p should be not be zero\n");
            }
            System.out.println("n^p : "+ Math.pow(n, p));
            
        }catch(Power e){
            System.out.println(e);
        }
    }
}
