import java.util.Scanner;
import java.util.StringTokenizer;
public class Program5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String ip = sc.nextLine();

        StringTokenizer st =new StringTokenizer(ip, ".");
        while(st.hasMoreTokens()){
            String token = st.nextToken();
            int x = Integer.parseInt(token);
            

        }
        sc.close();
    }
}
