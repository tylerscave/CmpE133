package model.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ListIterator;
import model.member.Driver;
import model.LocationMap;
import model.member.Member;
import model.Notification;

/**
 * Scheduler for one-time drives.
 * @author David Lerner
 */
public class DriveScheduler extends Scheduler{
    
    @Override
    public String schedule(Request request, Schedulable s) {
        String fail = correctData(request);
        //make sure data is correct
        if (!fail.equals(SUCCESS))
            return fail;
        Member member = request.getMember();
        //make sure member is a driver
        if (!member.getDrivingType().isDriver())
            return "Failure: You are not a driver";
        GregorianCalendar time;
        //get a concrete start time if drive start type is anytime
        if (request.getStartType() == Request.TimeType.Anytime) {
            LocationMap map = context.getMap();
            time = map.getStartTime(request.getEndTime(), request.getStartLocation(), request.getEndLocation());
        }
        else {
            time = request.getStartTime();
        }
        //generate drive and check if failed
        Drive drive = generateDrive(time, request);
        if (drive == null)
            return "Failure: Conflict with prior schedule";
        
        drive.setIdNumber(data.getNewSchedulableId());
        member.getDrives().add(drive);
        
        //auto-send notification
        String ls = System.lineSeparator();
        
        StringBuilder sb = new StringBuilder();
        sb.append("You have scheduled a new drive on ").append(getDateFromCalendar(drive.getStartTime())).append(ls);
        sb.append("Details: ");
        List<Location> stops = drive.getStops();
        sb.append(stops.get(0)).append(" at ").append(getTimeFromCalendar(drive.getStartTime())).append(" to ");
        sb.append(stops.get(stops.size()-1)).append(" at ").append(getTimeFromCalendar(drive.getEndTime()));
        member.addNewNotification(new Notification(sb.toString()));
        
        //automatically add any available riders
        List<Member> members = new ArrayList<>(data.getMembers());
        //make it fair
        Collections.shuffle(members);
        List<Member> changed = new ArrayList<>();
        for (Member m : members) {
            //skip if member is driver itself
            if (m.getIdNumber() == member.getIdNumber())
                continue;
            //iterate through all requests
            ListIterator<Request> it = m.getRequests().listIterator();
            while (it.hasNext()) {
                //return if no more available seats
                if (drive.getNumSeats() < 1) {
                    member.setChanged();
                    member.notifyObservers(changed);
                    return SUCCESS;
                }
                Request r = it.next();
                
                //sadd ride if possIble
                Ride ride = (new RideScheduler()).generateRide(drive, m, r);
                if (ride != null) {
                    ride.setIdNumber(data.getNewSchedulableId());
                    drive.addRide(ride);
                    m.getRides().add(ride);
                    //remove saved request
                    it.remove();
                    
                    //automatically send notifications to relevant members
                    sb = new StringBuilder();
                    sb.append(m).append(" is a new passenger for your drive on ").append(getDateFromCalendar(drive.getStartTime())).append(ls);
                    sb.append("Details: ");
                    stops = ride.getStops();
                    sb.append(stops.get(0)).append(" at ").append(getTimeFromCalendar(ride.getStartTime())).append(" to ");
                    sb.append(stops.get(stops.size()-1)).append(" at ").append(getTimeFromCalendar(ride.getEndTime()));
                    member.addNewNotification(new Notification(sb.toString()));
                    
                    sb = new StringBuilder();
                    sb.append(member).append(" is now your driver for your ride on ").append(getDateFromCalendar(ride.getStartTime())).append(ls);
                    sb.append("Details: ");
                    sb.append(stops.get(0)).append(" at ").append(getTimeFromCalendar(ride.getStartTime())).append(" to ");
                    sb.append(stops.get(stops.size()-1)).append(" at ").append(getTimeFromCalendar(ride.getEndTime()));
                    m.addNewNotification(new Notification(sb.toString()));
                    
                    changed.add(m);
                }
            }
        }
        
        //update database and return
        member.setChanged();
        member.notifyObservers(changed);
        
        return SUCCESS;
    }

    //same as schedule() except instead of adding it to the schedule immediately,
    //returns a list containing just the new drive
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
        
        //check for time conflicts
        for (Drive d : member.getDrives()) {
            if (d.conflicts(drive))
                return null;
        }
        for (Ride r : member.getRides()) {
            if (r.conflicts(drive))
                return null;
        }
        /*//may not want to check for this 
        for (ParkingTime p : member.getParkingTimes()) {
            if (p.conflicts(drive))
                return null;
        }*/
        
        return drive;
    }
    
}
