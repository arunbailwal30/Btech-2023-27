import java.util.Scanner;
import course.Course;
import enrollment.Enrollment;
import student.Student;
public class Program15 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        Enrollment arr[] = new Enrollment[n];
        for (int i = 0; i < arr.length; i++) {
            System.out.print("Enter student id: ");
            int id= sc.nextInt();
            System.out.print("Enter name of student: ");
            sc.nextLine();
            String name = sc.nextLine();
            System.out.print("Enter age: ");
            int age = sc.nextInt();
            System.out.print("Enter Course id: ");
            int Cid = sc.nextInt();
            System.out.print("Enter the name of course: ");
            sc.nextLine();
            String Cname = sc.nextLine();
            System.out.print("Enter Credits of course: ");
            int credits = sc.nextInt();
            Student s1 = new Student(id,name, age);
            Course c1 = new Course(Cid, Cname, credits);
            arr[i] = new Enrollment(s1, c1);
        }
        System.out.println("Students information::\n");
        for(int i=0;i<n;i++){
            arr[i].getEnrollmentInfo();
        }
        sc.close();
    }
}

