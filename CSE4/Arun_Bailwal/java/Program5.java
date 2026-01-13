import java.util.*;
class zigzag{
    void swap(int arr[], int i, int j)
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    public static void main(String args[]) 
    {
        zigzag ob=new zigzag();
        Scanner sc = new Scanner(System.in);   
        System.out.println("Enter the size:");
        int n = sc.nextInt();
        int arr[] = new int[n];
        System.out.println("Enter the elements:");
        for (int i=0;i<n;i++) {
            arr[i]=sc.nextInt();
        }
        for(int i=0;i<n-1; i++){
            if (i%2==1) { 
                if (arr[i]>arr[i+1]){ 
                ob.swap(arr,i,i+1);
                }
            } 
            else { 
                if(arr[i] < arr[i+1]){ 
                ob.swap(arr, i, i+1);
                }
            } 
        }
        System.out.println("Zig-Zag: ");
        for (int i = 0; i<n; i++) {
            System.out.print(arr[i] + " ");
        }        
        sc.close();  
    }
}
