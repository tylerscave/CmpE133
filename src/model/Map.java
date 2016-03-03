package model;

import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author David
 */
public interface Map {
    
    public List<Stop> getStops(Location start, Location stop, List<Location> inBetweens);
    
    public GregorianCalendar getTimeofRoute(Location start, Location stop, GregorianCalendar startTime);
}
