package model.schedule;

import java.util.GregorianCalendar;
import model.member.Member;

/**
 *
 * @author David
 */
public class ParkingTime extends Schedulable {
    private GregorianCalendar startTime;
    private GregorianCalendar endTime;
    private Location location;
    private ParkingSpot parkingSpot;
    
    public ParkingTime(Member member, GregorianCalendar startTime, 
            GregorianCalendar endTime, Location location, ParkingSpot parkingSpot) {
        super(member);
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.parkingSpot = parkingSpot;
    }
    
    
    @Override
    public boolean conflicts(Schedulable s) {
        return super.conflicts(s);/*
        if (s instanceof ParkingTime)
            
        
        if ((!parkingTime.getLocation().equals(location) || !parkingTime.getParkingSpot().equals(parkingSpot)) 
                && memberId != parkingTime.getMemberId())
            return false;
        return ((!parkingTime.getStartTime().before(this.startTime) && parkingTime.getStartTime().before(this.endTime)) 
                ||(!parkingTime.getEndTime().after(this.endTime) && parkingTime.getEndTime().after(this.startTime)));*/
    }

    @Override
    public GregorianCalendar getStartTime() {
        return startTime;
    }

    @Override
    public GregorianCalendar getEndTime() {
        return endTime;
    }

    public Location getLocation() {
        return location;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    @Override
    public void remove() {
        //ToDo
    }
}
