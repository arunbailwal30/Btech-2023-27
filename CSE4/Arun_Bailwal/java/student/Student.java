package student;
public class Student {
    int studentId;
    String name;
    int age;
    public Student(int id, String name, int age)
    {
        this.studentId= id;
        this.name = name;
        this.age = age;
    }
    public void getStudentInfo(){
        System.out.println("Student Id: "+studentId);
        System.out.println("Student Name: "+name);
        System.out.println("Student Age: "+age);
    }
}
