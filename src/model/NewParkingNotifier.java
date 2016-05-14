package model;

import model.member.Member;
import model.schedule.Location;

/**
 * Simulation of a parking notifier. An empty string is always returned. 
 * @author David Lerner
 */
public class NewParkingNotifier extends ParkingNotifier{

    @Override
    public String[] getParkingMessages(Location location, Member member) {
        //stub
        return new String[0];
    }
    
}
