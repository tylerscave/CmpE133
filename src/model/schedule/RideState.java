package model.schedule;

import model.Context;
import model.Tracker;

/**
 *
 * @author David
 */
public class RideState {
    protected RideStatus rideStatus;
    protected Tracker tracker;
    
    public RideState(RideStatus rs) {
        this.rideStatus = rs;
        this.tracker = Context.getInstance().getTracker();
    }
}
