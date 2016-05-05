package model.schedule;

import model.schedule.Ride;
import model.schedule.Drive;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import model.Context;
import model.DataHandler;
import model.member.Driver;
import model.LocationMap;
import model.member.Member;
import model.Notification;

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
    private DataHandler data;
    private Context context;
    
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
        
        name = null;
        context = Context.getInstance();
        data = context.getDataHandler();
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
        if (!(member.getDrivingType() instanceof Driver))
            return false;
        return generateDrive(startTime);
    }
    
    public boolean generateDriveByEndTime() {
        if (!correctData())
            return false;
        if (!(member.getDrivingType() instanceof Driver))
            return false;
        LocationMap map = context.getMap();
        return generateDrive(map.getStartTime(endTime, startLocation, endLocation));
    }
    
    private boolean generateDrive(GregorianCalendar time) {
        Route route = new Route(time, startLocation, endLocation);
        for (Drive d : member.getDrives()) {
            if (d.getRoute().conflicts(route))
                return false;
        }
        for (Ride r : member.getRides()) {
            if (r.getRoute().conflicts(route))
                return false;
        }
        
        if (!(member.getDrivingType() instanceof Driver))
            return false;
        Driver driver = (Driver) member.getDrivingType();
        Drive drive = new Drive(driver.getVehicle().getCapacity(), member);
        drive.setRoute(route);
        drive.setIdNumber(data.getNewSchedulableId());
        
        member.getDrives().add(drive);
        if (name != null)
            member.getRideRequests().remove(this);
        List<Member> changed = new ArrayList<>();
        List<Member> members = data.getMembers();
        for (Member m : members) {
            for (RideRequest r : m.getRideRequests()) {
                Ride ride = generateRide(drive);
                if (ride != null) {
                    drive.addRide(ride);
                    m.getRides().add(ride);
                    m.getRideRequests().remove(this);
                    m.addNewNotification(new Notification("You have a new driver!"));
                    changed.add(m);
                }
            }
        }
        
        member.setChanged();
        member.notifyObservers(changed);
        
        return true;
    }
    
    public boolean generateRideFromRequest(Drive drive) {
        if (!correctData())
            return false;
        int test = 0;
        //get latest drive info from dataHamdler
        drive = (Drive) data.getSchedulable(drive.getIdNumber());
        Ride ride = generateRide(drive);
        if (ride == null)
            return false;
        
        drive.addRide(ride);
        member.getRides().add(ride);
        if (name != null)
            member.getRideRequests().remove(this);
        Member driver = data.getMember(drive.getMemberId());
        driver.addNewNotification(new Notification("You have a new passenger!"));
        List<Member> changed = new ArrayList<>();
        changed.add(driver);
        
        member.setChanged();
        member.notifyObservers(changed);
        
        return true;
    }
    
    private Ride generateRide(Drive drive) {
        Route route = getRouteFromRequest(drive);
        if (route == null)
            return null;
        for (Drive d : member.getDrives()) {
            if (d.getRoute().conflicts(route))
                return null;
        }
        for (Ride r : member.getRides()) {
            if (r.getRoute().conflicts(route))
                return null;
        }
        
        Ride ride = new Ride(member, drive);
        ride.setRoute(route);
        ride.setIdNumber(data.getNewSchedulableId());
        return ride;
    }
    
    public List<DriveChoice> getAvailableDriveChoices() {
//        System.out.println("Testing getAvailableDriveChoices():");
        if (!correctData())
            return new ArrayList<>();
//        System.out.println("correctData passed");
        List<DriveChoice> availableDrives = new ArrayList<>();
        List<Member> members = data.getMembers();
        for (Member m : members) {
            for (Drive d : m.getDrives()) {
                Route route = getRouteFromRequest(d);
                if (route != null) {
                    availableDrives.add(new DriveChoice(d, route));
//                    System.out.println("availableDrive passed");
                }
            }
        }
        return availableDrives;
    }
    
    private Route getRouteFromRequest(Drive drive) {
        Route route = drive.getRoute();
//        System.out.println("(drive)available seats passed");
        if (drive.getNumSeats() < 1)
            return null;
        if (route.getEndTime().before(new GregorianCalendar()))
            return null;
//        System.out.println("(drive)notPastTime passed");
        if (route.getEndTime().before(endTime) && (endType == TimeType.After))
            return null;
//        System.out.println("(drive)notReversedTime passed");
        if (route.getStartTime().after(startTime) && (startType == TimeType.Before))
            return null;
        
        Route r = route.createSubroute(startLocation, endLocation);
        if (r == null)
            return null;
//        System.out.println("(drive)createSubroute passed");
        if (r.getEndTime().before(endTime) && (endType == TimeType.After))
            return null;
//        System.out.println("(drive)Route.endTime<E.Time&&E.type=after passed");
        if (r.getStartTime().after(startTime) && (startType == TimeType.Before))
            return null;
//        System.out.println("(drive)Route.startTime>S.Time&&S.type=before passed");
        if (r.getEndTime().after(endTime) && (endType == TimeType.Before))
            return null;
//        System.out.println("(drive)Route.endTime>E.Time&&E.type=before passed");
        if (r.getStartTime().before(startTime) && (startType == TimeType.After))
            return null;
//        System.out.println("(drive)Route.startTime<S.Time&&S.type=after passed");
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
//        System.out.println("(drive)(Route.endTime>E.Time+N||Route.endTime<E.Time-N)&&E.type=near passed");
        if ((r.getStartTime().after(sthigh) || r.getStartTime().before(stlow)) && (startType == TimeType.Near))
            return null;
//        System.out.println("(drive)(Route.startTime>S.Time+N||Route.startTime<S.Time-N)&&S.type=near passed");
        return r;
    }
    
    public boolean addToRequests(String name) {
        if (!correctData())
            return false;
        this.name = name;
        member.getRideRequests().add(this);
        member.setChanged();
        member.notifyObservers();

        return true;
    }

    public String getName() {
        return name;
    }
    
    private boolean correctData() {
        if (startLocation == null || startTime == null || startType == null
                || endLocation == null || endTime == null || endType == null)
            return false;
//        System.out.println("correctInfo passed");
        if (startTime.after(endTime) && startType != TimeType.AnyTime && endType != TimeType.AnyTime)
            return false;
//        System.out.println("notReversedTime passed");
        if (endTime.before(new GregorianCalendar()) && endType != TimeType.AnyTime)
            return false;
//        System.out.println("notPastTime passed");
        return true;
    }
}
