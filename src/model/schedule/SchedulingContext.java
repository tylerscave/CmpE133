package model.schedule;

import java.util.List;
import model.Member;

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
    
    public boolean addToRequests(Request r, String name) {
        if (r.getRequestType() != Request.RequestType.RIDE)
            return false;
        Member member = r.getMember();
        r.setName(name);
        member.getRequests().add(r);
        member.setChanged();
        member.notifyObservers();
        return true;
    }
}
