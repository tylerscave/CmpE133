package model.member;

import model.schedule.RideRequest;
import model.schedule.Ride;
import model.schedule.ParkingTime;
import model.schedule.WeeklySchedule;
import model.schedule.Drive;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import model.Context;
import model.Notification;
import model.payment.BankAccountInfo;
import model.payment.CreditCard;
import model.payment.CreditCardInfo;
import model.schedule.Request;

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
    private DrivingType drivingType;
    private MemberType memberType;
    //private Vehicle vehicle;
    private List<Drive> drives;
    private List<Ride> rides;
    private List<ParkingTime> parkingTimes;
    private List<Request> requests;
    //legacy
    private List<RideRequest> rideRequests;
    //
    private List<Notification> oldNotifications;
    private List<Notification> newNotifications;
    private WeeklySchedule weeklySchedule;
    private int idNumber;
    
    private CreditCardInfo creditCardInfo;
    private BankAccountInfo bankAccountInfo;

    public Member() {
        this.loginInfo = new LoginInformation("", "");
        this.lastName = "";
        this.firstName = "";
        this.address = new Address("", "", "", "", "");
        this.phoneNumber = "";
        this.drivingType = new Passenger();
        this.memberType = new Student("000000000");
        this.drives = new ArrayList<>();
        this.rides = new ArrayList<>();
        this.parkingTimes = new ArrayList<>();
        this.requests = new ArrayList<>();
        //legacy
        this.rideRequests = new ArrayList<>();
        //
        this.oldNotifications = new ArrayList<>();
        this.newNotifications = new ArrayList<>();
        this.newNotifications.add(new Notification("Welcome to SpartanPool!"));
        this.weeklySchedule = new WeeklySchedule();
        
        this.creditCardInfo = new CreditCardInfo(toString(), CreditCardInfo.CardType.Visa, "", "", 1, 16);
        this.bankAccountInfo = new BankAccountInfo(toString(), "", "", "");
        }
    
    public Member(LoginInformation loginInfo, String lastName, String firstName, Address address, String phoneNumber, DrivingType drivingType){
        this.loginInfo = loginInfo;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.drivingType = drivingType;
        this.drives = new ArrayList<>();
        this.rides = new ArrayList<>();
        this.parkingTimes = new ArrayList<>();
        this.rideRequests = new ArrayList<>();
        this.oldNotifications = new ArrayList<>();
        this.newNotifications = new ArrayList<>();
        this.newNotifications.add(new Notification("Welcome to SpartanPool!"));
        this.weeklySchedule = new WeeklySchedule();
        
        this.creditCardInfo = new CreditCardInfo(toString(), CreditCardInfo.CardType.Visa, "", "", 0, 0);
        this.bankAccountInfo = new BankAccountInfo(toString(), "", "", "");
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

    public DrivingType getDrivingType() {
        return drivingType;
    }

    public void setDrivingType(DrivingType drivingType) {
        this.drivingType = drivingType;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
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

    public List<ParkingTime> getParkingTimes() {
        return parkingTimes;
    }

    public List<Request> getRequests() {
        return requests;
    }

    //legacy
    public List<RideRequest> getRideRequests() {
        return rideRequests;
    }

    /**
     * Returns a list of new notifications and adds them them to the old notifications
     * 
     * @return the list of new notifications
     */
    public List<Notification> readNewNotifications() {
        Context context = Context.getInstance();
        context.getParkingNotifier().addNewParkingNotifcations(context.getCentral(), this);
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

    public WeeklySchedule getWeeklySchedule() {
        return weeklySchedule;
    }

    public void setWeeklySchedule(WeeklySchedule weeklySchedule) {
        this.weeklySchedule = weeklySchedule;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }
    
    @Override
    public void setChanged() {
        super.setChanged();
    }

    @Override
    public String toString() {
        return firstName+" "+lastName;
    }

    public BankAccountInfo getBankAccountInfo() {
        return bankAccountInfo;
    }

    public CreditCardInfo getCreditCardInfo() {
        return creditCardInfo;
    }

    public void setBankAccountInfo(BankAccountInfo bankAccountInfo) {
        this.bankAccountInfo = bankAccountInfo;
    }

    public void setCreditCardInfo(CreditCardInfo creditCardInfo) {
        this.creditCardInfo = creditCardInfo;
    }
    
    
}
