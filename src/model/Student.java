package model;

public class Student extends Member {
	private String studentID;

	public Student(String email, String password, String lastName, String firstName, Address address, String phoneNumber, MemberType type, String studentID){
		super(email, password, lastName, firstName, address, phoneNumber, type);
		this.studentID = studentID;
	}
	
	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	
	
}
