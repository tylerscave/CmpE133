package model;

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
        //stub
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean resolveReward(Member recipient, Ride ride, Object compensation) {
        //stub
        throw new UnsupportedOperationException("Not supported yet."); 
    }
	
	
}
