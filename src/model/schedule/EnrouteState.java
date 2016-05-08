package model.schedule;

/**
 *
 * @author David
 */
public class EnrouteState extends RideState{

    public EnrouteState(RideStatus rs) {
        super(rs);
    }
    
    @Override
    public void update() {
        if (isDone()) {
            rideStatus.setRideState(new UnpaidState(rideStatus));
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
        return "Enroute. Current Location: "+location;
    }

    @Override
    public void cancel() {
        //too late to cancel; do nothing;
    }

    @Override
    public void pay() {
        //not correct state to pay; do nothing
    }
}
