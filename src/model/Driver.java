package model;

public class Driver extends MemberType {
	private String DriverLicenseNumber;
	private Vehicle vehicle;
	private Address departureLocation;
        private double hourlyRate;

    public Driver() {
    }

    public Driver(String DriverLicenseNumber, Vehicle vehicle, Address departureLocation) {
        this.DriverLicenseNumber = DriverLicenseNumber;
        this.vehicle = vehicle;
        this.departureLocation = departureLocation;
        this.hourlyRate = 0;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getDriverLicenseNumber() {
        return DriverLicenseNumber;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
        
}
