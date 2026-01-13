import java.util.Scanner; 
public class Program14 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice ;
        I1 obj;
        double radius, height;
        System.out.println("""
                1. calculate volume of cone
                2. calculate volume of hemisphere
                3. calculate volume of cylinder
                4. exit
                """);
        do { 
            System.out.println("Enter choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter radius and height: ");
                    radius = sc.nextDouble();
                    height = sc.nextDouble();
                    obj = new Cone(radius, height);
                    obj.volume();
                    break;
                case 2:
                System.out.print("Enter radius: ");
                    radius = sc.nextDouble();
                    obj = new Hemisphere(radius);
                    obj.volume();
                    break;
                case 3:
                System.out.println("Enter radius and height: ");
                    radius = sc.nextDouble();
                    height= sc.nextDouble();
                    obj = new Cylinder(radius, height);
                    obj.volume();
                    break;
                default:
                    break;
            }
        } while (choice != 4);
        sc.close();
    }
}

interface I1{
    public void volume();
}

class Cone implements I1{
    public double radius, height;
    private final double pi;
    public Cone(double radius, double  height){
        this.height = height;
        this.radius = radius;
        pi = 22/7;
    }
    public void volume(){
        System.out.println("Volume of cone = "+String.format("%.2f",(1.0/3.0)*pi*radius*height));
    }
}

class Hemisphere implements I1{
    public double radius;
    private final double pi;
    public Hemisphere(double radius){
        this.radius = radius;
        pi = 22/7;
    }
    public void volume(){
        System.out.println("Volume of hemisphere = "+String.format("%.2f",(2.0/3.0)*pi*radius*3));
    }
}
class Cylinder implements I1{
    public double radius, height;
    private final double pi;
    public Cylinder(double radius, double height){
        this.height = height;
        this.radius = radius;
        pi = 22/7;
    }
    public void volume(){
        System.out.println("Volume of cone = "+String.format("%.2f",pi*radius*2*height));
    }
}
