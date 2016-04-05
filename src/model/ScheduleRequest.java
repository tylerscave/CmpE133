package model;

import java.util.GregorianCalendar;

/**
 *
 * @author David
 */
public class ScheduleRequest {
    
    public enum RequestType {
        RIDE,
        DRIVE,
        WEEKLY,
        PARKING;
    }
    
    public enum TimeType {
        NEAR,
        BEFORE,
        AFTER,
        ANYTIME;
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
    
    public ScheduleRequest(Member member, GregorianCalendar startTime, GregorianCalendar endTime, 
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
    
    public ScheduleRequest(Member member, GregorianCalendar time, Location startLocation,  
            Location endLocation, TimeType timeType, boolean byStartTime) {
        if (byStartTime) {
            this.member = member;
            this.startTime = time;
            this.endTime = time;
            this.startLocation = startLocation;
            this.endLocation = endLocation;
            this.startType = timeType;
            this.endType = TimeType.ANYTIME;
        } else {
            this.member = member;
            this.startTime = time;
            this.endTime = time;
            this.startLocation = startLocation;
            this.endLocation = endLocation;
            this.endType = timeType;
            this.startType = TimeType.ANYTIME;
        }
        requestType = RequestType.DRIVE;
        name = null;
    }
    
    public ScheduleRequest(Member member, GregorianCalendar startTime, GregorianCalendar endTime, 
            Location location, TimeType startType, TimeType endType) {
        this.member = member;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startLocation = location;
        this.endLocation = location;
        this.startType = startType;
        this.endType = endType;
        
        requestType = RequestType.PARKING;
        name = null;
    }

    public RequestType getRequestType() {
        return requestType;
    }
    
    
}
