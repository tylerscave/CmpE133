package model;

import java.util.GregorianCalendar;
import model.schedule.Location;
import model.schedule.ScheduleViewer;

/**
 * Uses the scheduled times to simulate the actual times 
 * @author David
 */
public class ScheduleTracker implements Tracker{

    @Override
    public boolean isEnroute(int rideId) {
        return (new GregorianCalendar()).after((new ScheduleViewer()).getRideById(rideId).getStartTime());
    }

    @Override
    public Location currentLocation(int rideId) {
        return (new ScheduleViewer()).getRideById(rideId).getRoute().getLocationAtTime(new GregorianCalendar());
    }

    @Override
    public boolean isDriving(int rideId) {
        ScheduleViewer sv = new ScheduleViewer();
        return (new GregorianCalendar()).after(sv.getDriveById(sv.getRideById(rideId).getDriveId()).getStartTime());
    }

    @Override
    public boolean isWaiting(int rideId) {
        return isEnroute(rideId);
    }

    @Override
    public boolean isLate(int rideId) {
        //schedule is never late
        return false;
    }
    
    @Override
    public boolean isDone(int rideId) {
        return (new GregorianCalendar()).after((new ScheduleViewer()).getRideById(rideId).getEndTime());
    }
    
}
