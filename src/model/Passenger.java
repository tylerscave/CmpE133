package model;

public class Passenger extends MemberType {
	private Address location;
	private PaymentMethod payMethod;

    public Passenger(Address location, PaymentMethod payMethod) {
        this.location = location;
        this.payMethod = payMethod;
    }

    public Passenger() {
    }
        
}
