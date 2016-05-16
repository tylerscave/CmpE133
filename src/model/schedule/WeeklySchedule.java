package model.schedule;
/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The model for the member weekly schedule
 * Solves CmpE133 Assignment 2
 * @author Tyler Jones, David Lerner
*/

import java.util.GregorianCalendar;
import java.util.Locale;

public class WeeklySchedule{
       
    private Location pickupLocation;
    private GregorianCalendar startTime, endTime, lastUpdate;
    
    private boolean[] drive = new boolean[7];
    private GregorianCalendar[] arrive = new GregorianCalendar[7];
    private GregorianCalendar[] depart = new GregorianCalendar[7];
    
    private static final int OFFSET = GregorianCalendar.SUNDAY;

    public WeeklySchedule() {
        for (boolean d : drive)
            d = false;
        for (GregorianCalendar g : arrive)
            g = null;
        for (GregorianCalendar g : depart)
            g = null;
        pickupLocation = null;
        startTime = null;
        endTime = null;
        lastUpdate = null;
    }

    /**
     * Returns true if the weekly schedule has been created. 
     * @return whether the weekly schedule has been created
     */
    public boolean isSet() {
        return (pickupLocation != null && startTime != null && endTime != null);
    }
    
    /**
     * Returns true if there is a schedule for that dayOfWeek.
     * @param dayOfWeek use the weekday constants from the Calendar class
     * @return whether there is a schedule for that dayOfWeek
     */
    public boolean isValid(int dayOfWeek) {
        return (arrive[dayOfWeek-OFFSET] != null && depart[dayOfWeek-OFFSET] != null);
    }
    
     /**
     * Returns true if member is available to drive that dayOfWeek.
     * @param dayOfWeek use the weekday constants from the Calendar class
     * @return whether member is available to drive that dayOfWeek
     */
    public boolean isDrive(int dayOfWeek) {
        return drive[dayOfWeek-OFFSET];
    }

    /**
     * Sets if member is available to drive that dayOfWeek.
     * @param dayOfWeek use the weekday constants from the Calendar class
     * @param isDrive whether member can drive
     */
    public void setDrive(int dayOfWeek, boolean isDrive) {
        drive[dayOfWeek-OFFSET] = isDrive;
    }

    /**
     * Returns the arrival time on dayOfWeek.
     * @param dayOfWeek use the weekday constants from the Calendar class
     * @return the arrival time on dayOfWeek
     */
    public GregorianCalendar getArrive(int dayOfWeek) {
        return arrive[dayOfWeek-OFFSET];
    }

    /**
     * Sets the arrival time on dayOfWeek.
     * @param dayOfWeek use the weekday constants from the Calendar class
     * @param arrive arrival time
     */
    public void setArrive(int dayOfWeek, GregorianCalendar arrive) {
        this.arrive[dayOfWeek-OFFSET] = arrive;
    }
    
   /**
     * Returns the departure time on dayOfWeek.
     * @param dayOfWeek use the weekday constants from the Calendar class
     * @return the departure time on dayOfWeek
     */
    public GregorianCalendar getDepart(int dayOfWeek) {
        return depart[dayOfWeek-OFFSET];
    }

    /**
     * Sets the departure time on dayOfWeek.
     * @param dayOfWeek use the weekday constants from the Calendar class
     * @param depart departure time
     */
    public void setDepart(int dayOfWeek, GregorianCalendar depart) {
        this.depart[dayOfWeek-OFFSET] = depart;
    }
    
    /**
     * Returns the start date of the weekly schedule.
     * @return the start date of the weekly schedule
     */
    public GregorianCalendar getStartTime() {
        return startTime;
    }

    /**
     * Returns the end date of the weekly schedule.
     * @return the end date of the weekly schedule
     */
    public GregorianCalendar getEndTime() {
        return endTime;
    }

    /**
     * Returns the last time individual rides/drives/parks were scheduled based on this weekly schedule.
     * @return the last time individual rides/drives/parks were scheduled based on this weekly schedule
     */
    public GregorianCalendar getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets start date of weekly schedule.
     * @param startTime
     */
    public void setStartTime(GregorianCalendar startTime) {
        this.startTime = startTime;
    }

    /**
     * Sets end date of weekly schedule.
     * @param endTime
     */
    public void setEndTime(GregorianCalendar endTime) {
        this.endTime = endTime;
    }

    /**
     * Sets the last time individual rides/drives/parks were scheduled based on this weekly schedule.
     * @param lastUpdate the last time individual rides/drives/parks were scheduled based on this weekly schedule
     */
    public void setLastUpdate(GregorianCalendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Clears weekly schedule and cancels individually scheduled rides/drives/parks
     * STUB
     */
    public void remove() {
        //todo
    }
    
    /**
     * Get location that member will leave to/arrive from central location.
     * @return location that member will leave to/arrive from central location
     */
    public Location getPickupLocation() {
        return pickupLocation;
    }
    
    /**
     * Set location that member will leave to/arrive from central location.
     * @param pickupLocation location that member will leave to/arrive from central location
     */
    public void setPickupLocation(Location pickupLocation) {
        this.pickupLocation = pickupLocation;
    }
    
}

