package model;

import model.schedule.Location;

/**
 * Represents GPS; currently a stub
 * @author David
 */
public class GPSTracker implements Tracker{

    @Override
    public boolean isEnroute(int rideId) {
        //stub
        return false;
    }

    @Override
    public Location currentLocation(int rideId) {
        //
        return null;
    }

    @Override
    public boolean isDriving(int rideId) {
        //stub
        return false;
    }

    @Override
    public boolean isWaiting(int rideId) {
        //stub
        return false;
    }

    @Override
    public boolean isLate(int rideId) {
        //stub
        return false;
    }
    
    @Override
    public boolean isDone(int rideId) {
        //stub
        return false;
    }
    
}
