package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author David
 */
public class NewDataHandler implements DataHandler {
    
    private List<Member> members;
    private List<Drive> drives;
    private List<Ride> rides;
    private List<RideRequest> rideRequests;
    private HashMap<Integer, Schedulable> schedulables;
    private int schedulableId;
    
    public NewDataHandler() {
        members = new ArrayList<>();
        schedulables = new HashMap<>();
        schedulableId = 0;
        generateLists();
    }
    
    public void generateLists() {
        drives = new ArrayList<>();
        rides = new ArrayList<>();
        rideRequests = new ArrayList<>();
        for (Member member : members) {
            drives.addAll(member.getDrives());
            rides.addAll(member.getRides());
            rideRequests.addAll(member.getRideRequests());
            for (Drive d : drives) 
                schedulables.put(d.getIdNumber(), d);
            for (Ride r : rides) 
                schedulables.put(r.getIdNumber(), r);
        }
    }

    @Override
    public List<RideRequest> getRideRequests() {
        return rideRequests;
    }

    @Override
    public List<Ride> getRides() {
        return rides;
    }

    @Override
    public List<Drive> getDrives() {
        return drives;
    }
    
    @Override
    public void update(Observable o, Object o1) {
        if (o instanceof Member) {
            Member member = (Member)o;
            setMember(member.getIdNumber(), member);
            updateSchedulables(member);
        }
        if (o1 instanceof List<?>) {
            List<?> list = (List<?>) o1;
            for (Object obj : list) {
                if (obj instanceof Member) {
                    Member member = (Member) obj;
                    setMember(member.getIdNumber(), member);
                    updateSchedulables(member);
                }
            }
        }
        
    }

    private void updateSchedulables(Member member) {
        for (Drive d : member.getDrives())
            schedulables.put(d.getIdNumber(), d);
        for (Ride r : member.getRides())
            schedulables.put(r.getIdNumber(), r);
    }
    
    @Override
    public int addMember(Member member) {
        int idNumber = members.size();
        member.setIdNumber(idNumber);
        members.add(member);
        return idNumber;
    }

    @Override
    public Member getMember(int memberId) {
        return members.get(memberId);
    }

    @Override
    public void setMember(int memberId, Member member) {
        members.set(memberId, member);
    }

    @Override
    public int getNewSchedulableId() {
         return schedulableId++;
    }
    
    @Override
    public Schedulable getSchedulable(int scheduleId) {
        return schedulables.get(scheduleId);
    }

    @Override
    public List<Member> getMembers() {
        return members;
    }

    @Override
    public void notify(int memberId, Notification notification) {
        Member member = members.get(memberId);
        member.addNewNotification(notification);
        members.set(memberId, member);
    }
}
