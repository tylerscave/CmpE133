package model;

import java.util.ArrayList;
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
    
    public static final int NEAR_TIME = 10; 

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
        Route route = drive.getRoute().createSubroute(startLocation, endLocation);
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
    
    public List<DriveChoice> getAvailableDriveChoices() {
        Data data = Data.getInstance();
        List<DriveChoice> availableDrives = new ArrayList<>();
        for (Drive d : data.getDrives()) {
            Route route = getRouteFromRequest(d);
            if (route != null)
                availableDrives.add(new DriveChoice(d, route));
        }
        return availableDrives;
    }
    
    private Route getRouteFromRequest(Drive drive) {
        Route route = drive.getRoute();
        if (route.getEndTime().before(new GregorianCalendar()))
            return null;
        if (route.getEndTime().before(endTime) && (endType == TimeType.AFTER))
            return null;
        if (route.getStartTime().after(startTime) && (startType == TimeType.BEFORE))
            return null;
        
        Route r = route.createSubroute(startLocation, endLocation);
        if (r == null)
            return null;
        if (r.getEndTime().before(endTime) && (endType == TimeType.AFTER))
            return null;
        if (r.getStartTime().after(startTime) && (startType == TimeType.BEFORE))
            return null;
        if (r.getEndTime().after(endTime) && (endType == TimeType.BEFORE))
            return null;
        if (r.getStartTime().before(startTime) && (startType == TimeType.AFTER))
            return null;
        GregorianCalendar stlow = new GregorianCalendar();
        GregorianCalendar sthigh = new GregorianCalendar();
        GregorianCalendar etlow = new GregorianCalendar();
        GregorianCalendar ethigh = new GregorianCalendar();
        stlow.setTime(startTime.getTime());
        sthigh.setTime(startTime.getTime());
        etlow.setTime(startTime.getTime());
        ethigh.setTime(startTime.getTime());
        stlow.add(GregorianCalendar.MINUTE, -NEAR_TIME);
        etlow.add(GregorianCalendar.MINUTE, -NEAR_TIME);
        sthigh.add(GregorianCalendar.MINUTE, NEAR_TIME);
        ethigh.add(GregorianCalendar.MINUTE, NEAR_TIME);
        if ((r.getEndTime().after(ethigh) || r.getEndTime().before(etlow)) && (endType == TimeType.NEAR))
            return null;
        if ((r.getStartTime().after(sthigh) || r.getStartTime().before(stlow)) && (startType == TimeType.NEAR))
            return null;
        return r;
    }
}
