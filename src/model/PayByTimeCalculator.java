package model;

import java.util.InputMismatchException;

/**
 *
 * @author David
 */
public class PayByTimeCalculator implements RewardCalculator{

    @Override
    public Object calculateReward(Member recipient, Ride ride) {
        if (recipient == null || ride == null)
            throw new InputMismatchException();
        if (recipient.getMemberType() instanceof Driver) {
            Driver driver = (Driver) recipient.getMemberType();
            return driver.getHourlyRate()*ride.getRoute().getTravelTime()/60;
        }
        throw new InputMismatchException();
    }

    @Override
    public boolean payReward(Member recipient, Ride ride, Object payment) {
        Double required = (Double) calculateReward(recipient, ride);
        if (payment instanceof Double) {
            Double paid = (Double) payment;
            if (paid >= required)
                return true;
        }
        return false;
    }
    
}
