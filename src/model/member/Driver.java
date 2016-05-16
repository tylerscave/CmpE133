package model.member;

import model.payment.PayFlatCalculator;
import model.payment.RewardCalculator;

/**
 * Class for holding driver information.
 * @author David Lerner
 */
public class Driver implements DrivingType {
    public static final double HOURLY_RATE = 12.50;
    public static final double PER_MILE_RATE = 2.99;
    public static final double FLAT_RATE = 25;

    private Vehicle vehicle;
    private double hourlyRate;
    private double perMileRate;
    private double flatrate;
    private RewardCalculator payBy;
        
    public Driver() {
        this.hourlyRate = 0;
        this.perMileRate = 0;
        this.flatrate = 0;
        payBy = new PayFlatCalculator();
        vehicle = new Vehicle();
    }

    public Driver(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.hourlyRate = 0;
        this.perMileRate = 0;
        this.flatrate = 0;
        payBy = new PayFlatCalculator();
    }

    public Vehicle getVehicle() {
        return vehicle;
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

    @Override
    public boolean isDriver() {
        return true;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public RewardCalculator getPayBy() {
        return payBy;
    }

    public void setPayBy(RewardCalculator payBy) {
        this.payBy = payBy;
    }
    
}
