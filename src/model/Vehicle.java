package model;
/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The model for the vehicle objects.  
 * Solves CmpE133 Carpool System
 * @author Tyler Jones
*/

public class Vehicle {
	
    public enum VehicleStyle {
        Sedan,
        TwoDoor,
        Wagon,
        SUV,
        Van,
        Truck;
    }
    private int year;
    private String make;
    private String model;
    private String color;
	private String plateNumber;
	private VehicleStyle style;
	private int capacity;
	
	public Vehicle(int year, String make, String model, String color,String plateNumber, VehicleStyle style, int capacity){
		this.year = year;
		this.make = make;
		this.model = model;
		this.color = color;
		this.plateNumber = plateNumber;
		this.style = style;
		this.capacity = capacity;
	}
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public String getMake() {
		return make;
	}
	
	public void setMake(String make) {
		this.make = make;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getPlateNumber() {
		return plateNumber;
	}
	
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	
	public VehicleStyle getStyle() {
		return style;
	}
	
	public void setStyle(VehicleStyle style) {
		this.style = style;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}	
}
