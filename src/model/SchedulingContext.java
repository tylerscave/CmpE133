package model;

import java.util.List;

/**
 *
 * @author David
 */
public class SchedulingContext {
    private Scheduler scheduler;

    public void setScheduler(ScheduleRequest r) {
        if (r.getRequestType() == ScheduleRequest.RequestType.RIDE)
            scheduler = null;
        if (r.getRequestType() == ScheduleRequest.RequestType.DRIVE)
            scheduler = null;
        if (r.getRequestType() == ScheduleRequest.RequestType.PARKING)
            scheduler = new ParkingScheduler();
        if (r.getRequestType() == ScheduleRequest.RequestType.WEEKLY)
            scheduler = null;
    }
    
    public List<Schedulable> getAvailable(ScheduleRequest r) {
        setScheduler(r);
        return scheduler.getAvailable(r);
    }
    
    public String schedule(ScheduleRequest r, Schedulable s) {
        setScheduler(r);
        return scheduler.schedule(r, s);
    }
}
