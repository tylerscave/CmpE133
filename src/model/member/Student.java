package model.member;

/**
 * Class representing student member type..
 * @author David Lerner
 */
public class Student extends MemberType {
    
    private String studentID;

    public Student() {
        studentID = "000000000";
    }

    public Student(String studentID) {
        this.studentID = studentID;
    }
    
    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
        
}
