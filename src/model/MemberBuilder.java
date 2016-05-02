package model;

import java.util.List;

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
    private MemberType type;
    private Vehicle vehicle;
    
    public MemberBuilder() {
        this.loginInfo = new LoginInformation("", "");
        this.lastName = "";
        this.firstName = "";
        this.address = new Address("", "", "", "", "");
        this.phoneNumber = "";
        this.type = new Passenger();
        this.vehicle = null;
        
        this.data = Context.getInstance().getDataHandler();
    }
    
    /**
     * Creates a new account with the attributes given and returns the unique
     * id number associated with it, if the login information already exits,
     * it returns -1.
     * 
     * @return the unique id number of the member, -1 on failure
     */
    public int build() {
        List<Member> members = data.getMembers();
        for (Member m : members) {
            if (m.getLoginInfo().equals(loginInfo))
                return -1;
        }
        Member member = new Member();
        member.setLoginInfo(loginInfo);
        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setAddress(address);
        member.setMemberType(type);
        member.setPhoneNumber(phoneNumber);
        member.setVehicle(vehicle);
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

    public void setType(MemberType type) {
        this.type = type;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
}
