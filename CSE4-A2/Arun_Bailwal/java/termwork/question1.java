/* Write  a  program  to  insert  a  string  into  another  string  (Without  using  any  predefined 
method) at any given index */

import java.util.Scanner;


public class question1 {

    String insert(String s1,String s2,int index)
    {
        System.out.println("IN INSERT !!");
        StringBuffer str=new StringBuffer();
        int i=0;
        for(;i<s1.length() && i<index;i++)
        {
            str.append(s1.charAt(i));
        }
        for(int j=0;j<s2.length();j++)
        {
            str.append(s2.charAt(j));
        }
        for(int j=index;j<s1.length();j++) str.append(s1.charAt(j));

        System.out.println(("STRING IS : "+str));
    
        return  str.toString();   
    
    } 
    public static void main(String []args)
    {

        Scanner sc=new Scanner(System.in);
        String str1=new String();
        String str2=new String();
        System.out.println("Enter main string : ");
        str1=sc.nextLine();
        System.out.println("Enter SECOND string : ");
        str2=sc.nextLine();  
        
        System.out.println("Enter index to insert : ");
        int index=sc.nextInt();
        
        String res=new String();

        question1 obj=new question1();
        res = obj.insert(str1,str2,index);

        System.out.println("Original first string is : "+str1);
        System.out.println("Original second string is : "+str2);
        System.out.println("RESULTANT STRING IS : "+res);
    }   
   
}
