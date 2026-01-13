import java.util.Scanner;
public class Program6 {
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
                for(int j=0;j<i;j++){
                    if(arr[j]>=0){
                        int temp = arr[i];
                        arr[i] = arr[j];
                        arr[j] = temp;
                        break;
                    }
                }
            }
        }
        for(int i: arr){
            System.out.print(i+" ");
        }
        sc.close();
    }
}
