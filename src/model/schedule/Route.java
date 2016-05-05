package model.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import model.Context;
import model.LocationMap;

/**
 *
 * @author David
 */
public class Route {
    
    private GregorianCalendar startTime;
    private GregorianCalendar endTime;
    private List<Stop> stops;
    private LocationMap map;
    private List<Stop> realStops;

    public Route(GregorianCalendar startTime, GregorianCalendar endTime, Location start, Location end) {
        this.map = Context.getInstance().getMap();
        this.startTime = startTime;
        this.endTime = endTime;
        this.stops = new ArrayList<>();
        this.stops.add(new Stop(startTime, start));
        this.stops.add(new Stop(endTime, end));
        this.realStops = new ArrayList<>();
    }
    
    public Route(GregorianCalendar startTime, Location start, Location end) {
        this.map = Context.getInstance().getMap();
        this.startTime = startTime;
        stops = map.getStops(startTime, start, end, null);
        endTime = stops.get(stops.size()-1).getTime();
        this.realStops = new ArrayList<>();
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
        return new Route(leave, arrive, start, end);
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
        return ((!route.getStartTime().before(this.startTime) && route.getStartTime().before(this.endTime)) 
                ||(!route.getEndTime().after(this.endTime) && route.getEndTime().after(this.startTime)));
    }
    
    /**
     * Returns the last location that was updated on the route before atTime.
     * If there is no updated route, returns the last location on the scheduled route before atTime.
     * If the atTime is before the route starts, returns null
     * @param atTime the time of interest
     * @return the last location on the route before atTime
     */
    public Location getLocationAtTime(GregorianCalendar atTime) {
        Location location = null;
        List<Stop> searchStops;
        if (realStops.size() > 0)
            searchStops = realStops;
        else;
            searchStops = stops;
        for (Stop stop : searchStops) {
                if (stop.getTime().after(atTime)) 
                    break;
                location = stop.getLocation();
            }
        return location;
    }
    
    /**
     * Adds a time and location to the real route traveled.
     * @param time time at location
     * @param location location at time
     */
    public void setRealRoute(GregorianCalendar time, Location location) {
        realStops.add(new Stop(time, location));
        Collections.sort(realStops);
    }
    
    /**
     * Returns the total time traveled in minutes.
     * By default, this is the difference between the first and last time setRealRoute was called.
     * If it wasn't called at least twice, returns the time of the scheduled route.
     * Returns 0 if no route.
     * @return the total time traveled in minutes
     */
    public int getTravelTime() {
        if (realStops.size() > 1)
            return (int) ((realStops.get(realStops.size()-1).getTime().getTime().getTime()-realStops.get(0).getTime().getTime().getTime())/(1000*60));
        else if (stops.size() > 1)
            return (int) ((endTime.getTime().getTime()-startTime.getTime().getTime())/(1000*60));
        return 0;
    }
    
    public double getRouteMiles() {
        List<Location> l = new ArrayList<>();
        for (Stop s : realStops) {
            l.add(s.getLocation());
        }
        return map.getMiles(l);
    }
}
