package enrollment;
import course.Course;
import student.Student;

public class Enrollment{
    Student s;
    Course c;
    public Enrollment(Student s, Course c){
        this.s = s;
        this.c = c;
    }
    public void getEnrollmentInfo(){
        s.getStudentInfo();
        c.getCourseInfo();
    }
}