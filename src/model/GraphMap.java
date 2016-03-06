package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import model.graph.DijkstraAllPairsSP;
import model.graph.DirectedEdge;
import model.graph.EdgeWeightedDigraph;

/**
 *
 * @author David
 */
public class GraphMap implements Map {
    
    private List<Location> locations;
    private int[][] costMatrix;
    private HashMap<String,Integer> hash;
    private DijkstraAllPairsSP dijkstra;

    public GraphMap() {
        locations = new ArrayList<>();
        locations.add(new Location("San Jose State"));
        locations.add(new Location("Location A"));
        locations.add(new Location("Location B"));
        locations.add(new Location("Location C"));
        locations.add(new Location("Location D"));
        
        hash = new HashMap<>();
        for (int i = 0; i < locations.size(); i++) {
            hash.put(locations.get(i).toString(), i);
        }
        
        costMatrix = new int[locations.size()][locations.size()];
        for (int i = 0; i < locations.size(); i++) {
            for (int j = 0; j < locations.size(); j++) {
                costMatrix[i][j] = 10;
            }
        }
        
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(locations.size());
        for (int i = 0; i < locations.size(); i++) {
            for (int j = 0; j < locations.size(); j++) {
                if (i != j)
                    graph.addEdge(new DirectedEdge(i, j, costMatrix[i][j]));
            }
        }
        
        dijkstra = new DijkstraAllPairsSP(graph);
    }
    
    @Override
    public List<Stop> getStops(GregorianCalendar startTime, Location start, Location stop, List<Location> inBetweens) {
        List<Stop> stops = new ArrayList<>();
        stops.add(new Stop(startTime, start));
        Iterable<DirectedEdge> edges = dijkstra.path(getIndexFromLocation(start), getIndexFromLocation(stop));
        List<Location> remainingLocations = new ArrayList<>();
        if (inBetweens == null)
            inBetweens = new ArrayList<>();
        for (Location location : inBetweens) {
            remainingLocations.add(location);
        }
        double timeCost = 0;
        for (DirectedEdge edge : edges) {
            timeCost += edge.weight();
            for (int i = 0; i < remainingLocations.size(); i++) {
                if (getIndexFromLocation(remainingLocations.get(i)) == edge.to()) {
                    GregorianCalendar newTime = new GregorianCalendar(
                            startTime.get(GregorianCalendar.YEAR), 
                            startTime.get(GregorianCalendar.MONTH), 
                            startTime.get(GregorianCalendar.DAY_OF_MONTH), 
                            startTime.get(GregorianCalendar.HOUR_OF_DAY), 
                            startTime.get(GregorianCalendar.MINUTE));
                    newTime.add(GregorianCalendar.MINUTE, (int)timeCost);
                    stops.add(new Stop(newTime, remainingLocations.get(i)));
                    remainingLocations.remove(i);
                    break;
                }
            }
        }
        GregorianCalendar newTime = new GregorianCalendar(
                startTime.get(GregorianCalendar.YEAR), 
                startTime.get(GregorianCalendar.MONTH), 
                startTime.get(GregorianCalendar.DAY_OF_MONTH), 
                startTime.get(GregorianCalendar.HOUR_OF_DAY), 
                startTime.get(GregorianCalendar.MINUTE));
        newTime.add(GregorianCalendar.MINUTE, (int)timeCost);
        stops.add(new Stop(newTime, stop));
        return stops;
    }

    @Override
    public GregorianCalendar getStartTime(GregorianCalendar arrivalTime, Location start, Location stop) {
        double timeCost = -dijkstra.dist(getIndexFromLocation(start), getIndexFromLocation(stop));
        GregorianCalendar newTime = new GregorianCalendar(
                arrivalTime.get(GregorianCalendar.YEAR), 
                arrivalTime.get(GregorianCalendar.MONTH), 
                arrivalTime.get(GregorianCalendar.DAY_OF_MONTH), 
                arrivalTime.get(GregorianCalendar.HOUR_OF_DAY), 
                arrivalTime.get(GregorianCalendar.MINUTE));
        newTime.add(GregorianCalendar.MINUTE, (int)timeCost);
        return newTime;
    }
    
    public int getIndexFromLocation(Location l) {
        return hash.get(l.toString());
    }
    
    public Location getLocationFromIndex(int i) {
        return locations.get(i);
    }
}