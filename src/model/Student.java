package model;

public class Student implements MemberType {
	private String studentID;

	public Student(String studentID){
		this.studentID = studentID;
	}
	
	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	
	
}
