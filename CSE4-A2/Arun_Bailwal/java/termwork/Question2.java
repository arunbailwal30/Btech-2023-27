import java.util.Arrays;
import java.util.Scanner;
public class Question2 {
    static boolean checkAnagram(String str1,String str2)
    {
        if(str1.length()!=str2.length()) return false;

        char s1[]=str1.toCharArray();
        char s2[]=str2.toCharArray();
        Arrays.sort(s1);
        Arrays.sort(s2);
        for(int i=0;i<str1.length();i++)
        {
            if(s1[i]!=s2[i]) return false;
        }

        return true;
    } 
    public static void main(String []args)
    {
        Scanner sc=new Scanner(System.in);
        String str1=new String(),str2=new String();
        System.out.println("Enter the first string : ");
        str1=sc.nextLine(); 
        System.out.println("Enter the second string : ");
        str2=sc.nextLine();
        System.out.println(str1+" AND "+str2+" ARE ANAGRAM = "+anagrams.checkAnagram(str1,str2));
    }
}
