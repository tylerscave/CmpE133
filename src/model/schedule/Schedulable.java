package model.schedule;

import java.util.GregorianCalendar;
import model.Member;

/**
 *
 * @author David
 */
public abstract class Schedulable {
    private int idNumber;
    private int memberId;
    private String memberName; 

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
    
    public abstract void remove();
}
