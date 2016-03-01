package model;

public class Faculty extends Member {
	
	public Faculty(LoginInformation loginInfo, String lastName, String firstName, Address address, String phoneNumber, MemberType type){
		super(loginInfo, lastName, firstName, address, phoneNumber, type);
	}
}
