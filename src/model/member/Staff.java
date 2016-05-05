package model.member;

import model.member.MemberType;

public class Staff implements MemberType{
	
	private String sjsuID;

	public Staff(String sjsuID){
		this.setSjsuID(sjsuID);
	}

	public String getSjsuID() {
		return sjsuID;
	}

	public void setSjsuID(String sjsuID) {
		this.sjsuID = sjsuID;
	}
}
