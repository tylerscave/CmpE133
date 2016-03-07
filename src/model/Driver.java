package model;

public class Driver extends MemberType {
	private String DriverLicenseNumber;
	private Vehicle vehicle;
	private Address departureLocation;

    public Driver() {
    }

    public Driver(String DriverLicenseNumber, Vehicle vehicle, Address departureLocation) {
        this.DriverLicenseNumber = DriverLicenseNumber;
        this.vehicle = vehicle;
        this.departureLocation = departureLocation;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getDriverLicenseNumber() {
        return DriverLicenseNumber;
    }
        
}
