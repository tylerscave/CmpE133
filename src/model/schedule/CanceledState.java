package model.schedule;

/**
 * This state represents the ride being canceled. A terminal state.
 * @author David Lerner
 */
public class CanceledState extends RideState{

    public CanceledState(RideStatus rs) {
        super(rs);
    }
    
    @Override
    public void update() {
        //Route already canceled; do nothing
    }

    @Override
    public String getStatus() {
        return "Canceled";
    }

    @Override
    public void cancel() {
        //ride already canceleded; do nothing
    }

    @Override
    public void pay() {
        //ride already canceleded; do nothing
    }
    
}
