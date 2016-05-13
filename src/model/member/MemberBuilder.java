package model.member;

import java.util.List;
import model.Context;
import model.DataHandler;

/**
 *
 * @author David
 */
public class MemberBuilder {
    private DataHandler data;
    private LoginInformation loginInfo;
    private String lastName;
    private String firstName;
    private Address address;
    private String phoneNumber;
    private DrivingType drivingType;
    private MemberType memberType;
    
    public MemberBuilder() {
        this.loginInfo = new LoginInformation("", "");
        this.lastName = "";
        this.firstName = "";
        this.address = new Address("", "", "", "", "");
        this.phoneNumber = "";
        this.drivingType = new Passenger();
        this.memberType = new Student("000000000");
        
        this.data = Context.getInstance().getDataHandler();
    }
    
    /**
     * Creates a new account with the attributes given and returns the unique
     * id number associated with it, if the login information already exits,
     * it returns -1, and if the loginInfo is invalid, it returns -2 
     * 
     * @return the unique id number of the member, negative on failure
     */
    public int build() {
        if (loginInfo.getEmail().equals("") || loginInfo.getPassword().equals(""))
            return -2;
        Member member = new Member();
        member.setLoginInfo(loginInfo);
        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setAddress(address);
        member.setDrivingType(drivingType);
        member.setMemberType(memberType);
        member.setPhoneNumber(phoneNumber);
        return data.addMember(member);
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLoginInfo(LoginInformation loginInfo) {
        this.loginInfo = loginInfo;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDrivingType(DrivingType drivingType) {
        this.drivingType = drivingType;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }
    
}
