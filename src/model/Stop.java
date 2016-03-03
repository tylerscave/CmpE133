package model;

import java.util.GregorianCalendar;

/**
 *
 * @author David
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

    @Override
    public int compareTo(Stop t) {
        return this.getTime().compareTo(t.getTime());
    }
    
}
