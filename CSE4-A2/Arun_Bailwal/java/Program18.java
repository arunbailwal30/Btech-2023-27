import java.io.*;
import java.util.Scanner;
public class Program18 {

    public static void main(String args[])throws FileNotFoundException, IOException{
        Scanner sc = new Scanner(new File("myfile.txt"));
        int count=0;
        while(sc.hasNext()){
            String word = sc.next();
            int flag = 1;
            for(int i=0;i<word.length()/2;i++){
                if(word.charAt(i) != word.charAt(word.length()-i-1)){
                    flag = 0;
                }
            }
            if(flag == 1){
                System.out.println(word);
                count++;
            }
        }
        System.out.println("Total palindromes: "+count);

        sc.close();
    }
}
