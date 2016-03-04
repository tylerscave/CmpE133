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

    public Route(Map map, GregorianCalendar startTime, Location start, Location destination) {
        this.map = map;
        this.startTime = startTime;
        stops = map.getStops(startTime, start, destination, null);
        endTime = stops.get(stops.size()-1).getTime();
    }
    
    public boolean onRoute(Location location) {
        GregorianCalendar time = getTimeAtStop(location);
        return (time != null);
    }
    
    public GregorianCalendar getTimeAtStop(Location location) {
        for (Stop stop : stops) {
            if (stop.getLocation().getName().equals(location.getName())) {
                return stop.getTime();
            }
        }
        List<Location> tempLocations = new ArrayList<>();
        tempLocations.add(location);
        List<Stop> tempStops = map.getStops(startTime, stops.get(0).getLocation(), stops.get(stops.size()-1).getLocation(), tempLocations);
        for (Stop stop : tempStops) {
            if (stop.getLocation().getName().equals(location.getName())) {
                return stop.getTime();
            }
        }
        return null;
    }
    
    public List<Location> getStops() {
        ArrayList<Location> locations = new ArrayList<>();
        for (Stop stop : stops) {
            locations.add(stop.getLocation());
        }
        return locations;
    }
    
    public void addStopInRoute(Location location) {
        List<Location> tempLocations = new ArrayList<>();
        for (int i = 1; i < stops.size()-1; i++) {
            tempLocations.add(stops.get(i).getLocation());
        }
        tempLocations.add(location);
        stops = map.getStops(startTime, stops.get(0).getLocation(), stops.get(stops.size()-1).getLocation(), tempLocations);
    }
}
