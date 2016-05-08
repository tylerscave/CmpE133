package model.schedule;
/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The model for the member weekly schedule
 * Solves CmpE133 Assignment 2
 * @author Tyler Jones,
*/

import java.util.GregorianCalendar;
import java.util.Locale;

public class WeeklySchedule{
    //new    
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

    public boolean isSet() {
        return (pickupLocation != null && startTime != null && endTime != null);
    }
    
    /**
     *
     * @param dayOfWeek use the weekday constants from the Calendar class
     * @return
     */
    public boolean isValid(int dayOfWeek) {
        return (arrive[dayOfWeek-OFFSET] != null && depart[dayOfWeek-OFFSET] != null);
    }
    
     /**
     *
     * @param dayOfWeek use the weekday constants from the Calendar class
     * @return
     */
    public boolean isDrive(int dayOfWeek) {
        return drive[dayOfWeek-OFFSET];
    }

    /**
     *
     * @param dayOfWeek use the weekday constants from the Calendar class
     * @param isDrive
     */
    public void setDrive(int dayOfWeek, boolean isDrive) {
        drive[dayOfWeek-OFFSET] = isDrive;
    }

    /**
     *
     * @param dayOfWeek use the weekday constants from the Calendar class
     * @return
     */
    public GregorianCalendar getArrive(int dayOfWeek) {
        return arrive[dayOfWeek-OFFSET];
    }

    /**
     *
     * @param dayOfWeek use the weekday constants from the Calendar class
     * @param arrive
     */
    public void setArrive(int dayOfWeek, GregorianCalendar arrive) {
        this.arrive[dayOfWeek-OFFSET] = arrive;
    }
    
    /**
     *
     * @param dayOfWeek use the weekday constants from the Calendar class
     * @return
     */
    public GregorianCalendar getDepart(int dayOfWeek) {
        return depart[dayOfWeek-OFFSET];
    }

    /**
     *
     * @param dayOfWeek use the weekday constants from the Calendar class
     * @param depart
     */
    public void setDepart(int dayOfWeek, GregorianCalendar depart) {
        this.depart[dayOfWeek-OFFSET] = depart;
    }
    
    public GregorianCalendar getStartTime() {
        return startTime;
    }

    public GregorianCalendar getEndTime() {
        return endTime;
    }

    public GregorianCalendar getLastUpdate() {
        return lastUpdate;
    }

    public void setStartTime(GregorianCalendar startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(GregorianCalendar endTime) {
        this.endTime = endTime;
    }

    public void setLastUpdate(GregorianCalendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void remove() {
        //todo
    }
    public Location getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Location pickupLocation) {
        this.pickupLocation = pickupLocation;
    }
    
    //rest of code is legacy
        
        //declare all variables for member schedule
	private GregorianCalendar monArrive, tuesArrive, wedArrive, thursArrive, friArrive;
	private GregorianCalendar monDepart, tuesDepart, wedDepart, thursDepart, friDepart;
	private boolean monDrive = false; 
	private boolean tuesDrive = false;
	private boolean wedDrive = false; 
	private boolean thursDrive = false;
	private boolean friDrive = false;
        
	//using default constructor for now...

//*********************************MONDAY*************************************
	
	public GregorianCalendar getMonArrive() {
		return monArrive;
	}
	
	public void setMonArrive(GregorianCalendar monArrive) {
		this.monArrive = monArrive;
	}
	
	public GregorianCalendar getMonDepart() {
		return monDepart;
	}
	
	public void setMonDepart(GregorianCalendar monDepart) {
		this.monDepart = monDepart;
	}
	
	public boolean getMonDrive() {
		return monDrive;
	}
	
	public void setMonDrive(boolean drive) {
		this.monDrive = drive;
	}
//*********************************TUESDAY*************************************
	
	public GregorianCalendar getTuesArrive() {
		return tuesArrive;
	}
	
	public void setTuesArrive(GregorianCalendar tuesArrive) {
		this.tuesArrive = tuesArrive;
	}
	
	public GregorianCalendar getTuesDepart() {
		return tuesDepart;
	}
	
	public void setTuesDepart(GregorianCalendar tuesDepart) {
		this.tuesDepart = tuesDepart;
	}
	
	public boolean getTuesDrive() {
		return tuesDrive;
	}
	
	public void setTuesDrive(boolean drive) {
		this.tuesDrive = drive;
	}
//*********************************WEDNESDAY***********************************
	
	public GregorianCalendar getWedArrive() {
		return wedArrive;
	}
	
	public void setWedArrive(GregorianCalendar wedArrive) {
		this.wedArrive = wedArrive;
	}
	
	public GregorianCalendar getWedDepart() {
		return wedDepart;
	}
	
	public void setWedDepart(GregorianCalendar wedDepart) {
		this.wedDepart = wedDepart;
	}
	
	public boolean getWedDrive() {
		return wedDrive;
	}
	
	public void setWedDrive(boolean drive) {
		this.wedDrive = drive;
	}
//*********************************THURSDAY***********************************

	public GregorianCalendar getThursArrive() {
		return thursArrive;
	}
	
	public void setThursArrive(GregorianCalendar thursArrive) {
		this.thursArrive = thursArrive;
	}
	
	public GregorianCalendar getThursDepart() {
		return thursDepart;
	}
	
	public void setThursDepart(GregorianCalendar thursDepart) {
		this.thursDepart = thursDepart;
	}
	
	public boolean getThursDrive() {
		return thursDrive;
	}
	
	public void setThursDrive(boolean drive) {
		this.thursDrive = drive;
	}
//*********************************FRIDAY*************************************
	
	public GregorianCalendar getFriArrive() {
		return friArrive;
	}
	
	public void setFriArrive(GregorianCalendar friArrive) {
		this.friArrive = friArrive;
	}
	
	public GregorianCalendar getFriDepart() {
		return friDepart;
	}
	
	public void setFriDepart(GregorianCalendar friDepart) {
		this.friDepart = friDepart;
	}
	
	public boolean getFriDrive() {
		return friDrive;
	}
	
	public void setFriDrive(boolean drive) {
		this.friDrive = drive;
	}
	
	//TEST METHOD TO SEE WHATS BEING SAVED --- DELETE LATER
	public void test() {
		//test - remove later
		if(pickupLocation!=null)
		System.out.println("pickup loc = " + pickupLocation);
		
		if(monArrive != null)
		System.out.println("mon arrive = " + monArrive + " " +
				monArrive.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
				GregorianCalendar.LONG, Locale.getDefault()));
		if(monDepart != null)
		System.out.println("mon depart = " + monDepart + " " +
				monDepart.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
				GregorianCalendar.LONG, Locale.getDefault()));
		if(tuesArrive != null)
		System.out.println("tues arrive = " + tuesArrive + " " +
				tuesArrive.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
				GregorianCalendar.LONG, Locale.getDefault()));
		if(tuesDepart != null)
		System.out.println("tues depart = " + tuesDepart + " " +
				tuesDepart.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
				GregorianCalendar.LONG, Locale.getDefault()));
		if(wedArrive != null)
		System.out.println("wed arrive = " + wedArrive + " " +
				wedArrive.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
				GregorianCalendar.LONG, Locale.getDefault()));
		if(wedDepart != null)
		System.out.println("wed depart = " + wedDepart + " " +
				wedDepart.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
				GregorianCalendar.LONG, Locale.getDefault()));
		if(thursArrive != null)
		System.out.println("thurs arrive = " + thursArrive + " " +
				thursArrive.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
				GregorianCalendar.LONG, Locale.getDefault()));
		if(thursDepart != null)
		System.out.println("thurs depart = " + thursDepart + " " +
				thursDepart.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
				GregorianCalendar.LONG, Locale.getDefault()));
		if(friArrive != null)
		System.out.println("fri arrive = " + friArrive + " " +
				friArrive.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
				GregorianCalendar.LONG, Locale.getDefault()));
		if(friDepart != null)
		System.out.println("fri depart = " + friDepart + " " +
				friDepart.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
				GregorianCalendar.LONG, Locale.getDefault()));
		
		System.out.println("monDriveCheck = " + monDrive);
		System.out.println("tuesDriveCheck = " + tuesDrive);
		System.out.println("wedDriveCheck = " + wedDrive);
		System.out.println("thursDriveCheck = " + thursDrive);
		System.out.println("friDriveCheck = " + friDrive);	
	}

}

