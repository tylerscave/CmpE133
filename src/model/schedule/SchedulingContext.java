package model.schedule;

import java.util.GregorianCalendar;
import java.util.List;
import model.Notification;
import model.StringFormat;
import model.member.Member;

/**
 *
 * @author David
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
    
    public List<Schedulable> getAvailable(Request r) {
        setScheduler(r);
        return scheduler.getAvailable(r);
    }
    
    public String schedule(Request r, Schedulable s) {
        setScheduler(r);
        return scheduler.schedule(r, s);
    }
    
    public void addRideToRequests(Request r, String name) {
        if (r.getRequestType() != Request.RequestType.RIDE)
            return;
        Member member = r.getMember();
        r.setName(name);
        member.getRequests().add(r);
        
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
        
        member.setChanged();
        member.notifyObservers();
    }
}
