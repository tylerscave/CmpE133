package model.schedule;

import model.schedule.Scheduler;
import model.schedule.ScheduleRequest;
import model.schedule.Schedulable;
import model.schedule.RideScheduler;
import model.schedule.ParkingScheduler;
import model.schedule.DriveScheduler;
import java.util.List;

/**
 *
 * @author David
 */
public class SchedulingContext {
    private Scheduler scheduler;

    public void setScheduler(ScheduleRequest r) {
        if (r.getRequestType() == ScheduleRequest.RequestType.RIDE)
            scheduler = new RideScheduler();
        if (r.getRequestType() == ScheduleRequest.RequestType.DRIVE)
            scheduler = new DriveScheduler();
        if (r.getRequestType() == ScheduleRequest.RequestType.PARKING)
            scheduler = new ParkingScheduler();
        if (r.getRequestType() == ScheduleRequest.RequestType.WEEKLY)
            scheduler = new WeeklyScheduler();
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
