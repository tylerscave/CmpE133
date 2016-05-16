package model.schedule;

import model.Context;
import model.Tracker;

/**
 * All concrete subclasses of this class represent different states the ride can be in.
 * @author David Lerner
 */
public abstract class RideState {
    
    protected RideStatus rideStatus;
    private Tracker tracker;
    
    /**
     * Constructor for all states.
     * @param rs the rideStatus client object of the state pattern 
     */
    public RideState(RideStatus rs) {
        this.rideStatus = rs;
        this.tracker = Context.getInstance().getTracker();
    }

     /**
     * Returns if the car is/has been driving the passenger.
     * @return if the car is/has been driving the passenger
     */ 
    protected boolean isEnroute() {
        return tracker.isEnroute(rideStatus.getRideId());
    }

    /**
     * Returns if the car is/has been on route to pick up the passenger.
     * @return if the car is/has been on route to pick up the passenger
     */       
    public boolean isDriving() {
        return tracker.isDriving(rideStatus.getRideId());
    }

    /**
     * Returns if the car is/has been waiting for the passenger at the pickup location.
     * @return if the car is/has been waiting for the passenger at the pickup location
     */
    public boolean isWaiting() {
        return tracker.isWaiting(rideStatus.getRideId());
    }

     /**
     * Returns if the car is/has been late.
     * @return if the car is/has been late
     */
    public boolean isLate() {
        return tracker.isLate(rideStatus.getRideId());
    }

     /**
     * Returns if the car is finished driving.
     * @return if the car is finished driving
     */    
    public boolean isDone() {
        return tracker.isDone(rideStatus.getRideId());
    }

     /**
     * Returns the last visited location of the car, null if the drive hasn't begun.
     * @return the last visited location of the car
     */    
    protected Location currentLocation() {
        return tracker.currentLocation(rideStatus.getRideId());
    }
    
    /**
     * Updates the current state to the latest one.
     */
    public abstract void update();
    
    /**
     * Returns a String containing a description of the current state. 
     * @return a description of the current state 
     */
    public abstract String getStatus();
    
    /**
     * Cancel the ride; put it into the canceled state if possible.
     */
    public abstract void cancel();
    
    /**
     * Pay for the ride; put it into the paid state if possible.
     */
    public abstract void pay();
}
