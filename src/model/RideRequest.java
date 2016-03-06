package model;

import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author David
 */
public class RideRequest {
    
    public enum TimeType {
        NEAR,
        BEFORE,
        AFTER,
        ANYTIME;
    }
    
    private Member member;
    private GregorianCalendar startTime;
    private GregorianCalendar endTime;
    private Location startLocation;
    private Location endLocation;
    private TimeType startType;
    private TimeType endType;

    public RideRequest(Member member, GregorianCalendar startTime, GregorianCalendar endTime, 
            Location startLocation, Location endLocation, TimeType startType, TimeType endType) {
        this.member = member;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startType = startType;
        this.endType = endType;
        
        member.getRideRequests().add(this);
        Data.getInstance().getRideRequests().add(this);
    }

    public boolean generateDrive() {
        if (startLocation == null || startTime == null || startType == null
                || endLocation == null || endTime == null || endType == null)
            return false;
        if (!(member.getMemberType() instanceof Driver))
            return false;
        
        Context context = Context.getInstance();
        Map map = context.getMap(); 
        Route route = new Route(map, map.getStartTime(endTime, startLocation, endLocation), startLocation, endLocation);
        for (Drive d : member.getDrives()) {
            if (d.getRoute().conflicts(route))
                return false;
        }
        for (Ride r : member.getRides()) {
            if (r.getRoute().conflicts(route))
                return false;
        }
        
        Driver driver = (Driver) member.getMemberType();
        Drive drive = new Drive(driver.getVehicle().getCapacity(), member);
        drive.setRoute(route);
        
        member.getDrives().add(drive);
        Data.getInstance().getDrives().add(drive);
        member.getRideRequests().remove(this);
        Data.getInstance().getRideRequests().remove(this);
        
        return true;
    }
    
    public boolean generateRide(Drive drive) {
        if (startLocation == null || startTime == null || startType == null
                || endLocation == null || endTime == null || endType == null)
            return false;
        if (drive.getNumSeats() < 1)
            return false;
        Route route = drive.getRoute().CreateSubroute(startLocation, endLocation);
        if (route == null)
            return false;
        for (Drive d : member.getDrives()) {
            if (d.getRoute().conflicts(route))
                return false;
        }
        for (Ride r : member.getRides()) {
            if (r.getRoute().conflicts(route))
                return false;
        }
        
        Ride ride = new Ride(member);
        ride.setRoute(route);
        member.getRides().add(ride);
        drive.addRide(ride);

        member.getRides().add(ride);
        Data.getInstance().getRides().add(ride);
        member.getRideRequests().remove(this);
        Data.getInstance().getRideRequests().remove(this);
        
        return true;
    }
    
    public List<Drive> getAvailableDrives() {
        //TODO
        return null;
    }
}
