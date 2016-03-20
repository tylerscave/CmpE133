package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The model for the member
 * Solves CmpE133 Assignment 2
 * @author Tyler Jones, Hyesung Ko,
*/
public class Member extends Observable {
	private LoginInformation loginInfo;
	private String lastName;
	private String firstName;
	private Address address;
	private String phoneNumber;
	private MemberType type;
        private List<Drive> drives;
        private List<Ride> rides;
        private List<RideRequest> rideRequests;
        private List<Notification> oldNotifications;
        private List<Notification> newNotifications;
        private MemberSchedule memberSchedule;
        private Vehicle vehicle;
        private int idNumber;

        public Member() {
                this.loginInfo = new LoginInformation("", "");
		this.lastName = "";
		this.firstName = "";
		this.address = new Address("", "", "", "", "");
		this.phoneNumber = "";
		this.type = new Passenger();
                this.drives = new ArrayList<>();
                this.rides = new ArrayList<>();
                this.rideRequests = new ArrayList<>();
                this.oldNotifications = new ArrayList<>();
                this.newNotifications = new ArrayList<>();
                this.newNotifications.add(new Notification("Welcome to SpartanPool!"));
        }
	
	public Member(LoginInformation loginInfo, String lastName, String firstName, Address address, String phoneNumber, MemberType type){
		this.loginInfo = loginInfo;
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.type = type;
                this.drives = new ArrayList<>();
                this.rides = new ArrayList<>();
                this.rideRequests = new ArrayList<>();
                this.oldNotifications = new ArrayList<>();
                this.newNotifications = new ArrayList<>();
                this.newNotifications.add(new Notification("Welcome to SpartanPool!"));
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

    /**
     * Returns a list of new notifications and adds them them to the old notifications
     * 
     * @return the list of new notifications
     */
    public List<Notification> readNewNotifications() {
        List<Notification> tempNotifications = newNotifications;
        oldNotifications.addAll(tempNotifications);
        newNotifications = new ArrayList<>();
        setChanged();
        notifyObservers();
        return tempNotifications;
    }

    public void addNewNotification(Notification notification) {
        newNotifications.add(notification);
    }
    
    public List<Notification> getOldNotifications() {
        return oldNotifications;
    }
    
    public int getNumberOfNewNotifications() {
        return newNotifications.size();
    }
    
    public int getNumberOfOldNotifications() {
        return oldNotifications.size();
    }

    public MemberSchedule getMemberSchedule() {
        return memberSchedule;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }
    
    public Vehicle getVehicle() {
    	return vehicle;
    }
    
    public void setVehicle(Vehicle vehicle) {
    	this.vehicle = vehicle;
    }
    
    @Override
    public void setChanged() {
        super.setChanged();
    }
}
