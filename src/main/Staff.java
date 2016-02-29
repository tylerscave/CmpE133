package main;

public class Staff extends Member{
	
	private String sjsuID;

	public Staff(String lastName, String firstName, Address address, LoginInformation loginInfo, String phoneNumber, MemberType type, String sjsuID){
		super(loginInfo, lastName, firstName, address, phoneNumber, type);
		this.setSjsuID(sjsuID);
	}

	public String getSjsuID() {
		return sjsuID;
	}

	public void setSjsuID(String sjsuID) {
		this.sjsuID = sjsuID;
	}
}
