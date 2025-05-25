
/*  Iterator interface
 * public boolean hasNext() - returns true if iterator has more elements otherwise it returns false
 * public Object next() - returns the enxt eleement and moves the cursor pointer to the next element
 * public void remove() - removes last elements returned by the iterator. 
 * 
 */
import java.util.*;

public class Program21 {

    public static void removeEvenLength(ArrayList<String> list) {
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).length() % 2 == 0) {
                list.remove(i);
            }
        }

        System.out.println("Updated list is: ");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public static void main(String args[]) {
        ArrayList<String> list = new ArrayList<String>();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of strings: ");
        int n = sc.nextInt();
        System.out.println("Enter strings: ");
        sc.next(); //

        for (int i = 0; i < n; i++) {
            String str = sc.nextLine();
            list.add(str);
        }

        removeEvenLength(list);

        sc.close();
    }
}