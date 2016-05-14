package model;

import model.schedule.Stop;
import model.schedule.Location;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * The map the program uses must implement this interface.
 * @author David Lerner
 */
public interface LocationMap {
    
    /**
     * Returns a list of stops (Location+time at location) of a route given by these parameters.
     * @param startTime time at the first stop
     * @param start the location of the first stop
     * @param stop the location of the last stop
     * @param inBetweens a list of locations (in order) between the first and last stops to be added to the returned list of stops
     * @return a list of stops
     */
    public List<Stop> getStops(GregorianCalendar startTime, Location start, Location stop, List<Location> inBetweens);
    
    /**
     * Returns the starting time of a route given by these parameters.
     * @param arrivalTime the time at the final stop
     * @param start the location of the first stop
     * @param stop the location of the last stop
     * @return the starting time of a route
     */
    public GregorianCalendar getStartTime(GregorianCalendar arrivalTime, Location start, Location stop);
    
    /**
     * Returns a list of all locations on the map.
     * @return a list of all locations on the map
     */
    public List<Location> getLocations();
    
    /**
     * Returns the miles traveled in a route given by a list of locations
     * @param locations the list of locations traveled
     * @return the miles traveled
     */
    public double getMiles(List<Location> locations);
}
