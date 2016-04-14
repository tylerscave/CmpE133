package model.schedule;

import model.schedule.ParkingSpot;
import java.util.GregorianCalendar;
import model.Location;
import model.Member;

/**
 *
 * @author David
 */
public class ParkingTime implements Schedulable {
    private int idNumber;
    private int memberId;
    private String memberName;
    private GregorianCalendar startTime;
    private GregorianCalendar endTime;
    private Location location;
    private ParkingSpot parkingSpot;
    
    public ParkingTime(Member member, GregorianCalendar startTime, 
            GregorianCalendar endTime, Location location, ParkingSpot parkingSpot) {
        this.memberId = member.getIdNumber();
        this.memberName = member.toString();
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.parkingSpot = parkingSpot;
    }
    
    
    public boolean conflicts(ParkingTime parkingTime) {
        if ((!parkingTime.getLocation().equals(location) || !parkingTime.getParkingSpot().equals(parkingSpot)) 
                && memberId != parkingTime.getMemberId())
            return false;
        return ((!parkingTime.getStartTime().before(this.startTime) && parkingTime.getStartTime().before(this.endTime)) 
                ||(!parkingTime.getEndTime().after(this.endTime) && parkingTime.getEndTime().after(this.startTime)));
    }

    public GregorianCalendar getStartTime() {
        return startTime;
    }

    public GregorianCalendar getEndTime() {
        return endTime;
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

    public Location getLocation() {
        return location;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public int getMemberId() {
        return memberId;
    }
    
}
