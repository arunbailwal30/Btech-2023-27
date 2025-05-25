import java.util.Scanner;
import java.util.Arrays;

public class wiggleSort {
    public static void main(String [] args)
    {
        int n;
        Scanner sc=new Scanner(System.in);
        n=sc.nextInt();
        int arr[]=new int[n];
        for(int i=0;i<n;i++)
        {
            arr[i]=sc.nextInt();
        }

        Arrays.sort(arr);
        System.out.println("SORTED ARRAY IS : ");
        for(int ele:arr) System.out.print(ele+" ");
        for(int i=1;i<n;i+=2)
        {
            int temp=arr[i];
            arr[i]=arr[i-1];
            arr[i-1]=temp;
        }
        System.out.println();
        for(int ele:arr) System.out.print(ele+" ");

        System.out.println("EXITING THE PROGRAM.....");

    }    
}
