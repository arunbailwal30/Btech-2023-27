import java.util.Scanner;

public class Program11 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayDemo a = new ArrayDemo();
        System.out.print("Enter the size: ");
        int n = sc.nextInt();
        int arr[] = new int[n];

        for(int i=0;i<n;i++){
            arr[i] = sc.nextInt();
        }
        System.out.print("Enter target: ");
        int target = sc.nextInt();
        a.arrayFunc(arr, target);

        System.out.print("Enter size p: ");
        int p = sc.nextInt();
        int[] A= new int[p];
        for(int i=0;i<p;i++){
            A[i] = sc.nextInt();
        }
        System.out.print("Enter size q: ");
        int q = sc.nextInt();
        int[] B = new int[q];
        for(int i=0;i<q;i++){
            B[i] = sc.nextInt();
        }
        sc.close();
        a.arrayFunc(A, p,B,q);
    }
}

class ArrayDemo{
    public void arrayFunc(int[] arr, int k){
        System.out.println("Pairs of elements whose sum is 10 are:");
        for(int i=0;i<arr.length-1;i++){
            for(int j=i+1;j<arr.length;j++){
                if(arr[i]+arr[j] == k){
                    System.out.println(arr[i]+"+"+arr[j]+"="+k);
                }
            }
        }

    }
    public void arrayFunc(int A[], int p, int B[], int q){
        int i=0,j=0,k;
        while(i<p){
            if(A[i] > B[j]){
                int temp1 = A[p-1];
                k =p-1;
                while(k>i){
                    A[k] = A[k-1];
                    k--;
                }
                A[k] = B[0];
                k = 0;
                while(k<q-1 && B[k+1]<temp1){
                    B[k] = B[k+1];
                    k++;
                }
                B[k] = temp1;  
            }
            i++;
        }
        System.out.print("A: ");
        for(int x: A){
            System.out.print(x+" ");
        }
        System.out.print("\nB: ");
        for(int x: B){
            System.out.print(x+" ");
        }
    }
}