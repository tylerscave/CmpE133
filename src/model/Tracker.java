package model;

import model.schedule.Location;

/**
 * The class used to track current ride status must implement this interface.
 * @author David
 */
public interface Tracker {
    
    /**
     * Returns if the car is/has been on route to pick up the passenger.
     * @param rideId the unique id number of the ride
     * @return if the car is/has been on route to pick up the passenger
     */
    public boolean isDriving(int rideId);
    
    /**
     * Returns if the car is/has been waiting for the passenger at the pickup location.
     * @param rideId the unique id number of the ride
     * @return if the car is/has been waiting for the passenger at the pickup location
     */
    public boolean isWaiting(int rideId);
    
    /**
     * Returns if the car is/has been driving the passenger.
     * @param rideId the unique id number of the ride
     * @return if the car is/has been driving the passenger
     */
    public boolean isEnroute(int rideId);
    
     /**
     * Returns if the car is/has been late.
     * @param rideId the unique id number of the ride
     * @return if the car is/has been late
     */
    public boolean isLate(int rideId);
    
     /**
     * Returns if the car is finished driving.
     * @param rideId the unique id number of the ride
     * @return if the car is finished driving
     */
    public boolean isDone(int rideId);
    
     /**
     * Returns the last visited location of the car, null if the drive hasn't begun.
     * @param rideId the unique id number of the ride
     * @return the last visited location of the car
     */
    public Location currentLocation(int rideId);
}
