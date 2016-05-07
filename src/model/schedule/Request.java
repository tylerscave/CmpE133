package model.schedule;

import java.util.GregorianCalendar;
import model.member.Member;

/**
 *
 * @author David
 */
public class Request {
    
    public enum RequestType {
        RIDE,
        DRIVE,
        WEEKLY,
        PARKING;
    }
    
    public enum TimeType {
        Near,
        Before,
        After,
        Anytime;
    }
    
    private RequestType requestType;
    private Member member;
    private GregorianCalendar startTime;
    private GregorianCalendar endTime;
    private Location startLocation;
    private Location endLocation;
    private TimeType startType;
    private TimeType endType;
    private String name;
    
    /**
     * Constructor for requesting a ride.
     * @param member
     * @param startTime
     * @param endTime
     * @param startLocation
     * @param endLocation
     * @param startType
     * @param endType
     */
    public Request(Member member, GregorianCalendar startTime, GregorianCalendar endTime, 
            Location startLocation, Location endLocation, TimeType startType, TimeType endType) {
        this.member = member;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startType = startType;
        this.endType = endType;
        
        requestType = RequestType.RIDE;
        name = null;
    }
    
    /**
     * Constructor for requesting a drive.
     * @param member
     * @param time
     * @param startLocation
     * @param endLocation
     * @param timeType
     * @param byStartTime
     */
    public Request(Member member, GregorianCalendar time, Location startLocation,  
            Location endLocation, TimeType timeType, boolean byStartTime) {
        this.member = member;
        this.startTime = time;
        this.endTime = time;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        
        if (byStartTime) {
            this.startType = timeType;
            this.endType = TimeType.Anytime;
        } else {
            this.endType = timeType;
            this.startType = TimeType.Anytime;
        }
        requestType = RequestType.DRIVE;
        name = null;
    }
    
    /**
     * Constructor for requesting a Park.
     * @param member
     * @param startTime
     * @param endTime
     * @param location
     */
    public Request(Member member, GregorianCalendar startTime, GregorianCalendar endTime, 
            Location location) {
        this.member = member;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startLocation = location;
        this.endLocation = location;
        this.startType = TimeType.Near;
        this.endType = TimeType.Near;
        
        requestType = RequestType.PARKING;
        name = null;
    }

    /**
     * Constructor for requesting a weekly schedule
     * @param member
     * @param startFrom the time from which new individual rides will be scheduled, use current time for a new schedule
     * @param location
     */
    public Request(Member member, GregorianCalendar startFrom, Location location) {
        this.member = member;
        this.startTime = startFrom;
        this.endTime = startTime;
        this.startLocation = location;
        this.endLocation = location;
        this.startType = TimeType.Anytime;
        this.endType = TimeType.Anytime;
        
        requestType = RequestType.WEEKLY;
        name = null;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public GregorianCalendar getEndTime() {
        return endTime;
    }

    public TimeType getEndType() {
        return endType;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public GregorianCalendar getStartTime() {
        return startTime;
    }

    public TimeType getStartType() {
        return startType;
    }

    public Member getMember() {
        return member;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
