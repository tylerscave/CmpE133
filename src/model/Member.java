package model;

import java.util.ArrayList;
import java.util.List;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The model for the member
 * Solves CmpE133 Assignment 2
 * @author Tyler Jones, Hyesung Ko,
*/
public abstract class Member {
	private LoginInformation loginInfo;
	private String lastName;
	private String firstName;
	private Address address;
	private String phoneNumber;
	private MemberType type;
        private List<Drive> drives;
        private List<Ride> rides;
        private List<RideRequest> rideRequests;
        private List<Notification> notifications;

        public Member() {
                this.setLoginInfo(new LoginInformation("", ""));
		this.lastName = "";
		this.firstName = "";
		this.address = new Address("", "", "", "", "");
		this.phoneNumber = "";
		this.type = new Passenger();
                this.drives = new ArrayList<>();
                this.rides = new ArrayList<>();
                this.rideRequests = new ArrayList<>();
                this.notifications = new ArrayList<>();
                notifications.add(new Notification("Welcome to SpartanPool!"));
        }
	
	public Member(LoginInformation loginInfo, String lastName, String firstName, Address address, String phoneNumber, MemberType type){
		this.setLoginInfo(loginInfo);
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.type = type;
                this.drives = new ArrayList<>();
                this.rides = new ArrayList<>();
                this.rideRequests = new ArrayList<>();
                this.notifications = new ArrayList<>();
                this.notifications.add(new Notification("Welcome to SpartanPool!"));
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public MemberType getMemberType() {
		return type;
	}

	public void setMemberType(MemberType type) {
		this.type = type;
	}

	public LoginInformation getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInformation loginInfo) {
		this.loginInfo = loginInfo;
	}

    public List<Drive> getDrives() {
        return drives;
    }

    public List<Ride> getRides() {
        return rides;
    }

    public List<RideRequest> getRideRequests() {
        return rideRequests;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }
    
}
