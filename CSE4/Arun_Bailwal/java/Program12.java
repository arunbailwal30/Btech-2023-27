import java.util.Scanner;
public class Program12 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Shape shape = new Area();
        System.out.print("""
                1. calculate are of rectangle
                2. calculate are of square
                3. calculate are of circle
                """);
        int choice = sc.nextInt();
        double length,width, radius;
        switch (choice) {
            case 1:
                System.out.print("Enter length and width: ");
                length = sc.nextDouble();
                width  = sc.nextDouble();
                shape.rectangleArea(length, width);
                break;
            case 2:
                System.out.print("Enter length: ");
                length = sc.nextDouble();
                shape.squareArea(length);
                break;
            case 3:
                System.out.print("Enter radius: ");
                radius = sc.nextDouble();
                shape.circleArea(radius);
                break;
        }
    }
}

abstract class Shape{
    abstract void rectangleArea(double length, double width);
    abstract void squareArea(double length);
    abstract void circleArea(double radius);
}

class Area extends Shape{

    public void rectangleArea(double length, double width){
        System.out.println("Area of rectangle is: "+length*width);
    }
    public void squareArea(double length){
        System.out.println("Area of square is: "+length*length);
    }
    public void circleArea(double radius){
        System.out.println("Area of circle is: "+2*3.14*radius);
    }

}