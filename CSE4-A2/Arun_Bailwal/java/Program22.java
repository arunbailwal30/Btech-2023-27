import java.util.*;

public class Program22 {

     public static void swapPairs(ArrayList<String> list) {
        for (int i = 0; i < list.size() - 1; i += 2) {
            String temp = list.get(i);
            list.set(i, list.get(i + 1));
            list.set(i + 1, temp);
        }
        System.out.println("Updated array is: ");
        for (String str : list) {
            System.out.println(str);
        }
    }

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        System.out.print("Enter no. of strings: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        System.out.println("Enter strings: ");
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            String str = sc.nextLine();
            list.add(str);
        }
        swapPairs(list);

    }
}
