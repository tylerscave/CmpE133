package model.schedule;

/**
 * The client class of the state pattern representing a rides state.
 * @author David Lerner
 */
public class RideStatus {
    private RideState rideState;
    private int rideId;
    
    public static final String UNPAID = "Completed. Unpaid";
    public static final String PAID = "Completed. Paid";
    
    public RideStatus() {
        rideState = new ScheduledState(this);
        rideId = -1;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public int getRideId() {
        return rideId;
    }

    public RideState getRideState() {
        return rideState;
    }

    public void setRideState(RideState rideState) {
        this.rideState = rideState;
    }
    
    public String getStatus() {
        rideState.update();
        return rideState.getStatus();
    }
    
    /**
     * Attempt to cancel the ride.
     */
    public void cancel() {
        rideState.update();
        rideState.cancel();
    }
    
    /**
     * Attempt to pay for the ride.
     */
    public void pay() {
        rideState.update();
        rideState.pay();
    }
    
    public boolean isUnpaid() {
        return rideState.getStatus().equals(UNPAID);
    }
}
