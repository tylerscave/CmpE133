package model;
/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The model for the member schedule
 * Solves CmpE133 Assignment 2
 * @author Tyler Jones,
*/
public class MemberSchedule {
	//declare all variables for member schedule
	private PickupLocation pickupLocation;
	private String monArrive, tuesArrive, wedArrive, thursArrive, friArrive;
	private String monDepart, tuesDepart, wedDepart, thursDepart, friDepart;
	private boolean monDrive, tuesDrive, wedDrive, thursDrive, friDrive;
	
	//using default constructor for now...
	
	public PickupLocation getPickupLocation() {
		return pickupLocation;
	}
	
	public void setPickupLocation(PickupLocation pickupLocation) {
		this.pickupLocation = pickupLocation;
	}
//*********************************MONDAY*************************************
	
	public String getMonArrive() {
		return monArrive;
	}
	
	public void setMonArrive(String monArrive) {
		this.monArrive = monArrive;
		System.out.println("set mondar arrive success");
	}
	
	public String getmonDepart() {
		return monDepart;
	}
	
	public void setMonDepart(String monDepart) {
		this.monDepart = monDepart;
	}
	
	public boolean getMonDrive() {
		return monDrive;
	}
	
	public void setMonDrive(boolean drive) {
		this.monDrive = drive;
	}
//*********************************TUESDAY*************************************
	
	public String getTuesArrive() {
		return tuesArrive;
	}
	
	public void setTuesArrive(String tuesArrive) {
		this.tuesArrive = tuesArrive;
	}
	
	public String getTuesDepart() {
		return tuesDepart;
	}
	
	public void setTuesDepart(String tuesDepart) {
		this.tuesDepart = tuesDepart;
	}
	
	public boolean getTuesDrive() {
		return tuesDrive;
	}
	
	public void setTuesDrive(boolean drive) {
		this.tuesDrive = drive;
	}
//*********************************WEDNESDAY***********************************
	
	public String getWedArrive() {
		return wedArrive;
	}
	
	public void setWedArrive(String wedArrive) {
		this.wedArrive = wedArrive;
	}
	
	public String getWedDepart() {
		return wedDepart;
	}
	
	public void setWedDepart(String wedDepart) {
		this.wedDepart = wedDepart;
	}
	
	public boolean getWedDrive() {
		return wedDrive;
	}
	
	public void setWedDrive(boolean drive) {
		this.wedDrive = drive;
	}
//*********************************THURSDAY***********************************

	public String getThursArrive() {
		return thursArrive;
	}
	
	public void setThursArrive(String thursArrive) {
		this.thursArrive = thursArrive;
	}
	
	public String getThursDepart() {
		return thursDepart;
	}
	
	public void setThursDepart(String thursDepart) {
		this.thursDepart = thursDepart;
	}
	
	public boolean getThursDrive() {
		return thursDrive;
	}
	
	public void setThursDrive(boolean drive) {
		this.thursDrive = drive;
	}
//*********************************FRIDAY*************************************
	
	public String getFriArrive() {
		return friArrive;
	}
	
	public void setFriArrive(String friArrive) {
		this.friArrive = friArrive;
	}
	
	public String getFriDepart() {
		return friDepart;
	}
	
	public void setFriDepart(String friDepart) {
		this.friDepart = friDepart;
	}
	
	public boolean getFriDrive() {
		return friDrive;
	}
	
	public void setFriDrive(boolean drive) {
		this.friDrive = drive;
	}

}

