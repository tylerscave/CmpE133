package main;

public class Staff extends Member{

	public Staff(String lastName, String firstName, Address address, LoginInformation loginInfo, String phoneNumber, MemberType type){
		super(loginInfo, lastName, firstName, address, phoneNumber, type);
	}
}
