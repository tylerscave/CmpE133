package model.member;

public class Staff extends MemberType{
	
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
