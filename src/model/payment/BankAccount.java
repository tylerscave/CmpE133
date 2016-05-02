package model.payment;

import model.Member;
import model.payment.RewardCalculator;
import model.payment.Reward;
import model.schedule.Ride;

public class BankAccount extends Reward{
	
	private String nameOnAccount;
	private String bank;
	private String accountNumber;
	private String routingNumber;
        private double payment;

    public BankAccount(String nameOnAccount, String bank, String accountNumber, String routingNumber, RewardCalculator rewardCalculator) {
        super(rewardCalculator);
        this.nameOnAccount = nameOnAccount;
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
        payment = 0;
    }

    @Override
    public Object findReward(Member recipient, Ride ride) {
        payment = (Double) rewardCalculator.calculateReward(recipient, ride);
        return payment;
    }

    @Override
    public boolean resolveReward(Member recipient, Ride ride, Object compensation) {
        //stub
        throw new UnsupportedOperationException("Not supported yet."); 
    }
	
	
}
