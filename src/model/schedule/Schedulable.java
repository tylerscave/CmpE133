package model.schedule;

import java.util.GregorianCalendar;
import model.member.Member;

/**
 * Abstract class representing things that can be scheduled.
 * @author David Lerner
 */
public abstract class Schedulable {
    private int idNumber;
    private int memberId;
    private String memberName; 

    /**
     * Constructor
     * @param member the member who has this in their schedule
     */
    public Schedulable(Member member) {
        this.memberId = member.getIdNumber();
        this.memberName = member.toString();
    }

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public String getMemberName() {
        return memberName;
    }

    public int getMemberId() {
        return memberId;
    }
    
    public abstract GregorianCalendar getStartTime();
    
    public abstract GregorianCalendar getEndTime();
    
    public boolean conflicts(Schedulable s) {
        return ((!s.getStartTime().before(getStartTime()) && s.getStartTime().before(getEndTime())) 
                ||(!s.getEndTime().after(getEndTime()) && s.getEndTime().after(getStartTime())));
    }
    
    /**
     * Remove this from the schedule
     */
    public abstract void remove();
}
