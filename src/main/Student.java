package main;

public class Student extends Member {
	private String studentID;

	public Student(String lastName, String firstName, Address address, LoginInformation loginInfo, String phoneNumber, MemberType type){
		super(lastName, firstName, address, loginInfo, phoneNumber, type);
		this.studentID = studentID;
	}
	
	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	
	
}
