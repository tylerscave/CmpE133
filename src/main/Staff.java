package main;

public class Staff extends Member{

	public Staff(String lastName, String firstName, Address address, LoginInformation loginInfo, String phoneNumber, MemberType type){
		super(lastName, firstName, address, loginInfo, phoneNumber, type);
	}
}
