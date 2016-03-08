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
        Near,
        Before,
        After,
        AnyTime;
    }
    
    private Member member;
    private GregorianCalendar startTime;
    private GregorianCalendar endTime;
    private Location startLocation;
    private Location endLocation;
    private TimeType startType;
    private TimeType endType;
    private String name;
    
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
        this.name = null;
        
        member.getRideRequests().add(this);
        Data.getInstance().getRideRequests().add(this);
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public GregorianCalendar getEndTime() {
        return endTime;
    }

    public TimeType getEndType() {
        return endType;
    }

    public Member getMember() {
        return member;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public GregorianCalendar getStartTime() {
        return startTime;
    }

    public TimeType getStartType() {
        return startType;
    }

    public boolean generateDriveByStartTime() {
        if (!correctData())
            return false;
        if (!(member.getMemberType() instanceof Driver))
            return false;
        Context context = Context.getInstance();
        Map map = context.getMap();
        return generateDrive(startTime);
    }
    
    public boolean generateDriveByEndTime() {
        if (!correctData())
            return false;
        if (!(member.getMemberType() instanceof Driver))
            return false;
        Context context = Context.getInstance();
        Map map = context.getMap();
        return generateDrive(map.getStartTime(endTime, startLocation, endLocation));
    }
    
    private boolean generateDrive(GregorianCalendar time) {
        Context context = Context.getInstance();
        Map map = context.getMap(); 
        Route route = new Route(map, time, startLocation, endLocation);
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
        if (!correctData())
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
        
        Ride ride = new Ride(member, drive);
        ride.setRoute(route);
        
        drive.addRide(ride);
        member.getRides().add(ride);
        Data.getInstance().getRides().add(ride);
        member.getRideRequests().remove(this);
        Data.getInstance().getRideRequests().remove(this);
        drive.getMember().getNotifications().add(new Notification("You have a new passenger!"));
        
        return true;
    }
    
    public List<DriveChoice> getAvailableDriveChoices() {
        //System.out.println("Testing getAvailableDriveChoices():");
        if (!correctData())
            return new ArrayList<>();
        //System.out.println("correctData passed");
        Data data = Data.getInstance();
        List<DriveChoice> availableDrives = new ArrayList<>();
        for (Drive d : data.getDrives()) {
            Route route = getRouteFromRequest(d);
            if (route != null) {
                availableDrives.add(new DriveChoice(d, route));
                //System.out.println("availableDrive passed");
            }
        }
        return availableDrives;
    }
    
    private Route getRouteFromRequest(Drive drive) {
        Route route = drive.getRoute();
        if (route.getEndTime().before(new GregorianCalendar()))
            return null;
        //System.out.println("(drive)notPastTime passed");
        if (route.getEndTime().before(endTime) && (endType == TimeType.After))
            return null;
        //System.out.println("(drive)notReversedTime passed");
        if (route.getStartTime().after(startTime) && (startType == TimeType.Before))
            return null;
        
        Route r = route.createSubroute(startLocation, endLocation);
        if (r == null)
            return null;
        //System.out.println("(drive)createSubroute passed");
        if (r.getEndTime().before(endTime) && (endType == TimeType.After))
            return null;
        //System.out.println("(drive)Route.endTime<E.Time&&E.type=after passed");
        if (r.getStartTime().after(startTime) && (startType == TimeType.Before))
            return null;
        //System.out.println("(drive)Route.startTime>S.Time&&S.type=before passed");
        if (r.getEndTime().after(endTime) && (endType == TimeType.Before))
            return null;
        //System.out.println("(drive)Route.endTime>E.Time&&E.type=before passed");
        if (r.getStartTime().before(startTime) && (startType == TimeType.After))
            return null;
        //System.out.println("(drive)Route.startTime<S.Time&&S.type=after passed");
        GregorianCalendar stlow = new GregorianCalendar();
        GregorianCalendar sthigh = new GregorianCalendar();
        GregorianCalendar etlow = new GregorianCalendar();
        GregorianCalendar ethigh = new GregorianCalendar();
        stlow.setTime(startTime.getTime());
        sthigh.setTime(startTime.getTime());
        etlow.setTime(endTime.getTime());
        ethigh.setTime(endTime.getTime());
        stlow.add(GregorianCalendar.MINUTE, -NEAR_TIME);
        etlow.add(GregorianCalendar.MINUTE, -NEAR_TIME);
        sthigh.add(GregorianCalendar.MINUTE, NEAR_TIME);
        ethigh.add(GregorianCalendar.MINUTE, NEAR_TIME);
        if ((r.getEndTime().after(ethigh) || r.getEndTime().before(etlow)) && (endType == TimeType.Near))
            return null;
        //System.out.println("(drive)(Route.endTime>E.Time+N||Route.endTime<E.Time-N)&&E.type=near passed");
        if ((r.getStartTime().after(sthigh) || r.getStartTime().before(stlow)) && (startType == TimeType.Near))
            return null;
        //System.out.println("(drive)(Route.startTime>S.Time+N||Route.startTime<S.Time-N)&&S.type=near passed");
        return r;
    }
    
    public boolean addToRequests(String name) {
        if (!correctData())
            return false;
        this.name = name;
        member.getRideRequests().add(this);
        Data.getInstance().getRideRequests().add(this);
        return true;
    }

    public String getName() {
        return name;
    }
    
    private boolean correctData() {
        if (startLocation == null || startTime == null || startType == null
                || endLocation == null || endTime == null || endType == null)
            return false;
        //System.out.println("correctInfo passed");
        if (startTime.after(endTime) && startType != TimeType.AnyTime && endType != TimeType.AnyTime)
            return false;
        //System.out.println("notReversedTime passed");
        if (endTime.before(new GregorianCalendar()) && endType != TimeType.AnyTime)
            return false;
        //System.out.println("notPastTime passed");
        return true;
    }
}
