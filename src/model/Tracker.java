package model;

import model.schedule.Location;

/**
 *
 * @author David
 */
public interface Tracker {
    
    public boolean isDriving(int rideId);
    
    public boolean isWaiting(int rideId);
    
    public boolean isEnroute(int rideId);
    
    public boolean isLate(int rideId);
    
    public boolean isDone(int rideId);
    
    public Location currentLocation(int rideId);
}
