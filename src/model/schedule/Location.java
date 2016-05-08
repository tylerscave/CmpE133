package model.schedule;

import model.schedule.ParkingSpot;
import java.util.ArrayList;
import java.util.List;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The model for the member schedule
 * Solves CmpE133 Assignment 2
 * @author David Lerner, Tyler Jones,
*/
public class Location {
    private String name;
    private List<ParkingSpot> parkingSpots;
    
    public Location (String name) {
        this.name = name;
        this.parkingSpots = new ArrayList<>();
    }
    
    public Location (String name, List<ParkingSpot> parkingSpots) {
        this.name = name;
        this.parkingSpots = parkingSpots;
    }

    /*public String getName() {
        return name;
    }*/
    // toString for ComboBoxes
    @Override
    public String toString() {
    	return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Location))
            return false;
        Location location = (Location)o;
        return location.toString().equals(this.name);
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }
    
}
