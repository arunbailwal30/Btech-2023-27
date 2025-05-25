import java.util.Scanner;

public class Program13 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("""
                1. Convert Celsius to Fahrenheit
                2. Convert Fahrenheit to Celsius
                """);
        int choice = sc.nextInt();
        System.out.print("Enter temperature:");
        double temp = sc.nextDouble();
        if(choice == 1){
            Temperature t1 = new Celsius();
            t1.setTempData(temp);
            t1.changeTemp();            
        }else if(choice == 2){
            Temperature t1 = new Fahrenheit();
            t1.setTempData(temp);
            t1.changeTemp();  
        }
    }
}

abstract class Temperature {
    double temp; 

    public void setTempData(double temp) {
        this.temp = temp;
    }
    abstract void changeTemp();
}

class Fahrenheit extends Temperature {
    double ctemp;

    public void changeTemp() {
        this.ctemp = (5.0 / 9.0) * (temp - 32);
        System.out.printf("Temperature in Celsius: %.2f%n", ctemp);   
    }
}

class Celsius extends Temperature {
    double ftemp;

    public void changeTemp() {
        this.ftemp = (9.0 / 5.0) * temp + 32;
        System.out.printf("Temperature in Fahrenheit: %.2f%n", ftemp);
    }
}