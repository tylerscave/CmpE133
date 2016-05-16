package model.schedule;

import java.util.GregorianCalendar;

/**
 * A container class to match locations with times.
 * @author David Lerner
 */
public class Stop implements Comparable<Stop> {
    
    private GregorianCalendar time;
    private Location location;
    
    public Stop(GregorianCalendar time, Location location) {
        this.time = time;
        this.location = location;
    }

    public GregorianCalendar getTime() {
        return time;
    }

    public Location getLocation() {
        return location;
    }

    //compares stops by time
    @Override
    public int compareTo(Stop t) {
        return this.getTime().compareTo(t.getTime());
    }
    
}
