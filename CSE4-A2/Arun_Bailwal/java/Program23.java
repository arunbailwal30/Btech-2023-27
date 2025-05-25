import java.util.LinkedList;
import java.util.Scanner;

public class Program23 {
    public static void alternate(LinkedList<Integer> list1, LinkedList<Integer> list2) {
        LinkedList<Integer> list3 = new LinkedList<Integer>();
        while (list1.size() > 0 && list2.size() > 0) {
            int x = list1.removeFirst(); // Get and remove the first element from list1
            int y = list2.removeFirst(); // Get and remove the first element from list2
            list3.add(x);
            list3.add(y);
        }
        while (list1.size() > 0) {
            list3.add(list1.removeFirst()); // Add remaining elements from list1
        }
        while (list2.size() > 0) {
            list3.add(list2.removeFirst()); // Add remaining elements from list2
        }

        System.out.println("List 3: ");
        for (Integer i : list3) {
            System.out.print(i + " ");
        }
        System.out.println(); // Print a new line at the end for better formatting
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LinkedList<Integer> list1 = new LinkedList<Integer>();
        LinkedList<Integer> list2 = new LinkedList<Integer>();

        System.out.print("Enter size of list1: ");
        int n = sc.nextInt();
        System.out.print("Enter size of list2: ");
        int m = sc.nextInt();

        System.out.println("Enter elements of list1:");
        for (int i = 0; i < n; i++) {
            Integer x = sc.nextInt();
            list1.add(x);
        }

        System.out.println("Enter elements of list2:");
        for (int i = 0; i < m; i++) {
            Integer x = sc.nextInt();
            list2.add(x);
        }

        alternate(list1, list2);
        sc.close();
    }
}
