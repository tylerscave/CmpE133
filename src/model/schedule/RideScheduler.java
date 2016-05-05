package model.schedule;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import model.member.Member;
import model.Notification;

/**
 *
 * @author David
 */
public class RideScheduler extends Scheduler{
    
    public static final int NEAR_TIME = 10; 

    @Override
    public String schedule(Request r, Schedulable s) {
        String fail = correctData(r);
        if (!fail.equals(""))
            return fail;
        Member member = r.getMember();
        if (!(s instanceof Drive))
            return "Failure: No drive selected";
        
        //get latest drive info from dataHamdler
        Drive drive = (Drive) data.getSchedulable(s.getIdNumber());
        Ride ride = generateRide(drive, member, r);
        if (ride == null)
            return "Failure: Ride cannot be scheduled";
        
        drive.addRide(ride);
        member.getRides().add(ride);
        //remove saved request
        if (r.getName() != null)
            member.getRequests().remove(r);
        
        Member driver = data.getMember(drive.getMemberId());
        driver.addNewNotification(new Notification("You have a new passenger!"));
        List<Member> changed = new ArrayList<>();
        changed.add(driver);
        
        member.setChanged();
        member.notifyObservers(changed);
        
        return "Success";
    }

    @Override
    public List<Schedulable> getAvailable(Request r) {
        List<Schedulable> available = new ArrayList<>();
        List<Drive> drives = data.getDrives();
        for (Drive d : drives) {
            if (getRouteFromRequest(d, r) != null)
                available.add(d);
        }
        
        return available;
    }

    private Ride generateRide(Drive drive, Member member, Request request) {
        Route route = getRouteFromRequest(drive, request);
        if (route == null)
            return null;
        Ride ride = new Ride(member, drive);
        ride.setRoute(route);
        
        for (Drive d : member.getDrives()) {
            if (d.conflicts(ride))
                return null;
        }
        for (Ride r : member.getRides()) {
            if (r.conflicts(ride))
                return null;
        }
        //may not want to check for this 
        for (ParkingTime p : member.getParkingTimes()) {
            if (p.conflicts(ride))
                return null;
        }
        
        ride.setIdNumber(data.getNewSchedulableId());
        return ride;
    }
    
    private Route getRouteFromRequest(Drive drive, Request request) {
        Route route = drive.getRoute();
//        System.out.println("(drive)available seats passed");
        if (drive.getNumSeats() < 1)
            return null;
        if (route.getEndTime().before(new GregorianCalendar()))
            return null;
//        System.out.println("(drive)notPastTime passed");
        if (route.getEndTime().before(request.getEndTime()) && (request.getEndType() == Request.TimeType.After))
            return null;
//        System.out.println("(drive)notReversedTime passed");
        if (route.getStartTime().after(request.getStartTime()) && (request.getStartType() == Request.TimeType.Before))
            return null;
        
        Route r = route.createSubroute(request.getStartLocation(), request.getEndLocation());
        if (r == null)
            return null;
//        System.out.println("(drive)createSubroute passed");
        if (r.getEndTime().before(request.getEndTime()) && (request.getEndType() == Request.TimeType.After))
            return null;
//        System.out.println("(drive)Route.endTime<E.Time&&E.type=after passed");
        if (r.getStartTime().after(request.getStartTime()) && (request.getStartType() == Request.TimeType.Before))
            return null;
//        System.out.println("(drive)Route.startTime>S.Time&&S.type=before passed");
        if (r.getEndTime().after(request.getEndTime()) && (request.getEndType() == Request.TimeType.Before))
            return null;
//        System.out.println("(drive)Route.endTime>E.Time&&E.type=before passed");
        if (r.getStartTime().before(request.getStartTime()) && (request.getStartType() == Request.TimeType.After))
            return null;
//        System.out.println("(drive)Route.startTime<S.Time&&S.type=after passed");
        GregorianCalendar stlow = new GregorianCalendar();
        GregorianCalendar sthigh = new GregorianCalendar();
        GregorianCalendar etlow = new GregorianCalendar();
        GregorianCalendar ethigh = new GregorianCalendar();
        stlow.setTime(request.getStartTime().getTime());
        sthigh.setTime(request.getStartTime().getTime());
        etlow.setTime(request.getEndTime().getTime());
        ethigh.setTime(request.getEndTime().getTime());
        stlow.add(GregorianCalendar.MINUTE, -NEAR_TIME);
        etlow.add(GregorianCalendar.MINUTE, -NEAR_TIME);
        sthigh.add(GregorianCalendar.MINUTE, NEAR_TIME);
        ethigh.add(GregorianCalendar.MINUTE, NEAR_TIME);
        if ((r.getEndTime().after(ethigh) || r.getEndTime().before(etlow)) && (request.getEndType() == Request.TimeType.Near))
            return null;
//        System.out.println("(drive)(Route.endTime>E.Time+N||Route.endTime<E.Time-N)&&E.type=near passed");
        if ((r.getStartTime().after(sthigh) || r.getStartTime().before(stlow)) && (request.getStartType() == Request.TimeType.Near))
            return null;
//        System.out.println("(drive)(Route.startTime>S.Time+N||Route.startTime<S.Time-N)&&S.type=near passed");
        return r;
    }
}
