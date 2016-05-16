package model.member;

/**
 * Class representing faculty member type..
 * @author David Lerner
 */
public class Faculty extends MemberType{
    
    private String sjsuID;
    
    public Faculty() {
        sjsuID = "000000000";
    }

    public Faculty(String sjsuID) {
        this.sjsuID = sjsuID;
    }

    public String getSjsuID() {
        return sjsuID;
    }

    public void setSjsuID(String sjsuID) {
        this.sjsuID = sjsuID;
    }
}