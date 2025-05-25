import java.util.Scanner;
public class Question8 {
    static boolean check(String str)
    {
        return str.substring(str.length()-10).equals("@gmail.com");
    }
    public static void main(String []args)
    {
        Scanner sc=new Scanner(System.in);
        String email=new String();
        System.out.println("Enter your email . ");
        email=sc.nextLine();
        System.out.println(email+" is Valid = "+Question8.check(email));
    }
}

