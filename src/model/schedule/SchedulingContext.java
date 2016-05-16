package model.schedule;

import java.util.List;
import model.Notification;
import model.StringFormat;
import model.member.Member;

/**
 * Scheduling Context. Uses strategy pattern to update member schedule based on request type
 * @author David Lerner
 */
public class SchedulingContext {
    private Scheduler scheduler;

    public void setScheduler(Request r) {
        if (r.getRequestType() == Request.RequestType.RIDE)
            scheduler = new RideScheduler();
        if (r.getRequestType() == Request.RequestType.DRIVE)
            scheduler = new DriveScheduler();
        if (r.getRequestType() == Request.RequestType.PARKING)
            scheduler = new ParkingScheduler();
        if (r.getRequestType() == Request.RequestType.WEEKLY)
            scheduler = new WeeklyScheduler();
    }

    /**
     * Returns a list of schedulables based on the request r.
     * @param r
     * @return a list of schedulables based on the request r
     */    
    public List<Schedulable> getAvailable(Request r) {
        setScheduler(r);
        return scheduler.getAvailable(r);
    }

    /**
     * Updates member schedule based on request r and schedulable s
     * @param r
     * @param s
     * @return SUCCESS if scheduling succeeded, some other String containing an error message otherwise
     */    
    public String schedule(Request r, Schedulable s) {
        setScheduler(r);
        return scheduler.schedule(r, s);
    }
    
    /**
     * Save a ride request to be added automatically to the next available drive.
     * @param r the ride request
     * @param name name of request
     */
    public void addRideToRequests(Request r, String name) {
        //only add ride requests
        if (r.getRequestType() != Request.RequestType.RIDE)
            return;
        Member member = r.getMember();
        r.setName(name);
        member.getRequests().add(r);
        
        //auto-send notification
        String start = "at Anytime";
        String end = "at Anytime";
        if (r.getStartType() != Request.TimeType.Anytime)
            start = r.getStartType().name()+" "+StringFormat.getTimeFromCalendar(r.getStartTime());
        if (r.getEndType() != Request.TimeType.Anytime)
            start = r.getEndType().name()+" "+StringFormat.getTimeFromCalendar(r.getEndTime());
        StringBuilder sb = new StringBuilder();
        sb.append("You have entered a new request for a ride on ").append(StringFormat.getDateFromCalendar(r.getStartTime())).append(System.lineSeparator());
        sb.append("Details: ");
        sb.append(r.getStartLocation()).append(" ").append(start).append(" to ").append(r.getEndLocation()).append(" ").append(end);
        member.addNewNotification(new Notification(sb.toString()));
        //update 
        member.setChanged();
        member.notifyObservers();
    }
}
