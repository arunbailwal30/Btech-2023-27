import java.util.Scanner;
public class P6 {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int n;
        System.out.print("Enter size of array: ");

        n = sc.nextInt();
        int arr[] = new int[n];
        for(int i =0;i<n;i++){
            arr[i] = sc.nextInt();
        }

        for(int i =1;i<n;i++){
            if(arr[i] < 0){
                int j = i;
                while(j> 0 && arr[j-1] > 0){
                    int temp = arr[j];
                    arr[j ] = arr[j-1];
                    arr[j-1] = temp;
                    j--;
                }
            }
        }

        for(int i: arr){
            System.out.print(i+" ");
        }
        sc.close();
    }
}
