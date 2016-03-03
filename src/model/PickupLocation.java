package model;
/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the member information form 
 * Solves CmpE133 Assignment 2
 * @author Tyler Jones,
*/
public class PickupLocation {

	private String location;
	public PickupLocation(String locationName) {
		this.location = locationName;
	}
	
	public String toString() {
		return location;
	}
	
}
