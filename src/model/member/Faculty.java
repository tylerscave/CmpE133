package model.member;

public class Faculty extends MemberType{
	
	private String sjsuID;
	
	public Faculty(){
		
	}
	
	public Faculty(String sjsuID){
		this.setSjsuID(sjsuID);
	}

	public String getSjsuID() {
		return sjsuID;
	}

	public void setSjsuID(String sjsuID) {
		this.sjsuID = sjsuID;
	}
}
