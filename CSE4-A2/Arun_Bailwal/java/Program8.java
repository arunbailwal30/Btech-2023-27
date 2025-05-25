import java.util.*;
public class Program8 {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter string: ");
        String str = sc.nextLine();
        int count = 0;
        int last  = 0;
        int countZero = 0;
        for(int i =0;i<str.length();i++){
            if(str.charAt(i) == '0'){
                if(countZero ==0){
                    countZero++;
                }else{
                    countZero=1;
                    if(i-last > 1){
                    count++;
                    System.out.println("substring: "+str.substring(last,i)+0);
                    }
                }    
                last = i;
            }
        }
        System.out.println("count: "+count);
        sc.close();
    }   
}
