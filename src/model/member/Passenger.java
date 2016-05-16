package model.member;

/**
 * Class for holding passenger information.
 * @author David Lerner
 */
public class Passenger implements DrivingType {
    
    @Override
    public boolean isDriver() {
        return false;
    }
}
