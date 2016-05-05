package model.payment;

import model.member.Member;
import model.schedule.Ride;

public class CreditCard extends Reward {
    
    private CreditCardInfo info;
    private double payment;
    
    public CreditCard(CreditCardInfo info, RewardCalculator rewardCalculator) {
        super(rewardCalculator);
        this.info = info;
        payment = 0;
    }

    @Override
    public Object findReward(Member recipient, Ride ride) {
        payment = (Double) rewardCalculator.calculateReward(recipient, ride);
        return payment;
    }

    @Override
    public boolean resolveReward(Member recipient, Ride ride, Object compensation) {
        
        if (CreditCardHandler.makePayment(info, payment))
            return rewardCalculator.payReward(recipient, ride, compensation);
        return false;
    }
}
