package model.schedule;

/**
 * This state represents the driver being on route for pickup.
 * @author David Lerner
 */
public class DrivingState extends RideState{

    public DrivingState(RideStatus rs) {
        super(rs);
    }

    @Override
    public void update() {
        if (isWaiting()) {
            rideStatus.setRideState(new WaitingState(rideStatus));
            //continue to update until it reaches the latest state
            rideStatus.getRideState().update();
        }
        else if (isLate()) {
            rideStatus.setRideState(new LateState(rideStatus));
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
        return "Driver enroute for pickup. Current Location: "+location;
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
