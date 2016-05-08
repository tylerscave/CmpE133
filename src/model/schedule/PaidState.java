package model.schedule;

/**
 *
 * @author David
 */
public class PaidState  extends RideState{
    
   public PaidState(RideStatus rs) {
        super(rs);
    }
   
   @Override
    public void update() {
        //Route already complete; do nothing
    }

    @Override
    public String getStatus() {
        return RideStatus.PAID;
    }

    @Override
    public void cancel() {
        //ride already happened; do nothing
    }

    @Override
    public void pay() {
        //already paid; do nothing
    }
}
