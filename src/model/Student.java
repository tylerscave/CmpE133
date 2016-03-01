package model;

public class Student extends Member {
	private String studentID;

	public Student(LoginInformation loginInfo, String lastName, String firstName, Address address, String phoneNumber, MemberType type, String studentID){
		super(loginInfo, lastName, firstName, address, phoneNumber, type);
		this.studentID = studentID;
	}
	
	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	
	
}
