package model.member;

import model.member.DrivingType;
import model.member.Address;
import model.payment.Reward;

public class Passenger implements DrivingType {
	private Address location;
	private Reward payMethod;

    public Passenger(Address location, Reward payMethod) {
        this.location = location;
        this.payMethod = payMethod;
    }

    public Passenger() {
    }
    
    @Override
    public boolean isDriver() {
        return false;
    }
}
