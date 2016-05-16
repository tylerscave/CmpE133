package model.schedule;

/**
 * This state represents the driver waiting for the passenger at the pickup location.
 * @author David Lerner
 */
public class WaitingState extends RideState{

    public WaitingState(RideStatus rs) {
        super(rs);
    }
    
    @Override
    public void update() {
        if (isEnroute()) {
            rideStatus.setRideState(new EnrouteState(rideStatus));
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
        return "Driver is waiting at pickup location: "+location;
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
