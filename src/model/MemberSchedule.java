package model;
/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The model for the member schedule
 * Solves CmpE133 Assignment 2
 * @author Tyler Jones,
*/

import java.util.GregorianCalendar;
import java.util.Locale;

public class MemberSchedule {
	//declare all variables for member schedule
	private Location pickupLocation;
	private GregorianCalendar monArrive, tuesArrive, wedArrive, thursArrive, friArrive;
	private GregorianCalendar monDepart, tuesDepart, wedDepart, thursDepart, friDepart;
	private boolean monDrive = false; 
	private boolean tuesDrive = false;
	private boolean wedDrive = false; 
	private boolean thursDrive = false;
	private boolean friDrive = false;
	
	//using default constructor for now...
	
	public Location getPickupLocation() {
		return pickupLocation;
	}
	
	public void setPickupLocation(Location pickupLocation) {
		this.pickupLocation = pickupLocation;
	}
//*********************************MONDAY*************************************
	
	public GregorianCalendar getMonArrive() {
		return monArrive;
	}
	
	public void setMonArrive(GregorianCalendar monArrive) {
		this.monArrive = monArrive;
	}
	
	public GregorianCalendar getmonDepart() {
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
		
		
		//test - remove later
		System.out.println("pickup loc = " + pickupLocation);
		
		System.out.println("mon arrive = " + monArrive + " " +
				monArrive.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
				GregorianCalendar.LONG, Locale.getDefault()));
		System.out.println("mon depart = " + monDepart + " " +
				monDepart.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
				GregorianCalendar.LONG, Locale.getDefault()));
		System.out.println("tues arrive = " + tuesArrive + " " +
				tuesArrive.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
				GregorianCalendar.LONG, Locale.getDefault()));
		System.out.println("tues depart = " + tuesDepart + " " +
				tuesDepart.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
				GregorianCalendar.LONG, Locale.getDefault()));
		System.out.println("wed arrive = " + wedArrive + " " +
				wedArrive.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
				GregorianCalendar.LONG, Locale.getDefault()));
		System.out.println("wed depart = " + wedDepart + " " +
				wedDepart.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
				GregorianCalendar.LONG, Locale.getDefault()));
		System.out.println("thurs arrive = " + thursArrive + " " +
				thursArrive.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
				GregorianCalendar.LONG, Locale.getDefault()));
		System.out.println("thurs depart = " + thursDepart + " " +
				thursDepart.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
				GregorianCalendar.LONG, Locale.getDefault()));
		System.out.println("fri arrive = " + friArrive + " " +
				friArrive.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
				GregorianCalendar.LONG, Locale.getDefault()));
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

