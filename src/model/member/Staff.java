package model.member;

/**
 * Class representing staff member type..
 * @author David Lerner
 */
public class Staff extends MemberType{
    
    private String sjsuID;
    
    public Staff() {
        sjsuID = "000000000";
    }

    public Staff(String sjsuID) {
        this.sjsuID = sjsuID;
    }

    public String getSjsuID() {
        return sjsuID;
    }

    public void setSjsuID(String sjsuID) {
        this.sjsuID = sjsuID;
    }
}
