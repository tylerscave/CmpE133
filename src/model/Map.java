package model;

import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author David
 */
public interface Map {
    
    public List<Stop> getStops(GregorianCalendar startTime, Location start, Location stop, List<Location> inBetweens);
    
    public GregorianCalendar getStartTime(GregorianCalendar arrivalTime, Location start, Location stop);
    
}
