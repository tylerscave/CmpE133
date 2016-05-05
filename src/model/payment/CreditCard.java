package model.payment;

import model.member.Member;
import model.schedule.Ride;

public class CreditCard extends Reward {
    
    private CreditCardInfo info;
    private double balance;
    
    public CreditCard(CreditCardInfo info, RewardCalculator rewardCalculator) {
        super(rewardCalculator);
        this.info = info;
        balance = 0;
    }

    @Override
    public Object findReward(Member recipient, Ride ride) {
        balance = (Double) rewardCalculator.calculateReward(recipient, ride);
        return balance;
    }

    @Override
    public boolean resolveReward(Member recipient, Ride ride, Object compensation) {
        
        if (CreditCardHandler.makePayment(info, balance))
            return rewardCalculator.payReward(recipient, ride, compensation);
        return false;
    }
}
