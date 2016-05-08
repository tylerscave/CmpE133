package model.schedule;

/**
 *
 * @author David
 */
public class ScheduledState extends RideState{

    public ScheduledState(RideStatus rs) {
        super(rs);
    }

    @Override
    public void update() {
        if (isDriving()) {
            rideStatus.setRideState(new DrivingState(rideStatus));
            //continue to update until it reaches the latest state
            rideStatus.getRideState().update();
        }
    }

    @Override
    public String getStatus() {
        return "Not yet started";
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
