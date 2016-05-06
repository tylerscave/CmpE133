package model.member;


public class Driver implements DrivingType {
	private String DriverLicenseNumber;
	private Vehicle vehicle;
	private Address departureLocation;
        private double hourlyRate;
        private double perMileRate;
        private double flatrate;
        
    public Driver() {
    }

    public Driver(String DriverLicenseNumber, Vehicle vehicle, Address departureLocation) {
        this.DriverLicenseNumber = DriverLicenseNumber;
        this.vehicle = vehicle;
        this.departureLocation = departureLocation;
        this.hourlyRate = 0;
        this.perMileRate = 0;
        this.flatrate = 0;
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

    public double getPerMileRate() {
        return perMileRate;
    }

    public void setPerMileRate(double perMileRate) {
        this.perMileRate = perMileRate;
    }

    public double getFlatrate() {
        return flatrate;
    }

    public void setFlatrate(double flatrate) {
        this.flatrate = flatrate;
    }

    public boolean isDriver() {
        return true;
    }

    public void setDepartureLocation(Address departureLocation) {
        this.departureLocation = departureLocation;
    }

    public void setDriverLicenseNumber(String DriverLicenseNumber) {
        this.DriverLicenseNumber = DriverLicenseNumber;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Address getDepartureLocation() {
        return departureLocation;
    }
    
}
