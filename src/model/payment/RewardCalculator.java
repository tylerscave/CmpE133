package model.payment;

import model.member.Member;
import model.schedule.Ride;

/**
 * Class for calculating payment due.
 * @author David Lerner
 */
public interface RewardCalculator {
    
    /**
     * Calculates what is owed to the recipient
     * @param recipient the one to be rewarded
     * @param ride the ride to be compensated for
     * @return an object that represents the required compensation to be awarded
     */
    public Object calculateReward(Member recipient, Ride ride);
    
    /**
     * Tries to compensate for the required award. Returns whether it is successful
     * @param recipient the one to be rewarded
     * @param ride the ride to be compensated for
     * @param payment represents the compensation
     * @return whether the reward was paid
     */
    public boolean payReward(Member recipient, Ride ride, Object payment);
}
