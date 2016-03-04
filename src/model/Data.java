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

    public void setMembers(List<Member> members) {
        this.members = members;
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
    
}
