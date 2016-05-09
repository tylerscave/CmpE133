package model.payment;

import java.util.ArrayList;
import java.util.List;
import model.Context;
import model.member.Member;
import model.schedule.Ride;

public abstract class Reward {
    
    protected RewardCalculator rewardCalculator;
    
    public Reward(RewardCalculator rewardCalculator) {
        this.rewardCalculator = rewardCalculator;
    }
    
    public boolean payReward(Member recipient, Ride ride, Object compensation) {
        if (resolveReward(recipient, ride, compensation)) {
            Member member = Context.getInstance().getMember();
            for (Ride r : member.getRides()) {
                if (r.getIdNumber() == ride.getIdNumber()) {
                    r.getRideStatus().pay();
                    break;
                }
            }
            
            member.setChanged();
            member.notifyObservers();
            return true;
        }
        return false;
    }
    
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
    
    public abstract Object findReward(Member recipient, Ride ride);
    
    public abstract boolean resolveReward(Member recipient, Ride ride, Object compensation);
}
