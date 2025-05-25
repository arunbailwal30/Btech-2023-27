import static java.lang.Integer.max;
import java.util.*;
public class Question9 {

    static int minPlatform(int arr[], int dep[], int n){
        int count=0,ans=0;
        Arrays.sort(arr);
        Arrays.sort(dep);
        int j=0;
        for(int i=0;i<n;i++){
            while (j<n && dep[j]<arr[i]) {
                count--;
                j++;
            }
            count++;
            ans = max(ans,count);
        }
        return ans;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of arrival times: ");
        int n = sc.nextInt();

        int arr[] = new int[n];
        int dep[] = new int[n];
        System.out.print("Enter arrival time of train: ");
        for(int i=0;i<n;i++){
            
            arr[i] = sc.nextInt();
            
        }
        System.out.print("Enter arrival time of train: ");
        for(int i=0;i<n;i++){
            
            dep[i] = sc.nextInt();
            
        }    
        System.err.println("Minimum platform required is: "+ Question9.minPlatform(arr, dep, n));
        
    }
}
