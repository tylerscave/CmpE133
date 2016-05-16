package model.payment;

import model.Context;
import model.member.Member;
import model.schedule.Ride;

/**
 * Class representing bank account payments.
 * @author David Lerner
 */
public class BankAccount extends Reward{
	
    private double payment;
    private BankAccountInfo info;

    public BankAccount(BankAccountInfo info, RewardCalculator rewardCalculator) {
        super(rewardCalculator);
        this.payment = 0;
        this.info = info;
    }

    @Override
    public Object findReward(Member recipient, Ride ride) {
        payment = (Double) rewardCalculator.calculateReward(recipient, ride);
        return payment;
    }

    @Override
    public boolean resolveReward(Member recipient, Ride ride, Object compensation) {
    if (Context.getInstance().getBankHandler().makePayment(info, payment))
            return rewardCalculator.payReward(recipient, ride, compensation);
        return false;
    }
	
	
}
