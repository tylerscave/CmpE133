package model;

import model.payment.Reward;

public class Passenger extends MemberType {
	private Address location;
	private Reward payMethod;

    public Passenger(Address location, Reward payMethod) {
        this.location = location;
        this.payMethod = payMethod;
    }

    public Passenger() {
    }
        
}
