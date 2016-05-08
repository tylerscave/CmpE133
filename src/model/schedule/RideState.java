package model.schedule;

import model.Context;
import model.Tracker;

/**
 *
 * @author David
 */
public abstract class RideState {
    protected RideStatus rideStatus;
    private Tracker tracker;
    
    public RideState(RideStatus rs) {
        this.rideStatus = rs;
        this.tracker = Context.getInstance().getTracker();
    }
    
    protected boolean isEnroute() {
        return tracker.isEnroute(rideStatus.getRideId());
    }
    
    public boolean isDriving() {
        return tracker.isDriving(rideStatus.getRideId());
    }

    public boolean isWaiting() {
        return tracker.isWaiting(rideStatus.getRideId());
    }

    public boolean isLate() {
        return tracker.isLate(rideStatus.getRideId());
    }
    
    public boolean isDone() {
        return tracker.isDone(rideStatus.getRideId());
    }
    
    protected Location currentLocation() {
        return tracker.currentLocation(rideStatus.getRideId());
    }
    
    public abstract void update();
    
    public abstract String getStatus();
    
    public abstract void cancel();
    
    public abstract void pay();
}
