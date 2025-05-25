import java.util.Scanner;
public class Program7 {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter size of array: ");
        int n = sc.nextInt();

        int arr[][] = new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                arr[i][j] = sc.nextInt();
            }
        }
        int count = 0;
        for(int i =0;i<n;i++){
            int minIdx = 0;
            for(int j=0;j<n;j++){
                if(arr[i][j] < arr[i][minIdx]){
                    minIdx = j;
                }
            }
            int max = arr[i][minIdx];
            boolean flag = true;
            for(int j =0 ;j<n;j++){
                if(arr[j][minIdx] > max){
                    flag = false;
                    break;
                }
            }
            if(flag){
                count++;
                System.out.print(max+" ");
            }
        }
        if(count ==0){
            System.out.println("There is no such element");
        }

        sc.close();
    }
}
