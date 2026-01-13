import java.util.Scanner;

public class Demo {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        sc.close();
    }
}


class Fruit{
    private int code;
    private String name;

    Fruit(String name){
        this.name = name;
    }
}

class Sale{
    private int code;
    private Fruit fruits;
    private double unit;
    public double Amount;
    public Sale(Fruit f, double unit){
        fruits = f;
        this.unit = unit;
    }
    public void Bill(double Amount){
        this.Amount = Amount;

    }

    public void discount(){

    }
}

class Fruitingms extends Fruit{
    private double available_kgs;
    private double price_per_kg;

    public Fruitingms(String name, double kgs, double price){
        super(name);
        this.available_kgs = kgs;
        this.price_per_kg = price;
    }

    public boolean checkavailability(double kg){
        return this.available_kgs > kg;
    }
    public void updateavailablity(double kg){
        this.available_kgs -= kg;
    }
}

class Fruitinpcs extends Fruit{
    private int available_pcs;
    private double price_per_piece;

    public Fruitinpcs(String name,int pcs, double price){
        super(name);
        this.available_pcs = pcs;
        this.price_per_piece = price;
    }
    public boolean checkavailability(int pcs){
        return this.available_pcs>pcs;
    }

    public void updateavailablity(int pcs){
        this.available_pcs -= pcs;
    }


}