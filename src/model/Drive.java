package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class Drive implements Schedulable{
    
    private int numSeats;
    private int memberId;
    private List<Integer> rideIds;
    private Route route;
    private int idNumber;
    private String memberName;
    
    public Drive(int numSeats, Member member) {
        this.numSeats = numSeats;
        this.memberId = member.getIdNumber();
        this.rideIds = new ArrayList<>();
        this.memberName = member.getFirstName()+" "+member.getLastName();
    }

    public int getMemberId() {
        return memberId;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public int getRideId(int i) {
        return rideIds.get(i);
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
    
    public void addRide(Ride ride) {
        this.rideIds.add(ride.getIdNumber());
        numSeats --;
    }
    
    public void removeRideById(int rideId) {
        int index = -1;
        for (int i = 0; i < rideIds.size(); i++) {
            if (rideIds.get(i) == rideId) {
                index = i;
                break;
            }
        }
        if (index > -1)
            this.rideIds.remove(index);
    }
    
    public int numberOfRides() {
        return this.rideIds.size();
    }
    
    public void remove() {
        //TODO
    }

    @Override
    public int getIdNumber() {
        return idNumber;
    }

    @Override
    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    @Override
    public String getMemberName() {
        return memberName;
    }
    
}
