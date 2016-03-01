package model;

public class Faculty extends Member {
	
	public Faculty(String email, String password, String lastName, String firstName, Address address, String phoneNumber, MemberType type){
		super(email, password, lastName, firstName, address, phoneNumber, type);
	}
}
