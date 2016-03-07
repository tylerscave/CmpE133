package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class Data {
    
    private List<Member> members;
    private List<Drive> drives;
    private List<Ride> rides;
    private List<RideRequest> rideRequests;
    
    private static Data instance;
    
    private Data() {
        // Exists only to defeat instantiation.
        generateLists();
    }
    
    public static Data getInstance() {
        if(instance == null) {
            instance = new Data();
        }
        return instance;	
    }

    public List<Member> getMembers() {
        return members;
    }
    
    public void generateLists() {
        drives = new ArrayList<>();
        rides = new ArrayList<>();
        rideRequests = new ArrayList<>();
        for (Member member : members) {
            drives.addAll(member.getDrives());
            rides.addAll(member.getRides());
            rideRequests.addAll(member.getRideRequests());
        }
    }

    public List<RideRequest> getRideRequests() {
        return rideRequests;
    }

    public List<Ride> getRides() {
        return rides;
    }

    public List<Drive> getDrives() {
        return drives;
    }
    
}
