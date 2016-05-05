package model.schedule;

import java.util.GregorianCalendar;
import model.Member;

/**
 *
 * @author David
 */
public class Ride extends Schedulable{
    private Route route;
    private int driveId;
    private RideStatus rideStatus;
    
    public Ride(Member member, Drive drive) {
        super(member);
        this.driveId = drive.getIdNumber();
        this.rideStatus = new RideStatus();
    }

    public int getDriveId() {
        return driveId;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public void remove() {
        //TODO
    }

    @Override
    public GregorianCalendar getStartTime() {
        return route.getStartTime();
    }

    @Override
    public GregorianCalendar getEndTime() {
        return route.getEndTime();
    }
}
