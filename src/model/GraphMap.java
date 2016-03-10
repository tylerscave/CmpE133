package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
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
    private EdgeWeightedDigraph graph;
    private DijkstraAllPairsSP dijkstra;

    public GraphMap() {
        locations = new ArrayList<>();
        locations.add(new Location("San Jose State"));
        locations.add(new Location("Location A"));
        locations.add(new Location("Location B"));
        locations.add(new Location("Location C"));
        locations.add(new Location("Location D"));
        locations.add(new Location("Location E"));
        locations.add(new Location("Location F"));
        locations.add(new Location("Location G"));
        
        hash = new HashMap<>();
        for (int i = 0; i < locations.size(); i++) {
            hash.put(locations.get(i).toString(), i);
        }
        
        costMatrix = new int[locations.size()][locations.size()];
        for (int i = 0; i < locations.size(); i++) {
            for (int j = 0; j < locations.size(); j++) {
                costMatrix[i][j] = 0;
            }
        }
        
        addUndirectedEdge(1, 2, 15);
        addUndirectedEdge(2, 3, 12);
        addUndirectedEdge(2, 4, 10);
        addUndirectedEdge(0, 4, 9);
        addUndirectedEdge(4, 5, 15);
        addUndirectedEdge(0, 5, 7);
        addUndirectedEdge(0, 7, 8);
        addUndirectedEdge(5, 6, 5);
        addUndirectedEdge(6, 7, 15);
        
        graph = new EdgeWeightedDigraph(locations.size());
        for (int i = 0; i < locations.size(); i++) {
            for (int j = 0; j < locations.size(); j++) {
                if (costMatrix[i][j] > 0)
                    graph.addEdge(new DirectedEdge(i, j, costMatrix[i][j]));
            }
        }
        
        dijkstra = new DijkstraAllPairsSP(graph);
    }
    
    @Override
    public List<Stop> getStops(GregorianCalendar startTime, Location start, Location stop, List<Location> inBetweens) {
        List<Stop> stops = new ArrayList<>();
        stops.add(new Stop(startTime, start));
        Stack<DirectedEdge> edges = (Stack<DirectedEdge>)dijkstra.path(getIndexFromLocation(start), getIndexFromLocation(stop));;
        List<Location> remainingLocations = new ArrayList<>();
        if (inBetweens == null)
            inBetweens = new ArrayList<>();
        for (Location location : inBetweens) {
            remainingLocations.add(location);
        }
        double timeCost = 0;
        while (!edges.empty()) {
            DirectedEdge edge = edges.pop();
            timeCost += edge.weight();
            for (int i = 0; i < remainingLocations.size(); i++) {
                if (getIndexFromLocation(remainingLocations.get(i)) == edge.to()) {
                    GregorianCalendar newTime = new GregorianCalendar();
                    newTime.setTime(startTime.getTime());
                    newTime.add(GregorianCalendar.MINUTE, (int)timeCost);
                    stops.add(new Stop(newTime, remainingLocations.get(i)));
                    remainingLocations.remove(i);
                    break;
                }
            }
        }
        GregorianCalendar newTime = new GregorianCalendar();
        newTime.setTime(startTime.getTime());
        newTime.add(GregorianCalendar.MINUTE, (int)timeCost);
        stops.add(new Stop(newTime, stop));
        return stops;
    }

    @Override
    public GregorianCalendar getStartTime(GregorianCalendar arrivalTime, Location start, Location stop) {
        double timeCost = -dijkstra.dist(getIndexFromLocation(start), getIndexFromLocation(stop));
        GregorianCalendar newTime = new GregorianCalendar();
        newTime.setTime(arrivalTime.getTime());
        newTime.add(GregorianCalendar.MINUTE, (int)timeCost);
        return newTime;
    }
    
    public int getIndexFromLocation(Location l) {
        return hash.get(l.toString());
    }
    
    public Location getLocationFromIndex(int i) {
        return locations.get(i);
    }
    
    private void addUndirectedEdge(int from, int to, int cost) {
        costMatrix[from][to] = cost;
        costMatrix[to][from] = cost;
    }

    @Override
    public List<Location> getLocations() {
        return locations;
    }

    @Override
    public String toString() {
        return graph.toString();
    }
    
}
