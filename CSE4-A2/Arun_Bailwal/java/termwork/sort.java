import java.util.Arrays;
import java.util.Scanner;;

public class sort {

    static String insertionSort(String str)
    {
        int n=str.length();
        char arr[]=str.toCharArray();
        int i=1;
        while(i<n)
        {
            int j=i-1;
            char key=arr[i];
            if(key!=' ')
            {
                while(j>=0 && key<arr[j])
                {
                    arr[j+1]=arr[j];
                    j--;
                }
                arr[j+1]=key;
            }
            i++;
        }
        String res=new String(arr);

        return res;
    } 

    static String builtin(String str)
    {
        char arr[]=str.toCharArray();
        Arrays.sort(arr);
        String res=new String(arr);
        return res;
    }

    public static void main(String [] args)
    {
        Scanner sc=new Scanner(System.in);
        String str1=new String();
        System.out.println("Enter the string : ");
        str1=sc.nextLine();
        /* str1=sort.insertionSort(str1); */

        str1=sort.builtin(str1);

        System.out.println("Sorted String is : "+str1);
    }
}
