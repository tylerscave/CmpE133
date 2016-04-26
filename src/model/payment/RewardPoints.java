package model.payment;

import model.Member;
import model.schedule.Ride;

/**
 *
 * @author David
 */
public class RewardPoints extends Reward {

    public RewardPoints(RewardCalculator rewardCalculator) {
        super(rewardCalculator);
    }

    @Override
    public Object findReward(Member recipient, Ride ride) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean resolveReward(Member recipient, Ride ride, Object compensation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
