package model.member;

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
    private String manufacturer;
    private String model;
    private String color;
    private String plateNumber;
    private VehicleStyle style;
    private int capacity;
    
    public Vehicle(){
        this.year = 2000;
        this.manufacturer = "";
        this.model = "";
        this.color = "";
        this.plateNumber = "";
        this.style = VehicleStyle.Sedan;
        this.capacity = 4;
    }
    
    public Vehicle(int year, String manufacturer, String model, String color,String plateNumber, VehicleStyle style, int capacity){
        this.year = year;
        this.manufacturer = manufacturer;
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
    
    public String getManufacturer() {
        return manufacturer;
    }
    
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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
