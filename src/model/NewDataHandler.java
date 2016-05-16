package model;

import model.member.Member;
import model.schedule.Schedulable;
import model.schedule.Ride;
import model.schedule.ParkingTime;
import model.schedule.Drive;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import model.schedule.Request;

/**
 * Simulation of database. All Information disappears when application is closed.
 * @author David Lerner
 */
public class NewDataHandler implements DataHandler {
    
    private List<Member> members;
    private HashMap<Integer, Schedulable> schedulables;
    private int schedulableId;
    
    public NewDataHandler() {
        members = new ArrayList<>();
        schedulables = new HashMap<>();
        schedulableId = 0;
    }
    
    @Override
    public List<Request> getRequests() {
        List<Request> requests = new ArrayList<>();
        for (Member member : members) {
            requests.addAll(member.getRequests());
        }
        return requests;
    }

    @Override
    public List<Ride> getRides() {
        List<Ride> rides = new ArrayList<>();
        for (Member member : members) {
            rides.addAll(member.getRides());
        }
        return rides;
    }

    @Override
    public List<Drive> getDrives() {
        List<Drive> drives = new ArrayList<>();
        for (Member member : members) {
            drives.addAll(member.getDrives());
        }
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
        for (ParkingTime p : member.getParkingTimes())
            schedulables.put(p.getIdNumber(), p);
    }
    
    @Override
    public int addMember(Member member) {
        for (Member m : members) {
            if (m.getLoginInfo().getEmail().equals(member.getLoginInfo().getEmail()))
                return -1;
        }
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

    @Override
    public List<ParkingTime> getParkingTimes() {
        List<ParkingTime> parkingTimes = new ArrayList<>();
        for (Member member : members) {
            parkingTimes.addAll(member.getParkingTimes());
        }
        return parkingTimes;
    }

}
