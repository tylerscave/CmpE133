package model.payment;

import java.util.InputMismatchException;
import model.member.Driver;
import model.member.Member;
import model.schedule.Ride;

/**
 * Class for paying a flat fee.
 * @author David Lerner
 */
public class PayFlatCalculator implements RewardCalculator{

    @Override
    public Object calculateReward(Member recipient, Ride ride) {
        if (recipient == null || ride == null)
            throw new InputMismatchException();
        if (recipient.getDrivingType() instanceof Driver) {
            Driver driver = (Driver) recipient.getDrivingType();
            return driver.getFlatrate();
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
