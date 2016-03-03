package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class Drive {
    
    private int numSeats;
    private Member member;
    private List<Ride> rides;
    private Route route;
    
    public Drive(int numSeats, Member member) {
        this.numSeats = numSeats;
        this.member = member;
        this.rides = new ArrayList<>();
    }

    public Member getMember() {
        return member;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public Ride getRide(int i) {
        return rides.get(i);
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
    
    public void addRide(Ride ride) {
        this.rides.add(ride);
    }
    
    public void removeRide(Ride ride) {
        this.rides.remove(ride);
    }
    
    public int numberOfRides() {
        return this.rides.size();
    }
    
    public void remove() {
        //TODO
    }
}
