package model.payment;

import java.util.ArrayList;
import java.util.List;
import model.Context;
import model.member.Member;
import model.schedule.Ride;

/**
 * Class representing a payment type. 
 * Amount to pay has a separate implementation using bridge pattern.
 * @author David Lerner
 */
public abstract class Reward {
    
    protected RewardCalculator rewardCalculator;
    
    /**
     * Constructor. Must pass in a reward calculator to compute how much to pay.
     * @param rewardCalculator
     */
    public Reward(RewardCalculator rewardCalculator) {
        this.rewardCalculator = rewardCalculator;
    }
    
    /**
     * Make the payment, then update the status.
     * @param recipient the one receiving the payment
     * @param ride the ride to pay for
     * @param compensation the payment amount
     * @return whether payment was successful
     */
    public boolean payReward(Member recipient, Ride ride, Object compensation) {
        if (resolveReward(recipient, ride, compensation)) {
            Member member = Context.getInstance().getMember();
            for (Ride r : member.getRides()) {
                if (r.getIdNumber() == ride.getIdNumber()) {
                    r.getRideStatus().pay();
                    break;
                }
            }
            //update status
            member.setChanged();
            member.notifyObservers();
            return true;
        }
        return false;
    }
    
    /**
     * Waive a required payment.
     * @param ride the ride which will have its payment waived
     */
    public void waiveReward(Ride ride) {
        Member member = Context.getInstance().getMember();
        Member m = Context.getInstance().getDataHandler().getMember(ride.getMemberId());
        for (Ride r : m.getRides()) {
            if (r.getIdNumber() == ride.getIdNumber()) {
                r.getRideStatus().pay();
                break;
            }
        }
        
        List<Member> changed = new ArrayList<>();
        changed.add(m);
        member.setChanged();
        member.notifyObservers(changed);
    }
    
    /**
     * Find how much is due.
     * @param recipient the one receiving the payment
     * @param ride the ride to pay for
     * @return how much is due
     */
    public abstract Object findReward(Member recipient, Ride ride);
    
    /**
     * Make the payment.
     * @param recipient the one receiving the payment
     * @param ride the ride to pay for
     * @param compensation the payment amount
     * @return whether payment was successful
     */
    public abstract boolean resolveReward(Member recipient, Ride ride, Object compensation);
}
