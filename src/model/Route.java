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

    public Route(Map map, GregorianCalendar startTime, GregorianCalendar endTime, Location start, Location end) {
        this.map = map;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stops = new ArrayList<>();
        this.stops.add(new Stop(startTime, start));
        this.stops.add(new Stop(endTime, end));
    }
    
    public Route(Map map, GregorianCalendar startTime, Location start, Location end) {
        this.map = map;
        this.startTime = startTime;
        stops = map.getStops(startTime, start, end, null);
        endTime = stops.get(stops.size()-1).getTime();
    }
    
    public Route createSubroute(Location start, Location end) {
        List<Location> locations = getStops();
        GregorianCalendar leave = TimeOfStop(this.stops, start);
        GregorianCalendar arrive = TimeOfStop(this.stops, end);
        if (leave == null || arrive == null)
        {
            List<Location> tempLocations = new ArrayList<>();
            tempLocations.add(start);
            tempLocations.add(end);
            List<Stop> TempStops = map.getStops(startTime, stops.get(0).getLocation(), stops.get(stops.size()-1).getLocation(), tempLocations);
            leave = TimeOfStop(TempStops, start);
            arrive = TimeOfStop(TempStops, end);
        }
        if (leave == null || arrive == null)
            return null;
        return new Route(map, leave, arrive, start, end);
    }
    
    private GregorianCalendar TimeOfStop(List<Stop> stops, Location location) {
        for (Stop stop : stops) {
            if (stop.getLocation().equals(location)) {
                return stop.getTime();
            }
        }
        return null;
    }
    
    private boolean inStops(List<Stop> stops, Location start, Location end) {
        for (int i = 0; i < stops.size(); i++) {
            if (stops.get(i).getLocation().equals(start)) {
                for (int j = i; j < stops.size(); j++) {
                    if (stops.get(j).getLocation().equals(end))
                        return true;
                }
                break;
            }
        }
        return false;
    }
    
    public boolean onRoute(Location start, Location end) {
        if (inStops(this.stops, start, end))
            return true;
        List<Location> tempLocations = new ArrayList<>();
        tempLocations.add(start);
        tempLocations.add(end);
        List<Stop> tempStops = map.getStops(startTime, stops.get(0).getLocation(), stops.get(stops.size()-1).getLocation(), tempLocations);
        return inStops(tempStops, start, end);
    }
    
    public GregorianCalendar getTimeAtStop(Location location) {
        GregorianCalendar time = TimeOfStop(this.stops, location);
        if (time != null)
            return time;
        List<Location> tempLocations = new ArrayList<>();
        tempLocations.add(location);
        return TimeOfStop(map.getStops(startTime, stops.get(0).getLocation(), stops.get(stops.size()-1).getLocation(), tempLocations), location);
    }
    
    public List<Location> getStops() {
        ArrayList<Location> locations = new ArrayList<>();
        for (Stop stop : stops) {
            locations.add(stop.getLocation());
        }
        return locations;
    }
    
    public void addStopInRoute(Location location) {
        for (Stop stop : stops) {
            if (stop.getLocation().equals(location))
                return;
        }
        List<Location> tempLocations = new ArrayList<>();
        for (int i = 1; i < stops.size()-1; i++) {
            tempLocations.add(stops.get(i).getLocation());
        }
        tempLocations.add(location);
        stops = map.getStops(startTime, stops.get(0).getLocation(), stops.get(stops.size()-1).getLocation(), tempLocations);
    }

    public GregorianCalendar getStartTime() {
        return startTime;
    }

    public GregorianCalendar getEndTime() {
        return endTime;
    }
    
    public boolean conflicts(Route route) {
        return ((route.getStartTime().after(this.startTime) && route.getStartTime().before(this.endTime)) 
                ||(route.getEndTime().before(this.endTime) && route.getEndTime().after(this.startTime)));
    }
}
