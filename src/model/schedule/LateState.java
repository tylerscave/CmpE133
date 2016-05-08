package model.schedule;

/**
 *
 * @author David
 */
public class LateState extends RideState{

    public LateState(RideStatus rs) {
        super(rs);
    }
    
    @Override
    public void update() {
        if (isWaiting()) {
            rideStatus.setRideState(new WaitingState(rideStatus));
            //continue to update until it reaches the latest state
            rideStatus.getRideState().update();
        }
    }

    @Override
    public String getStatus() {
        String location = "N/A";
        Location currentLocation = currentLocation();
        if (currentLocation != null)
            location = currentLocation.toString();
        return "Driver is running late. Current Location: "+location;
    }

    @Override
    public void cancel() {
        rideStatus.setRideState(new CanceledState(rideStatus));
    }

    @Override
    public void pay() {
        //not correct state to pay; do nothing
    }
        
}
