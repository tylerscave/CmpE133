package model.schedule;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import model.Driver;
import model.LocationMap;
import model.Member;
import model.Notification;

/**
 *
 * @author David
 */
public class DriveScheduler extends Scheduler{
    
    @Override
    public String schedule(Request r, Schedulable s) {
        String fail = correctData(r);
        if (!fail.equals(""))
            return fail;
        Member member = r.getMember();
        if (!member.getDrivingType().isDriver())
            return "Failure: You are not a driver";
        GregorianCalendar time;
        if (r.getStartType() == Request.TimeType.Anytime) {
            LocationMap map = context.getMap();
            time = map.getStartTime(r.getEndTime(), r.getStartLocation(), r.getEndLocation());
        }
        else {
            time = r.getStartTime();
        }
        Drive drive = generateDrive(time, r);
        if (drive == null)
            return "Failure: Conflict with prior schedule";
        
        drive.setIdNumber(data.getNewSchedulableId());
        member.getDrives().add(drive);
        //toggle on/off with auto schedule code below
        List<Member> changed = new ArrayList<>();
        /*
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
        */
        member.setChanged();
        member.notifyObservers(changed);
        
        return "Success";
    }

    @Override
    public List<Schedulable> getAvailable(Request r) {
        List<Schedulable> available = new ArrayList<>();
        String fail = correctData(r);
        if (!fail.equals(""))
            return available;
        Member member = r.getMember();
        if (!member.getDrivingType().isDriver())
            return available;
        GregorianCalendar time;
        if (r.getStartType() == Request.TimeType.Anytime) {
            LocationMap map = context.getMap();
            time = map.getStartTime(r.getEndTime(), r.getStartLocation(), r.getEndLocation());
        }
        else {
            time = r.getStartTime();
        }
        Drive drive = generateDrive(time, r);
        if (drive == null)
            return available;
        
        available.add(drive);
        return available;
    }

    private Drive generateDrive(GregorianCalendar time, Request request) {
        Route route = new Route(time, request.getStartLocation(), request.getEndLocation());
        Member member = request.getMember();
        //already checked type in calling method
        Driver driver = (Driver) member.getDrivingType();
        Drive drive = new Drive(driver.getVehicle().getCapacity(), member);
        drive.setRoute(route);
        
        for (Drive d : member.getDrives()) {
            if (d.conflicts(drive))
                return null;
        }
        for (Ride r : member.getRides()) {
            if (r.conflicts(drive))
                return null;
        }
        //may not want to check for this 
        for (ParkingTime p : member.getParkingTimes()) {
            if (p.conflicts(drive))
                return null;
        }
        
        return drive;
    }
    
}
