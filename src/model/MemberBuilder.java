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
    private DrivingType drivingType;
    private MemberType memberType;
    private Vehicle vehicle;
    
    public MemberBuilder() {
        this.loginInfo = new LoginInformation("", "");
        this.lastName = "";
        this.firstName = "";
        this.address = new Address("", "", "", "", "");
        this.phoneNumber = "";
        this.drivingType = new Passenger();
        this.memberType = new Student("000000000");
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
        member.setDrivingType(drivingType);
        member.setMemberType(memberType);
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

    public void setDrivingType(DrivingType drivingType) {
        this.drivingType = drivingType;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
}
