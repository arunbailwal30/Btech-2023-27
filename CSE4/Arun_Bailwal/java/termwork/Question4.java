/* Program to Extract Substring from a String with Equal 0, 1, and 2 */

import java.util.Arrays;
import java.util.Scanner;
public class Question4 {

    static int find(String str)
    {
        int n=str.length(),count=0;
        for(int i=0;i<n;i++)
        {
            int count0=0,count1=0,count2=0;
            for(int j=i;j<n;j++)
            {
                if(str.charAt(j)=='0') count0++;
                else if(str.charAt(j)=='1') count1++;
                else if(str.charAt(j)=='2') count2++;
                if(count0!=0 && count0==count1 && count1==count2) 
                {
                    System.out.println(str.substring(i,j));
                    count++;
                }
            }
            
        }
        return count;
    }
    public static void main(String []args)
    {
        Scanner sc=new Scanner(System.in);
        String str=new String();
        System.out.println("Enter the string : ");
        str=sc.nextLine();

        System.out.println(str+" contains "+ find(str)+ " number of substring containing 0's 1's and 2's ");

    }
}
