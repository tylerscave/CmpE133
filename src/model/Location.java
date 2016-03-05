package model;
/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The model for the member schedule
 * Solves CmpE133 Assignment 2
 * @author David Lerner, Tyler Jones,
*/
public class Location {
    private String name;
    
    public Location (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    // toString for ComboBoxes
    @Override
    public String toString() {
    	return this.name;
    }
    
}
