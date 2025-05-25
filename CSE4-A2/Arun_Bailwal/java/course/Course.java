package course;

public class Course{
	int coursId;
	String courseName;
	int credits;
    public Course(int id, String name, int credits){
        this.coursId = id;
        this.courseName = name;
        this.credits = credits;
    }

    public void getCourseInfo(){
        System.out.println("Course Id: "+coursId);
        System.out.println("Credits of course: "+credits);
        System.out.println("Course Name: "+courseName);
    }

}