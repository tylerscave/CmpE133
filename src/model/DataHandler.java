package model;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author David
 */
public interface DataHandler extends Observer {
    
    /**
     * Returns a list of all the members
     * @return a list of all the members
     */
    public List<Member> getMembers();
    
    /**
     * Adds a new member to the database and returns a unique id number
     * 
     * @param member the member to add
     * @return the unique member id number
     */
    public int addMember(Member member);
    
    /**
     *
     * Returns the member given by a unique id number
     * 
     * @param memberId a member's unique id number
     * @return the member
     */
    public Member getMember(int memberId);
    
    /**
     * Returns a new unique schedulable id
     * @return
     */
    public int getNewSchedulableId();
            
    /**
     *
     * Returns the schedulable (ride, drive) given by a unique id number
     * 
     * @param scheduleId a member's unique id number
     * @return the schedulable
     */
    public Schedulable getSchedulable(int scheduleId);
    
    /**
     *
     * Replaces the member info of unique id i with the modified member
     * @param memberId a member's unique id number
     * @param member the modified member
     */
    public void setMember(int memberId, Member member);
    
    /**
     *
     * @return list of pending ride requests
     */
    public List<RideRequest> getRideRequests();

    /**
     *
     * @return list of pending rides
     */
    public List<Ride> getRides();

    /**
     *
     * @return list of pending drives
     */
    public List<Drive> getDrives();

    /**
     * Send a notification message to a member
     * @param memberId unique id of the member to send to
     * @param notification the notification to send
     */
    public void notify(int memberId, Notification notification);
}
