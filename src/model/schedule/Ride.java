package model.schedule;

import model.Member;
import model.schedule.Drive;

/**
 *
 * @author David
 */
public class Ride implements Schedulable{
    private int memberId;
    private Route route;
    private int driveId;
    private int idNumber;
    private String memberName;
    private RideStatus rideStatus;
    
    public Ride(Member member, Drive drive) {
        this.memberId = member.getIdNumber();
        this.driveId = drive.getIdNumber();
        this.memberName = member.toString();
        this.rideStatus = new RideStatus();
    }

    public int getMemberId() {
        return memberId;
    }

    public int getDriveId() {
        return driveId;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void remove() {
        //TODO
    }

    @Override
    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    @Override
    public int getIdNumber() {
        return idNumber;
    }

    @Override
    public String getMemberName() {
        return memberName;
    }
}
