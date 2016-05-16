package model.schedule;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import model.member.Member;
import model.Notification;

/**
 * Scheduler for one-time rides.
 * @author David Lerner
 */
public class RideScheduler extends Scheduler{
    
    public static final int NEAR_TIME = 10; 

    @Override
    public String schedule(Request r, Schedulable s) {
        String fail = correctData(r);
        //make sure data is correct
        if (!fail.equals(SUCCESS))
            return fail;
        Member member = r.getMember();
        //make sure there is a driver
        if (!(s instanceof Drive))
            return "Failure: No drive selected";
        
        //get latest drive info from dataHamdler
        Drive drive = (Drive) data.getSchedulable(s.getIdNumber());
        //generate ridee and check if failed
        Ride ride = generateRide(drive, member, r);
        if (ride == null)
            return "Failure: Ride cannot be scheduled";
        
        //add ride to schedules
        ride.setIdNumber(data.getNewSchedulableId());
        drive.addRide(ride);
        member.getRides().add(ride);
        //remove saved request
        if (r.getName() != null)
            member.getRequests().remove(r);
        
        Member driver = data.getMember(drive.getMemberId());
        
        //auto-send notification
        String ls = System.lineSeparator();
        
        StringBuilder sb = new StringBuilder();
        sb.append(ride.getMemberName()).append(" is a new passenger for your drive on ").append(getDateFromCalendar(drive.getStartTime())).append(ls);
        sb.append("Details: ");
        List<Location> stops = ride.getStops();
        sb.append(stops.get(0)).append(" at ").append(getTimeFromCalendar(ride.getStartTime())).append(" to ");
        sb.append(stops.get(stops.size()-1)).append(" at ").append(getTimeFromCalendar(ride.getEndTime()));
        driver.addNewNotification(new Notification(sb.toString()));
        
        sb = new StringBuilder();
        sb.append("You have scheduled a new ride on ").append(getDateFromCalendar(ride.getStartTime())).append(", ").append(driver).append(" is your driver.").append(ls);
        sb.append("Details: ");
        sb.append(stops.get(0)).append(" at ").append(getTimeFromCalendar(ride.getStartTime())).append(" to ");
        sb.append(stops.get(stops.size()-1)).append(" at ").append(getTimeFromCalendar(ride.getEndTime()));
        member.addNewNotification(new Notification(sb.toString()));
        
        List<Member> changed = new ArrayList<>();
        changed.add(driver);
        
        //update & return
        member.setChanged();
        member.notifyObservers(changed);
        
        return SUCCESS;
    }

    /**
     * Returns a list of available drives based on the ride request.
     * @param r
     * @return a list of available drives based on the ride request
     */
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

    /**
     * Returns a new ride object based on the parameters.
     * @param drive drive of this ride
     * @param member rider
     * @param request the ride request
     * @return a new ride object
     */
    public Ride generateRide(Drive drive, Member member, Request request) {
        Route route = getRouteFromRequest(drive, request);
        //null check
        if (route == null)
            return null;
        Ride ride = new Ride(member, drive);
        ride.setRoute(route);
        
        //check for time conflict
        for (Drive d : member.getDrives()) {
            if (d.conflicts(ride))
                return null;
        }
        for (Ride r : member.getRides()) {
            if (r.conflicts(ride))
                return null;
        }
        /*may not want to check for this 
        for (ParkingTime p : member.getParkingTimes()) {
            if (p.conflicts(ride))
                return null;
        }*/
        
        return ride;
    }
    
    private Route getRouteFromRequest(Drive drive, Request request) {
        Route route = drive.getRoute();
        //run several checks to make sure ride is possible
        if (drive.getNumSeats() < 1)
            return null;
//        System.out.println("(drive)available seats passed");
        if (route.getEndTime().before(new GregorianCalendar()))
            return null;
//        System.out.println("(drive)notPastTime passed");
        if (route.getEndTime().before(request.getEndTime()) && (request.getEndType() == Request.TimeType.After))
            return null;
        if (route.getStartTime().after(request.getStartTime()) && (request.getStartType() == Request.TimeType.Before))
            return null;
        //check if rider's route is one driver's route then do several time checks
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
        //passed all tests, return new ride
        return r;
    }
}
