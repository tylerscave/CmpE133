package model.schedule;

import java.util.GregorianCalendar;
import java.util.List;
import model.Notification;
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
            start = r.getStartType().name()+" "+getTimeFromCalendar(r.getStartTime());
        if (r.getEndType() != Request.TimeType.Anytime)
            start = r.getEndType().name()+" "+getTimeFromCalendar(r.getEndTime());
        StringBuilder sb = new StringBuilder();
        sb.append("You have entered a new request for a ride on ").append(getDateFromCalendar(r.getStartTime())).append(System.lineSeparator());
        sb.append("Details: ");
        sb.append(r.getStartLocation()).append(" ").append(start).append(" to ").append(r.getEndLocation()).append(" ").append(end);
        member.addNewNotification(new Notification(sb.toString()));
        
        member.setChanged();
        member.notifyObservers();
    }
    
    private String getDateFromCalendar(GregorianCalendar gc) {
        return gc.get(GregorianCalendar.MONTH)+"/"+gc.get(GregorianCalendar.DATE)+"/"+gc.get(GregorianCalendar.YEAR);
    }
    
    private String getTimeFromCalendar(GregorianCalendar gc) {
        String ampm[] = new String[2];
        ampm[0] = " AM";
        ampm[1] = " PM";
        int hour = gc.get(GregorianCalendar.HOUR);
        if (hour == 0)
            hour = 12;
        String minute = Integer.toString(gc.get(GregorianCalendar.MINUTE));
        if (minute.length() == 1)
            minute = "0"+minute;
        return hour+":"+minute+ampm[gc.get(GregorianCalendar.AM_PM)];
    }
}
