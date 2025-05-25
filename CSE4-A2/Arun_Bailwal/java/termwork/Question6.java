import java.util.Scanner;

public class Question6 {
    static int findFact(int n) {
        if (n <= 1) return 1; // Corrected to handle 0 and 1
        return findFact(n - 1) * n;
    }

    static void solve(String str, int i, StringBuilder arr[], int n, int[] index) {
        if (i == n) {
            arr[index[0]] = new StringBuilder(str); // Store the current permutation
            index[0]++;
            return;
        }
        for (int j = i; j < n; j++) {
            str = swap(str, i, j); // Swap characters
            solve(str, i + 1, arr, n, index); // Recur
            str = swap(str, i, j); // Backtrack
        }
    }

    static String swap(String str, int i, int j) {
        char[] charArray = str.toCharArray();
        char temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
        return new String(charArray);
    }

    public static void main(String[] args) {
        String str;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the string: ");
        str = sc.nextLine();
        int number = Question6.findFact(str.length());
        StringBuilder arr[] = new StringBuilder[number];
        int n = str.length();
        int[] index = {0}; // To keep track of the current index in the array
        solve(str, 0, arr, n, index);
        for (StringBuilder x : arr) {
            System.out.println(x);
        }
        System.out.println("EXITING THE PROGRAM......");
    }
}
