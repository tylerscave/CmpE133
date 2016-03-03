package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author David
 */
public class Route {
    
    private GregorianCalendar startTime;
    private GregorianCalendar endTime;
    private List<Stop> stops;
    private Map map;

    public Route(Map map, GregorianCalendar startTime) {
        this.map = map;
        this.startTime = startTime;
    }
    
    public boolean onRoute(Location location) {
        return true;
    }
    
    public GregorianCalendar getTimeAtStop(Location location) {
        return new GregorianCalendar();
    }
    
    public List<Location> getStops() {
        return new ArrayList<>();
    }
    
    public void addStopInRoute(Location location) {
        //TODO
    }
}
