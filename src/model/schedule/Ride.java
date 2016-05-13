package model.schedule;

import java.util.GregorianCalendar;
import java.util.List;
import model.StringFormat;
import model.member.Member;

/**
 *
 * @author David
 */
public class Ride extends Schedulable{
    private Route route;
    private int driveId;
    private RideStatus rideStatus;
    private String description;
    
    public Ride(Member member, Drive drive) {
        super(member);
        this.driveId = drive.getIdNumber();
        this.rideStatus = new RideStatus();
        description = "";
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

    @Override
    public void remove() {
        //TODO
    }

    @Override
    public GregorianCalendar getStartTime() {
        return route.getStartTime();
    }

    @Override
    public GregorianCalendar getEndTime() {
        return route.getEndTime();
    }
    
    public List<Location> getStops() {
        return route.getStops();
    }

    @Override
    public void setIdNumber(int idNumber) {
        super.setIdNumber(idNumber);
        rideStatus.setRideId(idNumber);
    }

    public RideStatus getRideStatus() {
        return rideStatus;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
    	return getMemberName()+": Ride on "+StringFormat.getDateFromCalendar(route.getEndTime())+". "+description;
    }
}
