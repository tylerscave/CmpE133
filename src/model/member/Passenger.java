package model.member;

public class Passenger implements DrivingType {
    public Passenger() {
    }
    
    @Override
    public boolean isDriver() {
        return false;
    }
}
