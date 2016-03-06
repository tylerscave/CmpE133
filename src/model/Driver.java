package model;

public class Driver extends MemberType {
	private String DriverLicenseNumber;
	private Vehicle vehicle;
	private Address departureLocation;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getDriverLicenseNumber() {
        return DriverLicenseNumber;
    }
        
}
