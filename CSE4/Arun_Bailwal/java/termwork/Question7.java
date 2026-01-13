import java.util.Scanner;
public class Question7 {

    static int findPat(String str)
    {
        int count =0;
        str = str.toLowerCase();
        String[] words = str.split("\\s+");
        for(String word : words){
            if(word.contains("city")){
            count++;
            }
        }
        return count;
    }
    public static void main(String []args)
    {
        String str=new String();
        System.out.println("Enter the string : ");
        
        Scanner sc=new Scanner(System.in); 
        str=sc.nextLine();
        System.out.println("Count of 'city' : "+Question7.findPat(str));
    }
}
