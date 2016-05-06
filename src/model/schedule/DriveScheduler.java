package model.schedule;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ListIterator;
import model.member.Driver;
import model.LocationMap;
import model.member.Member;
import model.Notification;

/**
 *
 * @author David
 */
public class DriveScheduler extends Scheduler{
    
    @Override
    public String schedule(Request request, Schedulable s) {
        String fail = correctData(request);
        if (!fail.equals(SUCCESS))
            return fail;
        Member member = request.getMember();
        if (!member.getDrivingType().isDriver())
            return "Failure: You are not a driver";
        GregorianCalendar time;
        if (request.getStartType() == Request.TimeType.Anytime) {
            LocationMap map = context.getMap();
            time = map.getStartTime(request.getEndTime(), request.getStartLocation(), request.getEndLocation());
        }
        else {
            time = request.getStartTime();
        }
        Drive drive = generateDrive(time, request);
        if (drive == null)
            return "Failure: Conflict with prior schedule";
        
        drive.setIdNumber(data.getNewSchedulableId());
        member.getDrives().add(drive);
        
        /*member.setChanged();
        member.notifyObservers();
        
        RideScheduler rs = new RideScheduler();
        List<Request> requests = data.getRequests();
        for (Request r : requests) {
            if (drive.getNumSeats() < 1)
                break;
            Member m = r.getMember();
            m.addObserver(data);
            if (rs.schedule(r, drive).equals(SUCCESS))
                data.notify(m.getIdNumber(), new Notification("You have a new driver!"));
            m.deleteObservers();
        }*/
        
        List<Member> members = data.getMembers();
        List<Member> changed = new ArrayList<>();
        for (Member m : members) {
            ListIterator<Request> it = m.getRequests().listIterator();
            while (it.hasNext()) {
                if (drive.getNumSeats() < 1) {
                    member.setChanged();
                    member.notifyObservers(changed);
                    return SUCCESS;
                }
                Request r = it.next();
                
                Ride ride = (new RideScheduler()).generateRide(drive, m, r);
                if (ride != null) {
                    ride.setIdNumber(data.getNewSchedulableId());
                    drive.addRide(ride);
                    m.getRides().add(ride);
                    //remove saved request
                    it.remove();
                    member.addNewNotification(new Notification("You have a new passenger!"));
                    m.addNewNotification(new Notification("You have a new driver!"));
                    changed.add(m);
                }
            }
        }
        
        member.setChanged();
        member.notifyObservers(changed);
        
        return SUCCESS;
    }

    @Override
    public List<Schedulable> getAvailable(Request r) {
        List<Schedulable> available = new ArrayList<>();
        String fail = correctData(r);
        if (!fail.equals(SUCCESS))
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
